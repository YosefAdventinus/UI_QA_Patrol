package com.example.ui_qa_patrol.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ui_qa_patrol.R;

public class DashboardActivity extends AppCompatActivity {

    final static String TAG = "DashboardActivity";
    private Button btnQCpatrol, btnUpload, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        btnQCpatrol = findViewById(R.id.btnQCPatrol);
        btnUpload = findViewById(R.id.btnUploadData);
        btnLogout = findViewById(R.id.btnLogout);

        btnQCpatrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(DashboardActivity.this, QCpatrolActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "onClick: "+ex.getMessage());
                    Toast.makeText(DashboardActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "onClick: "+ex.getMessage());
                    Toast.makeText(DashboardActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }
            }
        });

    }
}