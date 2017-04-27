package com.freedom.lauzy.gankpro.function.utils;

import com.freedom.lauzy.gankpro.app.GankApp;
import com.freedom.lauzy.gankpro.app.GankWithTinkerApp;

public class SharePrefUtils {

    public static final String DAY_NIGHT_KEY = "day_night_mode";

    public static void setNightMode(boolean isNight) {
//        GankWithTinkerApp.sConfigPreferences.edit().putBoolean(DAY_NIGHT_KEY, isNight).apply();
        GankApp.sConfigPreferences.edit().putBoolean(DAY_NIGHT_KEY, isNight).apply();
    }

    public static boolean isNightMode() {
//        return GankWithTinkerApp.sConfigPreferences.getBoolean(DAY_NIGHT_KEY, false);
        return GankApp.sConfigPreferences.getBoolean(DAY_NIGHT_KEY, false);
    }
}
