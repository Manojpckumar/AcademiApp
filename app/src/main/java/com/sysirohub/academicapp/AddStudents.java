package com.sysirohub.academicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Adapter.AllClassAdapter;
import com.sysirohub.academicapp.Model.AllClass;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Model.ResponseCommon;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityAddClassBinding;
import com.sysirohub.academicapp.databinding.ActivityAddStudentsBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

public class AddStudents extends AppCompatActivity implements GetResult.MyListener, View.OnClickListener {

    ActivityAddStudentsBinding binding;
    CustPrograssbar custPrograssbar;
    List<AllClass> classesList;

    List<String> classIdList;
    List<String> classNameList;

    String fireemail,password;

    private FirebaseAuth auth;


    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddStudentsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.edDoj.setOnClickListener(this);
        binding.edDob.setOnClickListener(this);
        binding.btnAddStudent.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        custPrograssbar = new CustPrograssbar();

        custPrograssbar.progressCreate(this);
        getAllClassesFromApi();


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
                binding.spnClass.setAdapter(dataAdapter);
            }


        }
        else if(callNo.equalsIgnoreCase("register")) {
            Gson gson = new Gson();

            ResponseCommon response = gson.fromJson(result.toString(), ResponseCommon.class);

            if(response.getResult().equalsIgnoreCase("true"))
            {

                auth.createUserWithEmailAndPassword(fireemail, password)
                        .addOnCompleteListener(AddStudents.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(AddStudents.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                if (!task.isSuccessful()) {
                                    custPrograssbar.close();
                                    Toast.makeText(AddStudents.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    custPrograssbar.close();
                                    Toast.makeText(AddStudents.this, response.getResponseMsg(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
            else
            {
                custPrograssbar.close();
                Toast.makeText(AddStudents.this, response.getResponseMsg(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnAddStudent:

                custPrograssbar.progressCreate(this);
                addStudenttoApi();

                break;

            case R.id.edDob:

                showDatepopup();

                break;

            case R.id.edDoj:

                showDatepopup();
                break;


        }

    }

    private void showDatepopup() {

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };


        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd";
        String myPassFormat = "yyyyMMdd";

        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdfPass = new SimpleDateFormat(myPassFormat, Locale.US);

        password = sdfPass.format(myCalendar.getTime());

        if (binding.edDob.getText().toString().isEmpty()) {
            binding.edDob.setText(sdf.format(myCalendar.getTime()));
        } else {
            binding.edDoj.setText(sdf.format(myCalendar.getTime()));


        }
    }


    private void addStudenttoApi() {

        String name,email,mobile,classid,dob,doj;

        name = binding.edName.getText().toString();
        email = binding.edEmail.getText().toString();
        fireemail = binding.edEmail.getText().toString();
        mobile = binding.edMobile.getText().toString();

       int classPos = binding.spnClass.getSelectedItemPosition();
       classid = classIdList.get(classPos);

        dob = binding.edDob.getText().toString();
        doj = binding.edDoj.getText().toString();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("email", email);
            jsonObject.put("mobile", mobile);
            jsonObject.put("role", "student");

            jsonObject.put("class_id",classid);

            jsonObject.put("dob", dob);
            jsonObject.put("doj", doj);
            jsonObject.put("password", password);
            jsonObject.put("status", "1");

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().registerUser((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "register");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}