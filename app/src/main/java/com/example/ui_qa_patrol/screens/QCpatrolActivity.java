package com.example.ui_qa_patrol.screens;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui_qa_patrol.Helper.AppConstants;
import com.example.ui_qa_patrol.R;
import com.example.ui_qa_patrol.models.ProdlineDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class QCpatrolActivity extends AppCompatActivity {

    final static String TAG = "QCpatrolActivity";
    private ImageView  btnBack;
    private Button btnHelpProd, btnSync;
    private Spinner spinnerShift;
    private AdapterView adapter;
    String xProdline = "";
    TextView tvProdline;
    TextInputEditText tietProdline, tietInspektor, tietDate;
    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qc_patrol);

        spinnerShift = findViewById(R.id.shift);
        btnBack = findViewById(R.id.ivBack);
        btnBack.setVisibility(View.VISIBLE);
        btnSync = findViewById(R.id.sync);
        btnHelpProd = findViewById(R.id.help_dashboard);
        tvProdline = findViewById(R.id.tvProdline);
        tietProdline = findViewById(R.id.input_prodLine);
        tietInspektor = findViewById(R.id.input_inspektor);
        tietDate = findViewById(R.id.input_dateLocal);

        tietInspektor.setText(AppConstants.username);
        tietDate.setText(currentDate);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShift.setAdapter(adapter);


        btnHelpProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProdlineDialog.showHelp(QCpatrolActivity.this, new ProdlineDialog.CallbackDialog() {
                    @Override
                    public void onResponse(Boolean success, String msg, ProdlineDialog.DummyProdline dummyProdline) {
                        if (success){
                            tietProdline.setText(dummyProdline.getTipeProduk());
                            Toast.makeText(QCpatrolActivity.this, msg,Toast.LENGTH_LONG)
                                    .show();
                        }
                        Toast.makeText(QCpatrolActivity.this, msg, Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(QCpatrolActivity.this, msg, Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
        });

        if (getIntent() !=null && getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();

            if (bundle.containsKey("xProdline")){
                if (bundle.getString("xProdline") != null){
                    xProdline = bundle.getString("xProdline");
                }
            }
        }

        if (xProdline != null){
            tvProdline.setText(xProdline);
        }


        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String shift = spinnerShift.getSelectedItem().toString();

                    if (shift.toLowerCase().equals("choose shift")){
                        Toast.makeText(QCpatrolActivity.this,"Shift masih belum di pilih!",Toast.LENGTH_LONG)
                                .show();
                        return;
                    }

                    if (tietProdline.getText().toString().isEmpty()){
                        Toast.makeText(QCpatrolActivity.this,"Prod Line masih belum di pilih!",Toast.LENGTH_LONG)
                                .show();
                        return;
                    }

                    Intent intent = new Intent(QCpatrolActivity.this, ProsesActivity.class);
                    AppConstants.prodline = tietProdline.getText().toString();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "onClick: "+ex.getMessage());
                    Toast.makeText(QCpatrolActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }
            }
        });



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(QCpatrolActivity.this, DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "OnClick: "+ex.getMessage());
                    Toast.makeText(QCpatrolActivity.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }


}