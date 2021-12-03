package com.sysirohub.academicapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Adapter.AllClassTeacherAdapter;
import com.sysirohub.academicapp.Adapter.AttendanceViewAdapter;
import com.sysirohub.academicapp.AllClasses;
import com.sysirohub.academicapp.AllTasks;
import com.sysirohub.academicapp.Model.AttendanceView;
import com.sysirohub.academicapp.Model.Common;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.PaymentPage;
import com.sysirohub.academicapp.R;
import com.sysirohub.academicapp.RecyclerViewClickInterface;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.databinding.FragmentParentStudentBinding;
import com.sysirohub.academicapp.databinding.FragmentTeacherBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherFragment extends Fragment implements GetResult.MyListener, RecyclerViewClickInterface {

    FragmentTeacherBinding binding;
    String teacherName;
    List<AttendanceView> attendanceViewList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeacherFragment() {
        // Required empty public constructor
    }

    public TeacherFragment(String name) {
        this.teacherName = name;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherFragment newInstance(String param1, String param2) {
        TeacherFragment fragment = new TeacherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTeacherBinding.inflate(inflater,container,false);

        //Toast.makeText(getContext(), "Today is : "+Common.getSystemTimeandDate() , Toast.LENGTH_SHORT).show();

        binding.tvTeacherName.setText(teacherName);

        String teachId,dayNumber;

        teachId = Common.teacherID;
        dayNumber = Common.getDayNumber();

        getAttendanceViewToTeacher(teachId,dayNumber);

        binding.llAllClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), AllClasses.class);
                startActivity(intent);

            }
        });

        binding.llAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), AllTasks.class);
                startActivity(intent);

            }
        });


        return binding.getRoot();
    }

    private void getAttendanceViewToTeacher(String teachId, String dayNumber) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("teacher_id", teachId);
            jsonObject.put("week_id", dayNumber);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAttendanceView((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getAttendanceView");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("getAttendanceView"))
        {
            Gson gson = new Gson();

            Example example = gson.fromJson(result.toString(),Example.class);

            attendanceViewList = new ArrayList<>();

            attendanceViewList =  example.getResultData().getAttendanceView();

            AttendanceViewAdapter adapter = new AttendanceViewAdapter(getContext(), attendanceViewList,this);
            binding.rcvAttendance.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            binding.rcvAttendance.setAdapter(adapter);
        }

    }

    @Override
    public void onItemClick(int position, String chk) {

    }
}