package com.example.pastech.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class TileManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Tiles.db";
    private static final int DATABASE_VERSION = 1;

    public TileManager(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "create table T_Tiles ("
                + "   position integer primary key,"
                + "   type text not null,"
                + "   number integer not null,"
                + "   content text not null"
                + ")";
        db.execSQL(strSql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String strSql = "drop table T_Tiles";
        db.execSQL(strSql);
        this.onCreate(db);
    }

    public void insertTile(Tile tile) {
        String strSql = "insert into T_Tiles (position, type, number, content) values ("
                + tile.getPosition() + ", '" + tile.getType() + "', " + tile.getNumber() + ", '" + tile.getContent() + "')";
        this.getWritableDatabase().execSQL(strSql);
    }

    public boolean isEmpty() {
        String strSql = "select * from T_Tiles";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        if (cursor.getCount() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteTile() {
        String strSql = "delete from T_Tiles";
        this.getWritableDatabase().execSQL(strSql);
    }
}
