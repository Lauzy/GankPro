package com.freedom.lauzy.gankpro.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具
 * Created by Lauzy on 2017/2/21.
 */

public class DateUtils {

    /**
     * 时间戳转换为时间格式
     * @param time 时间戳
     * @return  时间
     */
    public static String toDate(long time) {
        Date date = new Date(time * 1000L);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    public static String toPicDateName(long time) {
        Date date = new Date(time);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd", Locale.CHINA);
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * 时间戳转换为时间格式
     * @param time 时间戳
     * @return  时间
     */
    public static String toSimpleDate(long time) {
        Date date = new Date(time * 1000L);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    /**
     * 时间戳转换为时间格式
     * @param time 时间戳
     * @return  时间
     */
    public static String toMonthDate(long time) {
        Date date = new Date(time * 1000L);
        String strs = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM", Locale.CHINA);
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }
}
