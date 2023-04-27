package com.zl.utils;

import org.apache.commons.lang3.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
*
* @return 日期时间处理工具类
* @author ZHD-xinwei.Fan
* @creed: 请描述这个类的作用...
* @date 2022/10/26 10:14
*/


public class DateConvertUtils {

	static String[] patternArrays = new String[] { 
			"yyyy-MM-dd HH:mm:ss", 
			"yyyy-MM-dd HH:mm", 
			"yyyyMMddHHmmss", 
			"yyyy-MM-dd",
			"yyyy/MM/dd",
			"yyyy-MM",
			"yyyyMM",
			"yyyy/MM",
			"dd/MM/yyyy" };
	private static final Map<String, ThreadLocal<SimpleDateFormat>> allPattern  = new ConcurrentHashMap<String, ThreadLocal<SimpleDateFormat>>();
	static {
		for (final String pattern : patternArrays) {
			allPattern.put(pattern, new ThreadLocal<SimpleDateFormat>() {
				@Override
				protected SimpleDateFormat initialValue() {
					return new SimpleDateFormat(pattern);
				}
			});
		}
	}
	public static Date convert(String dateString) {
		if (dateString != null && !"".equals(dateString.trim())) {
			if (dateString.matches("^\\d{4}-\\d{2}$")) {
				return parse(dateString, "yyyy-MM");
			} else if (dateString.matches("^\\d{6}}$")) {
				return parse(dateString, "yyyyMM");
			} else if (dateString.matches("^\\d{4}/\\d{2}$")) {
				return parse(dateString, "yyyy/MM");
			}
			// 遍历日期支持格式，进行转换
			for (String dateFormat : patternArrays) {
				try {
					Date date = parse(dateString,dateFormat);
					return date;
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

	public static Date parse(String dateString, String dateFormat) {
		return parse(dateString, dateFormat, Date.class);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Date> T parse(String dateString, String dateFormat, Class<T> targetResultType) {
		if (StringUtils.isEmpty(dateString))
			return null;
		//DateFormat df = new SimpleDateFormat(dateFormat);
		try {
			Date date = allPattern.get(dateFormat).get().parse(dateString);
			long time = date.getTime();
			Date t = targetResultType.getConstructor(long.class).newInstance(time);
			return (T) t;
		} catch (ParseException e) {
			String errorInfo = "cannot use dateformat:" + dateFormat + " parse datestring:" + dateString;
			throw new IllegalArgumentException(errorInfo, e);
		} catch (Exception e) {
			throw new IllegalArgumentException("error targetResultType:" + targetResultType.getName(), e);
		}
	}

	public static String format(Date date, String dateFormat) {
		if (date == null)
			return null;
		return new SimpleDateFormat(dateFormat).format(date);
	}
	
	
}
