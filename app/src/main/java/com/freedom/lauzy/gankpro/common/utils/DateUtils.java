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

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     *
     * @param timeStamp 时间戳
     * @return 转换后的时间
     */
    public static String convertTimeToFormat(long timeStamp) {
        long curTime =System.currentTimeMillis() / (long) 1000 ;
        long time = curTime - timeStamp;

        if (time < 60 && time >= 0) {
            return "刚刚";
        } else if (time >= 60 && time < 3600) {
            return time / 60 + "分钟前";
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + "小时前";
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + "天前";
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + "个月前";
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + "年前";
        } else {
            return "刚刚";
        }
    }

}
