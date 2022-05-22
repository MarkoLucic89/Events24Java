package com.lucic.cubes.events24.ui.activity.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.lucic.cubes.events24.databinding.ActivityMainRegistrationBinding;
import com.lucic.cubes.events24.ui.activity.BasicActivity;

public class MainRegistrationActivity extends BasicActivity {

    private ActivityMainRegistrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setListeners() {
        binding.buttonLogin.setOnClickListener(view -> login());
        binding.buttonRegister.setOnClickListener(view -> registration());
    }

    private void registration() {
//        finish();
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    private void login() {
//        finish();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}