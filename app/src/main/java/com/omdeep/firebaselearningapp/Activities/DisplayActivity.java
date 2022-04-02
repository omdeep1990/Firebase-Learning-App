package com.omdeep.firebaselearningapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.omdeep.firebaselearningapp.Adapter.DisplayAdapter;
import com.omdeep.firebaselearningapp.Database.FirebaseDb;
import com.omdeep.firebaselearningapp.Interfaces.OnItemClickListenerFirebase;
import com.omdeep.firebaselearningapp.StudentInfo;
import com.omdeep.firebaselearningapp.Utility.Utility;
import com.omdeep.firebaselearningapp.databinding.ActivityDisplayBinding;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private ActivityDisplayBinding binding;
    private FirebaseDb firebaseDb;
    private List<StudentInfo> studentInfoList = new ArrayList<>();
    DisplayAdapter displayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDb = new FirebaseDb();
        displayAdapter = new DisplayAdapter(this, new OnItemClickListenerFirebase() {
            @Override
            public void onItemClick(int position, List<StudentInfo> studentInfo) {
//                Toast.makeText(DisplayActivity.this, studentInfo.get(position).getFirstName(), Toast.LENGTH_SHORT).show();
                Utility.showLongToast(DisplayActivity.this, studentInfo.get(position).getFirstName());
                Intent intent = new Intent(DisplayActivity.this, DisplayInfoActivity.class);
                intent.putExtra("_student_info_", new Gson().toJson(studentInfo.get(position)));
                startActivity(intent);
            }
        });

        binding.revView.setLayoutManager(new LinearLayoutManager(this));
        binding.revView.setAdapter(displayAdapter);

      addUpdatedData();


    }
    public void addUpdatedData() {
        firebaseDb.get().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    StudentInfo studentInfo = dataSnapshot.getValue(StudentInfo.class);
                    //TODO: Here setKey is the setter method and getKey is the method of firebase to get Key
                    studentInfo.setKey(dataSnapshot.getKey());
                    studentInfoList.add(studentInfo);
                    displayAdapter.setStudentInfoList(studentInfoList);
                    displayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (studentInfoList.size() > 0) studentInfoList.clear();
        addUpdatedData();
    }
}