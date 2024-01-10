package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.color.DynamicColors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    Button b;
    FloatingActionButton b;
    ImageView img_vie;
    LinearLayout layout;
    DBHelper dbHelper;
    TextView dyn_text;
    RecyclerView recyclerView;
    ArrayList<String> title,desc,day,date,month,status,urg,cat,time;
//    Button b1,b2;
    private Spinner sort,category;
    MyAdapter myAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        DynamicColors.applyToActivitiesIfAvailable(getApplication());
        dbHelper=new DBHelper(this);
        dyn_text=findViewById(R.id.dyn_text);
        layout=findViewById(R.id.layout);
        b=findViewById(R.id.addTask);
        b.setOnClickListener(this);
        title=new ArrayList<String>();
        date=new ArrayList<String>();
        desc=new ArrayList<String>();
        month=new ArrayList<String>();
        day=new ArrayList<String>();
        time=new ArrayList<String>();
        status=new ArrayList<String>();
        urg=new ArrayList<String>();
        cat=new ArrayList<String>();
        recyclerView=findViewById(R.id.recycle);
        displayData();
        myAdapter=new MyAdapter(this,title,date,day,month,desc,status,urg,cat);
        if (title.size()==0){
            layout.setVisibility(View.VISIBLE);
        }
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sort=findViewById(R.id.sort);
        category=findViewById(R.id.category);
        sort.getAnimation();
        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    displayData();
                    dyn_text.setText("All Tasks");
                    return ;
                }
                String s=adapterView.getItemAtPosition(i).toString();
                Cursor cursor=dbHelper.sort(s);
                if (cursor==null){
                    Toast.makeText(getApplicationContext(), "No Available Items", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<String> title,desc,day,date,month,time,status,urg;
                title=new ArrayList<String>();
                date=new ArrayList<String>();
                desc=new ArrayList<String>();
                month=new ArrayList<String>();
                day=new ArrayList<String>();
                time=new ArrayList<String>();
                status=new ArrayList<String>();
                urg=new ArrayList<String>();
                cat=new ArrayList<String>();
                while (cursor.moveToNext()){
                    title.add(cursor.getString(0));
                    desc.add(cursor.getString(1));
                    date.add(cursor.getString(2));
                    day.add(cursor.getString(3));
                    month.add(cursor.getString(4));
                    time.add(cursor.getString(5));
                    status.add(cursor.getString(6));
                    urg.add(cursor.getString(7));
                    cat.add(cursor.getString(8));
                }
                myAdapter=new MyAdapter(getApplicationContext(),title,date,day,month,desc,status,urg,cat);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                displayData();
            }
        });


        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    displayData();
                    dyn_text.setText("All Tasks");
                    return ;
                }
                String s=adapterView.getItemAtPosition(i).toString();
                dyn_text.setText(s+" tasks");
                Cursor cursor=dbHelper.filter(s);
                if (cursor==null){
                    Toast.makeText(getApplicationContext(), "No Available Items", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<String> title,desc,day,date,month,time,status,urg,cat;
                title=new ArrayList<String>();
                date=new ArrayList<String>();
                desc=new ArrayList<String>();
                month=new ArrayList<String>();
                day=new ArrayList<String>();
                time=new ArrayList<String>();
                status=new ArrayList<String>();
                urg=new ArrayList<String>();
                cat=new ArrayList<String>();
                while (cursor.moveToNext()){
                    title.add(cursor.getString(0));
                    desc.add(cursor.getString(1));
                    date.add(cursor.getString(2));
                    day.add(cursor.getString(3));
                    month.add(cursor.getString(4));
                    time.add(cursor.getString(5));
                    status.add(cursor.getString(6));
                    urg.add(cursor.getString(7));
                    cat.add(cursor.getString(8));
                }
                myAdapter=new MyAdapter(getApplicationContext(),title,date,day,month,desc,status,urg,cat);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                displayData();
            }
        });


    }

    public void filter_data(){
        Cursor cursor=dbHelper.getData();
    }

    private void displayData() {
        Cursor cursor=dbHelper.getData();
        if (cursor.getCount()==0){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            layout.setVisibility(View.VISIBLE);
        }
        title.clear();desc.clear();date.clear();day.clear();month.clear();status.clear();urg.clear();cat.clear();
        while (cursor.moveToNext()){
            title.add(cursor.getString(0));
            desc.add(cursor.getString(1));
            date.add(cursor.getString(2));
            day.add(cursor.getString(3));
            month.add(cursor.getString(4));
            time.add(cursor.getString(5));
            status.add(cursor.getString(6));
            urg.add(cursor.getString(7));
            cat.add(cursor.getString(8));
        }
        myAdapter=new MyAdapter(getApplicationContext(),title,date,day,month,desc,status,urg,cat);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    public void reload() {
        Intent i = new Intent(this, MainActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }


    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,create.class);
        startActivity(intent);
//        finish();
    }
}