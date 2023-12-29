package com.example.ui_qa_patrol.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ui_qa_patrol.Helper.AppConstants;
import com.example.ui_qa_patrol.R;
import com.example.ui_qa_patrol.models.ProsesDialog;
import com.google.android.material.textfield.TextInputEditText;


public class ProsesActivity extends AppCompatActivity {

    final static String TAG = "ProcesActivity";
    ImageView btnBack;
    private Button btnHelpPrs;
    TextInputEditText tietInputProd, tietUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilan_proses);


        btnBack = findViewById(R.id.ivBack);
        btnBack.setVisibility(View.VISIBLE);
        btnHelpPrs = findViewById(R.id.help_proses);
        tietInputProd = findViewById(R.id.input_prodProses);
        tietUsername = findViewById(R.id.input_inspektorProses);


        tietInputProd.setText(AppConstants.prodline);
        tietUsername.setText(AppConstants.username);





        btnHelpPrs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProsesDialog.showHelp(ProsesActivity.this, new ProsesDialog.CallbackDialog() {
                    @Override
                    public void onResponse(Boolean success, String msg, ProsesDialog.DummyProduk dummyProduk) {
                        if (success){
                            Toast.makeText(ProsesActivity.this, "Tipe Pemeriksaan = " + dummyProduk.getTipePemeriksaan(), Toast.LENGTH_LONG)
                                    .show();

                            if(dummyProduk.getTipePemeriksaan().equals("pengukuran")){
                                // Intent ke pengukuran
                                Intent intent = new Intent(ProsesActivity.this, patrol_pengukuran.class);
                                intent.putExtra("xProses", dummyProduk.getProses());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else{
                                // Intent ke visit/estetika
                                Intent intent = new Intent(ProsesActivity.this, pipe_bending.class);
                                intent.putExtra("xProses", dummyProduk.getProses());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            return;
                        }
                        Toast.makeText(ProsesActivity.this, msg, Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(ProsesActivity.this, msg, Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(ProsesActivity.this, QCpatrolActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "OnClick: "+ex.getMessage());
                    Toast.makeText(ProsesActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }
            }
        });

    }
}