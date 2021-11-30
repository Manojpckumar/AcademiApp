package com.sysirohub.academicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Model.ActiveWeekday;
import com.sysirohub.academicapp.Model.AllClass;
import com.sysirohub.academicapp.Model.AllSubject;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Model.ResponseCommon;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityAddSubjectBinding;
import com.sysirohub.academicapp.databinding.ActivityAddTimeTableBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;

public class AddTimeTable extends AppCompatActivity implements GetResult.MyListener{

    ActivityAddTimeTableBinding binding;
    CustPrograssbar custPrograssbar;

    List<ActiveWeekday> weekList;
    List<String> weekNameList;
    List<String> weekIDList;

    List<AllClass> classList;
    List<String> classNameList;
    List<String> classIdList;

    List<AllSubject> subjectList;
    List<String> subjectNameList;
    List<String> subjectIDList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        custPrograssbar = new CustPrograssbar();
        binding = ActivityAddTimeTableBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String weekDay = "0";
        custPrograssbar.progressCreate(this);

        getWeekDaysFromApi(weekDay);

        binding.spnClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String class_ID = classIdList.get(position);
            getSubjects(class_ID);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.edStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                int second = mcurrentTime.get(Calendar.SECOND);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddTimeTable.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String minitue = String.valueOf(selectedMinute);

                        if( minitue.length() == 2)
                        {
                            binding.edStartTime.setText( selectedHour + ":" + selectedMinute + ":" + second);
                        }else
                        {
                            binding.edStartTime.setText( selectedHour + ":" + selectedMinute + "0:" + second);
                        }


                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        binding.edEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();

                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                int second = mcurrentTime.get(Calendar.SECOND);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(AddTimeTable.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String minitue = String.valueOf(selectedMinute);

                        if( minitue.length() == 2)
                        {
                            binding.edEndTime.setText( selectedHour + ":" + selectedMinute + ":" + second);
                        }else
                        {
                            binding.edEndTime.setText( selectedHour + ":" + selectedMinute + "0:" + second);
                        }

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        binding.btnAddTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String day,classID,subID,startTime,endTime;
                int classPos,subjectPos,dayPos;

                classPos = binding.spnClasses.getSelectedItemPosition();
                subjectPos = binding.spnSubjects.getSelectedItemPosition();
                dayPos = binding.spnDays.getSelectedItemPosition();

                classID = classIdList.get(classPos);
                subID = subjectIDList.get(subjectPos);
                day = weekIDList.get(dayPos);
                startTime = binding.edStartTime.getText().toString();
                endTime = binding.edEndTime.getText().toString();

                if( !startTime.equalsIgnoreCase("") && !endTime.equalsIgnoreCase(""))
                {
                    addTimeTableToAPI(classID,subID,day,startTime,endTime);
                }
                else
                {
                    Toast.makeText(AddTimeTable.this, "select start and end time", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }

    private void addTimeTableToAPI(String classID, String subID, String day, String startTime, String endTime) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("class_id", classID);
            jsonObject.put("week_id", day);
            jsonObject.put("subject_id", subID);
            jsonObject.put("start", startTime);
            jsonObject.put("end", endTime);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().AddTimetable((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "AddTimetable");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getWeekDaysFromApi(String weekDay) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("week", weekDay);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getWeekDays((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getActiveWeekdays");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("getActiveWeekdays")) {

            Gson gson = new Gson();
            Example example = gson.fromJson(result.toString(), Example.class);

            weekList = new ArrayList();
            weekNameList = new ArrayList();
            weekIDList = new ArrayList();

            weekList = example.getResultData().getActiveWeekdays();

            for(ActiveWeekday allClass : weekList)
            {
                weekNameList.add(allClass.getDays().toString());
                weekIDList.add(allClass.getId().toString());
            }

            if (!weekNameList.isEmpty()) {

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weekNameList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spnDays.setAdapter(dataAdapter);

                getAllClassesFromApi();
            }


        }
        else  if (callNo.equalsIgnoreCase("getAllClasses")) {

            Gson gson = new Gson();
            custPrograssbar.close();
            Example example = gson.fromJson(result.toString(), Example.class);

            classList = new ArrayList<>();
            classIdList = new ArrayList<>();
            classNameList = new ArrayList<>();

            classList = example.getResultData().getAllClasses();

            for(AllClass allClass : classList)
            {
                classIdList.add(allClass.getId().toString());
                classNameList.add(allClass.getClassName().toString());
            }

            if (!classNameList.isEmpty()) {

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classNameList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spnClasses.setAdapter(dataAdapter);

                int studentPos = binding.spnClasses.getSelectedItemPosition();
                String classI = classIdList.get(studentPos);
                getSubjects(classI);

            }



        }
        else if (callNo.equalsIgnoreCase("getSubjectsbyClass")) {

            Gson gson = new Gson();

            custPrograssbar.close();

            Example example = gson.fromJson(result.toString(),Example.class);

            if(example.getResultData().getAllSubjects() != null)
            {
                subjectList = new ArrayList<>();
                subjectNameList = new ArrayList<>();
                subjectIDList = new ArrayList<>();

                subjectList = example.getResultData().getAllSubjects();

                for(AllSubject subject : subjectList)
                {
                    subjectNameList.add(subject.getSubjectName());
                    subjectIDList.add(subject.getId());
                }

                custPrograssbar.close();
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subjectNameList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spnSubjects.setAdapter(dataAdapter);

            }
            else
            {
                List<String> noClass = new ArrayList<>();

                custPrograssbar.close();
                noClass.add("No subjects found");

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, noClass);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spnSubjects.setAdapter(dataAdapter);

            }

        }
        else if (callNo.equalsIgnoreCase("AddTimetable")) {

            Gson gson = new Gson();

            custPrograssbar.close();

            //Example example = gson.fromJson(result.toString(),Example.class);
            ResponseCommon common = gson.fromJson(result.toString(),ResponseCommon.class);

            if(common.getResult().equalsIgnoreCase("true"))
            {
                Toast.makeText(AddTimeTable.this, common.getResponseMsg(), Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(AddTimeTable.this, common.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }


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
}