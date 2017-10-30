package com.lotfi.halel.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lotfi-pc on 02/08/2017.
 */
public class Prefs {
    private static SharedPreferences prefs;

    public Prefs(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void setCity(String city) {
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("city", city);
        prefsEditor.apply();
    }

    public void setLaunched() {
        prefs.edit().putBoolean("first" , true).apply();
    }

    public boolean getLaunched() {
        return prefs.getBoolean("first" , false);
    }

    public void setLastCity(String city) {
        prefs.edit().putString("last" , city).apply();
    }

    public String getLastCity() {
        return prefs.getString("last" , null);
    }


}
