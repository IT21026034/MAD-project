package com.example.hospitalmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class CreateAppointment extends AppCompatActivity {

    TextView dateTextView,timeTextView;
    Button button,timePickerButton,checkForAppointmentsButton;

    DatePickerDialog.OnDateSetListener setListener;

    int t1Hour,t1Minute = 0;
    String dateValue = "";
    String timeValue = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        dateTextView = findViewById(R.id.dateTextView);
        button = findViewById(R.id.pickButton);
        timePickerButton = findViewById(R.id.pickTimeButton);
        timeTextView =findViewById(R.id.timeTextView);
        checkForAppointmentsButton = findViewById(R.id.checkButton);

        Calendar calendar = Calendar.getInstance();
        final int year =calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int date = calendar.get(Calendar.DAY_OF_MONTH);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateAppointment.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,date);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });


        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateAppointment.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                t1Hour = i;
                                t1Minute = i1;

                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.set(0,0,0,t1Hour,t1Minute);
                                timeValue = String.valueOf(DateFormat.format("hh:mm aa",calendar1));
                                timeTextView.setText(DateFormat.format("hh:mm aa",calendar1));
                            }
                        },12,0,false
                );

                timePickerDialog.updateTime(t1Hour,t1Minute);
                timePickerDialog.show();
            }
        });



        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1+1;
                String d = i + "/" + i1 + "/" + i2;
                dateValue = d;
                dateTextView.setText(d);
            }
        };

        checkForAppointmentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timeValue == "" ){
                    Toast.makeText(CreateAppointment.this, "Please pick a time", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(dateValue == ""){
                    Toast.makeText(CreateAppointment.this, "Please pick a date", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(CreateAppointment.this,DoctorsView.class);
                intent.putExtra("TIME",timeValue);
                intent.putExtra("DATE",dateValue);
                System.out.println("timeValue " + timeValue);
                startActivity(intent);
            }
        });
    }
}