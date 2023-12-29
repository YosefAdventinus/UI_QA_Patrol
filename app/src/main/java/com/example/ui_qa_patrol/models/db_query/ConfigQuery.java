package com.example.ui_qa_patrol.models.db_query;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ui_qa_patrol.Helper.DBHelper;
import com.example.ui_qa_patrol.models.Config;

import java.util.ArrayList;
import java.util.List;

public class ConfigQuery extends DBHelper {

    final String TAG = "ConfigQuery";
    private String TABLE_NAME = "CONFIG";
    public ConfigQuery(Context context){
        super(context);
    }

    public long insertConfig(String Tvar, String Tval){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tvar", Tvar);
        values.put("tval", Tval);
        long id = db.insert(TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<Config> searchConfig(String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs, groupBy, having, orderBy, limit);
        List<Config> configs = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                configs.add(new Config(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                ));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return configs;
    }

    public int UpdateConfig(Config config){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tvar", config.getTvar());
        values.put("tval", config.getTval());

        return db.update(TABLE_NAME, values, "id = ?",
                new String[]{String.valueOf(config.getId())});
    }



}
