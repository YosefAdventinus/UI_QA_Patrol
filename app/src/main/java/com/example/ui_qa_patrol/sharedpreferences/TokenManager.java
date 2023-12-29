package com.example.ui_qa_patrol.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ui_qa_patrol.Helper.AppConstants;

import java.util.Random;

public class TokenManager {
    static final String TAG = "TOKEN_MANAGER";
    private static final String SHARED_PREF_NAME = "myTokenOperatorQA";
    private static final String KEY_HOSTNAME = "hostname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_Password ="password";
    private static final String KEY_NAME_ACCESS = "name_access";
    private static final String KEY_AES = "aes_key";
    private static final String KEY_ADMINPASSWORD = "admin_password";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public TokenManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveEmailPassword(String email, String password){
        try {
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_Password, password);
            editor.apply();
            Log.i(TAG, "save Email, Password, and Token: SUCCESS");
        }
        catch (Exception ex){
            Log.e(TAG, "save Email, Password, and Token: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void saveHEPA(String hostname, String email, String password, String nameAccess){
        try{
            editor.putString(KEY_HOSTNAME, hostname);
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_Password, password);
            editor.putString(KEY_NAME_ACCESS, nameAccess);
            editor.apply();
            Log.i(TAG, "saveHEPA: SUCCESS");
        }
        catch (Exception ex){
            Log.e(TAG, "saveHEPA: " +ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void saveHostname(String hostname){
        try {
            editor.putString(KEY_HOSTNAME, hostname);
            editor.apply();
            Log.i(TAG, "saveHostname: SUCCESS");
        }
        catch (Exception ex){
            Log.e(TAG, "saveHostname: "+ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void saveAdminPassword(String adminPassword){
        try{
            editor.putString(KEY_ADMINPASSWORD, adminPassword);
            editor.apply();
            Log.i(TAG, "saveAdminPassword: SUCCESS");
        }
        catch (Exception ex){
            Log.e(TAG, "saveAdminPassword: "+ex.getMessage());
            ex.printStackTrace();
        }
    }

    public String getAdminPassword(){
        try {
            String  result = sharedPreferences.getString(KEY_ADMINPASSWORD, null);
            return (result != null && !result.isEmpty()) ? result : "";
        }
        catch (Exception ex){
            Log.e(TAG, "getAdminPassword: "+ex.getMessage());
            ex.printStackTrace();
        }
        return "";
    }

    public String getHostname(){
        try{
            String result = sharedPreferences.getString(KEY_HOSTNAME, null);
            return (result != null && !result.isEmpty()) ? result : "";
        }
        catch (Exception ex){
            Log.e(TAG, "getHostname: "+ex.getMessage());
            ex.printStackTrace();
        }
        return "";
    }

    public String getNameAccess(){
        try{
            String result = sharedPreferences.getString(KEY_NAME_ACCESS, null);
            return (result != null && !result.isEmpty()) ? result : "";
        }
        catch (Exception ex){
            Log.e(TAG, "getNameAccess: "+ex.getMessage());
            ex.printStackTrace();
        }
        return "";
    }

    public void getKeyAes(){
        String result = "";
        try {
            if (sharedPreferences.contains(KEY_AES)){
                AppConstants.AES_KEY = sharedPreferences.getString(KEY_AES, null);
                Log.i(TAG, "getKeyAes: "+ AppConstants.AES_KEY);
            }
            else {
                String newKey = "";
                Random rand = new Random();
                for(int i = 0; i < rand.nextInt((10 - 8) + 1) + 8; i++){
                    newKey += (rand.nextInt((9 - 0) + 1) + 0) + "";
                }
                newKey = newKey.length() + newKey;

                if (!newKey.isEmpty()){
                    editor.putString(KEY_AES, newKey);
                    editor.apply();
                    Log.i(TAG, "getKeyAes: got a new key & saved || "+ newKey);
                    AppConstants.AES_KEY = newKey;
                }
                else
                    Log.e(TAG, "getKeyAes: failed to generate random key aes");
            }
        }
        catch (Exception ex){
            Log.e(TAG, "getKeyAes: "+ ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void reset(){
        try {
            editor.putString(KEY_EMAIL, "");
            editor.putString(KEY_Password, "");
            editor.apply();
            Log.i(TAG, "reset: SUCCESS");
        }
        catch (Exception ex){
            Log.e(TAG, "reset: "+ ex.getMessage());
            ex.printStackTrace();
        }
    }

    public boolean hasEmailPassword(){
        String email = sharedPreferences.getString(KEY_EMAIL, null);
        String pass = sharedPreferences.getString(KEY_Password, null);

        if (email != null){
            if (!email.isEmpty()){
                if (pass != null){
                    if (!pass.isEmpty()){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getEmail(){
        try{
            return sharedPreferences.getString(KEY_EMAIL, null);
        }
        catch (Exception ex){
            Log.e(TAG, "getEmail: "+ ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public String getPassword(){
        try {
            return sharedPreferences.getString(KEY_Password, null);
        }
        catch (Exception ex){
            Log.e(TAG, "getPassword: "+ ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public void printAtSystem(){
        try {
            System.out.println("INFO:\nEmail = "+ sharedPreferences.getString(KEY_EMAIL, null)+
                    "\nPassword = "+ sharedPreferences.getString(KEY_Password, null));
        }
        catch (Exception ex){
            Log.e(TAG, "printAtSystem: "+ ex.getMessage());
            ex.printStackTrace();
        }
    }


}
