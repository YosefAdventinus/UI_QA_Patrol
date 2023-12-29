package com.example.ui_qa_patrol.screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui_qa_patrol.Helper.AppConstants;
import com.example.ui_qa_patrol.LoadingDialog;
import com.example.ui_qa_patrol.R;
import com.example.ui_qa_patrol.models.AES;
import com.example.ui_qa_patrol.models.CaptureAct;
import com.example.ui_qa_patrol.models.GlobalSnackBar;
import com.example.ui_qa_patrol.models.SettingDeviceManager;
import com.example.ui_qa_patrol.models.response.ResponseLoginApps;
import com.example.ui_qa_patrol.sharedpreferences.TokenManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    final static String TAG = "SettingsActivity";
    private EditText etAdminPassword, etConfirmation, etScanLoginJwt;
    private TextView tvTitle;
    private Button btnLoginJwt, btnSave, btnBack;
    private ImageView btnback, ivBack, ivSetting, ivLogout;
    private boolean required = false;
    private LinearLayout llMain;
    private TokenManager tokenManager;
    private String msgError = "";
    private LoadingDialog loadingDialog;
    private SettingDeviceManager sdm;

    private void findId() {
        etAdminPassword = (EditText) findViewById(R.id.etAdminPass);
        etConfirmation = (EditText) findViewById(R.id.etConfirmationPass);
        btnLoginJwt = (Button) findViewById(R.id.btnLoginJwt);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnBack = (Button) findViewById(R.id.btnBack);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivSetting = (ImageView) findViewById(R.id.ivSettings);
        ivLogout = (ImageView) findViewById(R.id.ivLogout);
        llMain = (LinearLayout) findViewById(R.id.llMain);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        try {
            tokenManager = new TokenManager(this);
            loadingDialog = new LoadingDialog(this);
            findId();
            tvTitle.setText("Admin");

            if (getIntent() != null && getIntent().getExtras() != null) {
                Bundle bundle = getIntent().getExtras();
                if (bundle.containsKey("required")) {
                    required = bundle.getBoolean("required");
                }
            }
            if (required)
                ivBack.setVisibility(View.GONE);
            else
                ivBack.setVisibility(View.VISIBLE);

            btnSave.setOnClickListener(this);
            btnLoginJwt.setOnClickListener(this);
            btnBack.setOnClickListener(this);
            ivBack.setOnClickListener(this);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnLoginJwt:
                try {
                    openDialogLoginJwt();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showSnackBar(ex.getMessage(), Snackbar.LENGTH_SHORT, true, false);
                }
                break;
            case R.id.btnBack:
                try {
                    if (required) {
                        if (!tokenManager.hasEmailPassword()) {
                            showMessageOKCancel("Login JWT masih kosong. Harap untuk menyetel Login JWT..\n(Akan keluar dari aplikasi jika memilih lanjut", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int k) {
                                    dialogInterface.dismiss();
                                    setResult(Activity.RESULT_CANCELED, new Intent());
                                    finish();
                                }
                            });
                        } else {
                            setResult(Activity.RESULT_OK, new Intent());
                            finish();
                        }
                    } else {
                        finish();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showSnackBar(ex.getMessage(), Snackbar.LENGTH_SHORT, true, false);
                }
                break;
            case R.id.btnSave:
                try {
                    String addMsg = "";

                    String adminPassword = etAdminPassword.getText().toString();
                    String confirmation = etConfirmation.getText().toString();
                    if (!adminPassword.isEmpty() || !confirmation.isEmpty()) {
                        if (adminPassword.equals(confirmation)) {
                            addMsg += "Admin Password berhasil diganti";
                        } else {
                            showSnackBar("Admin Password dan Confirmation tidak sama", Snackbar.LENGTH_SHORT, true, false);
                            return;
                        }
                    }
                    if (required) {
                        if (!tokenManager.hasEmailPassword()) {
                            showMessageOKCancel("Login JWT masih kosong. Harap untuk menyetel Login JWT..\n(Akan keluar dari aplikasi jika memilih  lanjut)", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int k) {
                                    dialogInterface.dismiss();
                                    setResult(Activity.RESULT_CANCELED, new Intent());
                                    finish();
                                }
                            });
                        } else {
                            setResult(Activity.RESULT_OK, new Intent());
                            finish();
                        }
                    } else {
                        if (!addMsg.isEmpty()) {
                            Toast.makeText(SettingsActivity.this, addMsg, Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showSnackBar(ex.getMessage(), Snackbar.LENGTH_SHORT, true, false);
                }
                break;
            default:
                break;
        }
    }


    private void showSnackBar(String msg, int duration, boolean error, boolean success) {
        GlobalSnackBar.show(this, llMain, msg, duration, error, success);
    }

    @Override
    public void onBackPressed() {
        if (required) {
            if (!tokenManager.hasEmailPassword()) {
                showMessageOKCancel("Login JWT masih kosong. Harap untuk menyetel Login JWT..\n(Akan keluar dari aplikasi jika memilih lanjut)", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        setResult(Activity.RESULT_CANCELED, new Intent());
                        finish();
                    }
                });
            } else {
                setResult(Activity.RESULT_OK, new Intent());
                finish();
            }
        } else
            super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, requestCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null)
                return;
            checkLoginJwtFromQeCode(intentResult.getContents());
            return;
        }

        if (data == null)
            return;
        switch (requestCode) {
            case AppConstants.READ_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String filePath = uri.getPath();
                    Log.i(TAG, "onActivityResult: " + filePath);
                    checkLoginJwt_2(uri);
                }
                break;
            default:
                break;
        }
    }

    String temp = "";
    String email = "", password = "", key = "", hostname = "", nameAccess = "";

    private void checkLoginJwt_2(Uri uri) {
        loadingDialog.startLoadingDialog("Check Login Apps...", false, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(uri);
                                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                                String line;

                                int count = 0;
                                while ((line = br.readLine()) != null) {
                                    if (count == 0)
                                        password = line;
                                    else if (count == 1)
                                        key = AES.decrypt(line, AppConstants.AES_KEY_FIX);
                                    else if (count == 2)
                                        email = line;
                                    else if (count == 3)
                                        hostname = line;
                                    else
                                        nameAccess = line;
                                    count++;
                                }
                                br.close();
                            } catch (IOException ex) {
                                msgError += ex.getMessage();
                                ex.printStackTrace();
                            }
                            if (!email.isEmpty() && !password.isEmpty() && !hostname.isEmpty()) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String enEmail = AES.decrypt(email, key), enPass = AES.decrypt(password, key),
                                                enHostname = AES.decrypt(hostname, key);

                                        ResponseLoginApps.testLoginAppsSynch_2(
                                                SettingsActivity.this,
                                                enHostname,
                                                enEmail,
                                                enPass,
                                                new ResponseLoginApps.ApiCallbackLoginApps() {
                                                    @Override
                                                    public void onResponse(boolean success, String msg) {
                                                        loadingDialog.dismissDialog();

                                                        if (!tokenManager.getHostname().equals(enHostname)) {
                                                            AppConstants.TOKEN = "";
                                                        }
                                                        tokenManager.saveHEPA(enHostname, enEmail, AES.encrypt(enPass, AppConstants.AES_KEY), nameAccess);
                                                        AppConstants.HOSTNAME_URL = enHostname;

                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT)
                                                                        .show();
                                                                try {
                                                                    if (alertDialogJwt != null) {
                                                                        if (alertDialogJwt.isShowing())
                                                                            alertDialogJwt.dismiss();
                                                                    }
                                                                } catch (Exception ex) {
                                                                    ex.printStackTrace();
                                                                }
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onFailure(String msg) {
                                                        loadingDialog.dismissDialog();
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT)
                                                                        .show();
                                                            }
                                                        });
                                                    }
                                                });
                                    }
                                });

                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (password.isEmpty() && email.isEmpty()) {
                                            Toast.makeText(SettingsActivity.this, "Email dan Password ada yang kosong. " + msgError, Toast.LENGTH_SHORT)
                                                    .show();
                                        } else {
                                            if (password.isEmpty())
                                                Toast.makeText(SettingsActivity.this, "Password ada yang kosong. " + msgError, Toast.LENGTH_SHORT)
                                                        .show();
                                            else if (email.isEmpty())
                                                Toast.makeText(SettingsActivity.this, "Email ada yang kosong. " + msgError, Toast.LENGTH_SHORT)
                                                        .show();
                                        }
                                    }
                                });
                            }
                            loadingDialog.dismissDialog();
                        } catch (Exception ex) {
                            loadingDialog.dismissDialog();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SettingsActivity.this, ex.getMessage() + ". " + msgError, Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }, 1000);
    }


    private void checkLoginJwtFromQeCode(String result){
        try {
            String[] resSplit = result.split("\\|");
            if (resSplit.length != 5){
                Log.e(TAG, "checkLoginJwtFromQrCode: " + resSplit.toString());
                Toast.makeText(SettingsActivity.this, "(Failed) Login JWT tidak sesuai format ("+resSplit.length+")", Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            password ="";
            key = "";
            email = "";
            hostname = "";
            nameAccess = "";
            loadingDialog.startLoadingDialog("Check Login Apss...", false, false);
            new  Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                password = resSplit[0];
                                key = AES.decrypt(resSplit[1], AppConstants.AES_KEY_FIX);
                                email = resSplit[2];
                                hostname = resSplit[3];
                                nameAccess = resSplit[4];

                                if (!email.isEmpty() && !password.isEmpty() && !hostname.isEmpty()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String enEmail = AES.decrypt(email, key), enPass = AES.decrypt(password, key), enHostname = AES.decrypt(hostname, key);

                                            ResponseLoginApps.testLoginAppsSynch_2(
                                                    SettingsActivity.this,
                                                    enHostname,
                                                    enEmail,
                                                    enPass,
                                                    new ResponseLoginApps.ApiCallbackLoginApps() {
                                                        @Override
                                                        public void onResponse(boolean success, String msg) {
                                                            loadingDialog.dismissDialog();

                                                            tokenManager.saveHEPA(enHostname, enEmail, AES.encrypt(enPass, AppConstants.AES_KEY), nameAccess);
                                                            AppConstants.HOSTNAME_URL = enHostname;

                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT)
                                                                            .show();
                                                                    try {
                                                                        if (alertDialogJwt != null) {
                                                                            if (alertDialogJwt.isShowing())
                                                                                alertDialogJwt.dismiss();
                                                                        }
                                                                    } catch (Exception ex) {
                                                                        ex.printStackTrace();
                                                                    }
                                                                }
                                                            });
                                                        }

                                                        @Override
                                                        public void onFailure(String msg) {
                                                            loadingDialog.dismissDialog();
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT)
                                                                            .show();
                                                                }
                                                            });
                                                        }
                                                    });
                                        }
                                    });
                                }
                                else {
                                    loadingDialog.dismissDialog();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (password.isEmpty() && email.isEmpty() && hostname.isEmpty()) {
                                                Toast.makeText(SettingsActivity.this, "Hostname, Email, dan Password ada yang kosong. ", Toast.LENGTH_SHORT)
                                                        .show();
                                            } else {
                                                if (password.isEmpty())
                                                    Toast.makeText(SettingsActivity.this, "Password ada yang kosong. ", Toast.LENGTH_SHORT)
                                                            .show();
                                                else if (email.isEmpty())
                                                    Toast.makeText(SettingsActivity.this, "Email ada yang kosong. ", Toast.LENGTH_SHORT)
                                                            .show();
                                                else if (hostname.isEmpty())
                                                    Toast.makeText(SettingsActivity.this, "Hostname ada yang kosong. ", Toast.LENGTH_SHORT)
                                                            .show();
                                            }
                                        }
                                    });
                                }
                            }
                            catch (Exception ex){
                                loadingDialog.dismissDialog();
                                ex.printStackTrace();
                                        Toast.makeText(SettingsActivity.this, ex.getMessage(), Toast.LENGTH_LONG)
                                                .show();
                            }
                        }
                    });
                }
            }, 1000);
        }
        catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void checkLoginJwtFromQrCode(String result){
        try{
            String[] resSplit = result.split("\\|");
            if(resSplit.length != 5){
                Log.e(TAG, "checkLoginJwtFromQrCode: " + resSplit.toString());
                Toast.makeText(this, "(Failed) Login JWT tidak sesuai format ("+resSplit.length+")", Toast.LENGTH_SHORT).show();
                return;
            }

            password = "";
            key = "";
            email = "";
            hostname = "";
            nameAccess = "";
            loadingDialog.startLoadingDialog("Check Login Apps...", false, false);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                password = resSplit[0];
                                key = AES.decrypt(resSplit[1], AppConstants.AES_KEY_FIX);
                                email = resSplit[2];
                                hostname = resSplit[3];
                                nameAccess = resSplit[4];

                                if(!email.isEmpty() && !password.isEmpty() && !hostname.isEmpty()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String enEmail = AES.decrypt(email, key), enPass = AES.decrypt(password, key)
                                                    , enHostname = AES.decrypt(hostname, key);

                                            ResponseLoginApps.testLoginAppsSynch_2(
                                                    SettingsActivity.this,
                                                    enHostname,
                                                    enEmail,
                                                    enPass,
                                                    new ResponseLoginApps.ApiCallbackLoginApps() {
                                                        @Override
                                                        public void onResponse(boolean success, String msg) {
                                                            loadingDialog.dismissDialog();
//                                                        tokenManager.saveHostname(hostname);
//                                                        tokenManager.saveEmailPassword(enEmail, AES.encrypt(enPass, AppConstants.AES_KEY));

                                                            tokenManager.saveHEPA(enHostname, enEmail, AES.encrypt(enPass, AppConstants.AES_KEY), nameAccess);

                                                            // Set Hostname
                                                            AppConstants.HOSTNAME_URL = enHostname;

                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                                    try{
                                                                        if(alertDialogJwt != null){
                                                                            if(alertDialogJwt.isShowing())
                                                                                alertDialogJwt.dismiss();
                                                                        }
                                                                    }
                                                                    catch (Exception ex){
                                                                        ex.printStackTrace();
                                                                    }
                                                                }
                                                            });
                                                        }

                                                        @Override
                                                        public void onFailure(String msg) {
                                                            loadingDialog.dismissDialog();
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    });
                                        }
                                    });
                                }
                                else{
                                    loadingDialog.dismissDialog();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(password.isEmpty() && email.isEmpty() && hostname.isEmpty()){
                                                Toast.makeText(SettingsActivity.this, "Hostname, Email, & Password ada yang kosong. "  + msgError, Toast.LENGTH_SHORT).show();
                                            }
                                            else {
                                                if(password.isEmpty())
                                                    Toast.makeText(SettingsActivity.this, "Password ada yang kosong. " + msgError, Toast.LENGTH_SHORT).show();
                                                else if (email.isEmpty())
                                                    Toast.makeText(SettingsActivity.this, "Email ada yang kosong. " + msgError, Toast.LENGTH_SHORT).show();
                                                else if (hostname.isEmpty())
                                                    Toast.makeText(SettingsActivity.this, "Hostname ada yang kosong. " + msgError, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                            catch (Exception ex){
                                loadingDialog.dismissDialog();
                                ex.printStackTrace();
                                Toast.makeText(SettingsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }, 1000);
        }
        catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    AlertDialog alertDialogJwt = null;
    private void openDialogLoginJwt(){
        try {
            if (alertDialogJwt != null)
                alertDialogJwt = null;
            if (etScanLoginJwt != null)
                etScanLoginJwt = null;

            AlertDialog.Builder dialog;
            LayoutInflater inflater;
            final View dialogView;

            inflater = getLayoutInflater();
            dialogView = inflater.inflate(R.layout.dialog_form_login_jwt, null);

            dialog = new AlertDialog.Builder(this)
                    .setView(dialogView);
            alertDialogJwt = dialog.show();
            alertDialogJwt.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    etScanLoginJwt = null;
                }
            });

            etScanLoginJwt = (EditText) dialogView.findViewById(R.id.etScanLoginJwt);
            Button btnBrowse = (Button) dialogView.findViewById(R.id.btnBrowseFile);
            Button btnOpenCam = (Button) dialogView.findViewById(R.id.btnOpenCamera);
            Button btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            etScanLoginJwt.requestFocus();

            etScanLoginJwt.addTextChangedListener(new TextWatcher() {
                boolean _ignore = false;

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (_ignore)
                        return;

                    _ignore = true;
                    try {
                        Log.i(TAG, "afterTextChanged: " + editable);
                        if (editable.length() > 0){
                            if (editable.toString().endsWith("\n")){
                                String getResScan = editable.toString();
                                etScanLoginJwt.setText("");
                                checkLoginJwtFromQrCode(getResScan.substring(0, getResScan.length() - 1));
                            }
                        }
                    }
                    catch (Exception ex){
                        Log.e(TAG, "onTextChanged: "+ ex.getMessage());
                        ex.printStackTrace();
                    }
                    _ignore = false;
                }
            });

            btnBrowse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
                        fileintent.setType("*/*");
                        try {
                            startActivityForResult(fileintent, AppConstants.READ_REQUEST_CODE);
                        }
                        catch (ActivityNotFoundException ex){
                            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
                        }
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                        Toast.makeText(SettingsActivity.this, ex.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });

            btnOpenCam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        IntentIntegrator intentIntegrator = new IntentIntegrator(SettingsActivity.this);
                        intentIntegrator.setPrompt("Scan a barcode or QR Code");
                        intentIntegrator.setBeepEnabled(true);
                        intentIntegrator.setOrientationLocked(true);
                        intentIntegrator.setCaptureActivity(CaptureAct.class);
                        intentIntegrator.initiateScan();
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                        Toast.makeText(SettingsActivity.this, ex.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        alertDialogJwt.dismiss();
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                        Toast.makeText(SettingsActivity.this, ex.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });

        }
        catch (Exception ex){
            if (alertDialogJwt != null){
                if(alertDialogJwt.isShowing())
                    alertDialogJwt.dismiss();
            }
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Lanjut", okListener)
                .setNegativeButton("Batal", null)
                .create()
                .show();
    }

}