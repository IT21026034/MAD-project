package com.example.hospitalmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<DoctorModel> doctorsArrayList;
    String date;
    String time;

    public MyAdapter(Context context, ArrayList<DoctorModel> doctorsArrayList, String date, String time) {
        this.context = context;
        this.doctorsArrayList = doctorsArrayList;
        this.date = date;
        this.time = time;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        DoctorModel doctorModel = doctorsArrayList.get(position);
        holder.doctorsName.setText(doctorModel.name);
        holder.doctorsEmail.setText(doctorModel.email);


        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DoctorProfile.class);
                context.startActivity(intent);
            }
        });

        holder.appointButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                AppointmentModel appointmentModel = new AppointmentModel(
                        "",
                        "currentUser",
                        doctorModel.id,
                        date,
                        time,
                        100,false
                );
                db.collection("appointments").document().set(appointmentModel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(context, "Appointment Created Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context,AllAppointmentsActivity.class);
                                    context.startActivity(intent);
                                }else{
                                    Toast.makeText(context, "Create Appointment failed,Try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorsArrayList.size();
    }

    public static class  MyViewHolder extends  RecyclerView.ViewHolder{

        TextView doctorsName,doctorsEmail;
        Button viewButton,appointButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorsName = itemView.findViewById(R.id.tvName);
            doctorsEmail = itemView.findViewById(R.id.tvEmail);
            viewButton = itemView.findViewById(R.id.viewDoctorButton);
            appointButton = itemView.findViewById(R.id.createAppointmentButton);


        }
    }
}
