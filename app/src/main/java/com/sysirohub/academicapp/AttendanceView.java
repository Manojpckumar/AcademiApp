package com.sysirohub.academicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Adapter.AllStudentsAdapter;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Model.Student;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityAttendanceViewBinding;
import com.sysirohub.academicapp.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AttendanceView extends AppCompatActivity implements GetResult.MyListener {

    ActivityAttendanceViewBinding binding;
    CustPrograssbar custPrograssbar;
    List<Student> studentList;
    String viewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAttendanceViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String classid = getIntent().getStringExtra("classId");


        custPrograssbar = new CustPrograssbar();
        custPrograssbar.progressCreate(this);

        String classID = "10";
        getStudentsByClassId(classid);


    }

    private void getStudentsByClassId(String classID) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("class_id", classID);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().AllStudentsByClass((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "AllStudentsByClass");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("AllStudentsByClass")) {

            Gson gson = new Gson();
            custPrograssbar.close();

            Example example = gson.fromJson(result.toString(),Example.class);

            if(example.getResultData().getStudents() == null)
            {
                custPrograssbar.close();
                binding.noStudents.setVisibility(View.VISIBLE);
                binding.rcvAttendanceView.setVisibility(View.GONE);
            }
            else
            {
                studentList = new ArrayList<>();

                studentList = example.getResultData().getStudents();

                viewType = "Attendance";

                custPrograssbar.close();
                binding.noStudents.setVisibility(View.GONE);
                binding.rcvAttendanceView.setVisibility(View.VISIBLE);
                AllStudentsAdapter adapter = new AllStudentsAdapter(AttendanceView.this, studentList,viewType);
                binding.rcvAttendanceView.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvAttendanceView.setAdapter(adapter);
            }

        }

    }
}