package com.example.ui_qa_patrol.models;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.ui_qa_patrol.R;

import java.util.ArrayList;

public class ProdlineDialog {

    public interface CallbackDialog{
        void onResponse(Boolean success, String msg, DummyProdline dummyProdline);
        void onFailure(String msg);


    }

    final static String TAG = "ProdlineDialog";
    static AlertDialog alertDialog;
    static AlertDialog.Builder dialog;

    public static void showHelp(Activity activity, final CallbackDialog callback){
        try {
            ArrayList<DummyProdline> listDummy = new ArrayList<>();
            listDummy.add(new DummyProdline("SYGAT", "REFRIGERATOR N2"));
            listDummy.add(new DummyProdline("SYGAT", "REFRIGERATOR N3"));
            listDummy.add(new DummyProdline("SYGAS", "REFRIGERATOR N5"));

            LayoutInflater inflater = activity.getLayoutInflater();
            View view =inflater.inflate(R.layout.help_prodline, null);
            dialog = new AlertDialog.Builder(activity)
                    .setView(view);

           SearchView searchView = view.findViewById(R.id.search_help);
           ListView listView = view.findViewById(R.id.listview_line);

            DummyProdline.DummyProdAdapter adapter = new DummyProdline.DummyProdAdapter(activity, listDummy);
            listView.setAdapter(adapter);

           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   DummyProdline dummyProdline = (DummyProdline) adapter.getItem(i);

                   alertDialog.dismiss();
                   callback.onResponse(true, "", dummyProdline);
               }
           });

            alertDialog = dialog.show();
            alertDialog.setCancelable(true);
            alertDialog.setCanceledOnTouchOutside(true);
        }
        catch (Exception ex){
            if (alertDialog != null)
                alertDialog.dismiss();
            Log.e(TAG,"showHelp: "+ex.getMessage());
            ex.printStackTrace();
            callback.onFailure(ex.getMessage());
        }
    }

    public static class DummyProdline{
        String jenisprodline, tipeProduk;

        public DummyProdline(String prodline, String tipeProduk){
            this.jenisprodline = prodline;
            this.tipeProduk = tipeProduk;
        }
        public String getTipeProduk(){
            return  tipeProduk;
    }

        public void setTipeProduk(String tipeProduk){
            this.tipeProduk = tipeProduk;
        }

        public String getJenisprodline(){
            return jenisprodline;
        }

        public void setJenisprodline(String jenisprodline){
            this.jenisprodline = jenisprodline;
        }

    public static class DummyProdAdapter extends BaseAdapter{
        Context c;
        ArrayList<DummyProdline> listProdline;


        public DummyProdAdapter(Context context, ArrayList<DummyProdline> listProdline){
            this.c = context;
            this.listProdline = listProdline;
        }

        @Override
        public int getCount() {
            return listProdline.size();
        }

        @Override
        public Object getItem(int i) {
            return listProdline.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(view == null){
                view = inflater.inflate(R.layout.jenis_produk, null);
            }

            TextView tvtipeProduk = (TextView) view.findViewById(R.id.tvKodeGudang),
                    tvProses = (TextView) view.findViewById(R.id.tvtipeProduk);
//                    tvPemeriksaan = (TextView) view.findViewById(R.id.tvTipePemeriksaan);

            tvtipeProduk.setText(listProdline.get(i).getJenisprodline());
            tvProses.setText(listProdline.get(i).getTipeProduk());

            return view;
        }
        }
    }


}
