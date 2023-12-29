package com.example.ui_qa_patrol.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ui_qa_patrol.Helper.AppConstants;
import com.example.ui_qa_patrol.R;
import com.example.ui_qa_patrol.models.UserDummy;
import com.example.ui_qa_patrol.models.UserDummyList;


public class LoginActivity extends AppCompatActivity {

    final static String TAG = "LoginActivity";
    private EditText etNik, etPassword;
    private Button btnLogin, btnSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etNik = findViewById(R.id.input_nik);
        etPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSettings = findViewById(R.id.btnSettings);

        UserDummyList userDummyList = new UserDummyList();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (etNik.getText().toString().length() == 0) {
                        etNik.setError("NIK tidak boleh kosong!");
                        return;
                    }
                    if (etPassword.getText().toString().length() == 0) {
                        etPassword.setError("Password tidak boleh kosong!");
                        return;
                    }

                    if (etNik.getText().toString().length() == 0) {
                        etNik.setError("NIK tidak boleh kosong!");
                        return;
                    }
                    if (etPassword.getText().toString().length() == 0) {
                        etPassword.setError("Password tidak boleh kosong!");
                        return;
                    }
                    int find = 0;
                    String username = "";
                    for (UserDummy user : userDummyList.getArrayList()){
                        if (user.getNik().equals(etNik.getText().toString()) && user.getPassword().equals(etPassword.getText().toString())){
                            find = 1;
                            username = user.getNama();
                        }
                    }
                    if (find == 0){
                        Toast.makeText(LoginActivity.this, "User tidak ditemukan!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    AppConstants.username = username;
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception exception){
                    Log.e(TAG, "onClick: "+ exception.getMessage() );
                    Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    exception.printStackTrace();
                }
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(LoginActivity.this, SettingsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "onClick: "+ ex.getMessage() );
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        });
    }
    }