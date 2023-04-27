package com.zl.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
*
* @return
* @author ZHD-xinwei.Fan
* @creed: 请描述这个类的作用...
* @date 2022/10/26 10:13
*/


public class RequestUtil {

	public static String getIp(HttpServletRequest request) {
		String ip = getIpString(request);
		return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	public static String getIpString(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	public static boolean isAjax(HttpServletRequest request) {
		return StringUtils.isNotBlank(request.getHeader("X-Requested-With"));
	}

	/**
	 * 获取操作系统,浏览器及浏览器版本信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getOsInfo(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");;
		String os = "";
		// =================OS Info=======================
		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "Windows";
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "Mac";
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix";
		} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "Android";
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		return os;
	}

	/**
	 * 获取操作系统,浏览器及浏览器版本信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getBrowserInfo(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");;
		String user = userAgent.toLowerCase();
		String browser = "";
		// ===============Browser===========================
		if (user.contains("edge")) {
			browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version")) {
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera")) {
				browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			} else if (user.contains("opr")) {
				browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
						.replace("OPR", "Opera");
			}

		} else if (user.contains("chrome")) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)
				|| (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1)
				|| (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
			browser = "Netscape-?";

		} else if (user.contains("firefox")) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("rv")) {
			String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
			browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
		} else {
			browser = "UnKnown, More-Info: " + userAgent;
		}
		return browser;
	}
	public static String getFileNameHasData(HttpServletRequest request, String outfilename)
			throws UnsupportedEncodingException {
		int index = outfilename.lastIndexOf(".");
		if(index>-1) {
			return getFileNameHasData(request,outfilename.substring(0, index),outfilename.substring(index));
		}else {
			String dateStr = DateConvertUtils.format(new Date(),"yyyyMMdd");
			return getFileName(request, outfilename+dateStr);
		}
		
	}
	public static String getFileNameHasData(HttpServletRequest request, String outfilename,String ext)
			throws UnsupportedEncodingException {
		String filename = getFileName(request, outfilename);
		String dateStr = DateConvertUtils.format(new Date(),"yyyyMMdd");
		return filename+dateStr+ext;
	}
	public static String getFileName(HttpServletRequest request, String outfilename)
			throws UnsupportedEncodingException {
		String agent = request.getHeader("USER-AGENT");
		String filename = null;
		if (null != agent) {
			if (-1 != agent.indexOf("MSIE") ||-1 != agent.indexOf("Trident") || -1 != agent.indexOf("Edge")) {
				// ie浏览器及Edge浏览器
				filename = java.net.URLEncoder.encode(outfilename, "UTF-8");
				filename = StringUtils.replace(filename, "+", "%20");// 替换空格
			}else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				// 火狐,Chrome等浏览器
				filename = new String(outfilename.getBytes("UTF-8"), "iso-8859-1");
			}else {// IE7+
				filename = java.net.URLEncoder.encode(outfilename, "UTF-8");
				filename = StringUtils.replace(filename, "+", "%20");// 替换空格
			}
		} else {
			filename = outfilename;
		}
		return filename;
	}

}
