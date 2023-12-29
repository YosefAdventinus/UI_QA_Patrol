package com.example.ui_qa_patrol.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui_qa_patrol.Helper.AppConstants;
import com.example.ui_qa_patrol.R;
import com.example.ui_qa_patrol.models.ListProsesLayout;
import com.example.ui_qa_patrol.models.PengukuranLayout;
import com.example.ui_qa_patrol.models.ProsesDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class TampilanListProsesActivity extends AppCompatActivity {

    final static String TAG = "TampilanListActivity";
    private ImageView btnBack;
    private Button btnSelesai, btnCancel, btnAddProcess;
    private TextView tvProses1;
    TextInputEditText tietUsername, tietProdline;
    String xProses = "";
    LinearLayout llContentProses;
    ArrayList<ListProsesLayout> listCustomProses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampilan_list_proses);

        btnBack = findViewById(R.id.ivBack);
        btnBack.setVisibility(View.VISIBLE);
        btnSelesai = findViewById(R.id.btnSelesai1);
        btnCancel = findViewById(R.id.btnCancel1);
        tvProses1 = findViewById(R.id.tvProses1);
        btnAddProcess = findViewById(R.id.btnAdd);
        llContentProses = findViewById(R.id.llMainProses);
        tietUsername = findViewById(R.id.input_inspektorList);
        tietProdline = findViewById(R.id.input_prodList);

        tietProdline.setText(AppConstants.prodline);
        tietUsername.setText(AppConstants.username);

        for(int i = 0; i < 3; i++){
            listCustomProses.add(new ListProsesLayout(this));
            llContentProses.addView(listCustomProses.get(i).getLlContentProses());
        }


        btnAddProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProsesDialog.showHelp(TampilanListProsesActivity.this, new ProsesDialog.CallbackDialog() {
                    @Override
                    public void onResponse(Boolean success, String msg, ProsesDialog.DummyProduk dummyProduk) {
                        if (success){
                            Toast.makeText(TampilanListProsesActivity.this, "Tipe Pemeriksaan = " + dummyProduk.getTipePemeriksaan(), Toast.LENGTH_LONG)
                                    .show();

                            if(dummyProduk.getTipePemeriksaan().equals("pengukuran")){
                                // Intent ke pengukuran
                                Intent intent = new Intent(TampilanListProsesActivity.this, patrol_pengukuran.class);
                                intent.putExtra("xProses", dummyProduk.getProses());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else{
                                // Intent ke visit/estetika
                                Intent intent = new Intent(TampilanListProsesActivity.this, pipe_bending.class);
                                intent.putExtra("xProses", dummyProduk.getProses());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            return;
                        }
                        Toast.makeText(TampilanListProsesActivity.this, msg, Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(TampilanListProsesActivity.this, msg, Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });


        if(getIntent() != null && getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();

            if(bundle.containsKey("xProses")){
                if(bundle.getString("xProses") != null){
                    xProses = bundle.getString("xProses");
                }
            }
        }

        if(xProses != ""){
            tvProses1.setText(xProses);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(TampilanListProsesActivity.this,ProsesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "onClick: "+ex.getMessage());
                    Toast.makeText(TampilanListProsesActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }
            }
        });

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(TampilanListProsesActivity.this,DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "onClick: "+ex.getMessage());
                    Toast.makeText(TampilanListProsesActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(TampilanListProsesActivity.this,ProsesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "onClick: "+ex.getMessage());
                    Toast.makeText(TampilanListProsesActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }
            }
        });


    }
}