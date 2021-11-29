package com.sysirohub.academicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Model.AllClass;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Model.ResponseCommon;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityAddClassBinding;
import com.sysirohub.academicapp.databinding.ActivityAddSubjectBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AddSubject extends AppCompatActivity implements GetResult.MyListener {

    ActivityAddSubjectBinding binding;
    CustPrograssbar custPrograssbar;

    List<AllClass> classesList;
    List<String> classIdList;
    List<String> classNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddSubjectBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        custPrograssbar = new CustPrograssbar();
        custPrograssbar.progressCreate(this);
        getAllClassesFromApi();

        binding.btnAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = binding.spnClassNames.getSelectedItemPosition();
                String classId = classIdList.get(pos);
                String subName = binding.edSubjectName.getText().toString();

                if( classId.equalsIgnoreCase("") && subName.equalsIgnoreCase("") )
                {
                    Toast.makeText(AddSubject.this, "Select class name and enter subject name", Toast.LENGTH_SHORT).show();
                }else if( !classId.equalsIgnoreCase("") && subName.equalsIgnoreCase("") )
                {
                    Toast.makeText(AddSubject.this, "Enter subject name", Toast.LENGTH_SHORT).show();

                }else if(classId.equalsIgnoreCase("") && !subName.equalsIgnoreCase(""))
                {
                    Toast.makeText(AddSubject.this, "Select class name first", Toast.LENGTH_SHORT).show();

                }else
                {
                    custPrograssbar.progressCreate(AddSubject.this);
                    addSubjectToAPI(classId,subName);
                }

            }
        });


    }

    private void addSubjectToAPI(String classId, String subName) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("class_id", classId);
            jsonObject.put("subject_name", subName);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().addSubjecttoAPI((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "addSubjects");
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
                binding.spnClassNames.setAdapter(dataAdapter);
            }


        }
       else if (callNo.equalsIgnoreCase("addSubjects")) {

            Gson gson = new Gson();
            custPrograssbar.close();

            ResponseCommon common = gson.fromJson(result.toString(),ResponseCommon.class);

            if(common.getResult().equalsIgnoreCase("true"))
            {
                custPrograssbar.close();
                Toast.makeText(AddSubject.this, common.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }else
            {
                custPrograssbar.close();
                Toast.makeText(AddSubject.this, common.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }


        }
       else
        {

        }
    }
}