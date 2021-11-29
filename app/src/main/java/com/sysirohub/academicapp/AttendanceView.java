package com.sysirohub.academicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.sysirohub.academicapp.databinding.ActivityAttendanceViewBinding;
import com.sysirohub.academicapp.databinding.ActivityMainBinding;

public class AttendanceView extends AppCompatActivity {

    ActivityAttendanceViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAttendanceViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


    }
}