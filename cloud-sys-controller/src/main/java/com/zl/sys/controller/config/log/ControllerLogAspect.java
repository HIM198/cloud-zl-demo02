package com.zl.sys.controller.config.log;

import com.alibaba.fastjson.JSON;
import com.zl.sys.entity.SLogOperatorall;
import com.zl.sys.service.SLogOperatorallService;
import com.zl.utils.DateUtils;
import com.zl.utils.RequestUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@SuppressWarnings("all")
@Aspect
@Component
public class ControllerLogAspect {
	private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);
	@Resource
	protected SLogOperatorallService operatorLogAllService;
	@Resource
	protected AppLogsConfiguration appLogsConfiguration;

	private static ThreadLocal<SLogOperatorall> tlocal = new ThreadLocal<SLogOperatorall>();

	@Pointcut("(@annotation(org.springframework.web.bind.annotation.RequestMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping)) && !@annotation(com.zl.sys.controller.config.log.SystemControllerNoLog)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		//String uri = request.getRequestURI();
		String uri = request.getRequestURI().replaceFirst(request.getContextPath(), "");
		if (appLogsConfiguration.getIgnoringUrls().indexOf(uri + ";") >= 0) {
			tlocal.set(null);
			return;
		}

		if (StringUtils.isNotEmpty(request.getRequestURL())){
			String url = request.getRequestURL().toString();
//			System.out.println(request.getSession());
//			String sessionId = request.getSession().getId();
			String ip = getIpAddr(request);
			String method = request.getMethod();
			// 请求的IP
			String params = "";
			if ("POST".equals(method)) {
				Object[] paramsArray = joinPoint.getArgs();
				params = argsArrayToString(paramsArray);
			} else {
				Map<?, ?> paramsMap = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
				params = paramsMap.toString();
			}
			try {
				SLogOperatorall log = new SLogOperatorall();
				// log = getControllerMethodDescription(joinPoint, log);
				log.setUrl(url);
				log.setUri(uri);
				log.setMethod(method);
				log.setBeanName(joinPoint.getTarget().getClass().getName());
				log.setBeanMethod(joinPoint.getSignature().getName() + "");
				log.setRequestId(ip);
				log.setExceptionCode(null);
				log.setExceptionDetail(null);
				log.setParams(params);
				log.setBeginTime(DateUtils.getISODateTime(new Date()));
				log.setRequestTime(System.currentTimeMillis());
//				log.setSessionId(sessionId);
				log.setCreateBy("admin");
				log.setCreateDate(DateUtils.getDateTime());
				log.setOsInfo(RequestUtil.getOsInfo(request));
				log.setBrowserInfo(RequestUtil.getBrowserInfo(request));
				log.setRequestParams(JSON.toJSON(request.getParameterMap()).toString());
				tlocal.set(log);
			} catch (Exception e) {
				logger.error("==前置通知异常==");
				logger.error("异常信息:{}", e.getMessage());
			}
		}

	}

	@AfterReturning(returning = "result", pointcut = "controllerAspect()")
	public void doAfterReturning(Object result) {
		try {
			// 处理完请求，返回内容
			SLogOperatorall optLog = tlocal.get();
			if (optLog != null) {
				String resultMsg = ObjectUtils.toString(result, "");
				if (StringUtils.isNotEmpty(resultMsg)){
					String resultString = getResultString(result);
					if (resultString.length()>6000){
						optLog.setResult("结果集长度过大");
					}else {
						optLog.setResult(resultString);
					}
					long beginTime = optLog.getRequestTime();
					long requestTime = (System.currentTimeMillis() - beginTime);
					optLog.setRequestTime(requestTime);
					optLog.setEndTime(DateUtils.getISODateTime(new Date()));
					optLog.setCreateBy("admin");
					logger.info("Uri: " + optLog.getUri() + "请求耗时：" + optLog.getRequestTime());
					operatorLogAllService.save(optLog);
				}
			}

		} catch (Exception e) {
			logger.error("***操作请求日志记录失败doAfterReturning()***", e);
		}
	}

	@AfterThrowing(throwing = "ex", pointcut = "controllerAspect()")
	public void doAfterThrowing(Throwable ex) {
		try {
			// 处理完请求，返回内容
			SLogOperatorall optLog = tlocal.get();
			if (optLog!=null){
				long beginTime = optLog.getRequestTime();
				long requestTime = (System.currentTimeMillis() - beginTime);
				optLog.setRequestTime(requestTime);
				optLog.setExceptionCode("");
				optLog.setExceptionDetail(ex.getMessage());
				optLog.setEndTime(DateUtils.getISODateTime(new Date()));
				optLog.setCreateBy("admin");
				logger.info("Uri: " + optLog.getUri() + "请求异常————耗时：" + optLog.getRequestTime());
				operatorLogAllService.save(optLog);
			}
		} catch (Exception e) {
			logger.error("***操作请求日志记录失败doAfterReturning()***", e);
		}
	}

	/**
	 * 获取登录用户远程主机ip地址
	 * 
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
		return RequestUtil.getIp(request);
	}

	/**
	 * 请求参数拼装
	 * 
	 * @param paramsArray
	 * @return
	 */
	private String argsArrayToString(Object[] paramsArray) {
		String params = "";
		if (paramsArray != null && paramsArray.length > 0) {
			for (int i = 0; i < paramsArray.length; i++) {
				Object obj = paramsArray[i];

				if (obj instanceof ServletRequest) {
					
				} else if (obj instanceof HttpServletResponse) {

				} else if (obj instanceof MultipartFile) {

				} else if (obj instanceof Model) {

				} else if (obj instanceof ModelAndView) {

				} else {
					try {
						if (obj != null && !"".equals(obj)) {
							Object jsonObj = JSON.toJSON(obj);
							params += jsonObj.toString() + ";";
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return params.trim();
	}

	private String getResultString(Object result) {
		if (result == null) {

		}
		if (result instanceof String) {
			return (String) result;
		} else {
			try {
				Object jsonObj = JSON.toJSON(result);
				return jsonObj.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
