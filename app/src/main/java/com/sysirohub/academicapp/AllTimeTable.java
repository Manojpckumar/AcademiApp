package com.sysirohub.academicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Adapter.TimeTable.FridayAdapter;
import com.sysirohub.academicapp.Adapter.TimeTable.MondayAdapter;
import com.sysirohub.academicapp.Adapter.TimeTable.SaturdayAdapter;
import com.sysirohub.academicapp.Adapter.TimeTable.ThursdayAdapter;
import com.sysirohub.academicapp.Adapter.TimeTable.TuesdayAdapter;
import com.sysirohub.academicapp.Adapter.TimeTable.WednesdayAdapter;
import com.sysirohub.academicapp.Model.AllClass;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Model.Friday;
import com.sysirohub.academicapp.Model.Monday;
import com.sysirohub.academicapp.Model.Saturday;
import com.sysirohub.academicapp.Model.Student;
import com.sysirohub.academicapp.Model.Thursday;
import com.sysirohub.academicapp.Model.Tuesday;
import com.sysirohub.academicapp.Model.Wednesday;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityAllTimeTableBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AllTimeTable extends AppCompatActivity implements GetResult.MyListener,RecyclerViewClickInterface {

    ActivityAllTimeTableBinding binding;
    CustPrograssbar custPrograssbar;

    List<AllClass> classesList;
    List<Student> studentList;

    List<String> classIdList;
    List<String> classNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllTimeTableBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        custPrograssbar = new CustPrograssbar();
        custPrograssbar.progressCreate(this);

        getAllClassesFromApi();

        binding.fabAddTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AllTimeTable.this,AddTimeTable.class));
                finish();

            }
        });

        binding.spnAllClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String classI = classIdList.get(position);
                getTimeTable(classI);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                getTimeTable(classI);
            }

        }
        else if (callNo.equalsIgnoreCase("getAttendanceModule")) {

            Gson gson = new Gson();
            custPrograssbar.close();

            Example example = gson.fromJson(result.toString(), Example.class);

            List<Monday> list = example.getResultData().getMonday();
            List<Tuesday> list2 = example.getResultData().getTuesday();
            List<Wednesday> list3 = example.getResultData().getWednesday();
            List<Thursday> list4 = example.getResultData().getThursday();
            List<Friday> list5 = example.getResultData().getFriday();
            List<Saturday> list6 = example.getResultData().getSaturday();

            if( list != null && list2 != null && list3 != null && list4 != null && list5 != null && list6 != null)
            {
                binding.rcvMonday.setVisibility(View.VISIBLE);
                binding.llDays.setVisibility(View.VISIBLE);
                binding.llTimes.setVisibility(View.VISIBLE);
                binding.tvNoStudents.setVisibility(View.GONE);

                MondayAdapter adapter = new MondayAdapter(this, list,this);
                binding.rcvMonday.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                binding.rcvMonday.setAdapter(adapter);

                binding.rcvTuesday.setVisibility(View.VISIBLE);
                TuesdayAdapter adapter1 = new TuesdayAdapter(this, list2,this);
                binding.rcvTuesday.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                binding.rcvTuesday.setAdapter(adapter1);

                binding.rcvWednesday.setVisibility(View.VISIBLE);
                WednesdayAdapter adapter2 = new WednesdayAdapter(this, list3,this);
                binding.rcvWednesday.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                binding.rcvWednesday.setAdapter(adapter2);

                binding.rcvThursday.setVisibility(View.VISIBLE);
                ThursdayAdapter adapter3 = new ThursdayAdapter(this, list4,this);
                binding.rcvThursday.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                binding.rcvThursday.setAdapter(adapter3);

                binding.rcvFriday.setVisibility(View.VISIBLE);
                FridayAdapter adapter4 = new FridayAdapter(this, list5,this);
                binding.rcvFriday.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                binding.rcvFriday.setAdapter(adapter4);

                binding.rcvSaturday.setVisibility(View.VISIBLE);
                SaturdayAdapter adapter5 = new SaturdayAdapter(this, list6,this);
                binding.rcvSaturday.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                binding.rcvSaturday.setAdapter(adapter5);
            }
            else
            {
                binding.rcvMonday.setVisibility(View.GONE);
                binding.llDays.setVisibility(View.GONE);
                binding.llTimes.setVisibility(View.GONE);
                binding.tvNoStudents.setVisibility(View.VISIBLE);

            }

        }


    }

    private void getTimeTable(String classI) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("class_id", classI);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getTimeTable((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAttendanceModule");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(int position, String chk) {

       if(chk.equalsIgnoreCase("MONDAY"))
       {
           Toast.makeText(AllTimeTable.this, "MONDAY", Toast.LENGTH_SHORT).show();
       }
       else if(chk.equalsIgnoreCase("TUESDAY"))
       {
           Toast.makeText(AllTimeTable.this, "TUESDAY", Toast.LENGTH_SHORT).show();
       }
       else if(chk.equalsIgnoreCase("WEDNESDAY"))
       {
           Toast.makeText(AllTimeTable.this, "WEDNESDAY", Toast.LENGTH_SHORT).show();
       }
       else if(chk.equalsIgnoreCase("THURSDAY"))
       {
           Toast.makeText(AllTimeTable.this, "THURSDAY", Toast.LENGTH_SHORT).show();
       }
       else if(chk.equalsIgnoreCase("FRIDAY"))
       {
           Toast.makeText(AllTimeTable.this, "FRIDAY", Toast.LENGTH_SHORT).show();
       }
       else if(chk.equalsIgnoreCase("SATURDAY"))
       {
           Toast.makeText(AllTimeTable.this, "SATURDAY", Toast.LENGTH_SHORT).show();
       }else
       {

       }

    }
}