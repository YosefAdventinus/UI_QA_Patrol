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

public class ChassisDialog {

    public interface CallbackDialog {
        void onResponse(Boolean success, String msg, DummyChassis dummyChassis);
        void onFailure(String msg);
    }

    final static String TAG = "ChassisDialog";
    static AlertDialog alertDialog;
    static AlertDialog.Builder dialog;

    public static void showHelp(Activity activity, final CallbackDialog callback){
        try {
            ArrayList<DummyChassis> listDummy = new ArrayList<>();
            listDummy.add(new DummyChassis("REFRIGERATOR", "R60"));
            listDummy.add(new DummyChassis("REFRIGERATOR", "R70"));
            listDummy.add(new DummyChassis("REFRIGERATOR", "R90"));

            LayoutInflater inflater = activity.getLayoutInflater();
            View view =inflater.inflate(R.layout.chassis_help, null);
            dialog = new AlertDialog.Builder(activity)
                    .setView(view);

            SearchView searchView = view.findViewById(R.id.pencarian_chassis);
            ListView listView = view.findViewById(R.id.listview_chassis);

            ChassisDialog.DummyChassis.DummyChassisAdapter adapter = new ChassisDialog.DummyChassis.DummyChassisAdapter(activity, listDummy);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ChassisDialog.DummyChassis dummyChassis = (ChassisDialog.DummyChassis) adapter.getItem(i);

                    alertDialog.dismiss();
                    callback.onResponse(true, "", dummyChassis);
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

    public static class DummyChassis{
        String jenisChasis, tipeChassis;

        public DummyChassis(String chassis, String tipeChassis){
            this.jenisChasis = chassis;
            this.tipeChassis = tipeChassis;
        }
        public String getTipeChassis(){
            return  tipeChassis;
        }

        public void setTipeChassis(String tipeProduk){
            this.tipeChassis = tipeChassis;
        }

        public String getJenisChasis(){
            return jenisChasis;
        }

        public void setJenisChasis(String jenisChasis){
            this.jenisChasis = jenisChasis;
        }

        public static class DummyChassisAdapter extends BaseAdapter{
            Context c;
            ArrayList<ChassisDialog.DummyChassis> listChassis;


            public DummyChassisAdapter(Context context, ArrayList<ChassisDialog.DummyChassis> listChassis){
                this.c = context;
                this.listChassis = listChassis;
            }

            @Override
            public int getCount() {
                return listChassis.size();
            }

            @Override
            public Object getItem(int i) {
                return listChassis.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {

                LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if(view == null){
                    view = inflater.inflate(R.layout.chassis_layout, null);
                }

                TextView tvtipeProduk = (TextView) view.findViewById(R.id.tvChassis),
                    tvChassis = (TextView) view.findViewById(R.id.tvJenisChasis);
//                    tvPemeriksaan = (TextView) view.findViewById(R.id.tvTipePemeriksaan);

                tvtipeProduk.setText(listChassis.get(i).getJenisChasis());
                tvChassis.setText(listChassis.get(i).getTipeChassis());

                return view;
            }
        }
    }

}
