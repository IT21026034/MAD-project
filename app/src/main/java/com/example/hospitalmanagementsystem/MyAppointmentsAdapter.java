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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAppointmentsAdapter extends RecyclerView.Adapter<MyAppointmentsAdapter.MyViewHolder> {

    Context context;
    ArrayList<AppointmentModel> appointments;


    public MyAppointmentsAdapter(Context context, ArrayList<AppointmentModel> appointments) {
        this.context = context;
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public MyAppointmentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.my_appointment_card,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAppointmentsAdapter.MyViewHolder holder, int position) {
        AppointmentModel doctorModel = appointments.get(position);

        holder.reviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.markCompletedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map data =new HashMap();
                data.put("status",true);
                db.collection("appointments").document(doctorModel.id).update(data).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context, "Appointment status updated Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context,AllAppointmentsActivity.class);
                            context.startActivity(intent);
                        }else{
                            Toast.makeText(context, "Update status failed,Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        holder.date.setText(doctorModel.appointDate);
        holder.time.setText(doctorModel.appointTime);
        holder.roomNumber.setText(String.valueOf(doctorModel.roomNumber));
        if(doctorModel.status){
            holder.status.setText("Completed");
        }else{
            holder.status.setText("Not Completed");
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("doctors").document(doctorModel.appointTo).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error == null){
                    holder.doctorsName.setText(value.getString("name"));
                }else{
                    holder.doctorsName.setText("");
                }
            }
        });
       if(doctorModel.status){
            holder.reviewButton.setVisibility(View.VISIBLE);
            holder.markCompletedButton.setVisibility(View.INVISIBLE);
        }else{
            holder.reviewButton.setVisibility(View.INVISIBLE);
            holder.markCompletedButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class  MyViewHolder extends  RecyclerView.ViewHolder{

        TextView doctorsName,date,time,roomNumber,status;
        Button reviewButton,markCompletedButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            doctorsName= itemView.findViewById(R.id.doctorsNameTextField);
            reviewButton = itemView.findViewById(R.id.appointmentAddReviewButton);
            markCompletedButton= itemView.findViewById(R.id.markCompletedButton);
            date= itemView.findViewById(R.id.appointmentDateTextField);
            time= itemView.findViewById(R.id.appointmentTimeField);
            roomNumber= itemView.findViewById(R.id.roomNumberTextField);
            status= itemView.findViewById(R.id.statusTextView);
        }
    }
}
