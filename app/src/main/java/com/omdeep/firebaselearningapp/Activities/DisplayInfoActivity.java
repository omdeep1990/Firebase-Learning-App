package com.omdeep.firebaselearningapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.omdeep.firebaselearningapp.Database.FirebaseDb;
import com.omdeep.firebaselearningapp.R;
import com.omdeep.firebaselearningapp.StudentInfo;
import com.omdeep.firebaselearningapp.Utility.Utility;
import com.omdeep.firebaselearningapp.databinding.ActivityDisplayInfoBinding;
import com.omdeep.firebaselearningapp.databinding.LayoutUpdateInfoBinding;

import java.util.HashMap;
import java.util.Map;

public class DisplayInfoActivity extends AppCompatActivity {
    private ActivityDisplayInfoBinding binding;
    private FirebaseDb firebaseDb;
    private StudentInfo studentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDb  = new FirebaseDb();
        String studentString = getIntent().getStringExtra("_student_info_");
        studentInfo = new Gson().fromJson(studentString, StudentInfo.class);

        binding.tvResult.setText(studentInfo.getFirstName()+" "+studentInfo.getLastName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_context_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.item_update_record:
            // TODO : Update
                LayoutUpdateInfoBinding binding1 =
                        LayoutUpdateInfoBinding.inflate(getLayoutInflater());
                Dialog dialog = new Dialog(DisplayInfoActivity.this);
                dialog.setContentView(binding1.getRoot());
                binding1.etFname.setText(studentInfo.getFirstName());
                binding1.etLname.setText(studentInfo.getLastName());
                binding1.etMobileno.setText(String.valueOf(studentInfo.getPhoneNumber()));
                binding1.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> hashMap = new HashMap<>();
                        hashMap.put("firstName", binding1.etFname.getText().toString());
                        hashMap.put("lastName", binding1.etLname.getText().toString());
                        hashMap.put("phoneNumber", Long.parseLong(binding1.etMobileno.getText().toString()));

                        firebaseDb.update(studentInfo.getKey(), hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Utility.showLongToast(DisplayInfoActivity.this, "Updated....");
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Utility.showLongToast(DisplayInfoActivity.this, e.getMessage());
                            }
                        });
                    }
                });

                dialog.show();


                break;
            case R.id.item_delete_record:
                //TODO : Delete
                firebaseDb.remove(studentInfo.getKey()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utility.showLongToast(DisplayInfoActivity.this, e.getMessage());
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}