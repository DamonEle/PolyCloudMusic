package com.damon43.common.baseapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.multidex.MultiDex;

/**
 * @author damonmasty
 *         Created on 上午10:12 17-1-29.
 */

public class BaseApplication extends Application {

    private static BaseApplication baseApplication;
    private static SharedPreferences sp;
    private static SharedPreferences.Editor save;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        sp = baseApplication.getSharedPreferences(Config.SHAPE_FILE, Context.MODE_PRIVATE);
        save = sp.edit();
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    public static Resources getAppResources() {
        return baseApplication.getResources();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static void put(String key, String value) {
        save.putString(key, value);
        save.commit();
    }

    public static String get(String key) {
        return sp.getString(key, null);
    }

    /**
     * 分包
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
