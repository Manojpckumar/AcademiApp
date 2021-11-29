package com.sysirohub.academicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Adapter.AllClassAdapter;
import com.sysirohub.academicapp.Adapter.AllClassTeacherAdapter;
import com.sysirohub.academicapp.Model.AllClass;
import com.sysirohub.academicapp.Model.Allassigned;
import com.sysirohub.academicapp.Model.Common;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Model.ResponseCommon;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityAllClassesBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AllClasses extends AppCompatActivity implements GetResult.MyListener,RecyclerViewClickInterface {

    ActivityAllClassesBinding binding;
    CustPrograssbar custPrograssbar;

    List<AllClass> classesList;
    List<Allassigned> classTeacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAllClassesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        custPrograssbar = new CustPrograssbar();

        String classid = "0";
        custPrograssbar.progressCreate(this);

        if(Common.userRole.equalsIgnoreCase("admin"))
        {
            getAllClasses(classid);
        }
        else if(Common.userRole.equalsIgnoreCase("teacher"))
        {
            binding.fabAddClass.setVisibility(View.GONE);
            getAllClassesByTeacherId(Common.teacherID);
        }



        binding.fabAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AllClasses.this,AddClass.class));
                finish();

            }
        });

    }

    private void getAllClassesByTeacherId(String teacherID) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("t_id", teacherID);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAssignedClasses((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAssignedClass");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getAllClasses(String classid) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("class", classid);

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

        if(callNo.equalsIgnoreCase("getAllClasses")){

            Gson gson = new Gson();
            custPrograssbar.close();

            Example example = gson.fromJson(result.toString(),Example.class);

            classesList = new ArrayList<>();

            classesList = example.getResultData().getAllClasses();

            if(classesList.isEmpty())
            {
                custPrograssbar.close();
                binding.tvNothing.setVisibility(View.VISIBLE);
                binding.rcvAllClasses.setVisibility(View.GONE);
            }else
            {
                custPrograssbar.close();
                binding.rcvAllClasses.setVisibility(View.VISIBLE);

                AllClassAdapter adapter = new AllClassAdapter(AllClasses.this, classesList);
                binding.rcvAllClasses.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvAllClasses.setAdapter(adapter);

            }

        }

        else if(callNo.equalsIgnoreCase("getAssignedClass")){

            Gson gson = new Gson();
            custPrograssbar.close();

            Example example = gson.fromJson(result.toString(),Example.class);

            classTeacherList = new ArrayList<>();

            classTeacherList = example.getResultData().getAllassigned();

            if(classTeacherList.isEmpty())
            {
                custPrograssbar.close();
                binding.tvNothing.setVisibility(View.VISIBLE);
                binding.rcvAllClasses.setVisibility(View.GONE);
            }else
            {
                custPrograssbar.close();
                binding.rcvAllClasses.setVisibility(View.VISIBLE);

                AllClassTeacherAdapter adapter = new AllClassTeacherAdapter(AllClasses.this, classTeacherList,this);
                binding.rcvAllClasses.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvAllClasses.setAdapter(adapter);

            }

        }

    }

    @Override
    public void onItemClick(int position, String chk) {

        if (chk.equalsIgnoreCase("GOTOSUB")) {

            Common.teacherClassId = classTeacherList.get(position).getClassId();

            Intent intent = new Intent(AllClasses.this,AllSubjects.class);
            startActivity(intent);

        }

    }
}