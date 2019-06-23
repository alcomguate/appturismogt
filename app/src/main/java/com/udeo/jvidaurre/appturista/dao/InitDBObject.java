package com.udeo.jvidaurre.appturista.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class InitDBObject extends SQLiteOpenHelper {


    private static final String DB_NAME = "turismogt.sqlite";
    private static final int DB_VERSION = 1;

    private List<String> tableCreateStatemnent;

    public InitDBObject(Context context, List<String> tableCreateStatemnent) {
        super(context, DB_NAME, null, DB_VERSION);
        this.tableCreateStatemnent = tableCreateStatemnent;
    }

    public InitDBObject(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        for (String statement: this.tableCreateStatemnent){
            db.execSQL(statement);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<String> getTableCreateStatemnent() {
        return tableCreateStatemnent;
    }

    public void setTableCreateStatemnent(List<String> tableCreateStatemnent) {
        this.tableCreateStatemnent = tableCreateStatemnent;
    }
}