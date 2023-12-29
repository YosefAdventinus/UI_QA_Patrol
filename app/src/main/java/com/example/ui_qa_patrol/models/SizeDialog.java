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

public class SizeDialog {

    public interface CallbackDialog {
        void onResponse(Boolean success, String msg, SizeDialog.DummySize dummySize);
        void onFailure(String msg);
    }

    final static String TAG = "SizeDialog";
    static AlertDialog alertDialog;
    static AlertDialog.Builder dialog;

    public static void showHelp(Activity activity, final CallbackDialog callback){
        try {
            ArrayList<SizeDialog.DummySize> listDummy = new ArrayList<>();
            listDummy.add(new SizeDialog.DummySize("150L", "REFRIGERATOR"));
            listDummy.add(new SizeDialog.DummySize("160L", "REFRIGERATOR"));
            listDummy.add(new SizeDialog.DummySize("200L", "REFRIGERATOR"));
            listDummy.add(new SizeDialog.DummySize("220L", "REFRIGERATOR"));

            LayoutInflater inflater = activity.getLayoutInflater();
            View view =inflater.inflate(R.layout.size_help, null);
            dialog = new AlertDialog.Builder(activity)
                    .setView(view);

            SearchView searchView = view.findViewById(R.id.pencarian_size);
            ListView listView = view.findViewById(R.id.listview_size);

            SizeDialog.DummySize.DummySizeAdapter adapter = new SizeDialog.DummySize.DummySizeAdapter(activity, listDummy);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SizeDialog.DummySize dummySize = (SizeDialog.DummySize) adapter.getItem(i);

                    alertDialog.dismiss();
                    callback.onResponse(true, "", dummySize);
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

    public static class DummySize{
        String size, tipeBarang;

        public DummySize(String size, String tipeBarang){
            this.size = size;
            this.tipeBarang = tipeBarang;
        }
        public String getTipeBarang(){
            return  tipeBarang;
        }

        public void setTipeBarang(String tipeBarang){
            this.tipeBarang = tipeBarang;
        }

        public String getSize(){
            return size;
        }

        public void setSize(String size){
            this.size = size;
        }

        public static class DummySizeAdapter extends BaseAdapter {
            Context c;
            ArrayList<SizeDialog.DummySize> listSize;


            public DummySizeAdapter(Context context, ArrayList<SizeDialog.DummySize> listSize){
                this.c = context;
                this.listSize = listSize;
            }

            @Override
            public int getCount() {
                return listSize.size();
            }

            @Override
            public Object getItem(int i) {
                return listSize.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {

                LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if(view == null){
                    view = inflater.inflate(R.layout.size_layout, null);
                }

                TextView tvtipeProduk = (TextView) view.findViewById(R.id.tvSize);
//                        tvChassis = (TextView) view.findViewById(R.id.tvJenisChasis);
//                    tvPemeriksaan = (TextView) view.findViewById(R.id.tvTipePemeriksaan);

                tvtipeProduk.setText(listSize.get(i).getSize());


                return view;
            }
        }
    }

}
