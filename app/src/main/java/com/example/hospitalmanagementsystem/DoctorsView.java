package com.example.hospitalmanagementsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DoctorsView extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<DoctorModel> doctorsList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    String selectedTime,selectedDate;
    ProgressDialog progressDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_view);
        doctorsList = new ArrayList<DoctorModel>();
        selectedDate = getIntent().getStringExtra("DATE");
        selectedTime = getIntent().getStringExtra("TIME");
        myAdapter = new MyAdapter(DoctorsView.this,doctorsList,selectedDate,selectedTime);

        progressDialog = new ProgressDialog(this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading data");
        progressDialog.show();



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(myAdapter);


        db = FirebaseFirestore.getInstance();


        EventChanger();
    }

    void EventChanger(){
        db.collection("doctors").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentSnapshot snapshot:value){
                    DoctorModel doctorModel = new DoctorModel(
                            snapshot.getId(),
                            snapshot.getString("name"),
                            snapshot.getString("email")
                    );
                    boolean shouldAdd =   checkDoctorHasAppointment(doctorModel.id,selectedTime,selectedDate);
                  if(shouldAdd){
                        doctorsList.add(doctorModel);
                        myAdapter.notifyDataSetChanged();
                  }
                }
            }
        });
        progressDialog.dismiss();
    }
    boolean result = true;
    boolean checkDoctorHasAppointment(String doctorId,String time,String date){
        db.collection("appointments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    for(DocumentSnapshot snapshot :value.getDocuments()){
                        if(snapshot.getString("appointTo") == doctorId){
                            if(snapshot.getString("appointDate") ==  date){
                                if(snapshot.getString("appointTime") == time){
                                    result  = false;
                                }
                            }
                        }
                    }
                }else{
                    result =  false;
                }
            }
        });
        return result;
    }
}