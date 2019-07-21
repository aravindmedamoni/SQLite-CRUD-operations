package com.aravind.hith_sqlite_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "Mystudent.db";
    public final static String TABLE_NAME = "myStudent_table";
    public final static String COL_ID = "ID";
    public final static String COL_NAME = "NAME";
    public final static String COL_EMAIL = "EMAIL";
    public final static String COL_COURSE_COUNT = "COURSE_COUNT";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT, COURSE_COUNT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String email, String coursecount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_NAME,name);
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_COURSE_COUNT,coursecount);

        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result==-1)
            return false;
        else
            return true;

    }

    public boolean updateData(String id, String name, String email, String coursecount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID,id);
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_COURSE_COUNT,coursecount);

        db.update(TABLE_NAME,contentValues,"ID=?", new String[]{id});
        return true;
    }

    public Cursor getData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE ID='"+id+"'";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db= this.getWritableDatabase();
        return db.delete(TABLE_NAME," WHERE ID='"+id+"'",null);
    }

    public Cursor getALLData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor =db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }

    public void deleteAllData(){
        SQLiteDatabase db =this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME);
    }
}
