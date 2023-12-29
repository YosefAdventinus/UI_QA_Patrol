package com.example.ui_qa_patrol.models;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ui_qa_patrol.R;

import java.util.AbstractList;

public class ConfirmationDialog {

    public interface CallbackDialog{
        void onResponse(Boolean success, String msg);
        void onFailure(String msg);
    }

    final static String TAG = "ConfirmationDialog";
    static AlertDialog alertDialog;
    static  AlertDialog.Builder dialog;

    public static void showHelp(Activity activity, final CallbackDialog callbackDialog){
        try {
            LayoutInflater inflater = activity.getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_confirmation, null);
            dialog = new AlertDialog.Builder(activity)
                    .setView(view);

            TextView tvTitle = view.findViewById(R.id.tvTitleConfirmation);
            Button btnYEs = view.findViewById(R.id.btnYes);
            Button btnNo = view.findViewById(R.id.btnNo);

            alertDialog = dialog.show();
            alertDialog.setCancelable(true);
            alertDialog.setCanceledOnTouchOutside(true);

        }
        catch (Exception ex){
            if(alertDialog != null)
                alertDialog.dismiss();
            Log.e(TAG, "showHelp: "+ex.getMessage());
            ex.printStackTrace();
            callbackDialog.onFailure(ex.getMessage());
        }
    }

}
