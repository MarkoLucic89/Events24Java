package com.lucic.cubes.events24.tools;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.data.SharedPrefs;

import java.util.Locale;

public class LanguageTool {

    public static void setLanguageResourceConfiguration(Activity activity) {

        String languageCode = SharedPrefs.getInstance(activity).getAppLanguageString();
//        String languageCode = activity.getResources()
//                .getStringArray(R.array.app_languages_string)[SharedPrefs.getInstance(activity).getAppLanguage()];

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

    }
}
