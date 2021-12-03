package com.sysirohub.academicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.sysirohub.academicapp.databinding.ActivityAddSubjectBinding;
import com.sysirohub.academicapp.databinding.ActivityAddTasksBinding;

public class AddTasks extends AppCompatActivity {

    ActivityAddTasksBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddTasksBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


    }
}