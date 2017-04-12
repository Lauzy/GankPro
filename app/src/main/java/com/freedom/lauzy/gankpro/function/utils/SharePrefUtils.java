package com.freedom.lauzy.gankpro.function.utils;

import com.freedom.lauzy.gankpro.app.GankApp;

public class SharePrefUtils {

    public static final String DAY_NIGHT_KEY = "day_night_mode";

    public static void setNightMode(boolean isNight) {
        GankApp.sConfigPreferences.edit().putBoolean(DAY_NIGHT_KEY, isNight).apply();
    }

    public static boolean isNightMode() {
        return GankApp.sConfigPreferences.getBoolean(DAY_NIGHT_KEY, false);
    }
}
