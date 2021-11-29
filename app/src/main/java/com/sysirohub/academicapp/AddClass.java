package com.sysirohub.academicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Fragments.AdminFragment;
import com.sysirohub.academicapp.Fragments.TeacherFragment;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Model.ResponseCommon;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityAddClassBinding;
import com.sysirohub.academicapp.databinding.ActivityAllClassesBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class AddClass extends AppCompatActivity implements GetResult.MyListener {

    ActivityAddClassBinding binding;
    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddClassBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        custPrograssbar = new CustPrograssbar();

        binding.btnAddClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String className = binding.edClassName.getText().toString();

                if(className.equalsIgnoreCase(""))
                {
                    Toast.makeText(AddClass.this, "enter class name", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    custPrograssbar.progressCreate(AddClass.this);
                    addClassToApi(className);
                }


            }
        });

    }

    private void addClassToApi(String className) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("class_name", className);
            jsonObject.put("division", "0");

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().addClasses((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "addClass");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("addClass")){

            Gson gson = new Gson();
            custPrograssbar.close();

            ResponseCommon common = gson.fromJson(result.toString(),ResponseCommon.class);

            if(common.getResult().equalsIgnoreCase("true"))
            {
                Toast.makeText(AddClass.this, common.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }else
            {
                Toast.makeText(AddClass.this, common.getResponseMsg(), Toast.LENGTH_SHORT).show();

            }

        }

    }
}