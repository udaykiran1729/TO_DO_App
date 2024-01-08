package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    Button b;
    DBHelper dbHelper;
    RecyclerView recyclerView;
    ArrayList<String> title,desc,day,date,month,time,status,urg;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DBHelper(this);
        title=new ArrayList<String>();
        date=new ArrayList<String>();
        desc=new ArrayList<String>();
        month=new ArrayList<String>();
        day=new ArrayList<String>();
        time=new ArrayList<String>();
        status=new ArrayList<String>();
        urg=new ArrayList<String>();
        recyclerView=findViewById(R.id.recycle);
        displayData();
        myAdapter=new MyAdapter(this,title,date,day,time,desc,status,urg);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void displayData() {
        Cursor cursor=dbHelper.getData();
        if (cursor.getCount()==0){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,create.class);
        }
        while (cursor.moveToNext()){
            title.add(cursor.getString(0));
            desc.add(cursor.getString(1));
            date.add(cursor.getString(2));
            day.add(cursor.getString(3));
            month.add(cursor.getString(4));
            time.add(cursor.getString(5));
            status.add(cursor.getString(6));
            urg.add(cursor.getString(7));
        }
    }


    public void onclick(View v) {
        Intent intent=new Intent(this,create.class);
        startActivity(intent);
    }

    public void reload() {
        Intent i = new Intent(this, MainActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}