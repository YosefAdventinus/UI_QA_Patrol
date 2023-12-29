package com.example.ui_qa_patrol.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    final static String TAG = "DB_HELPER";
    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "qa_patrol";

    public DBHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String query = "" +
                "CREATE TABLE TEMP_QC_PATROL_DETAILS (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "qc_doc_header_id INTEGER," +
                "proses TEXT," +
                "item_pemeriksaan TEXT," +
                "tipe_pemeriksaan TEXT," +
                "waktu_mulai TEXT," +
                "status TEXT," +
                "hasil_check TEXT," +
                "qty TEXT," +
                "created_by TEXT," +
                "created_at INTEGER," +
                "updated_by TEXT," +
                "updated_at INTEGER);";
        db.execSQL(query);

        query = "" +
                "CREATE TABLE TEMP_QC_PATROL_SUB_DETAILS (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "qc_patrol_detail_id INTEGER," +
                "index_of TEXT," +
                "chassis TEXT," +
                "size TEXT," +
                "tipe_check TEXT," +
                "hasil_ukur TEXT," +
                "toleransi_minimum TEXT," +
                "toleransi_maximum TEXT," +
                "created_by TEXT," +
                "created_at TEXT," +
                "updated_by TEXT," +
                "updated_at TEXT);";
        db.execSQL(query);

        query = "" +
                "CREATE TABLE TEMP_DOC_HEADER (" +
                "id INTEGER," +
                "kode_line_produksi TEXT," +
                "nama_inspektor TEXT," +
                "nik TEXT," +
                "jenis_kegiatan TEXT," +
                "shift TEXT," +
                "tanggal_check TEXT," +
                "waktu_selesai TEXT," +
                "status TEXT," +
                "created_by TEXT," +
                "created_at TEXT," +
                "updated_by TEXT," +
                "updated_at TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TEMP_QC_PATROL_DETAILS");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TEMP_QC_PATROL_SUB_DETAILS");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TEMP_DOC_HEADER");
        onCreate(sqLiteDatabase);
    }


}
