package controller.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 时间处理类
 * 
 * @author www.gomall.com
 * 
 */
public class UtilDate {
	public static final String dtLong = "yyyyMMddHHmmss";
	public static final String simple = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 *  获取年月日时分秒，注意月+1，如：Calendar.getInstance().MONTH + 1
	 * @param a =  Calendar.getInstance().MONTH
	 * @return
	 */
	public static int getCurrent( int a ) {
		Calendar now = Calendar.getInstance( Locale.CHINA );
		now.setTime(new Date());
		return now.get(a);
	}
	
  
	
	/**
	 * 得到几分钟后的时间
	 * 
	 * @param date
	 * @param minute
	 * @return String
	 */
	public static String getMinuteAfter(Date date, int minute) {
		Calendar now = Calendar.getInstance( Locale.CHINA );
		now.setTime(date);
		if (minute > 0) {
			now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + minute);
		}
		now.set(Calendar.MINUTE, now.get(Calendar.MINUTE));
		SimpleDateFormat format = new SimpleDateFormat(dtLong);
		return format.format(now.getTime());
	}

	/**
	 * 格式化时间
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getSimpleDateFormat(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(simple,Locale.CHINA);
	
			return format.format(date);
		
	}

	/**
	 * 转换时间格式
	 * 
	 * @return 返回String类型 yyyy-MM-dd HH:mm:ss
	 */
	public static String getChangeDate(Date data) {
		SimpleDateFormat formatter = new SimpleDateFormat(simple);
		return formatter.format(data);
	}

	/**
	 * 格式化时间
	 * 
	 * @param String
	 *            date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date getSimpleDateFormat(String date) {
		SimpleDateFormat format = new SimpleDateFormat(simple,Locale.CHINA);
		Date dateA;
		try {
			dateA = format.parse(date);
			return dateA;
		} catch (ParseException e) {
			System.out.println("Date format error !");
		}
		return null;
	}
	
	
	

	/**
	 * 时间start 到 end(未来)时间差为零吗？
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static Boolean compare(Date start, String end) {
		SimpleDateFormat format = new SimpleDateFormat(dtLong);
		String s1 = format.format(start);
		String s2 = end;

		java.util.Calendar c1 = java.util.Calendar.getInstance();
		java.util.Calendar c2 = java.util.Calendar.getInstance();

		try {
			c1.setTime(format.parse(s1));
			c2.setTime(format.parse(s2));
		} catch (java.text.ParseException e) {
		}
		int result = c1.compareTo(c2);
		if (result <= 0) {
			return false;
		} else {
			return true;
		}

	}

	/**
	 * 获取序列号产生时间，间隔一秒
	 * 
	 * @return
	 */
	public synchronized static String getSNCreated() {
		String curretDateA = new String();
		try {
			Thread.sleep(1000);
			SimpleDateFormat df = new SimpleDateFormat(simple);
			curretDateA = df.format(new Date());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return curretDateA;

	}

	/**
	 * 当前系统时间,可用于订单编号
	 * 
	 * @return 当前系统时间，格式为：yyyyMMddHHmmss
	 */
	public static String getOrderNum() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dtLong);
		return df.format(date);
	}

	/**
	 * 获取系统当前日期 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getSimpleDateFormatter() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(simple);
		return df.format(date);
	}

	/**
	 * 获取系统当期年月日 yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDate() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat(dtLong);
		return df.format(date);
	}

	/**
	 * 产生随机的三位数
	 * 
	 * @return
	 */
	public static String getThree() {
		Random rad = new Random();
		return rad.nextInt(1000) + "";
	}

	/****************************************/
	/**
	 * 获得某年月的开始时间
	 * 
	 * @return 即2012-01-01 00:00:00
	 */
	public static String getMonthStartTime(int year, int month) {
		Calendar now = Calendar.getInstance( Locale.CHINA );
		if (year > 2014) {
			now.set(Calendar.YEAR, year);
		}
		now.set(Calendar.MONTH, month - 1);
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		Date date =now.getTime(); 
		 DateFormat format = new SimpleDateFormat( simple );
		 String abcd = format.format(date);
		return abcd;
	}

	/**
	 * 某年某月的结束时间
	 * 
	 * @return 即2012-01-31 23:59:59
	 */
	public static String getMonthEndTime(int year, int month) {
		Calendar now = Calendar.getInstance(  );
		now.setTime(new Date());
		try {
			if (year > 2014) {
				now.set(Calendar.YEAR, year);
			}
			now.set(Calendar.MONTH, month);
			now.set(Calendar.DAY_OF_MONTH, 1);
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date date = now.getTime(); 
	 DateFormat format = new SimpleDateFormat( simple );
	 String abcd = format.format(date);
		return abcd;
	}
	
 

	/****************************************/

	/**
	 * 当月开始时间，即2015-01-01 00:00:00
	 * 
	 * @return
	 */
	public static String getCurrentMonthStartTime() {
		Calendar c = Calendar.getInstance( Locale.CHINA );
		String now = null;
		c.setTime(new Date());
		c.set(Calendar.MONTH, c.get(Calendar.MONTH));
		c.set(Calendar.DAY_OF_MONTH, +1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		SimpleDateFormat df = new SimpleDateFormat(simple);
		now = df.format(c.getTime());
		return now;
	}
 

	/**
	 * 当前月的结束时间，即2012-01-31 23:59:59
	 * 
	 * @return
	 */
	public static String getCurrentMonthEndTime() {
		Calendar c = Calendar.getInstance( Locale.CHINA );
		String now = null;
		c.setTime(new Date());
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
		c.set(Calendar.DAY_OF_MONTH, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, -1);
		SimpleDateFormat df = new SimpleDateFormat(simple);
		now = df.format(c.getTime());
		return now;
	}

	/****************************************/
	/**
	 * Date Format yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static Date getDateFormat(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(simple);
		try {
			return df.parse(df.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 当前系统：yyyy-MM-dd
	 * @return
	 */
	public static Date getCurrentDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			return	df.parse( df.format( new Date()));
		} catch (ParseException e) {
		  
		}
		return null;
	}

}
