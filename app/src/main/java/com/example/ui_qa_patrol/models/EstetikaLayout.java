package com.example.ui_qa_patrol.models;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.ui_qa_patrol.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;

public class EstetikaLayout {
    LinearLayout llMainEstetika, llContentEstetika;
    TextView tvItemPemeriksaan, tvDetailItem;
    TextInputLayout tilQty;
    TextInputEditText tietQty;
    RadioGroup rgChoise;

    public EstetikaLayout(Context context){
        llMainEstetika = new LinearLayout(context);
        LinearLayout.LayoutParams lpMainEstetika = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llMainEstetika.setLayoutParams(lpMainEstetika);
        llMainEstetika.setOrientation(LinearLayout.VERTICAL);

        //----------------------------------------
        llContentEstetika = new LinearLayout(context);
        LinearLayout.LayoutParams lpContentEstetika = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llContentEstetika.setLayoutParams(lpContentEstetika);
        llContentEstetika.setOrientation(LinearLayout.HORIZONTAL);
        llContentEstetika.setGravity(Gravity.CENTER_VERTICAL);

        tvItemPemeriksaan = new TextView(context);
        LinearLayout.LayoutParams lpItemPemeriksaan = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpItemPemeriksaan.weight = 1;
        tvItemPemeriksaan.setLayoutParams(lpItemPemeriksaan);
        tvItemPemeriksaan.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        tvItemPemeriksaan.setTextColor(ContextCompat.getColor(context, R.color.black));
        tvItemPemeriksaan.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        tvItemPemeriksaan.setText("Kondisi Condenser");

        tilQty = new TextInputLayout(context);
        LinearLayout.LayoutParams lpTilQty = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpTilQty.weight = 0.5f;
        tilQty.setLayoutParams(lpTilQty);
        tilQty.setHint("QTY");

        tietQty = new TextInputEditText(context);
        LinearLayout.LayoutParams lpTietQty = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tietQty.setLayoutParams(lpTietQty);
        tietQty.setInputType(InputType.TYPE_CLASS_NUMBER);
        tietQty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);

        tilQty.addView(tietQty);
        llContentEstetika.addView(tvItemPemeriksaan);
        llContentEstetika.addView(tilQty);
        //----------------------------------------

        //----------------------------------------
        tvDetailItem = new TextView(context);
        LinearLayout.LayoutParams lpDetailItem = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpDetailItem.bottomMargin = 10;
        tvDetailItem.setLayoutParams(lpDetailItem);
        tvDetailItem.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        tvDetailItem.setTextColor(ContextCompat.getColor(context, R.color.black));
        tvDetailItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        tvDetailItem.setText("1. Pipe tidak tertutup cat pipe");
        //----------------------------------------

        //----------------------------------------
        rgChoise = new RadioGroup(context);
        LinearLayout.LayoutParams lpRgChoise = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpRgChoise.setMargins(10, 0, 10, 0);
        rgChoise.setLayoutParams(lpRgChoise);
        rgChoise.setOrientation(LinearLayout.HORIZONTAL);

        RadioButton rb1 = new RadioButton(context);
        LinearLayout.LayoutParams lpRb1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpRb1.setMargins(5, 0, 5, 0);
        rb1.setLayoutParams(lpRb1);
        rb1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        rb1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rb1.setTextColor(ContextCompat.getColor(context, R.color.black));
        rb1.setText("OK");

        RadioButton rb2 = new RadioButton(context);
        LinearLayout.LayoutParams lpRb2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpRb1.setMargins(5, 0, 5, 0);
        rb2.setLayoutParams(lpRb2);
        rb2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        rb2.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        rb2.setTextColor(ContextCompat.getColor(context, R.color.black));
        rb2.setText("NG");

        rgChoise.addView(rb1);
        rgChoise.addView(rb2);
        //----------------------------------------

        llMainEstetika.addView(llContentEstetika);
        llMainEstetika.addView(tvDetailItem);
        llMainEstetika.addView(rgChoise);
    }

    public LinearLayout getLlMainEstetika(){
        return llMainEstetika;
    }
}
