package com.sysirohub.academicapp.BottomDialogue;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Adapter.AllSubjectsAdapter;
import com.sysirohub.academicapp.AddClass;
import com.sysirohub.academicapp.AllSubjects;
import com.sysirohub.academicapp.Model.AllClass;
import com.sysirohub.academicapp.Model.AllSubject;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Model.ResponseCommon;
import com.sysirohub.academicapp.Model.Student;
import com.sysirohub.academicapp.R;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class ActionBottomSheetDialog extends BottomSheetDialogFragment implements View.OnClickListener,GetResult.MyListener {

    CustPrograssbar custPrograssbar;

    List<AllClass> classesList;
    List<String> classIdList;
    List<String> classNameList;

    List<AllSubject> subjectList;
    List<String> subjectNameList;
    List<String> subjectIDList;

    Spinner classDetails,subjectDetails;
    String teachID;
    Button btnAssign;

    public static final String TAG = "ActionBottomDialog";
    private ItemClickListener mListener;


    public static ActionBottomSheetDialog newInstance() {

        return new ActionBottomSheetDialog();

    }
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_assign_bottom_dialogue, container, false);

        classDetails = view.findViewById(R.id.spnAllClassDetails);
        subjectDetails = view.findViewById(R.id.spnAllSubjectDetails);
        btnAssign = view.findViewById(R.id.btnAssignTeacher);

        custPrograssbar = new CustPrograssbar();
        custPrograssbar.progressCreate(getContext());
        getAllClassesFromApi();

        classDetails.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String classI = classIdList.get(position);
                getSubjects(classI);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnAssign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String classId,SubId,teacherId;
                int classPos,subjectPos;

                classPos = classDetails.getSelectedItemPosition();
                subjectPos = subjectDetails.getSelectedItemPosition();

                classId = classIdList.get(classPos);
                SubId = subjectIDList.get(subjectPos);
                teacherId = getData();

                AssignSubjectToTeacher(classId,SubId,teacherId);

            }
        });

//        Log.d("inBottomDialogue","hi"+teachID);
//        Toast.makeText(getContext(), "hi"+teachID, Toast.LENGTH_SHORT).show();


        return view;
    }

    private void AssignSubjectToTeacher(String classId, String subId, String teacherId) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("class_id", classId);
            jsonObject.put("teacher_id", teacherId);
            jsonObject.put("subject_id", subId);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().assignSubToTeacher((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "AssignTeacher");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override public void onClick(View view) {
        TextView tvSelected = (TextView) view;
        mListener.onItemClick(tvSelected.getText().toString());
        dismiss();
    }



    public interface ItemClickListener {
        void onItemClick(String item);
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

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, classNameList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                classDetails.setAdapter(dataAdapter);

                int studentPos = classDetails.getSelectedItemPosition();
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

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, subjectNameList);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjectDetails.setAdapter(dataAdapter);

            }
            else
            {
                List<String> noClass = new ArrayList<>();

                noClass.add("No subjects found");

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, noClass);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjectDetails.setAdapter(dataAdapter);

            }


        }
        else if (callNo.equalsIgnoreCase("AssignTeacher")) {

            Gson gson = new Gson();
            custPrograssbar.close();

            ResponseCommon common = gson.fromJson(result.toString(),ResponseCommon.class);

            if(common.getResult().equalsIgnoreCase("true"))
            {
                dismiss();
                Toast.makeText(getContext(), common.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }else
            {
                dismiss();
                Toast.makeText(getContext(), common.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }

        }


    }

    public void setData(String id)
    {
        this.teachID = id;
    }

    public String getData()
    {
        return teachID;
    }
}