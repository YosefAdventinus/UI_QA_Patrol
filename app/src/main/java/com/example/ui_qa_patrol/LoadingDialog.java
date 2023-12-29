package com.example.ui_qa_patrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LoadingDialog {

    private Activity activity;
    private AlertDialog dialog;

    public LoadingDialog(Activity activity){
        this.activity = activity;
    }

    public void startLoadingDialog(String msg, boolean cancelable){
        try {
            if (dialog == null){
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                LayoutInflater inflater = activity.getLayoutInflater();
                View view = inflater.inflate(R.layout.custom_loading, null);
                TextView tvMsg = view.findViewById(R.id.tvMsg);
                tvMsg.setText(msg.equals("") ? "Sedang Menunggu..." : msg);
                builder.setView(view);

                dialog = builder.create();
                dialog.setCancelable(cancelable);
                dialog.setCanceledOnTouchOutside(cancelable);
            }
            if (!dialog.isShowing()){
                dialog.show();
            }
        }
        catch (Exception ex){
            Log.e("LoadingDialog", "startLoadingDialog: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void startLoadingDialog(String msg, boolean cancelable, boolean outsideCancelable){
        try {
            if (dialog != null){
                dialog = null;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            View view = inflater.inflate(R.layout.custom_loading, null);
            TextView tvMsg = view.findViewById(R.id.tvMsg);
            tvMsg.setText(msg.equals("") ? "Sedang Menunggu..." : msg);
            builder.setView(view);

            dialog = builder.create();
            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(outsideCancelable);

            if (!dialog.isShowing()){
                dialog.show();
            }
        }
        catch (Exception ex){
            Log.e("LoadingDialog", "startLoadingDialog: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void dismissDialog(){
        try {
            if (dialog == null)
                return;
            if (dialog.isShowing())
                dialog.dismiss();
        }
        catch (Exception ex){
            Log.e("LoadingDialog", "startLoadingDialog: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


}