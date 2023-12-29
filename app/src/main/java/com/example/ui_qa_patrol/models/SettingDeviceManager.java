package com.example.ui_qa_patrol.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SettingDeviceManager {
    static final String TAG = "SETTING_DEVICE_MANAGER";
    private static final String SHARED_PREF_NAME = "mySettingDevicePolytron";

    private static final String KEY_IS_NIK_OR_EMAIL = "nik_or_email";
    private static final String KEY_DEVICE_ID = "device_id";
    private static final String KEY_ISLOGGED_IN = "is_logged_in";
    private static final String KEY_DEVICE_GROUP = "device_group";
    private static final String KEY_AUTO_LOGIN_KEY = "auto_login_key";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Activity activity;

    public SettingDeviceManager(Activity activity){
        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean saveIsLoggedIn(boolean isLoggedIn) {
        try {
            editor.putBoolean(KEY_ISLOGGED_IN, isLoggedIn);
            editor.apply();
            Log.i(TAG, "saveIsLoggedIn: Saved");
            return true;
        }
        catch (Exception ex) {
            Log.e(TAG, "saveIsLogged: "+ ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean saveNikOrEmail(String nikOrEmail){
        try{
            editor.putString(KEY_IS_NIK_OR_EMAIL, nikOrEmail);
            editor.apply();
            Log.i(TAG, "saveNikOrEmail: SAVED");
            return true;
        }
        catch (Exception ex){
            Log.e(TAG, "saveNikOrEmail: "+ ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean saveAutoLoginKey(String autoLoginKey){
        try{
            editor.putString(KEY_AUTO_LOGIN_KEY, autoLoginKey);
            editor.apply();
            Log.i(TAG, "saveAutoLoginKey: SAVED");
            return true;
        }
        catch (Exception ex){
            Log.e(TAG, "saveAutoLoginKey: "+ ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean saveDeviceId(String deviceId){
        try{
            editor.putString(KEY_DEVICE_ID, deviceId);
            editor.apply();
            Log.i(TAG, "saveDeviceId: SAVED");
            return true;
        }
        catch (Exception ex){
            Log.e(TAG, "saveDeviceId: "+ ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean saveDeviceGroup(String deviceGroup){
        try {
            editor.putString(KEY_DEVICE_GROUP, deviceGroup);
            editor.apply();
            Log.i(TAG, "saveDeviceGroup: SAVED");
            return true;
        }
        catch (Exception ex){
            Log.e(TAG, "saveDeviceGroup: "+ ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public boolean getIsLoggedIn(){
        try{
            if (!sharedPreferences.contains(KEY_ISLOGGED_IN)){
                editor.putBoolean(KEY_ISLOGGED_IN, false);
                editor.apply();
                return true;
            }
            else {
                return sharedPreferences.getBoolean(KEY_ISLOGGED_IN, false);
            }
        }
        catch (Exception ex){
            Log.e(TAG, "getLoggedIn: "+ ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public String getNikOrEmail(){
        try{
            String result = sharedPreferences.getString(KEY_IS_NIK_OR_EMAIL, null);
            return (result != null && !result.isEmpty()) ? result : "";
        }
        catch (Exception ex){
            Log.e(TAG, "getNikOrEmail: "+ ex.getMessage());
            ex.printStackTrace();
        }
        return "";
    }

    public String getAutoLoginKey(){
        try {
            String result = sharedPreferences.getString(KEY_DEVICE_ID, null);
            return (result != null && !result.isEmpty()) ? result : "";
        }
        catch (Exception ex){
            Log.e(TAG, "getAutoLoginKey: "+ ex.getMessage());
            ex.printStackTrace();
        }
        return "";
    }

    public String getDeviceId(){
        try {
            String result = sharedPreferences.getString(KEY_DEVICE_ID, null);
            return (result != null && !result.isEmpty()) ? result : "";
        }
        catch (Exception ex){
            Log.e(TAG, "getDeviceId: "+ ex.getMessage());
            ex.printStackTrace();
        }
        return "";
    }

    public String getDeviceGroup(){
        try {
            String result = sharedPreferences.getString(KEY_DEVICE_GROUP, null);
            return (result != null && !result.isEmpty()) ? result : "";
        }
        catch (Exception ex){
            Log.e(TAG, "getDeviceGroup: "+ ex.getMessage());
            ex.printStackTrace();
        }
        return "";
    }

}
