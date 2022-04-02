package com.omdeep.firebaselearningapp.Interfaces;

import com.omdeep.firebaselearningapp.StudentInfo;

import java.util.List;

public interface OnItemClickListenerFirebase {

    //TODO: In interface by default all methods are public, they need to be changed if required.
    void onItemClick(int position, List<StudentInfo> studentInfo);
}
