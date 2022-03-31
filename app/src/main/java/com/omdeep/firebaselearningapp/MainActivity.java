package com.omdeep.firebaselearningapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.omdeep.firebaselearningapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseDb firebaseDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDb = new FirebaseDb();

        binding.btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DisplayActivity.class));
            }
        });
        binding.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setFirstName(binding.etFname.getText().toString());
                studentInfo.setLastName(binding.etLname.getText().toString());
                studentInfo.setPhoneNumber(Long.parseLong(binding.etMobileno.getText().toString()));

                firebaseDb.add(studentInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Utility.showLongToast(MainActivity.this, "Successfully Inserted...");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utility.showLongToast(MainActivity.this, e.getMessage());
                    }
                });

            }
        });
    }
}