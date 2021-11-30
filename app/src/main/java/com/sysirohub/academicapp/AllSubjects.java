package com.sysirohub.academicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Adapter.AllClassAdapter;
import com.sysirohub.academicapp.Adapter.AllStudentsAdapter;
import com.sysirohub.academicapp.Adapter.AllSubjectTeacherAdapter;
import com.sysirohub.academicapp.Adapter.AllSubjectsAdapter;
import com.sysirohub.academicapp.Model.AllClass;
import com.sysirohub.academicapp.Model.AllSubject;
import com.sysirohub.academicapp.Model.AllSubyclas;
import com.sysirohub.academicapp.Model.Common;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Model.Student;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityAddSubjectBinding;
import com.sysirohub.academicapp.databinding.ActivityAllStudentsBinding;
import com.sysirohub.academicapp.databinding.ActivityAllSubjectsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AllSubjects extends AppCompatActivity implements GetResult.MyListener,RecyclerViewClickInterface{

    ActivityAllSubjectsBinding binding;
    CustPrograssbar custPrograssbar;

    List<AllClass> classesList;

    List<AllSubject> subjects;

    List<AllSubyclas> teachersSubject;

    List<String> classIdList;
    List<String> classNameList;

    String classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAllSubjectsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        custPrograssbar = new CustPrograssbar();

        if(Common.userRole.equalsIgnoreCase("admin"))
        {
            getAllClassesFromApi();
        }
        else if(Common.userRole.equalsIgnoreCase("teacher"))
        {
            binding.spnAllSubjects.setVisibility(View.GONE);
            binding.fabAddSubject.setVisibility(View.GONE);

            String tId =  Common.teacherID;
            getSubjectsToTeacher(Common.teacherClassId,tId);

        }



        binding.fabAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AllSubjects.this,AddSubject.class));
                finish();

            }
        });

        binding.spnAllSubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String cId = classIdList.get(position);
                custPrograssbar.progressCreate(AllSubjects.this);
                getSubjects(cId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getSubjectsToTeacher(String classId, String teacherId) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("class_id", classId);
            jsonObject.put("teacher_id", teacherId);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getSubjectsByClassTeacher((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAssignedClassbysubT");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getSubjects(String cId) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("class_id", cId);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllSubjects((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getSubjectsbyClass");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getAllClassesFromApi() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("class", "0");

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllClasses((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAllClasses");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("getAllClasses")) {

            Gson gson = new Gson();
            custPrograssbar.close();
            Example example = gson.fromJson(result.toString(), Example.class);

            classesList = new ArrayList<>();
            classIdList = new ArrayList<>();
            classNameList = new ArrayList<>();

            classesList = example.getResultData().getAllClasses();

            for(AllClass allClass : classesList)
            {
                classIdList.add(allClass.getId().toString());
                classNameList.add(allClass.getClassName().toString());
            }

            if (!classNameList.isEmpty()) {

                custPrograssbar.close();

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classNameList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spnAllSubjects.setAdapter(dataAdapter);

                int studentPos = binding.spnAllSubjects.getSelectedItemPosition();
                String classI = classIdList.get(studentPos);
                getSubjects(classI);
            }


        }
        else if (callNo.equalsIgnoreCase("getSubjectsbyClass")) {

            Gson gson = new Gson();

            custPrograssbar.close();

            Example example = gson.fromJson(result.toString(),Example.class);

            if(example.getResultData().getAllSubjects() == null)
            {
                custPrograssbar.close();
                binding.tvNoStudents.setVisibility(View.VISIBLE);
                binding.rcvAllSubjects.setVisibility(View.GONE);
            }
            else
            {

                    subjects = new ArrayList<>();

                    subjects = example.getResultData().getAllSubjects();

                    custPrograssbar.close();
                    binding.rcvAllSubjects.setVisibility(View.VISIBLE);
                    AllSubjectsAdapter adapter = new AllSubjectsAdapter(AllSubjects.this, subjects);
                    binding.rcvAllSubjects.setLayoutManager(new LinearLayoutManager(this));
                    binding.rcvAllSubjects.setAdapter(adapter);

            }

        }
        else if (callNo.equalsIgnoreCase("getAssignedClassbysubT")) {

            Gson gson = new Gson();

            custPrograssbar.close();

            Example example = gson.fromJson(result.toString(),Example.class);

            if(example.getResultData().getAllSubyclass() == null)
            {
                custPrograssbar.close();
                binding.tvNoStudents.setVisibility(View.VISIBLE);
                binding.rcvAllSubjects.setVisibility(View.GONE);
            }
            else
            {

                teachersSubject = new ArrayList<>();

                teachersSubject = example.getResultData().getAllSubyclass();

                custPrograssbar.close();
                binding.rcvAllSubjects.setVisibility(View.VISIBLE);

                AllSubjectTeacherAdapter adapter = new AllSubjectTeacherAdapter(AllSubjects.this, teachersSubject,this);
                binding.rcvAllSubjects.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvAllSubjects.setAdapter(adapter);

            }

        }



    }


    @Override
    public void onItemClick(int position, String chk) {

        if (chk.equalsIgnoreCase("GOTOATT")) {

            Common.teacherSubjectId = teachersSubject.get(position).getSubjectId();

            Intent intent = new Intent(AllSubjects.this,AttendanceView.class);
            startActivity(intent);

        }
    }
}