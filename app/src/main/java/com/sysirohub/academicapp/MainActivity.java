package com.sysirohub.academicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.Fragments.AdminFragment;
import com.sysirohub.academicapp.Fragments.TeacherFragment;
import com.sysirohub.academicapp.Model.Example;
import com.sysirohub.academicapp.R;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements GetResult.MyListener {

    ActivityMainBinding binding;
    FirebaseAuth auth;

    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth=FirebaseAuth.getInstance();
        custPrograssbar = new CustPrograssbar();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }

        binding.linearLg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.edtusername.getText().toString();
                final String password = binding.edtpassword.getText().toString();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                custPrograssbar.progressCreate(MainActivity.this);

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                binding.progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        binding.edtpassword.setError("Password Requires Min 6 characters");
                                    } else {
                                        Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                                    }
                                } else {

                                    checkLoginInApi(email,password);

                                }
                            }
                        });


            }
        });

    }

    private void checkLoginInApi(String email, String password) {

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("username", email);
            jsonObject.put("password", password);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().userLogin((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "login");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void gotoRegister(View view)
    {
        Intent intent = new Intent(MainActivity.this,RegistrationActivity.class);
        startActivity(intent);
    }


    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("login"))
        {
            Gson gson = new Gson();
            custPrograssbar.close();

            Example example = gson.fromJson(result.toString(), Example.class);

            if(example.getResult().equalsIgnoreCase("true"))
            {
                String status = example.getUsers().getStatus();

                if(status.equalsIgnoreCase("1"))
                {
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else
                {
                    Toast.makeText(MainActivity.this, "Account inactive please wait for approval", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(MainActivity.this, example.getResponseMsg(), Toast.LENGTH_SHORT).show();
            }


        }

    }
}