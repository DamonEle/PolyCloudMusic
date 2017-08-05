package com.damon43.common.commonutils;

/**
 * Created by Administrator on 2017/4/13.
 */

public class MathUtils {

    /**
     * (数据类型)(最小值+Math.random()*(最大值-最小值+1))
     *
     * @param min
     * @param max
     * @return
     */
    public static double getRandomInt(int min, int max) {
        return min + Math.random() * (max - min + 1);
    }
}
