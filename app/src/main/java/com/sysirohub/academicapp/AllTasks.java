package com.sysirohub.academicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Adapter.AllClassTeacherAdapter;
import com.sysirohub.academicapp.Model.AllClass;
import com.sysirohub.academicapp.Model.Allassigned;
import com.sysirohub.academicapp.Model.Common;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityAllSubjectsBinding;
import com.sysirohub.academicapp.databinding.ActivityAllTasksBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AllTasks extends AppCompatActivity implements GetResult.MyListener {

    ActivityAllTasksBinding binding;
    CustPrograssbar custPrograssbar;

    List<Allassigned> classesList;
    List<String> classIDList;
    List<String> classNameList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAllTasksBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        custPrograssbar = new CustPrograssbar();
        custPrograssbar.progressCreate(this);

        getAllClassesFromApi();

        binding.fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AllTasks.this,AddTasks.class);
                startActivity(intent);

            }
        });

    }

    private void getAllClassesFromApi() {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("t_id", Common.teacherID);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAssignedClasses((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAssignedClass");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("getAssignedClass")){

            Gson gson = new Gson();
            custPrograssbar.close();

            Example example = gson.fromJson(result.toString(),Example.class);

            classesList = new ArrayList<>();
            classIDList = new ArrayList<>();
            classNameList = new ArrayList<>();

            classesList = example.getResultData().getAllassigned();

            for(Allassigned allClass : classesList)
            {
                classIDList.add(allClass.getClassId().toString());
                classNameList.add(allClass.getClassName().toString());
            }

            if (!classNameList.isEmpty()) {

                custPrograssbar.close();

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classNameList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spnAllClasses.setAdapter(dataAdapter);

//                int studentPos = binding.spnAllClasses.getSelectedItemPosition();
//                String classI = classIdList.get(studentPos);
//                getStudentsFromAPI(classI);
            }

        }

    }
}