package com.example.ui_qa_patrol.models;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.ui_qa_patrol.R;
import com.google.android.material.snackbar.Snackbar;

public class GlobalSnackBar {
    public static void show(Activity activity, View view, String msg, int duration, boolean isError, boolean success){ // IF isError & success = false, then show the normal snackbar
        final Snackbar snackbar = Snackbar.make(view, "", duration);
        View customSnackBar = activity.getLayoutInflater().inflate(R.layout.custom_snackbar, null);
        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);

        CardView cardView = customSnackBar.findViewById(R.id.cvLayout);
        TextView textView = customSnackBar.findViewById(R.id.tvMessageSnackbar);

        textView.setText(msg);
        textView.setTextColor(Color.WHITE);
        if(isError){ // show snackbar error
            cardView.setCardBackgroundColor(activity.getResources().getColor(R.color.red));
        }
        else{
            if(success){ // show snackbar success
                cardView.setCardBackgroundColor(Color.GREEN);
                textView.setTextColor(Color.BLACK);
            }
            else { // show snackbar normal
                cardView.setCardBackgroundColor(Color.BLACK);
            }
        }

        snackbarLayout.addView(customSnackBar, 0);
        snackbar.show();
    }
}
