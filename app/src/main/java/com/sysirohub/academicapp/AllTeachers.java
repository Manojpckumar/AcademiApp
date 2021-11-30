package com.sysirohub.academicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Adapter.AllTeachersAdapter;
import com.sysirohub.academicapp.BottomDialogue.ActionBottomSheetDialog;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Model.ResponseCommon;
import com.sysirohub.academicapp.Model.Teacher;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityAllTeachersBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AllTeachers extends AppCompatActivity implements GetResult.MyListener, RecyclerViewClickInterface,ActionBottomSheetDialog.ItemClickListener {

    ActivityAllTeachersBinding binding;
    CustPrograssbar custPrograssbar;

    List<Teacher> teacherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAllTeachersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        custPrograssbar = new CustPrograssbar();

        String classID = "0";
        getAllTeachersFromApi(classID);

    }

    private void getAllTeachersFromApi(String classID) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("class_id", "0");

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getAllTeachers((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "AllTeachers");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if (callNo.equalsIgnoreCase("AllTeachers")) {

            Gson gson = new Gson();
            custPrograssbar.close();

            Example example = gson.fromJson(result.toString(), Example.class);
            //Log.d("result102030","hi "+result.toString());

            if (example.getResultData().getTeachers() == null) {
                custPrograssbar.close();
                binding.tvNoTeachers.setVisibility(View.VISIBLE);
                binding.rcvAllTeachers.setVisibility(View.GONE);
            } else {
                teacherList = new ArrayList<>();

                teacherList = example.getResultData().getTeachers();

                custPrograssbar.close();
                binding.rcvAllTeachers.setVisibility(View.VISIBLE);

                AllTeachersAdapter adapter = new AllTeachersAdapter(teacherList, AllTeachers.this, this);
                binding.rcvAllTeachers.setLayoutManager(new LinearLayoutManager(this));
                binding.rcvAllTeachers.setAdapter(adapter);
            }


        } else if(callNo.equalsIgnoreCase("TeacherApproval"))
        {
            Gson gson = new Gson();
            custPrograssbar.close();

            ResponseCommon common = gson.fromJson(result.toString(),ResponseCommon.class);

            if(common.getResult().equalsIgnoreCase("true"))
            {
                String CiD = "0";
                getAllTeachersFromApi(CiD);
                Toast.makeText(AllTeachers.this, common.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }else
            {
                Toast.makeText(AllTeachers.this, common.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }
        }


    }


    @Override
    public void onItemClick(int position, String chk) {

        if (chk.equalsIgnoreCase("APPROVE")) {

            ApproveTeacherStatus(teacherList.get(position).getId(),"1");


        } else if(chk.equalsIgnoreCase("BLOCK"))
            {

           ApproveTeacherStatus(teacherList.get(position).getId(), "0");

        }
        else if(chk.equalsIgnoreCase("ASSIGN"))
        {

           ActionBottomSheetDialog openBottomSheet = ActionBottomSheetDialog.newInstance();
           openBottomSheet.setData(teacherList.get(position).getId());
           openBottomSheet.show(getSupportFragmentManager(),ActionBottomSheetDialog.TAG);

        }
        else{

        }

    }

    private void ApproveTeacherStatus(String id, String s) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("t_id", id);
            jsonObject.put("status", s);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().updateTeacherStatus((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "TeacherApproval");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(String item) {

    }
}