package com.example.sqllitedata;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.net.Uri;

import java.io.CharArrayWriter;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="register.db";
    public static final String TABLE_NAME_1="registeration";
    SQLiteOpenHelper HelperDB;
    public static final String COL_1="ID";
    public static final String COL_2="FirstName";
    public static final String COL_3="LastName";
    public static final String COL_4="Password";
    public static final String COL_5="Email";
    public static final String COL_6="Phone";
    public static final String Image="Image";
    public static final String Name_image = "Image_name";
    public static final String Description_image = "Description_Image";
    SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context,
                DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME_1 +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FirstName TEXT,LastName TEXT,Password TEXT," +
                "Email TEXT,Phone TEXT,Image blob,Name_image Text,Description_image)");


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME_1); //Drop older table if exists
        onCreate(db);
    }
    public void insertdata(String Name, String Des){

        ContentValues contentValues = new ContentValues();
        db = HelperDB.getWritableDatabase();
        contentValues.put(DatabaseHelper.Name_image, Name);
        contentValues.put(DatabaseHelper.Description_image, Des);
         db.insert(DatabaseHelper.TABLE_NAME_1, null, contentValues);
    }
    boolean insertDataImage(String title, String Des,byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Name_image,title);
        contentValues.put(Description_image,Des);
        contentValues.put(Image,image);
        long res = db.insert(TABLE_NAME_1,null,contentValues);
        if (res==-1) return false;
        else return true;

    }

}
