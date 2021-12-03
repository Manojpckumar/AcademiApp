package com.sysirohub.academicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.sysirohub.academicapp.databinding.ActivityAddClassBinding;
import com.sysirohub.academicapp.databinding.ActivityPaymentPageBinding;

public class PaymentPage extends AppCompatActivity {

    ActivityPaymentPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPaymentPageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.llCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.llCardDetails.setVisibility(View.VISIBLE);
                binding.llUpiDetails.setVisibility(View.GONE);


            }
        });

        binding.llUpi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.llCardDetails.setVisibility(View.GONE);
                binding.llUpiDetails.setVisibility(View.VISIBLE);


            }
        });


    }
}