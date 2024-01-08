package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;
public class DBHelper  extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context,"Tasks.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table tasks(title TEXT,description TEXT,date Text,day TEXT,month TEXT,time TEXT,status TEXT,urg TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists tasks");
    }

    public boolean insert(String title, String desc,String date,String day,String month,String time,String status,String urg){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",title);
        contentValues.put("desc",desc);
        contentValues.put("date",date);
        contentValues.put("day",day);
        contentValues.put("month",month);
        contentValues.put("time",time);
        contentValues.put("status",status);
        contentValues.put("urg",urg);
        long result=sqLiteDatabase.insert("tasks",null,contentValues);
        if (result==-1){
            return false;
        }
        return true;
    }

    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from tasks",null);
        return cursor;
    }

    public Boolean deleteuserdata(String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from tasks where task = ?", new String[]{name});
        if(cursor.getCount()>0)
        {
            long result = DB.delete("tasks", "task= ?", new String[]{name});
            if(result==-1)
            {
                return  false;
            }
            else
            {
                return true;
            }
        }

        return false;


    }
}