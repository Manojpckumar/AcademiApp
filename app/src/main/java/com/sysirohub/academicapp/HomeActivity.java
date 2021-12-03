package com.sysirohub.academicapp;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Fragments.AdminFragment;
import com.sysirohub.academicapp.Fragments.TeacherFragment;
import com.sysirohub.academicapp.Interface.OnFragmentInteractionListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;
import com.sysirohub.academicapp.Model.Common;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.Model.ResponseCommon;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityHomeBinding;
import com.sysirohub.academicapp.databinding.ActivityRegistrationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class HomeActivity extends AppCompatActivity implements OnFragmentInteractionListener,GetResult.MyListener {

    ActivityHomeBinding binding;
    private FirebaseAuth mAuth;
    CustPrograssbar custPrograssbar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        custPrograssbar = new CustPrograssbar();
        custPrograssbar.progressCreate(this);


        String checkTime = Common.getSystemTimeandDate();
        String systemDate = Common.getSystemDate();
        Log.d("checkPostTime00",checkTime);
        Log.d("checkPostTime00",systemDate);
        checkSystemTime(checkTime,systemDate);

//        auth = FirebaseAuth.getInstance();
//        custPrograssbar = new CustPrograssbar();
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        custPrograssbar.progressCreate(this);
//        Log.d("currentEmail",currentUser.getEmail());
//
//        getUserAccountType(currentUser.getEmail());


        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if (tabId == R.id.tabSignOut) {
                    auth.signOut();
                    Common.setDefault();
                    startActivity(new Intent(HomeActivity.this, MainActivity.class));
                    finish();
                }
            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_favorites) {
                    // The tab with id R.id.tab_favorites was reselected,
                    // change your content accordingly.
                }
            }
        });

    }

    private void checkSystemTime(String time, String date) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("tymzone", time);
            jsonObject.put("date", date);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().checkTime((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "ServerConfig");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getUserAccountType(String email) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("email", email);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getStatusByEmail((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "getStatusbyemail");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void loadFragment(Fragment adminFragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.contentContainer,adminFragment);
        ft.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("getStatusbyemail")){

            Gson gson = new Gson();

            Example example = gson.fromJson(result.toString(), Example.class);

            String accountType = example.getResultData().getUserDetails().getRole();

            if(accountType.equalsIgnoreCase("Admin"))
            {
                Common.userRole = example.getResultData().getUserDetails().getRole();
                Common.adminID = example.getResultData().getUserDetails().getId();

                String name = example.getResultData().getUserDetails().getName();
                custPrograssbar.close();
                loadFragment(new AdminFragment(name));

            }else if(accountType.equalsIgnoreCase("Teacher"))
            {
                Common.userRole = example.getResultData().getUserDetails().getRole();
                Common.teacherID = example.getResultData().getUserDetails().getId();

                String name = example.getResultData().getUserDetails().getName();
                custPrograssbar.close();
                loadFragment(new TeacherFragment(name));
            }
            else
            {
                //Toast.makeText(HomeActivity.this, "Student", Toast.LENGTH_SHORT).show();
            }


        }
        else if(callNo.equalsIgnoreCase("ServerConfig"))
        {

            Gson gson = new Gson();

            Example example = gson.fromJson(result.toString(), Example.class);

            String accountType = example.getResult();

            if (accountType.equalsIgnoreCase("true"))
            {
                auth = FirebaseAuth.getInstance();
                custPrograssbar = new CustPrograssbar();
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                getUserAccountType(currentUser.getEmail());
            }
            else
            {
                Toast.makeText(HomeActivity.this, "Your clock is backward", Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
            }


        }

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        String checkTime = Common.getSystemTimeandDate();
        String systemDate = Common.getSystemDate();
        checkSystemTime(checkTime,systemDate);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String checkTime = Common.getSystemTimeandDate();
        String systemDate = Common.getSystemDate();
        checkSystemTime(checkTime,systemDate);
    }
}