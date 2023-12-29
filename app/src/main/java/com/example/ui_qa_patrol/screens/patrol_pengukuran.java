package com.example.ui_qa_patrol.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui_qa_patrol.Helper.AppConstants;
import com.example.ui_qa_patrol.R;
import com.example.ui_qa_patrol.models.ChassisDialog;
import com.example.ui_qa_patrol.models.EstetikaLayout;
import com.example.ui_qa_patrol.models.PengukuranLayout;
import com.example.ui_qa_patrol.models.ProdlineDialog;
import com.example.ui_qa_patrol.models.SizeDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class patrol_pengukuran extends AppCompatActivity {

    final static String TAG = "PatrolPengukuran";
    private Button btnSubmit, btnChassis, btnSize;
    private AdapterView adapter;
    private ImageView imBack;
    String xProses;
    LinearLayout llContent;
    TextInputEditText tietChassis, tietSize;
    ArrayList<PengukuranLayout> listCustomPengukuran = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patrol_pengukuran);

        btnSubmit = findViewById(R.id.pengukuran_submit);
        llContent = findViewById(R.id.llMainPengukuran);
        btnChassis = findViewById(R.id.help_chassis);
        btnSize = findViewById(R.id.help_size);
        imBack = findViewById(R.id.ivBack);
        tietChassis = findViewById(R.id.input_chassis);
        tietSize = findViewById(R.id.input_size);

        tietChassis.setText(AppConstants.chassis);
        tietSize.setText(AppConstants.size);

        imBack.setVisibility(View.VISIBLE);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(patrol_pengukuran.this, ProsesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "onClick: "+ex.getMessage());
                    Toast.makeText(patrol_pengukuran.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }
            }
        });


        btnSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SizeDialog.showHelp(patrol_pengukuran.this, new SizeDialog.CallbackDialog() {
                    @Override
                    public void onResponse(Boolean success, String msg, SizeDialog.DummySize dummySize) {
                        if (success){
                            tietSize.setText(dummySize.getSize());
                            Toast.makeText(patrol_pengukuran.this, msg,Toast.LENGTH_LONG)
                                    .show();
                        }
                        Toast.makeText(patrol_pengukuran.this, msg, Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(patrol_pengukuran.this, msg, Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });


        btnChassis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChassisDialog.showHelp(patrol_pengukuran.this, new ChassisDialog.CallbackDialog() {
                    @Override
                    public void onResponse(Boolean success, String msg, ChassisDialog.DummyChassis dummyChassis) {
                        if (success){
                            tietChassis.setText(dummyChassis.getTipeChassis());
                            Toast.makeText(patrol_pengukuran.this, msg,Toast.LENGTH_LONG)
                                    .show();
                        }
                        Toast.makeText(patrol_pengukuran.this, msg, Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(patrol_pengukuran.this, msg, Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });


        for(int i = 0; i < 3; i++){
            listCustomPengukuran.add(new PengukuranLayout(this));
            llContent.addView(listCustomPengukuran.get(i).getLlContent());
        }

        if(getIntent() != null && getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();

            if(bundle.containsKey("xProses")){
                if(bundle.getString("xProses") != null){
                    xProses = bundle.getString("xProses");
                }
            }
        }


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(patrol_pengukuran.this, TampilanListProsesActivity.class);
                    intent.putExtra("xProses", xProses);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "onClick: "+ex.getMessage());
                    Toast.makeText(patrol_pengukuran.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }
            }
        });
    }
}