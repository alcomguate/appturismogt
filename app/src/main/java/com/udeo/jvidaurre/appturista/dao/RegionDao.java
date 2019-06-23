package com.udeo.jvidaurre.appturista.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udeo.jvidaurre.appturista.dto.Region;

import java.util.ArrayList;
import java.util.List;

public class RegionDao extends SQLiteOpenHelper {

    private static final String CREATE_STATEMENT = "CREATE TABLE region(_id INTEGER PRIMARY KEY AUTOINCREMENT, descripcion TEXT, activo BIT);";
    private static final String DB_NAME = "turismogt.sqlite";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase db;

    public RegionDao(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Region> getRegions() {
        List<Region> result = new ArrayList<>();
        Cursor c = db.rawQuery("select _id, descripcion, activo from region", null);
        if (c != null && c.getPosition() > 0){
            int id = c.getInt(c.getColumnIndex("id"));
            String descripcion = c.getString(c.getColumnIndex("descripcion"));
            Region region = new Region(id, descripcion, true);
            result.add(region);
        }
        return result;
    }

}
