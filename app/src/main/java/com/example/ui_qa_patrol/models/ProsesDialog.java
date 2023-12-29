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

public class ProsesDialog {

    public interface CallbackDialog{
        void onResponse(Boolean success, String msg, DummyProduk dummyProduk);
        void onFailure(String msg);
    }

    final static String TAG = "ProsesDialog";
    static AlertDialog alertDialog;
    static AlertDialog.Builder dialog;

    public static void showHelp(Activity activity, final CallbackDialog callbackDialog){
        try {
            ArrayList<DummyProduk> listDummy = new ArrayList<>();
            listDummy.add(new DummyProduk("Refrigerator", "Pipe Bending", "visit"));
            listDummy.add(new DummyProduk("Refrigerator", "Pipe Bending", "estetika"));
            listDummy.add(new DummyProduk("Refrigerator", "Lockring", "pengukuran"));

            LayoutInflater inflater = activity.getLayoutInflater();
            View view = inflater.inflate(R.layout.process_help, null);
            dialog = new AlertDialog.Builder(activity)
                    .setView(view);

            SearchView searchView = view.findViewById(R.id.scProcess);
            ListView listView = view.findViewById(R.id.lvProcess);

            DummyProdukAdapter adapter = new DummyProdukAdapter(activity, listDummy);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    DummyProduk dummyProduk = (DummyProduk) adapter.getItem(i);

                    alertDialog.dismiss();
                    callbackDialog.onResponse(true, "", dummyProduk);
                }
            });

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

    public static class DummyProduk{
        String jenisProduk, proses, tipePemeriksaan;

        public DummyProduk(String jenisProduk, String proses, String tipePemeriksaan) {
            this.jenisProduk = jenisProduk;
            this.proses = proses;
            this.tipePemeriksaan = tipePemeriksaan;
        }

        public String getTipePemeriksaan() {
            return tipePemeriksaan;
        }

        public void setTipePemeriksaan(String tipePemeriksaan) {
            this.tipePemeriksaan = tipePemeriksaan;
        }

        public String getJenisProduk() {
            return jenisProduk;
        }

        public void setJenisProduk(String jenisProduk) {
            this.jenisProduk = jenisProduk;
        }

        public String getProses() {
            return proses;
        }

        public void setProses(String proses) {
            this.proses = proses;
        }
    }

    public static class DummyProdukAdapter extends BaseAdapter{

        Context c;
        ArrayList<DummyProduk> listProduk;

        public DummyProdukAdapter(Context context, ArrayList<DummyProduk> listProduk){
            this.c = context;
            this.listProduk = listProduk;
        }

        @Override
        public int getCount() {
            return listProduk.size();
        }

        @Override
        public Object getItem(int i) {
            return listProduk.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(view == null){
                view = inflater.inflate(R.layout.item_produk, null);
            }

            TextView tvJenisProduk = (TextView) view.findViewById(R.id.tvJenisProduk),
                    tvProses = (TextView) view.findViewById(R.id.tvProses),
                    tvPemeriksaan = (TextView) view.findViewById(R.id.tvTipePemeriksaan);

            tvJenisProduk.setText(listProduk.get(i).getJenisProduk());
            tvProses.setText(listProduk.get(i).getProses());
            tvPemeriksaan.setText(listProduk.get(i).getTipePemeriksaan());

            return view;
        }
    }

}
