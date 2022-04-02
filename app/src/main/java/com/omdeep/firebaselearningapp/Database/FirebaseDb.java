package com.omdeep.firebaselearningapp.Database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.omdeep.firebaselearningapp.StudentInfo;

import java.util.Map;

public class FirebaseDb {
    public DatabaseReference databaseReference;
    public FirebaseDb() {
        databaseReference = FirebaseDatabase.getInstance().getReference(StudentInfo.class.getSimpleName());
    }

    //TODO: Query to add items to firebase
    public Task<Void> add(StudentInfo studentInfo) {
        return databaseReference.push().setValue(studentInfo);
    }

    //TODO: Query to display items
    public Query get(){
        return databaseReference;
    }

    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }


    public Task<Void> update(String key, Map<String, Object> data){
        return databaseReference.child(key).updateChildren(data);
    }
}
