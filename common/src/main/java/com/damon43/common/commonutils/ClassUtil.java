package com.damon43.common.commonutils;

import java.lang.reflect.ParameterizedType;

/**
 * @author damonmasty
 *         Created on 上午11:51 17-1-27.
 *         类转换初始化
 */

public class ClassUtil {
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }


}
