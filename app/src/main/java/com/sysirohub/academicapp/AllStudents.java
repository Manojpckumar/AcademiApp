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
import com.sysirohub.academicapp.Model.AllClass;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Model.Student;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityAllStudentsBinding;
import com.sysirohub.academicapp.databinding.ActivityHomeBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AllStudents extends AppCompatActivity implements GetResult.MyListener {

    ActivityAllStudentsBinding binding;
    CustPrograssbar custPrograssbar;

    List<AllClass> classesList;
    List<Student> studentList;

    List<String> classIdList;
    List<String> classNameList;

    String classId,viewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAllStudentsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        custPrograssbar = new CustPrograssbar();

        getAllClassesFromApi();

        binding.fabAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AllStudents.this,AddStudents.class));
                finish();

            }
        });


        binding.spnAllClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              String cId = classIdList.get(position);
              custPrograssbar.progressCreate(AllStudents.this);
              getStudentsFromAPI(cId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getStudentsFromAPI(String classId) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("class_id", classId);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().AllStudentsByClass((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "AllStudentsByClass");
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
                binding.spnAllClasses.setAdapter(dataAdapter);

                int studentPos = binding.spnAllClasses.getSelectedItemPosition();
                 String classI = classIdList.get(studentPos);
                getStudentsFromAPI(classI);
            }


        }
       else if (callNo.equalsIgnoreCase("AllStudentsByClass")) {

            Gson gson = new Gson();
            custPrograssbar.close();

            Example example = gson.fromJson(result.toString(),Example.class);

            if(example.getResultData().getStudents() == null)
            {
                custPrograssbar.close();
                binding.tvNoStudents.setVisibility(View.VISIBLE);
                binding.rcvAllStudents.setVisibility(View.GONE);
            }
            else
            {
                classesList = new ArrayList<>();

                studentList = example.getResultData().getStudents();

                viewType = "Students";

                custPrograssbar.close();
                binding.rcvAllStudents.setVisibility(View.VISIBLE);
                AllStudentsAdapter adapter = new AllStudentsAdapter(AllStudents.this, studentList,viewType);
                binding.rcvAllStudents.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvAllStudents.setAdapter(adapter);
            }

        }

    }
}