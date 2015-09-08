package com.ccf.controll.autocomplateeditext;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ccfwiz on 2015/9/7.
 */
public class AppSqliteHelp extends SQLiteOpenHelper {
    public static final String DBNAME="AutoCompleteDB";
    public static final String TABLENAME="Keywords";
    public AppSqliteHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE  TABLE 'main'.'"+TABLENAME+"' ('keywords' VARCHAR NOT NULL )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        onCreate(db);
    }
}
