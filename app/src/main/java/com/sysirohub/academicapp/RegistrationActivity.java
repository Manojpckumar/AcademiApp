package com.sysirohub.academicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sysirohub.academicapp.CustomViews.CustomEditText;
import com.sysirohub.academicapp.Model.ResponseCommon;
import com.sysirohub.academicapp.R;
import com.sysirohub.academicapp.Retrofit.APIClient;
import com.sysirohub.academicapp.Retrofit.GetResult;
import com.sysirohub.academicapp.Utils.CustPrograssbar;
import com.sysirohub.academicapp.databinding.ActivityMainBinding;
import com.sysirohub.academicapp.databinding.ActivityRegistrationBinding;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class RegistrationActivity extends AppCompatActivity implements GetResult.MyListener {

//    CustomEditText edtEmail,edtPassword,edtMobile;
//    Spinner spnAccountType;

    ActivityRegistrationBinding binding;
    private FirebaseAuth auth;
    CustPrograssbar custPrograssbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        custPrograssbar = new CustPrograssbar();
        auth = FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = binding.edName.getText().toString().trim();
                String email = binding.edtMail.getText().toString().trim();
                String password = binding.edtPassword.getText().toString().trim();
                String mobile = binding.edtMobile.getText().toString().trim();
                String accountType = binding.spnAccountType.getSelectedItem().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(getApplicationContext(), "Enter Mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(accountType)) {
                    Toast.makeText(getApplicationContext(), "Select account Type!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //binding.progressBar.setVisibility(View.VISIBLE);
                custPrograssbar.progressCreate(RegistrationActivity.this);

                registerUserToApi(email,name,password,mobile,accountType);
            }
        });


    }

    private void registerUserToApi(String email, String name, String password, String mobile, String accountType) {


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("email", email);
            jsonObject.put("mobile", mobile);
            jsonObject.put("role", accountType);

            if(accountType.equalsIgnoreCase("Teacher"))
            {
                jsonObject.put("status", "0");

            }
            else if(accountType.equalsIgnoreCase("Parent"))
            {
                jsonObject.put("status", "1");

            }
            jsonObject.put("class_id", "0");
            jsonObject.put("dob", "0000-00-00");
            jsonObject.put("doj", "0000-00-00");
            jsonObject.put("password", password);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().registerUser((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.onNCHandle(call, "register");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void GoToLogin(View view)
    {
        Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void callback(JsonObject result, String callNo) {

        if(callNo.equalsIgnoreCase("register")){
            Gson gson = new Gson();

            ResponseCommon response = gson.fromJson(result.toString(), ResponseCommon.class);

            if(response.getResult().equalsIgnoreCase("true"))
            {
                String name = binding.edName.getText().toString().trim();
                String email = binding.edtMail.getText().toString().trim();
                String password = binding.edtPassword.getText().toString().trim();
                String mobile = binding.edtMobile.getText().toString().trim();
                String accountType = binding.spnAccountType.getSelectedItem().toString();

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(RegistrationActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                binding.progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    custPrograssbar.close();
                                    Toast.makeText(RegistrationActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    custPrograssbar.close();
                                    Toast.makeText(RegistrationActivity.this, response.getResponseMsg(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistrationActivity.this,HomeActivity.class);
                                    startActivity(intent);

                                }
                            }
                        });
            }
            else
            {
                custPrograssbar.close();
                Toast.makeText(RegistrationActivity.this, response.getResponseMsg(), Toast.LENGTH_SHORT).show();
            }

        }

    }
}