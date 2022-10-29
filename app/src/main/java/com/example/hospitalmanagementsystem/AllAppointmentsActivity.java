package com.example.hospitalmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class AllAppointmentsActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    Button btn;

    MyAppointmentsAdapter adapter;
    ArrayList<AppointmentModel> appointments;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_appointments);


        recyclerView = findViewById(R.id.myResc);

        db = FirebaseFirestore.getInstance();
        appointments = new ArrayList<AppointmentModel>();
        adapter = new MyAppointmentsAdapter(this,appointments);
        recyclerView.setAdapter(adapter);

      db.collection("appointments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null){
                    for(DocumentSnapshot snapshot:value){
                        AppointmentModel appointmentModel = new AppointmentModel(
                                snapshot.getId(),
                        snapshot.getString("appointBy"),
                        snapshot.getString ("appointTo"),
                        snapshot.getString("appointDate"),
                        snapshot.getString("appointTime"),
                        snapshot.getDouble ("roomNumber").intValue(),
                        snapshot.getBoolean("status")
                        );
                        appointments.add(appointmentModel);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(AllAppointmentsActivity.this, "Get Appointments Failed " + error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}