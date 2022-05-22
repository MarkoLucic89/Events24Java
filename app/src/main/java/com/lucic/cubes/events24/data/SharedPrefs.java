package com.lucic.cubes.events24.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private final String EMAIL = "prefs_email";
    private final String LANGUAGE = "prefs_language";
    private final String NOTIFICATION = "prefs_notification";

    private static SharedPrefs instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    private SharedPrefs(Activity activity) {
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public synchronized static SharedPrefs getInstance(Activity activity) {

        if (instance == null) {
            instance = new SharedPrefs(activity);
        }

        return instance;
    }

    public void saveEmail(String email) {
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public String getEmail() {
        return preferences.getString(EMAIL, "");
    }

    public void saveAppLanguage(int languageIndex) {
        editor.putInt(LANGUAGE, languageIndex);
        editor.apply();
    }

    public int getAppLanguage() {
        return preferences.getInt(LANGUAGE, 0);
    }

    public String getAppLanguageString() {
        if (getAppLanguage() == 0) {
            return "en";
        } else {
            return "sr";
        }
    }

    public boolean isNotificationOn() {
        return preferences.getBoolean(NOTIFICATION, true);
    }

    public void setNotificationStatus(boolean isOn) {
        editor.putBoolean(NOTIFICATION, isOn);
        editor.apply();
    }
}
