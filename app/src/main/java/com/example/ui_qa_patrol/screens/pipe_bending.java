package com.example.ui_qa_patrol.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui_qa_patrol.Helper.AppConstants;
import com.example.ui_qa_patrol.R;
import com.example.ui_qa_patrol.models.ConfirmationDialog;
import com.example.ui_qa_patrol.models.EstetikaLayout;
import com.example.ui_qa_patrol.models.SizeDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class pipe_bending extends AppCompatActivity {

    final static String TAG = "PipeBending";
    private Button btnSubmit;
    String xProses;
    ImageView imBack;
    LinearLayout llContent, llMainEstetika, llContentEstetika_2;
    TextView tvItemPemeriksaan, tvDetailItem, tvLocalTime;
    TextInputLayout tilQty;
    TextInputEditText tietQty;
    RadioGroup rgChoise;
    ArrayList<EstetikaLayout> listCustomEstetika = new ArrayList<>();
    String localTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patrol_estetika);

        btnSubmit = findViewById(R.id.estetika_submit);
        llContent = findViewById(R.id.llContent);
        llMainEstetika = findViewById(R.id.llMainEstetika);
        tvItemPemeriksaan = findViewById(R.id.tvTipePemeriksaan);
        tvDetailItem = findViewById(R.id.tvDetailItem);
        tietQty = findViewById(R.id.tietQty);
        rgChoise = findViewById(R.id.rgChoise);
        tvLocalTime = findViewById(R.id.local_time);
        imBack = findViewById(R.id.ivBack);

        imBack.setVisibility(View.VISIBLE);
        imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(pipe_bending.this, ProsesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "onClick: "+ex.getMessage());
                    Toast.makeText(pipe_bending.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }
            }
        });


        tvLocalTime.setText(localTime);
        AppConstants.startTime = localTime;

        llContent = findViewById(R.id.llContent);

        for(int i = 0; i < 3; i++){
            listCustomEstetika.add(new EstetikaLayout(this));
            llContent.addView(listCustomEstetika.get(i).getLlMainEstetika());
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
            public void onClick(View v) {
//                ConfirmationDialog.showHelp(pipe_bending.this, new ConfirmationDialog.CallbackDialog() {
//                    @Override
//                    public void onResponse(Boolean success, String msg) {
//                        if (success){
//                            Toast.makeText(pipe_bending.this, msg,Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                        Toast.makeText(pipe_bending.this, msg, Toast.LENGTH_LONG)
//                                .show();
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        Toast.makeText(pipe_bending.this, msg, Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
//
//
//            }
                try {


                    Intent intent = new Intent(pipe_bending.this, TampilanListProsesActivity.class);
                    intent.putExtra("xProses", xProses);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "onClick: "+ex.getMessage());
                    Toast.makeText(pipe_bending.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }
            }
        });

    }
}