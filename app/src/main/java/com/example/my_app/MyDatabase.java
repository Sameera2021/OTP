package com.example.my_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME ="secure.db";
    public static final int DB_VERSION = 1;

    public static final String INFO_TB_NAME ="Info";
    public static final String INFO_CL_ID = "id";
    public static final String INFO_CL_OTPKEY = "OTPkey";
    public static final String INFO_CL_FILEPATH = "filePath";


    public MyDatabase(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+INFO_TB_NAME+" ( "
                +INFO_CL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +INFO_CL_OTPKEY+" TEXT NOT NULL ,"
                +INFO_CL_FILEPATH+" TEXT NOT NULL )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS info");
        onCreate(db);
    }

    public boolean isert_key(Info info){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(INFO_CL_OTPKEY,info.getOTPkey());
        values.put(INFO_CL_FILEPATH,info.getFilePath());
        long result = db.insert(INFO_TB_NAME,null,values);
        return result != -1 ;
    }

    public boolean delete_file(String path){
        SQLiteDatabase db = getWritableDatabase();
        String args[] = {path};
        int result = db.delete(INFO_TB_NAME,"filePath=?",args);
        return result > 0;
    }
    public boolean check_file(String path){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+INFO_TB_NAME+" WHERE "+INFO_CL_FILEPATH+"='"+path+"'",null);
        if(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(INFO_CL_ID));
            return true;
        }
        else
            return false;
    }


    public String get_key(String filePath){
        String result = null;
        SQLiteDatabase db = getReadableDatabase();
        //db.rawQuery("SELECT * FROM student WHERE rollno='"+editRollno.getText()+"'", null);
        Cursor cursor = db.rawQuery("SELECT * FROM "+INFO_TB_NAME
                +" WHERE "+INFO_CL_FILEPATH+"='"+filePath+"'",null);
        if(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(INFO_CL_ID));
            String key = cursor.getString(cursor.getColumnIndex(INFO_CL_OTPKEY));
            String path = cursor.getString(cursor.getColumnIndex(INFO_CL_FILEPATH));
            result = key ;
            return result ;
        }
        else
        return result ;
    }
   /* public void get_key(String file_path){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String result;
        //db.execSQL("SELECT "+INFO_CL_OTPKEY+" FROM "+INFO_TB_NAME+" WHERE "+INFO_CL_FILEPATH+" = "+file_path);
    }
*/


}
