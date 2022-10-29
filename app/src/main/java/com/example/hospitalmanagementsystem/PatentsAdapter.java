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

public class PatentsAdapter  extends RecyclerView.Adapter<PatentsAdapter.MyViewHolder>{

    Context context;
    ArrayList<PatientModel> patientsArrayList;


    public PatentsAdapter(Context context, ArrayList<PatientModel> patientsArrayList) {
        this.context = context;
        this.patientsArrayList = patientsArrayList;
    }

    @NonNull
    @Override
    public PatentsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.patient_card,parent,false);

        return new PatentsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PatentsAdapter.MyViewHolder holder, int position) {
        PatientModel patientModel = patientsArrayList.get(position);

       holder.patientsId.setText(patientModel.id);
       holder.patientsWard.setText(patientModel.ward);
          holder.patientsName.setText(patientModel.name);
          holder.patientsRoomNo.setText(patientModel.roomNo);

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AddEditPatientActivity.class);
                intent.putExtra("PATIENT_NAME",patientModel.name);
                intent.putExtra("PATIENT_ID",patientModel.id);
                intent.putExtra("PATIENT_WARD",patientModel.ward);
                intent.putExtra("PATIENT_ROOM_NUMBER",patientModel.roomNo);
                context.startActivity(intent);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("patients").document(patientModel.id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(context,AllPatientsActivity.class);
                            context.startActivity(intent);
                            Toast.makeText(context, "Patient deleted successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Patient deleting failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientsArrayList.size();
    }

    public static class  MyViewHolder extends  RecyclerView.ViewHolder{

        TextView patientsName,patientsRoomNo,patientsId,patientsWard;
        Button editButton,deleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            patientsId = itemView.findViewById(R.id.patientIDTextView);
            patientsName = itemView.findViewById(R.id.patientNameTextView);
            patientsRoomNo = itemView.findViewById(R.id.patientRoomNumberTextView);
            patientsWard = itemView.findViewById(R.id.patientWardTextView);

            editButton = itemView.findViewById(R.id.editPatientButton);
            deleteButton = itemView.findViewById(R.id.deletePatientButton);

        }
    }
}
