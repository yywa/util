package com.yyw.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author yyw
 * @date 2018/12/11 10:48
 */
@Slf4j
public class DateUtil {

    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    //报警时间统一格式
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd-HH-mm-ss-SSS";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY$MM$ = "yyyy年MM月";

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 得到当前日期或时间
     */
    public static String getDate(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }

    /**
     * 得到指定日期或时间
     */
    public static String getDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static Date getDateByString(String dateStr, String pattern) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.parse(dateStr);
    }

    /**
     * 当日起始时间
     */
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 000);
        return todayStart.getTime();
    }

    /**
     * 指定日期的当日起始时间
     */
    public static Date getStartTime(Date date) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 000);
        return todayStart.getTime();
    }

    /**
     * 指定日期的当日结束时间
     */
    public static Date getEndTime(Date date) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 当日结束时间
     */
    public static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 取得对账日期
     *
     * @return date
     */
    public static Date getAccountDate() {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_MONTH, -1);
        return date.getTime();
    }

    /**
     * 取得对账日期字符串
     *
     * @return string
     */
    public static String getBOADateStr() {
        Date date = DateUtil.getAccountDate();
        return new SimpleDateFormat(DateUtil.YYYY_MM_DD).format(date);
    }

    /**
     * 解析字符串到时间
     */
    public static Date parseStrToDate(String pattern, String date) throws ParseException {
        return new SimpleDateFormat(pattern).parse(date);
    }

    /**
     * 字符串转日期
     */
    public static Date stringToDate(String pattern, String date) {
        Date transactionTime = new Date();
        if (StringUtils.isNotBlank(date)) {
            try {
                transactionTime = DateUtil.parseStrToDate(pattern, date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return transactionTime;
    }

    /**
     * 字符串时间重新格式化
     */
    public static String parseStrToStr(String newpattern, String date, String oldpattern) throws ParseException {
        Date newDate = new SimpleDateFormat(oldpattern).parse(date);
        return DateUtil.getDate(newDate, newpattern);
    }

    /**
     * 获取当前时间后多长时间
     *
     * @param addTime 单位:秒
     */
    public static Date addDateBySecond(Date date, long addTime) {
        return new Date(date.getTime() + addTime * 1000);
    }

    /**
     * 获取下一天
     */
    public static String getNextDay(Date date, String pattern) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return new SimpleDateFormat(pattern).format(cal.getTime());
    }

    /**
     * 获取下一天
     */
    public static String getNextDayByString(String date, String pattern) throws ParseException {

        Date tdate = (new SimpleDateFormat(pattern)).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(tdate);
        cal.add(Calendar.DATE, 1);
        return new SimpleDateFormat(DateUtil.YYYYMMDD).format(cal.getTime());
    }

    /**
     * 获取前一天（yyyyMMdd）
     */
    public static String getBeforeDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat(DateUtil.YYYYMMDD).format(cal.getTime());
    }

    /**
     * 获取前一天
     */
    public static String getBeforeDay(Date date, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return new SimpleDateFormat(format).format(cal.getTime());
    }

    /**
     * 获取前一天（yyyy_MM_dd）
     */
    public static Date getBeforeDayDate(String stringDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYY_MM_DD);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(stringDate));
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 获取前一天
     */
    public static Date getBeforeDayDate(Date date) throws ParseException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * 获取前一月
     */
    public static String getLastMonth(Date date) throws ParseException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM);
        return sdf.format(cal.getTime());
    }

    /**
     * 获取前一天
     */
    public static String getBeforeDayDate(Date date, String pattern) throws ParseException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);

        return getDate(cal.getTime(), pattern);
    }

    /**
     * 获取下一天
     */
    public static Date getNextDayDate(Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, +1);
        return cal.getTime();
    }

    /**
     * 获取两个日期之间的所有日期集合(包括两个日期)
     */
    public static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        result.add(start);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        if (start.getTime() != end.getTime()) {
            result.add(end);
        }

        return result;
    }

    /**
     * 获取昨天0点的时间
     *
     * @return Date
     */
    public static Date getYesterdayZeroDate() {

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * 获取今天0点的时间
     *
     * @return Date
     */
    public static Date getTodayZeroDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String getFirstDayOfCurrentYear(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.set(Calendar.DAY_OF_YEAR, 1);
        return sdf.format(c.getTime());
    }

    /**
     * 获取指定日期下个月的第一天
     */
    public static String getFirstDayOfNextMonth(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, 1);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定日期当前月的第一天
     */
    public static String getFirstDayOfMonth(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定日期当前月的第一天
     */
    public static String getFirstDayOfMonth(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取指定日期下个月的第一天
     */
    public static Date getFirstDayOfNextMonthII(Date date, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取n天前的日期
     *
     * @param date
     * @param n
     * @param format
     * @return
     */
    public static String getBeforeNDayByDate(Date date, int n, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        calendar.add(Calendar.DATE, -n);
        return dateFormat.format(calendar.getTime());
    }


    public static int compareDate(String date1, String date2, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前小时数
     *
     * @return 小时
     */
    public static int getHour() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取上个月
     *
     * @param date   时间
     * @param format 格式
     * @return 返回时间字符串
     */
    public static String getPreviousMonth(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        return dateFormat.format(c.getTime());
    }

    /**
     * 获取下个月
     *
     * @param date   时间
     * @param format 格式
     * @return 返回时间字符串
     */
    public static String getNextMonth(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, +1);
        return dateFormat.format(c.getTime());
    }

    /**
     * 获取给定日期的明天的0点日期
     *
     * @param date 时间
     * @return date
     */
    public static Date getLastZeroHourByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 判断两个日期是否为同一天
     *
     * @param day1
     * @param day2
     * @return
     */
    public static Boolean isSameDate(Date day1, Date day2) {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.YYYY_MM_DD);
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        return ds1.equals(ds2);
    }

    public static void main(String[] args) throws Exception {
//        try {
//            Date date1 = DateUtil.parseStrToDate(DateUtil.YYYY_MM_DD_HH_MM_SS, "2019-03-01 01:01:01");
//            Date previousMonth = DateUtil.getTomorrowZeroHourByDate(date1);
//            String date = DateUtil.getDate(previousMonth, DateUtil.YYYY_MM_DD_HH_MM_SS);
//            System.out.println(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Date date = DateUtil.parseStrToDate(DateUtil.YYYY_MM_DD, "2019-06-17");
        Boolean result = DateUtil.isSameDate(new Date(), date);

        System.out.println(result);
    }


}
