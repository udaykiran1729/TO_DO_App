package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import android.widget.Toast;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class create extends AppCompatActivity implements View.OnClickListener {

    Button b;
    EditText t1,t2,other;
    LinearLayout layout;
    TextView ed2;
    DBHelper dbHelper;
    String s1,s2,s3,day_name;
    int year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_create);
        final Spinner spinner = (Spinner) findViewById(R.id.priority);
        layout=findViewById(R.id.llayout);
        other=findViewById(R.id.other);
        dbHelper=new DBHelper(this);
        b=findViewById(R.id.addTask);
        b.setOnClickListener(this);
        t1=findViewById(R.id.title);
        t2=findViewById(R.id.description);
        ed2=findViewById(R.id.date);
        //date
        ed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        create.this,
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

                                ed2.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        //for priority
        List<String> categories = new ArrayList<String>();
        categories.add("High");
        categories.add("Medium");
        categories.add("Low");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s1=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //for status
        final Spinner spinner1 = (Spinner) findViewById(R.id.status);
        List<String> categories1 = new ArrayList<String>();
        categories1.add("Completed");
        categories1.add("Partially Completed");
        categories1.add("Not Completed");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s2=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner spinner2=(Spinner) findViewById(R.id.category);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s3=adapterView.getItemAtPosition(i).toString();
                if (s3.equals("Other")){
                    layout.setVisibility(View.VISIBLE);
                }
                else {
                    layout.setVisibility(view.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        String title=t1.getText().toString();
        String desc=t2.getText().toString();
//        String day1=String.valueOf(day);
        String month1="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Month mon = Month.of(month);
            month1=mon.toString();
        }

        if (!(s3.isEmpty() || s1.isEmpty() || s2.isEmpty() || title.isEmpty() || desc.isEmpty() || day_name.isEmpty() || month1.isEmpty())){

            if (s3.equals("Others")){
                s3=other.getText().toString();
            }
            Boolean check=dbHelper.insert(title,desc,String.valueOf(day),day_name.substring(0,3),month1,"No time",s2,s1,s3);
            if (check){
                Toast.makeText(create.this, "Remainder added successfully", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(create.this, "Internal Error", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(create.this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        else{
            Toast.makeText(this, "Incomplete Fields", Toast.LENGTH_SHORT).show();
        }
    }
}