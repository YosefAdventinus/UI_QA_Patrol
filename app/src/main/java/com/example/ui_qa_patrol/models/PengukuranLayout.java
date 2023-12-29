package com.example.ui_qa_patrol.models;

import static android.view.Gravity.CENTER;
import static android.view.Gravity.CENTER_VERTICAL;

import android.content.Context;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.ui_qa_patrol.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PengukuranLayout {

    LinearLayout llContent, llContent_2;
    TableRow tbMain, tbDetail;
    TableLayout tlMain;
    TextView tvMinimal, tvMax, tvDetailMin, tvDetailMax, tvDaerahPengukuran;
    TextInputLayout tilHasilUkur;
    TextInputEditText tietHasilUkur;

    public PengukuranLayout(Context context){

        llContent = new LinearLayout(context);
        LinearLayout.LayoutParams lpContent = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llContent.setLayoutParams(lpContent);
        llContent.setOrientation(LinearLayout.VERTICAL);
        llContent.setGravity(CENTER_VERTICAL);

        tlMain = new TableLayout(context);
        TableRow.LayoutParams lpTlMain = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        tlMain.setLayoutParams(lpTlMain);
        tlMain.setOrientation(LinearLayout.VERTICAL);
        tlMain.setGravity(Gravity.CENTER);

        tbMain = new TableRow(context);
        TableRow.LayoutParams lpMain = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT);
        tbMain.setLayoutParams(lpMain);
        tbMain.setOrientation(LinearLayout.HORIZONTAL);

        tvMinimal = new TextView(context);
        TableRow.LayoutParams lpTvMinimal = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
        lpTvMinimal.weight = 1;
        tvMinimal.setLayoutParams(lpTvMinimal);
        tvMinimal.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        tvMinimal.setTextColor(ContextCompat.getColor(context, R.color.black));
        tvMinimal.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        tvMinimal.setText("Min");
        tvMinimal.setBackgroundResource(R.drawable.table_shape);
        tvMinimal.setGravity(CENTER);

        tvMax = new TextView(context);
        TableRow.LayoutParams lpTvMax = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
        lpTvMax.weight = 1;
        tvMax.setLayoutParams(lpTvMax);
        tvMax.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        tvMax.setTextColor(ContextCompat.getColor(context, R.color.black));
        tvMax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        tvMax.setText("Max");
        tvMax.setBackgroundResource(R.drawable.table_shape);
        tvMax.setGravity(CENTER);

        tbDetail = new TableRow(context);
        TableRow.LayoutParams lpDetail = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
        tbDetail.setLayoutParams(lpDetail);
        tbDetail.setOrientation(LinearLayout.HORIZONTAL);

        tvDetailMin = new TextView(context);
        TableRow.LayoutParams lpTvDetail = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
        lpTvDetail.weight = 1;
        tvDetailMin.setLayoutParams(lpTvDetail);
        tvDetailMin.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        tvDetailMin.setTextColor(ContextCompat.getColor(context, R.color.black));
        tvDetailMin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        tvDetailMin.setText("1");
        tvDetailMin.setBackgroundResource(R.drawable.table_shape_disable);
        tvDetailMin.setGravity(CENTER);

        tvDetailMax = new TextView(context);
        TableRow.LayoutParams lpTvDetail_2 = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
        lpTvDetail_2.weight = 1;
        tvDetailMax.setLayoutParams(lpTvDetail_2);
        tvDetailMax.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        tvDetailMax.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        tvDetailMax.setTextColor(ContextCompat.getColor(context, R.color.black));
        tvDetailMax.setText("5");
        tvDetailMax.setBackgroundResource(R.drawable.table_shape_disable);
        tvDetailMax.setGravity(CENTER);

        llContent_2 = new LinearLayout(context);
        LinearLayout.LayoutParams lpLlContent_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llContent_2.setLayoutParams(lpLlContent_2);
        llContent_2.setOrientation(LinearLayout.VERTICAL);
        llContent_2.setPadding(0, 0, 0, 10);

        tvDaerahPengukuran = new TextView(context);
        LinearLayout.LayoutParams lpTvDaerahPengukuran = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpTvDaerahPengukuran.setMargins(0,10,0,0);
        tvDaerahPengukuran.setLayoutParams(lpTvDaerahPengukuran);
        tvDaerahPengukuran.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        tvDaerahPengukuran.setTextColor(ContextCompat.getColor(context, R.color.black));
        tvDaerahPengukuran.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
        tvDaerahPengukuran.setText("Daerah A");

        tilHasilUkur = new TextInputLayout(context);
        LinearLayout.LayoutParams lpTilHasilUkur = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tilHasilUkur.setLayoutParams(lpTilHasilUkur);
        tilHasilUkur.setHint("Hasil Ukur");

        tietHasilUkur = new TextInputEditText(context);
        LinearLayout.LayoutParams lpTietInputDaerah = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tietHasilUkur.setLayoutParams(lpTietInputDaerah);
        tietHasilUkur.setInputType(InputType.TYPE_CLASS_PHONE);
        tietHasilUkur.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);



        tlMain.addView(tbMain);
        tbMain.addView(tvMinimal);
        tbMain.addView(tvMax);
        tbDetail.addView(tvDetailMin);
        tbDetail.addView(tvDetailMax);
        llContent_2.addView(tvDaerahPengukuran);
        tilHasilUkur.addView(tietHasilUkur);
        llContent_2.addView(tilHasilUkur);
        llContent.addView(tlMain);
        llContent.addView(tbDetail);
        llContent.addView(llContent_2);

    }
    public LinearLayout getLlContent(){
        return llContent;
    }

}
