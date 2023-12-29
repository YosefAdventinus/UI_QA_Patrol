package com.example.ui_qa_patrol.Helper;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class AppConstants {
    final static String TAG = "APP_CONSTANTS";
    public static int RELEASE_VERSION = 0;
    public static String HOSTNAME_URL = "", NAME_ACCESS = "";
    public static String TOKEN = "";
    public static long REFRESH_TOKEN_DURATION = 10;
    public static String AES_KEY = "";
    public static String AES_KEY_FIX = "hJ6Gj9vLUEkDqikK0M7d";
    public static String DEVICE_ID = "";
    public static boolean CHECK_UPDATE_APK = true;
    public static String username = "";
    public static String prodline = "";
    public static String startTime = "";
    public static String chassis = "";
    public static String size = "";
    public static String localTime = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    public static final int PERMISSION_REQUEST_CODE = 200, INSTALLED_REQUEST_CODE = 300, READ_REQUEST_CODE = 400;
    public static String GET_SERVER_LAST_MOD = "", ROLLBACK_LAST_MOD = "", LOCAL_LAST_MOD = "";


    public static boolean isServerReachable() {
        try{
//            URL url = new URL(BuildConfig.NAME_URL);
            URL url = new URL(HOSTNAME_URL);
//            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setSSLSocketFactory(getSSLcontext().getSocketFactory());
            connection.connect();
            int state = connection.getResponseCode();
            return (state== HttpURLConnection.HTTP_OK);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isServerReachable(String hostname) {
        try{
            URL url = new URL(hostname);
//            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setSSLSocketFactory(getSSLcontext().getSocketFactory());
            connection.connect();
            int state = connection.getResponseCode();
            return (state== HttpURLConnection.HTTP_OK);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isInternetConnected(Context context){
        try{
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        catch (Exception ex){
            Log.e("APP_CONSTANTS", "isInternetConnected: "+ ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    public static String getDateNow(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            return dtf.format(now);
        }
        return "?";
    }

    public static boolean checkPermission(Activity activity){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            return false;
        }
        return true;
    }

//    public static String getLocalLastMod(ConfigQuery db) {
//        String result = "";
//        List<Config> configs = new ArrayList<>(db.searchConfigs("tvar=?",new String[]{"apklastmod"},null,null, null, null));
//        if(!configs.isEmpty())
//        {
//            result = configs.get(0).getTval();
//        } else {
//            db.insertConfig("apklastmod","");
//        }
//        return result;
//    }

    public static SSLContext getSSLcontext() throws NoSuchAlgorithmException, KeyManagementException{
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{
                    new X509TrustManager(){
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[]{}; }
                        }
                    }, null);
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return ctx;
        }
        catch (Exception ex){
            throw ex;
        }
    }



}
