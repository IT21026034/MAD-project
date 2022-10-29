package com.example.hospitalmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyProfileView extends AppCompatActivity {

    Button prescriptionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       /* prescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicked");
                Intent intent = new Intent(MyProfileView.this,CreateAppointment.class);
                startActivity(intent);
            }
        });*/
    }
}