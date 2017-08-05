package com.damon43.common.commonutils;


import com.damon43.common.baseapp.Config;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * @author damonmasty
 *         Created on 下午5:18 17-1-28.
 */

public class LogUtils {

    public static boolean DEBUG = true;// 是否调试模式

    /**
     * 在application调用初始化
     */
    public static void init(boolean debug) {
        DEBUG = debug;
        if (DEBUG) {
            Logger.init(Config.DEBUG_TAG)                 // default PRETTYLOGGER or use just init()
                    .setMethodCount(2)                 // default 2
                    .setLogLevel(LogLevel.FULL);       // default LogLevel.FULL
        } else {
            Logger.init()                 // default PRETTYLOGGER or use just init()
                    .setMethodCount(3)                 // default 2
                    .hideThreadInfo()               // default shown
                    .setLogLevel(LogLevel.NONE);  // default LogLevel.FULL
        }
    }

    public static void logD(String tag, String message) {
        if (DEBUG) {
            Logger.d(tag, message);
        }
    }

    public static void logD(String message) {
        if (DEBUG) {
            Logger.d(message);
        }
    }

    public static void logE(Throwable throwable, String message, Object... args) {
        if (DEBUG) {
            Logger.e(throwable, message, args);
        }
    }

    public static void logE(String message, Object... args) {
        if (DEBUG) {
            Logger.e(message, args);
        }
    }

    public static void logI(String message, Object... args) {
        if (DEBUG) {
            Logger.i(message, args);
        }
    }

    public static void logV(String message, Object... args) {
        if (DEBUG) {
            Logger.v(message, args);
        }
    }

    public static void logW(String message, Object... args) {
        if (DEBUG) {
            Logger.v(message, args);
        }
    }

    public static void logWtf(String message, Object... args) {
        if (DEBUG) {
            Logger.wtf(message, args);
        }
    }

    public static void logJson(String message) {
        if (DEBUG) {
            Logger.json(message);
        }
    }

    public static void logXml(String message) {
        if (DEBUG) {
            Logger.xml(message);
        }
    }
}
