package com.hngymt.almes.pda.client.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigHelper {
    public static final String APP_CODE = "wms-client-adnroid";

    public static final String CONFIG_FILE_NAME = "Config";
    public static final String SERVICE_ROOT_URL_NAME = "ServiceRootUrl";

    private static class ConfigInstance {
        private static final ConfigHelper instance = new ConfigHelper();
    }

    private ConfigHelper() {
    }

    public static ConfigHelper getInstance() {
        return ConfigInstance.instance;
    }

    public void setServiceRootUrl(Context context, String value) {
        putString(context, SERVICE_ROOT_URL_NAME, value);
    }

    public String getServiceRootUrl(Context context) {
        return getString(context, SERVICE_ROOT_URL_NAME, "http://10.1.10.31:7300/mock/5ec4d668286a4c3e7cb35233/demo");
    }

    private void putString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(CONFIG_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String getString(Context context, String key, String defValue) {
        SharedPreferences preferences = context.getSharedPreferences(CONFIG_FILE_NAME, context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }
}