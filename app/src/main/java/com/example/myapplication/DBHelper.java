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
        sqLiteDatabase.execSQL("create Table tasks(title TEXT,description TEXT,date Text,day TEXT,month TEXT,time TEXT,status TEXT,urg TEXT,cat Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists tasks");
    }

    public boolean insert(String title, String desc,String date,String day,String month,String time,String status,String urg,String cat){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",title);
        contentValues.put("description",desc);
        contentValues.put("date",date);
        contentValues.put("day",day);
        contentValues.put("month",month.substring(0,3));
        contentValues.put("time",time);
        contentValues.put("status",status);
        contentValues.put("urg",urg);
        contentValues.put("cat",cat);
        long result=sqLiteDatabase.insert("tasks",null,contentValues);
        if (result==-1){
            return false;
        }
        return true;
    }

    public Cursor filter(String name){
        SQLiteDatabase DB = this.getWritableDatabase();
//        System.out.println(name);
//        name=name.toLowerCase();
        Cursor cursor = DB.rawQuery("Select * from tasks where cat = \""+name+"\"",null);
        if (cursor.getCount()>0)
            return cursor;
        return null;
    }

    public Cursor sort(String name){
        SQLiteDatabase DB = this.getWritableDatabase();
        name=name.toLowerCase();
        if (name.equals("priority"))
            name="urg";
        Cursor cursor = DB.rawQuery("Select * from tasks order by "+ name +" asc", null);
        if (cursor.getCount()>0)
            return cursor;
        return null;
    }

    public Cursor getData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from tasks",null);
        return cursor;
    }

    public Boolean deleteuserdata(String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from tasks where title = ?", new String[]{name});
        if(cursor.getCount()>0)
        {
            long result = DB.delete("tasks", "title= ?", new String[]{name});
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

    public boolean update(String org,String title, String desc,String date,String day,String month,String time,String status,String urg,String cat){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("title",title);
        contentValues.put("description",desc);
        contentValues.put("date",date);
        contentValues.put("day",day.substring(0,3));
        contentValues.put("month",month.substring(0,3));
        contentValues.put("time",time);
        contentValues.put("status",status);
        contentValues.put("urg",urg);
        contentValues.put("cat",cat);
        int m=DB.update("tasks", contentValues, "title=?", new String[]{org});
        DB.close();
        if (m>0)
            return true;
        return false;
    }

    public Cursor fetch(String name){
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from tasks where title = \""+name+"\"",null);
        if (cursor.getCount()>0)
            return cursor;
        return null;
    }
}