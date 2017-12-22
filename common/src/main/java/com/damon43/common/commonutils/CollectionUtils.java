package com.damon43.common.commonutils;

import java.util.Collection;

/**
 * @author damonmasty
 *         Created on 上午10:41 17-1-29.
 */

public class CollectionUtils {

    /**
     * 判断集合是否为null或者0个元素
     *
     * @param c
     * @return
     */
    public static boolean isNullOrEmpty(Collection c) {
        if (null == c || c.isEmpty()) {
            return true;
        }
        return false;
    }

    public static long[] getLongArrayFromOld(long[] old) {
        long[] news = old;
        return news;
    }

}
