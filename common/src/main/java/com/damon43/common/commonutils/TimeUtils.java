package com.damon43.common.commonutils;

import java.util.Date;

/**
 * @author damonmasty
 *         Created on 下午9:55 17-2-14.
 */

public class TimeUtils {

    /**
     * 友好显示时间差
     *
     * @param diff 毫秒
     * @return
     */
    public static String getFriendTimeOffer(long diff) {
        int day = (int) (diff / (24 * 60 * 60 * 1000));
        if (day > 0)
            return day + "天";
        int time = (int) (diff / (60 * 60 * 1000));
        if (time > 0)
            return time + "小时";
        int min = (int) (diff / (60 * 1000));
        if (min > 0)
            return min + "分钟";
        int sec = (int) diff / 1000;
        if (sec > 0)
            return sec + "秒";
        return "1秒";
    }

    /**
     * 通过时间戳来获取 日
     * @param str
     * @return
     */
    public static int getDaybyString(long str){
        Date date = new Date(str);
        return date.getDay();
    }
    /**
     * 通过时间戳来获取 月-日 11-2
     * @param str
     * @return
     */
    public static String getDaybylong(long str){
        Date date = new Date(str);
        return (date.getMonth()+1)+"-"+date.getDay();
    }
}
