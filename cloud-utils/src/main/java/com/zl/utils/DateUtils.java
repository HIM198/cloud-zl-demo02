package com.zl.utils;

import org.joda.time.format.ISODateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
  * @ClassName: DateUtils
  * @Description: TODO 日期时间处理工具类
  * @author GQ(guoquan913@qq.com)
  * @date 2018年7月13日 下午5:07:05
  *
 */
@SuppressWarnings("all")
public class DateUtils {

	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getNow() {
    	return new Date();
    }
	public static Integer getNowYear() {
    	return Calendar.getInstance().get(Calendar.YEAR);
    }

	public static Integer getMaxDayOrMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DATE, -1);
		return c.get(Calendar.DATE);
	}

	public static Integer getMaxDayOrMonth(Integer year, Integer month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DATE, 1);
		c.add(Calendar.DATE, -1);
		return c.get(Calendar.DATE);
	}

	public static Integer getMaxDayOrMonth(String year, String month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.valueOf(year));
		c.set(Calendar.MONTH, Integer.valueOf(month));
		c.set(Calendar.DATE, 1);
		c.add(Calendar.DATE, -1);
		return c.get(Calendar.DATE);
	}

	public static Date getMaxDateOrMonth(String year, String month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.valueOf(year));
		c.set(Calendar.MONTH, Integer.valueOf(month));
		c.set(Calendar.DATE, 1);
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}

	public static Date getMaxDateOrMonth(Integer year, Integer month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DATE, 1);
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}

	public static Date getMaxDateOrMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}

	public static Date getMinDateOrMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		return c.getTime();
	}

	public static Date getMinDateOrMonth(Integer year, Integer month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, 1);
		return c.getTime();
	}

	public static Date getMinDateOrMonth(String year, String month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.valueOf(year));
		c.set(Calendar.MONTH, Integer.valueOf(month) - 1);
		c.set(Calendar.DATE, 1);
		return c.getTime();
	}

	public static String getCurDateForYmdString() {
		return DateConvertUtils.format(getNow(), "yyyyMMdd");
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	/**
	 * 两个日期比较
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compare_date(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = df.parse(date1);
			Date d2 = df.parse(date2);
			if (d1.getTime() > d2.getTime()) {

				return true;
			} else if (d1.getTime() < d2.getTime()) {

				return false;
			} else {
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return true;

	}
	
	/**
	 * 两个日期比较
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d1 = df.parse(date1);
			Date d2 = df.parse(date2);
			if (d1.getTime() > d2.getTime()) {

				return 1;
			} else if (d1.getTime() < d2.getTime()) {

				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;

	}
	
	

	/**
	 * 获取时间
	 * 
	 * @param Date时间
	 * @return yyyy-MM-dd'T'HH:mm:ss.SSSZ 格式日期字符串
	 */
	public static String getTimestamp() {
		return getISODateTime(new Date());
	}
	public static String getTimestamp(Date date) {
		return getISODateTime(date);
	}
	public static String getISODateTime(Date date) {
		return ISODateTimeFormat.dateTime().print(date.getTime());
	}
	
	/**
	 * 获取时间
	 * @param Date时间
	 * @return yyyy-MM-dd HH:mm:ss 格式日期字符串
	 */
	public static String getDateTime() {
		return getDateTime(new Date());
	}
	public static String getDateTime(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		return time;
	}
	
	/**
	 * 获取当前日期
	 * @return yyyy-MM-dd 格式日期字符串
	 */

	public static String getDate() {
		return getDate(new Date());
	}
	public static String getDate(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String time = format.format(date);
		return time;
	}

	/**
	 * 获取当前月份
	 * @return yyyy-MM 格式日期字符串
	 */
	public static String getMonth() {
		return getMonth(new Date());
	}
	
	
	
	/**
	 * 获取月份
	 * @param Date时间
	 * @return yyyy-MM 格式日期字符串
	 */
	public static String getMonth(Date date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM");
		String time = format.format(date);
		return time;
	}
	
	public static String getNowQuarter(String month,String semi){
		String str = "";
		if(month.equals("01")||month.equals("1")||
		   month.equals("02")||month.equals("2")||	
		   month.equals("03")||month.equals("3")
				){
			str  = semi + "1";
		}else if(month.equals("04")||month.equals("4")||
				   month.equals("05")||month.equals("5")||	
				   month.equals("06")||month.equals("6")
						){
			str  = semi + "2";
		}else if(month.equals("07")||month.equals("7")||
				   month.equals("08")||month.equals("8")||	
				   month.equals("09")||month.equals("9")
						){
			str  = semi + "3";
		}else if(month.equals("10")||
				   month.equals("11")||	
				   month.equals("12")
						){
			str  = semi + "4";
		}
		return str;
	}
	
	public static List getNowAndNextYearMonth(){
		List list = new ArrayList<>();
//		String nowYear = DateUtils.getNowYearStr();
//		String nowMonth = DateUtils.getNowMonthStr();
		String nextYear = DateUtils.getNextYearStr("yyyy-MM");
		String nextMonth = DateUtils.getNextMonthStr("yyyy-MM");
		Map mapNow = new HashMap();
		Map mapNext = new HashMap();
//		mapNow.put("id", nowYear + nowMonth);
//		mapNow.put("name", nowYear + nowMonth);
		mapNext.put("id", nextYear + nextMonth);
		mapNext.put("name", nextYear + nextMonth);
		list.add(mapNext);
//		list.add(mapNow);
		return list;
	}
	
	/*public static List getNowYearMonthA(){
		List list = new ArrayList<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -12);
		for(int i=1;i<=12;i++){
			c.add(Calendar.MONTH, 1);
			Map map = new HashMap();
			String id = df.format(c.getTime());
			map.put("id",id);
			map.put("name",id);
			list.add(map);
		}
		return list;
	}*/
	/*public static List getNowYearMonthB(){
		List list = new ArrayList<>();
		String year = DateUtils.getNowYearStr();
		for(int i=1;i<=12;i++){
			Map map = new HashMap();
			if(i<10){
				map.put("id",year+"0"+i);
				map.put("name",year+"0"+i);
				list.add(map);
			}else{
				map.put("id",year+""+i);
				map.put("name",year+""+i);
				list.add(map);
			}
		}
		return list;
	}*/
	public static List getNowYearMonth(){
		List list = new ArrayList<>();
		String year = DateUtils.getNowYearStr();
		
		/*Map mTerritoryFlag = new HashMap();
		mTerritoryFlag.put("id", TerritoryUtil.YXQ);
		mTerritoryFlag.put("name", "原辖区");*/
		
		for(int i=1;i<=12;i++){
			Map map = new HashMap();
			if(i<10){
				map.put("id",year+"0"+i);
				map.put("name",year+"0"+i);
				list.add(map);
			}else{
				map.put("id",year+""+i);
				map.put("name",year+""+i);
				list.add(map);
			}
		}
		return list;
	}
	
	public static String getNowMonthStr(){
		return getMonth().substring(5);
	}
	
	public static String getNowYearStr(){
		return getMonth().substring(0,4);
	}
	
	public static String getLastMonthStr(String formatType){
		return getLastYearMonth(formatType).substring(5);
	}
	
	public static String getLastYearStr(String formatType){
		return getLastYearMonth(formatType).substring(0,4);
	}
	
	public static String getNextMonthStr(String formatType){
		return getNextYearMonth(formatType).substring(5);
	}
	
	public static String getNextYearStr(String formatType){
		return getNextYearMonth(formatType).substring(0,4);
	}
	
	/**
	 * 获取上一个月
	 * @param formatType 格式
	 * @return
	 */
	public static String getLastYearMonth(String formatType){
		SimpleDateFormat df = new SimpleDateFormat(formatType);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		return df.format(c.getTime());
	}
	
	/**
	 * 获取下一个月
	 * @param formatType 格式
	 * @return
	 */
	public static String getNextYearMonth(String formatType){
		SimpleDateFormat df = new SimpleDateFormat(formatType);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 1);
		return df.format(c.getTime());
	}
	public static String getNextYearMonth(){
		return getNextYearMonth("yyyyMM");
	}
	
	/**
	 * 
	 * @param yearmonth
	 * @return yyyyMM
	 */
	public static String getNextByYearMonth(String yearmonth){
		return getNextByYearMonth(yearmonth,1);
	}
	/**
	 * 
	 * @param yearmonth
	 * @param addNum
	 * @return yyyyMM
	 */
	public static String getNextByYearMonth(String yearmonth, int addNum){
		String date = yearmonth+"01";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
		Calendar c = Calendar.getInstance();
		c.setTime(DateConvertUtils.parse(date,"yyyyMMdd"));
		c.add(Calendar.MONTH, addNum);
		return df.format(c.getTime());
	}
	public static String getLastYearMonth(){
		return getLastYearMonth("yyyyMM");
	}
	public static String getYearMonth(){
		return getYearMonth("yyyyMM");
	}
	public static String getYearMonth(String formatType){
		SimpleDateFormat df = new SimpleDateFormat(formatType);
		Calendar c = Calendar.getInstance();
		return df.format(c.getTime());
	}
	public static String getYearMonth(Date date,String formatType){
		SimpleDateFormat df = new SimpleDateFormat(formatType);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return df.format(c.getTime());
	}
	 /**
     * 使用预设格式提取字符串日期
     * 
     * @param strDate
     *            日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, "yyyy-MM-dd");
    }

    /**
     * 使用用户格式提取字符串日期
     * 
     * @param strDate
     *            日期字符串
     * @param pattern
     *            日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	/**
	 * 获取当前月份
	 * @return yyyy-MM 格式日期字符串
	 *//*
	public static String getCurrentMonth() {
		return getMonth(new Date());
	}
	*//**
	 * 获取当前日期
	 * 
	 * @return yyyy-MM-dd 格式日期字符串
	 *//*
	public static String getCurrentDate() {
		return getDateTime(new Date());
	}
	*//**
	 * 获取当前时间
	 * @return yyyy-MM-dd HH:mm:ss 格式日期字符串
	 *//*
	public static String getCurrentDateTime() {
		return getISODateTime(new Date());
	}*/
	
	public static void main(String [] asgs){
		System.out.println(DateUtils.getMonth());
		//System.out.println(getCurrentDateTime());
		//System.out.println(ISODateTimeFormat.dateTime().print(new Date().getTime()));
		System.out.println(DateUtils.getMaxDayOrMonth("201802".substring(0, 4),"201802".substring(4, 6)));
	}
	public static int compareDate(Date date,Date date1, int num) {
	  Calendar   calendar =  Calendar.getInstance();
	  calendar.setTime(date); 
	  calendar.add(Calendar.DATE, num+1);
	  return calendar.getTime().compareTo(date1);
	}
	public static String getAMPM(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		if(c.get(Calendar.HOUR_OF_DAY) >=12 ){
			return "PM";
		}else{
			return "AM";
		}
	}
	public static String getDateYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR)+"";
	}
	public static String getDateMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int m = c.get(Calendar.MONTH) + 1;
		if(m<10){
			return "0"+m;
		}
		return m+"";
	}
	
    public static String[] getCurrentMonthEachDay() {
    	int arrIndex = 0;
        int TOTAL_DAY = getCurrentMonthDay();  
        String[] arrDays=new String[TOTAL_DAY];
        for (int currentDay = 1;currentDay <= TOTAL_DAY;currentDay++) {
           // System.out.println(currentDay);
            arrDays[arrIndex++] = currentDay+"";
        }
        return arrDays;
    }
	
    
    /** 
     * 获取当月的 天数 
     * */  
    public static int getCurrentMonthDay() {  

        Calendar a = Calendar.getInstance();  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    }
    
    public static int getMonthDay(String year,String month){
    	
    	String strDate = year + "-" + month; 
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); 
    	Calendar calendar = new GregorianCalendar(); 
    	Date date1;
    	try {
			date1 = sdf.parse(strDate);
			calendar.setTime(date1); //放入你的日期 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    public static String[] getMonthEachDay(String year,String month) {
    	int arrIndex = 0;
        int TOTAL_DAY = getMonthDay(year,month);  
        String[] arrDays=new String[TOTAL_DAY];
        for (int currentDay = 1;currentDay <= TOTAL_DAY;currentDay++) {
           // System.out.println(currentDay);
            arrDays[arrIndex++] = currentDay+"";
        }
        return arrDays;
    }
    
    /** 
     * 根据年 月 获取对应的月份 天数 
     * */  
    public static int getDaysByYearMonth(int year, int month) {  

        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    }  

    /** 
     * 根据日期 找到对应日期的 星期 
     */  
    public static String getDayOfWeekByDate(String date) {  
        String dayOfweek = "-1";  
        try {  
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");  
            Date myDate = myFormatter.parse(date);  
            SimpleDateFormat formatter = new SimpleDateFormat("E");  
            String str = formatter.format(myDate);  
            dayOfweek = str;  

        } catch (Exception e) {  
            System.out.println("错误!");  
        }  
        return dayOfweek;  
    }  
    
    /**
     * 判断一个月份是哪一个季度的
     * @param month
     * @return
     */
    public static int getQuarterByMonth(int month) {
		int quarts[] = { 1, 2, 3, 4 };
		if (month >= 1 && month <= 3) // 1-3月;0,1,2
			return quarts[0];
		else if (month >= 4 && month <= 6) // 4-6月;3,4,5
			return quarts[1];
		else if (month >= 7 && month <= 9) // 7-9月;6,7,8
			return quarts[2];
		else
			return quarts[3];
	}
    
    /**
     * 判断一个月份是哪一个季度的
     * @param month
     * @return
     */
    public static int getNextQuarterByMonth(int month) {
		int quarts[] = { 1, 2, 3, 4 };
		if (month >= 1 && month <= 3) // 1-3月;0,1,2
			return quarts[1];
		else if (month >= 4 && month <= 6) // 4-6月;3,4,5
			return quarts[2];
		else if (month >= 7 && month <= 9) // 7-9月;6,7,8
			return quarts[3];
		else
			return quarts[0];
	}
    public static List getNowQuaterMonth() {
	   	 Calendar a = Calendar.getInstance(); 
	   	 int y = (a.get(Calendar.MONTH)+1)%3;
	   	 int yy = (y==0?3:y);
	   	 a.add(Calendar.MONTH, 1-(y==0?3:y));
	   	 List list = new ArrayList();
	   	 for (int i = -yy;i<0;i++) {
	   		 list.add(getYearMonth(a.getTime(),"yyyyMM"));
	   		 a.add(Calendar.MONTH, 1);
	   	 }
	   	 return list;
	   }


}
