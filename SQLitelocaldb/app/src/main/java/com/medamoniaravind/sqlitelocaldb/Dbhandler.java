package com.medamoniaravind.sqlitelocaldb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhandler extends SQLiteOpenHelper {
    public static final String DATABASENAME="usersdetails";
    public static final String Tablename="usersinfo";
    public static final String columnid="Id";
    public static final String columnname="Username";
    public static final String columnMail="E_mail";
    public static final String columnmobileno="Mobilenum";
    public static final String columnpassword="password";
    public static final int databaseversion=1;

    public Dbhandler(Context context) {
        super(context,DATABASENAME,null,databaseversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usersinfo (Id integer Primary Key Autoincrement,Username text,E_mail text,Mobilenum number,password text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+Tablename);
        onCreate(db);

    }

    public boolean insertdata(String Username,String E_mail,String Mobilenum,String password){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(columnname,Username);
        contentValues.put(columnMail,E_mail);
        contentValues.put(columnmobileno,Mobilenum);
        contentValues.put(columnpassword,password);
       long result= sqLiteDatabase.insert(Tablename,null,contentValues);
       if(result==-1)
           return false;
       else
        return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        Cursor res=sqLiteDatabase.rawQuery("select * from "+Tablename,null);
        return res;
    }

    public boolean updatedata(String id,String Username,String E_mail,String Mobilenum,String password){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(columnid,id);
        contentValues.put(columnname,Username);
        contentValues.put(columnMail,E_mail);
        contentValues.put(columnmobileno,Mobilenum);
        contentValues.put(columnpassword,password);
        sqLiteDatabase.update(Tablename,contentValues,"Id = ?",new String[]{ id });
        return true;
    }

    public Integer deletedata(String id){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.delete(Tablename,"Id = ?",new String[] { id });
    }
}
