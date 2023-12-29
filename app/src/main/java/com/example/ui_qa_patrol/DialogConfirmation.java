package com.example.ui_qa_patrol;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ui_qa_patrol.screens.ProsesActivity;
import com.example.ui_qa_patrol.screens.TampilanListProsesActivity;
import com.example.ui_qa_patrol.screens.patrol_pengukuran;
import com.example.ui_qa_patrol.screens.pipe_bending;

public class DialogConfirmation extends AppCompatActivity {

    final static  String TAG = "DialogConfirmation";
    private Button btnYes, btnNo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirmation);

        btnYes = findViewById(R.id.btnYes);
        btnNo = findViewById(R.id.btnNo);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(DialogConfirmation.this, TampilanListProsesActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception ex){
                    Log.e(TAG, "onClick: "+ex.getMessage());
                    Toast.makeText(DialogConfirmation.this, ex.getMessage(), Toast.LENGTH_LONG);
                    ex.printStackTrace();
                }

            }
        });





    }
}