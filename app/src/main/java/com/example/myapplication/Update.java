package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Update extends AppCompatActivity {

    EditText tit,desc,other;
    TextView date;
    LinearLayout layout;
    String ind_t="";
    DBHelper dbHelper;
    String s1,s2,s3,day_name;
    Button b;
    int year,month,day;
    private Spinner cat,prio,stat;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();
        tit=findViewById(R.id.title);
        desc=findViewById(R.id.description);
        date=findViewById(R.id.date);
        cat=findViewById(R.id.category);
        prio=findViewById(R.id.priority);
        stat=findViewById(R.id.status);
        layout=findViewById(R.id.llayout);
        other=findViewById(R.id.other);
        dbHelper = new DBHelper(this);



        List<String> categories = new ArrayList<String>();
        categories.add("High");
        categories.add("Medium");
        categories.add("Low");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prio.setAdapter(dataAdapter);

        List<String> categories1 = new ArrayList<String>();
        categories1.add("Completed");
        categories1.add("Partially Completed");
        categories1.add("Not Completed");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stat.setAdapter(dataAdapter1);




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ind_t = extras.getString("title");
        }
        Cursor cursor= dbHelper.fetch(ind_t);
        if (cursor!=null) {
            while(cursor.moveToNext()) {
                tit.setText(cursor.getString(0));
                desc.setText(cursor.getString(1));
            }
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Update.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year1,
                                                  int monthOfYear, int dayOfMonth) {
                                Date date1;
                                day=dayOfMonth;month=monthOfYear+1;year=year1;
                                try {
                                    date1=new SimpleDateFormat("dd/MM/yyyy").parse(day+"/"+month+"/"+year);
                                } catch (ParseException e) {
                                    throw new RuntimeException(e);
                                }
                                Format f = new SimpleDateFormat("EEEE");
                                day_name = f.format(date1);

                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s1=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        prio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s2=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        stat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s3=adapterView.getItemAtPosition(i).toString();
                if (s3.equals("Other"))
                    layout.setVisibility(View.VISIBLE);
                else
                    layout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        b=findViewById(R.id.addTask);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (s3.equals("Other"))
                    s3=other.getText().toString();
                String month1="";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    Month mon = Month.of(month);
                    month1=mon.toString();
                }
                boolean b=dbHelper.update(ind_t,tit.getText().toString(),desc.getText().toString(),String.valueOf(day),day_name,month1,"No time",s3,s2,s1);
                if (b){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}