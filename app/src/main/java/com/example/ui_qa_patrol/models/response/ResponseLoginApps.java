package com.example.ui_qa_patrol.models.response;

import android.app.Activity;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.RequiresApi;


import com.example.ui_qa_patrol.Helper.AppConstants;
import com.example.ui_qa_patrol.models.AES;
import com.example.ui_qa_patrol.models.jwt.JWTToken;
import com.example.ui_qa_patrol.sharedpreferences.TokenManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResponseLoginApps {

    public interface ApiCallbackLoginApps {
        void onResponse(boolean success, String msg);
        void onFailure(String msg);
    }

    static final String TAG = "ResponseLoginApps";
    static JWTToken jwtToken;

    // Update [21-01-2023]
    // Synchronous testing login apps
    public static void testLoginAppsSynch(Activity activity, String email, String password, ApiCallbackLoginApps callback){
        try{
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy gfgPolicy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(gfgPolicy);
            }

            if(!AppConstants.isInternetConnected(activity)){
                callback.onFailure("Mohon untuk mengaktifkan internet...");
                return;
            }

            if(!AppConstants.isServerReachable()){
                callback.onFailure("Hostname \"" + AppConstants.HOSTNAME_URL + "\" is Unreachable...");
                return;
            }

            //String decrypt = AES.decrypt(password, AppConstants.AES_KEY);
//            Log.i(TAG, "loginAppsSynch (decrypt): " + password);

            Call<ResponseBody> call = jwtToken.apiCall.loginApps(email, password);
            Response<ResponseBody> response = call.execute();
            if(response.isSuccessful()){
                String strRes = "";
                try {
                    strRes = response.body().string();
                    Log.d(TAG, "onResponse: " + strRes);
                    JSONObject jsonObject = new JSONObject(strRes);
                    if(!jsonObject.isNull("error")){
                        callback.onFailure(jsonObject.getString("error"));
                        return;
                    }

                    if(jsonObject.isNull("status")){
                        callback.onFailure("Field \"status\" tidak ketemu");
                        return;
                    }

                    if(jsonObject.isNull("token")){
                        callback.onFailure("Field \"token\" tidak ketemu");
                        return;
                    }

                    if(!jsonObject.getBoolean("status")){
                        callback.onResponse(false, "Test Login Apps gagal...");
                        return;
                    }

                    Call<ResponseBody> callLogOut = jwtToken.apiCall.logOutApps("application/json", "Bearer " + jsonObject.getString("token"));
                    callLogOut.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            String resBodyStr = "";
                            try{
                                String msgError = "";
                                if(response.isSuccessful()){
                                    resBodyStr = response.body().string();
                                    Log.d(TAG, "onResponse: " + resBodyStr);
                                    JSONObject jsonObject = new JSONObject(resBodyStr);

                                    // if field "error" is found
                                    if(!jsonObject.isNull("error")){
                                        Log.e(TAG, "onResponse: " + jsonObject.getString("error"));
                                    }
                                    // if field "success" is null
                                    if(jsonObject.isNull("status")){
                                        callback.onFailure("Field \"status\" tidak ketemu");
                                        return;
                                    }
                                    // if field "message" is null
                                    if(jsonObject.isNull("message")){
                                        callback.onFailure("Field \"message\" tidak ketemu");
                                        return;
                                    }
                                    if(!jsonObject.getBoolean("status")){
                                        callback.onResponse(false, "Logout Apps gagal...");
                                        return;
                                    }

                                    Log.i(TAG, "onResponse: Logout Apps berhasil");
                                }
                                else {
                                    try {
                                        resBodyStr = response.errorBody().string();
                                        Log.d(TAG, "onResponseUnsuccessful: " + resBodyStr);
                                        JSONObject jsonObject = new JSONObject(resBodyStr);
                                        if(!jsonObject.isNull("error")){
                                            msgError = jsonObject.getString("error");
                                        }
                                    } catch (JSONException | IOException e) {
                                        msgError = e.getMessage();
                                        e.printStackTrace();
                                    }

                                    if(msgError.equals("")){
                                        msgError = "Login gagal.";
                                    }

                                    Log.e(TAG, "onResponseUnsuccessfulLogin: " + msgError);
                                }
                            }
                            catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                    callback.onResponse(true, "Test Login Apps berhasil");
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailure(e.getMessage());
                }
            }
            else {
                String msgError = "";
                try {
                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                    msgError = jsonObject.toString();
                } catch (JSONException | IOException e) {
                    msgError = e.getMessage();
                    e.printStackTrace();
                }

                if(msgError.equals("")){
                    msgError = "Login Failure.";
                }

                callback.onFailure(msgError);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            callback.onFailure(e.getMessage());
        }
    }

    public static void testLoginAppsSynch_2(Activity activity, String hostname, String email, String password, ApiCallbackLoginApps callback){
        try{
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy gfgPolicy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(gfgPolicy);
            }

            if(!AppConstants.isInternetConnected(activity)){
                callback.onFailure("Mohon untuk mengaktifkan internet...");
                return;
            }

            if(!AppConstants.isServerReachable(hostname)){
                callback.onFailure("Hostname \"" + hostname + "\" is Unreachable...");
                return;
            }

            //String decrypt = AES.decrypt(password, AppConstants.AES_KEY);
//            Log.i(TAG, "loginAppsSynch (decrypt): " + password);

            JWTToken jwtToken = new JWTToken(hostname);
            Call<ResponseBody> call = jwtToken.apiCall.loginApps(email, password);
            Response<ResponseBody> response = call.execute();
            if(response.isSuccessful()){
                String strRes = "";
                try {
                    strRes = response.body().string();
                    Log.d(TAG, "onResponse: " + strRes);
                    JSONObject jsonObject = new JSONObject(strRes);
                    if(!jsonObject.isNull("error")){
                        callback.onFailure(jsonObject.getString("error"));
                        return;
                    }

                    if(jsonObject.isNull("status")){
                        callback.onFailure("Field \"status\" tidak ketemu");
                        return;
                    }

                    if(jsonObject.isNull("token")){
                        callback.onFailure("Field \"token\" tidak ketemu");
                        return;
                    }

                    if(!jsonObject.getBoolean("status")){
                        callback.onResponse(false, "Test Login Apps gagal...");
                        return;
                    }

                    Call<ResponseBody> callLogOut = jwtToken.apiCall.logOutApps("application/json", "Bearer " + jsonObject.getString("token"));
                    callLogOut.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            String resBodyStr = "";
                            try{
                                String msgError = "";
                                if(response.isSuccessful()){
                                    resBodyStr = response.body().string();
                                    Log.d(TAG, "onResponse: " + resBodyStr);
                                    JSONObject jsonObject = new JSONObject(resBodyStr);

                                    // if field "error" is found
                                    if(!jsonObject.isNull("error")){
                                        Log.e(TAG, "onResponse: " + jsonObject.getString("error"));
                                    }
                                    // if field "success" is null
                                    if(jsonObject.isNull("status")){
                                        callback.onFailure("Field \"status\" tidak ketemu");
                                        return;
                                    }
                                    // if field "message" is null
                                    if(jsonObject.isNull("message")){
                                        callback.onFailure("Field \"message\" tidak ketemu");
                                        return;
                                    }
                                    if(!jsonObject.getBoolean("status")){
                                        callback.onResponse(false, "Logout Apps gagal...");
                                        return;
                                    }

                                    Log.i(TAG, "onResponse: Logout Apps berhasil");
                                }
                                else {
                                    try {
                                        resBodyStr = response.errorBody().string();
                                        Log.d(TAG, "onResponseUnsuccessful: " + resBodyStr);
                                        JSONObject jsonObject = new JSONObject(resBodyStr);
                                        if(!jsonObject.isNull("error")){
                                            msgError = jsonObject.getString("error");
                                        }
                                    } catch (JSONException | IOException e) {
                                        msgError = e.getMessage();
                                        e.printStackTrace();
                                    }

                                    if(msgError.equals("")){
                                        msgError = "Login gagal.";
                                    }

                                    Log.e(TAG, "onResponseUnsuccessfulLogin: " + msgError);
                                }
                            }
                            catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                    callback.onResponse(true, "Test Login Apps berhasil");
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailure(e.getMessage());
                }
            }
            else {
                String msgError = "";
                try {
                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                    msgError = jsonObject.toString();
                } catch (JSONException | IOException e) {
                    msgError = e.getMessage();
                    e.printStackTrace();
                }

                if(msgError.equals("")){
                    msgError = "Login Failure.";
                }

                callback.onFailure(msgError);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            callback.onFailure(e.getMessage());
        }
    }

    public static void refreshToken(TokenManager tokenManager, final ApiCallbackLoginApps callback){
        try{
            Call<ResponseBody> call = jwtToken.apiCall.refreshToken("application/json", "Bearer ?");
            call.enqueue(new Callback<ResponseBody>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            Log.d(TAG, "onResponse: " + jsonObject.toString());
                            if(!jsonObject.isNull("error")){
                                if(jsonObject.getString("error").equals("Token not provided") || jsonObject.getString("error").equals("TOKEN_EXPIRED") || jsonObject.getString("error").equals("Token is expired")){
                                    Log.i(TAG, "onResponse: " + jsonObject.getString("error") + ". Try to login...");
//                                    loginApps();

                                    callback.onResponse(false, "");
                                }
                                else{
                                    Log.e(TAG, "onResponseError: " + jsonObject.getString("error") );
                                    callback.onResponse(false, "");
                                }
                            }
                            else{
                                if(jsonObject.getBoolean("status")){
                                    System.out.println("New Token After Refresh Old Token = " + jsonObject.getString("access_token"));
//                                    tokenManager.saveToken(jsonObject.getString("access_token"));
                                    callback.onResponse(true, "");
                                }
                                else{
                                    Log.i(TAG, "onResponse: Login Failure. Try to login...");
                                    callback.onResponse(false, "");
                                }
                            }
                        } catch (JSONException | IOException e) {
                            Log.e(TAG, "onResponseErrorRefreshLogin: " + e.getMessage());
                            e.printStackTrace();
                            callback.onResponse(false, "");
                        }
                    }
                    else{
                        String msgError = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            msgError = jsonObject.toString();
                        } catch (JSONException | IOException e) {
                            msgError = e.getMessage();
                            e.printStackTrace();
                        }

                        if(msgError.equals("")){
                            msgError = "Refresh Token Failure. Try to Login...";
                        }
                        else{
                            msgError += " - Try to login...";
                        }

                        Log.e(TAG, "onResponseUnsuccessfulRefreshToken: " + msgError);
                        callback.onResponse(false, "");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    Log.e(TAG, "onFailureRefreshToken: " + throwable.getMessage());
                    throwable.printStackTrace();
                    callback.onResponse(false, "");
                }
            });
        }
        catch (Exception e){
            Log.e(TAG, "refreshToken: " + e.getMessage());
            e.printStackTrace();
            callback.onResponse(false, "");
        }
    }

    // Asynchronous
    public static void loginApps(TokenManager tokenManager, final ApiCallbackLoginApps callback){
        try{
            String decrypt = AES.decrypt(tokenManager.getPassword(), AppConstants.AES_KEY);
            Call<ResponseBody> call = jwtToken.apiCall.loginApps(tokenManager.getEmail(), decrypt);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            Log.d(TAG, "onResponse: " + jsonObject.toString());
                            if(!jsonObject.isNull("error")){
                                Log.e(TAG, "onResponseErrorLogin: " + jsonObject.getString("error"));
                                callback.onResponse(false, jsonObject.getString("error"));
                            }
                            else{
                                if(jsonObject.getBoolean("status")){
                                    Log.i(TAG, "onResponseLogin: New Token = " + jsonObject.getString("token"));
//                                    tokenManager.saveToken(jsonObject.getString("token"));
                                    callback.onResponse(true, "");
                                }
                                else{
                                    Log.e(TAG, "onResponseErrorLogin: Login Gagal");
                                    callback.onResponse(false, "Login Aplikasi Gagal");
                                }
                            }

                        } catch (JSONException | IOException e) {
                            Log.e(TAG, "onResponseErrorLogin: " + e.getMessage());
                            e.printStackTrace();
                            callback.onResponse(false, e.getMessage());
                        }
                    }
                    else {
                        String msgError = "";
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            msgError = jsonObject.toString();
                        } catch (JSONException | IOException e) {
                            msgError = e.getMessage();
                            e.printStackTrace();
                        }

                        if(msgError.equals("")){
                            msgError = "Login Failure.";
                        }

                        Log.i(TAG, "onResponseUnsuccessfulLogin: " + msgError);
                        callback.onResponse(false, msgError);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                    Log.e(TAG, "onFailureLogin: " + throwable.getMessage());
                    throwable.printStackTrace();
                    callback.onResponse(false, throwable.getMessage());
                }
            });
        }
        catch (Exception e){
            Log.e(TAG, "login: " + e.getMessage());
            e.printStackTrace();
            callback.onResponse(false, e.getMessage());
        }
    }

    // Synchronous
    public static boolean loginAppsSynch(TokenManager tokenManager){
        try{
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy gfgPolicy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(gfgPolicy);
            }

            String decrypt = AES.decrypt(tokenManager.getPassword(), AppConstants.AES_KEY);
            Call<ResponseBody> call = jwtToken.apiCall.loginApps(tokenManager.getEmail(), decrypt);
            Response<ResponseBody> response = call.execute();
            if(response.isSuccessful()){
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d(TAG, "onResponse: " + jsonObject.toString());
                    if(!jsonObject.isNull("error")){
                        Log.e(TAG, "onResponseErrorLogin: " + jsonObject.getString("error"));
                        return false;
                    }
                    else{
                        if(jsonObject.getBoolean("status")){
                            Log.i(TAG, "onResponseLogin: New Token = " + jsonObject.getString("token"));
//                            tokenManager.saveToken(jsonObject.getString("token"));
                            return true;
                        }
                        else{
                            Log.e(TAG, "onResponseErrorLogin: Login Gagal");
                            return false;
                        }
                    }

                } catch (JSONException | IOException e) {
                    Log.e(TAG, "onResponseErrorLogin: " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            }
            else {
                String msgError = "";
                try {
                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                    msgError = jsonObject.toString();
                } catch (JSONException | IOException e) {
                    msgError = e.getMessage();
                    e.printStackTrace();
                }

                if(msgError.equals("")){
                    msgError = "Login Failure.";
                }

                Log.i(TAG, "onResponseUnsuccessfulLogin: " + msgError);
                return false;
            }
        }
        catch (Exception e){
            Log.e(TAG, "login: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }


    // Update [24-06-2022]
    // Synchronous
    public static boolean loginAppsSynch(String email, String password){
        try{
            JWTToken jwtToken = new JWTToken();

            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy gfgPolicy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(gfgPolicy);
            }

            String decrypt = AES.decrypt(password, AppConstants.AES_KEY);
            Log.i(TAG, "loginAppsSynch (decrypt): " + decrypt);
            Call<ResponseBody> call = jwtToken.apiCall.loginApps(email, decrypt);
            Response<ResponseBody> response = call.execute();
            if(response.isSuccessful()){
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d(TAG, "onResponse: " + jsonObject.toString());
                    if(!jsonObject.isNull("error")){
                        AppConstants.TOKEN = (AppConstants.TOKEN.equals("")) ? "?" : AppConstants.TOKEN;
                        Log.e(TAG, "onResponseErrorLogin: " + jsonObject.getString("error"));
                        return false;
                    }
                    else{
                        if(jsonObject.getBoolean("status")){
                            Log.i(TAG, "onResponseLogin: New Token = " + jsonObject.getString("token"));
//                            tokenManager.saveToken(jsonObject.getString("token"));
                            AppConstants.TOKEN = jsonObject.getString("token");
//                            AppConstants.REFRESH_TOKEN_DURATION = 30;
                            AppConstants.REFRESH_TOKEN_DURATION = jsonObject.getLong("expires") - 300;

                            return true;
                        }
                        else{
                            AppConstants.TOKEN = (AppConstants.TOKEN.equals("")) ? "?" : AppConstants.TOKEN;
                            Log.e(TAG, "onResponseErrorLogin: Login Apps Gagal. Akan login ulang selama 10 Detik...");
                            return false;
                        }
                    }

                } catch (JSONException | IOException e) {
                    AppConstants.TOKEN = (AppConstants.TOKEN.equals("")) ? "?" : AppConstants.TOKEN;
                    Log.e(TAG, "onResponseErrorLogin: " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            }
            else {
                AppConstants.TOKEN = (AppConstants.TOKEN.equals("")) ? "?" : AppConstants.TOKEN;
                String msgError = "";
                try {
                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                    msgError = jsonObject.toString();
                } catch (JSONException | IOException e) {
                    msgError = e.getMessage();
                    e.printStackTrace();
                }

                if(msgError.equals("")){
                    msgError = "Login Failure.";
                }

                Log.i(TAG, "onResponseUnsuccessfulLogin: " + msgError);
                return false;
            }
        }
        catch (Exception e){
            AppConstants.TOKEN = (AppConstants.TOKEN.equals("")) ? "?" : AppConstants.TOKEN;
            Log.e(TAG, "login: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public static boolean loginAppsSynch_2(String email, String password){
        try{
            JWTToken jwtToken = new JWTToken();

            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy gfgPolicy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(gfgPolicy);
            }

            String decrypt = AES.decrypt(password);
//            Log.e(TAG, "Password : " + password + "loginAppsSynch (decrypt): " + decrypt);
            Call<ResponseBody> call = jwtToken.apiCall.loginApps(email, decrypt);
            Response<ResponseBody> response = call.execute();
            if(response.isSuccessful()){
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    response.body().close();
                    Log.d(TAG, "onResponse: " + jsonObject.toString());

                    if(!jsonObject.isNull("error")){
                        AppConstants.TOKEN = (AppConstants.TOKEN.equals("")) ? "?" : AppConstants.TOKEN;
                        Log.e(TAG, "onResponseErrorLogin: " + jsonObject.getString("error"));
                        return false;
                    }
                    if(jsonObject.isNull("status")){
                        Log.e(TAG, "field \"status\" tidak ada...");
                        return false;
                    }
                    if(jsonObject.isNull("token")){
                        Log.e(TAG, "field \"token\" tidak ada...");
                        return false;
                    }
                    if(!jsonObject.getBoolean("status")){
                        Log.e(TAG, "login gagal...");
                        return false;
                    }

                    AppConstants.TOKEN = jsonObject.getString("token");
                    return true;
                } catch (JSONException | IOException e) {
                    response.body().close();
                    AppConstants.TOKEN = "";
                    Log.e(TAG, "onResponseErrorLogin: " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            }
            else {
                AppConstants.TOKEN = (AppConstants.TOKEN.equals("")) ? "?" : AppConstants.TOKEN;
                String msgError = "";
                try {
                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                    response.errorBody().close();
                    msgError = jsonObject.toString();
                } catch (JSONException | IOException e) {
                    msgError = e.getMessage();
                    e.printStackTrace();
                }

                if(msgError.equals("")){
                    msgError = "Login Failure.";
                }

                Log.i(TAG, "onResponseUnsuccessfulLogin: " + msgError);
                return false;
            }
        }
        catch (Exception e){
            AppConstants.TOKEN = (AppConstants.TOKEN.equals("")) ? "?" : AppConstants.TOKEN;
            Log.e(TAG, "login: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Synchronous
    public static void refreshTokenSynch(String email, String password) throws Exception {
        try{
            jwtToken = new JWTToken();

            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy gfgPolicy =
                        new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(gfgPolicy);
            }

            Call<ResponseBody> call = jwtToken.apiCall.refreshToken("application/json", "Bearer " + AppConstants.TOKEN);
            Response<ResponseBody> response = call.execute();
            if(response.isSuccessful()){
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d(TAG, "onResponse: " + jsonObject.toString());
                    if(!jsonObject.isNull("error")){
                        if(jsonObject.getString("error").equals("Token not provided") || jsonObject.getString("error").equals("TOKEN_EXPIRED") || jsonObject.getString("error").equals("Token is expired")){
                            Log.i(TAG, "onResponse: " + jsonObject.getString("error") + ". Try to login...");
//                                    loginApps();

                            if(!loginAppsSynch(email, password)){
                                throw new Exception("Login Apps Gagal. Akan login ulang selama 10 detik...");
                            }
                        }
                        else{
                            Log.e(TAG, "onResponseError: " + jsonObject.getString("error") );
                            throw new Exception(jsonObject.getString("error") + ". Akan login ulang selama 10 detik...");
                        }
                    }
                    else{
                        if(jsonObject.getBoolean("status")){
                            System.out.println("New Token After Refresh Old Token = " + jsonObject.getString("access_token"));
//                                    tokenManager.saveToken(jsonObject.getString("access_token"));

                            AppConstants.TOKEN = jsonObject.getString("access_token");
//                            AppConstants.REFRESH_TOKEN_DURATION = 30;
                            AppConstants.REFRESH_TOKEN_DURATION = jsonObject.getLong("expires_in") - 300;
                        }
                        else{
                            Log.i(TAG, "onResponse: Login Failure. Try to login...");
                            if(!loginAppsSynch(email, password)){
                                throw new Exception("onResponseErrorLogin: Login Apps Gagal. Akan login ulang selama 10 detik...");
                            }
                        }
                    }
                } catch (JSONException | IOException e) {
                    Log.e(TAG, "onResponseErrorRefreshLogin: " + e.getMessage());
                    e.printStackTrace();
                    throw e;
                }
            }
            else{
                String msgError = "";
                try {
                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                    msgError = jsonObject.toString();
                } catch (JSONException | IOException e) {
                    msgError = e.getMessage();
                    e.printStackTrace();
                }

                if(msgError.equals("")){
                    msgError = "Refresh Token Failure. Try to Login...";
                }
                else{
                    msgError += " - Try to login...";
                }

                Log.e(TAG, "onResponseUnsuccessfulRefreshToken: " + msgError);
                throw new Exception(msgError);
            }
        }
        catch (Exception e){
            Log.e(TAG, "login: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
