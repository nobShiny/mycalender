package com.lsj.mycalendar.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 系统当前时间工具类
 * @author shiny_Jia
 *
 */

public class CurrentTimeUtils {

	public static String TimeFormatByModel(long time, SimpleDateFormat format,
			String model) {

		if ("12".equals(model)) {
			format = new SimpleDateFormat("hh:mm:ss");

		} else {
			format = new SimpleDateFormat("HH:mm:ss");
		}
		return format.format(new Date(time));
	}

	/**
	 * 获取系统当前 年份
	 * 
	 * @return year
	 */
	public static int getTimeYear() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取系统当前 年份的后一年
	 *
	 * @return year
	 */
	public static int getTimeNextYear() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return c.get(Calendar.YEAR) + 1;
	}

	/**
	 * 获取系统当前 月份(calendar的月份是从0算起的)
	 * 
	 * @return month+1
	 */
	public static int getTimeMonth() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return c.get(Calendar.MONTH) + 1;

	}

	/**
	 * 获取系统当前 当天时间
	 * 
	 * @return day
	 */
	public static int getTimeDay() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取系统当前 当天几点
	 * 
	 * @return hour
	 */
	public static int getTimeHour() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取系统当前 分钟
	 * 
	 * @return minute
	 */
	public static int getTimeMinute() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 获取系统当前 秒钟
	 * 
	 * @return second
	 */
	public static int getTimeSecond() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		return c.get(Calendar.SECOND);

	}

	/**
	 * 获取系统星期
	 * 
	 * @return weekDay
	 */
	public static String getWeekDay() {
		Calendar c = Calendar.getInstance(Locale.getDefault());
		if (c == null) {
			return "星期一";
		}

		if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "星期一";
		}
		if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "星期二";
		}
		if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "星期三";
		}
		if (Calendar.THURSDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "星期四";
		}
		if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "星期五";
		}
		if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "星期六";
		}
		if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
			return "星期日";
		}

		return "星期一";
	}

	public static String crrentTimeByYY_MM_DD() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
}
