package org.maxwe.tao.android.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Pengwei Ding on 2017-01-08 12:36.
 * Email: www.dingpengwei@foxmail.com www.dingpegnwei@gmail.com
 * Description: TODO
 */
public class DateTimeUtils {
    public static void test() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String month = String.format("%02d", Calendar.getInstance().get(Calendar.MONTH));
        String date = String.format("%02d", Calendar.getInstance().get(Calendar.DATE));
        String hour = String.format("%02d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        String minute = String.format("%02d", Calendar.getInstance().get(Calendar.MINUTE));
        int second = Calendar.getInstance().get(Calendar.SECOND);
        System.out.println(year + "/" + month + "/" + date + " " + hour + ":" + minute + ":" + second);
    }

    /**
     * 把年月日格式的日期转化为秒级数据
     *
     * @param ymdString 年月日字符串，使用中划线分割，如：2016-08-11
     * @return
     * @throws Exception
     */
    public static long parseYMDToLong(String ymdString) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long time = simpleDateFormat.parse(ymdString).getTime();
        return time;
    }

    /**
     * 全时间值转化为秒级数据
     *
     * @param ymdString
     * @return
     * @throws Exception
     */
    public static long parseFullTimeToLong(String ymdString) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = simpleDateFormat.parse(ymdString).getTime();
        return time;
    }

    public static String parseLongToFullTime(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static long getFixedTimestamp() throws Exception {
        long fixTime = parseFullTimeToLong("2015-12-31 23:59:59");
        return fixTime;
    }

    public static long getCurrentTimestamp(){
        return new Date().getTime();
    }

    public static String getCurrentFullTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        return format;
    }
}
