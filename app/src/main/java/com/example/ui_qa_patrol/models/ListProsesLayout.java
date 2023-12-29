package com.example.ui_qa_patrol.models;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.ui_qa_patrol.R;

public class ListProsesLayout {

    LinearLayout llContentProses, llMainProces;
    TableLayout tlMain1;
    TableRow trMainProses;
    TextView tvProses1,tvWaktu;


    public ListProsesLayout(Context context){

        llContentProses = new LinearLayout(context);
        LinearLayout.LayoutParams lpContentProses = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llContentProses.setLayoutParams(lpContentProses);
        llContentProses.setOrientation(LinearLayout.VERTICAL);
        llContentProses.setGravity(Gravity.CENTER);

        llMainProces = new LinearLayout(context);
        LinearLayout.LayoutParams lpMainProses = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llMainProces.setLayoutParams(lpMainProses);
        llMainProces.setOrientation(LinearLayout.VERTICAL);
        llMainProces.setGravity(Gravity.CENTER);

        tlMain1 = new TableLayout(context);
        TableRow.LayoutParams lpTlMain = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        tlMain1.setLayoutParams(lpTlMain);
        tlMain1.setOrientation(LinearLayout.VERTICAL);
        tlMain1.setGravity(Gravity.CENTER);

        trMainProses = new TableRow(context);
        TableRow.LayoutParams trMain = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        trMainProses.setLayoutParams(trMain);
        trMainProses.setOrientation(LinearLayout.HORIZONTAL);

        tvProses1 = new TextView(context);
        TableRow.LayoutParams tvProses = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
        tvProses.weight = 1;
        tvProses1.setLayoutParams(tvProses);
        tvProses1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        tvProses1.setTextColor(ContextCompat.getColor(context, R.color.black));
        tvProses1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        tvProses1.setText("xProcess");
        tvProses1.setBackgroundResource(R.drawable.table_shape);
        tvProses1.setGravity(Gravity.CENTER_HORIZONTAL);

        tvWaktu = new TextView(context);
        TableRow.LayoutParams tcWaktu1 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
        tcWaktu1.weight = 1;
        tvWaktu.setLayoutParams(tvProses);
        tvWaktu.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        tvWaktu.setTextColor(ContextCompat.getColor(context, R.color.black));
        tvWaktu.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        tvWaktu.setText("xWaktu");
        tvWaktu.setBackgroundResource(R.drawable.table_shape);
        tvWaktu.setGravity(Gravity.CENTER_HORIZONTAL);

        llContentProses.addView(llMainProces);
        llMainProces.addView(tlMain1);
        tlMain1.addView(trMainProses);
        trMainProses.addView(tvProses1);
        trMainProses.addView(tvWaktu);

    }
    public LinearLayout getLlContentProses(){
        return llContentProses;
    }

}
