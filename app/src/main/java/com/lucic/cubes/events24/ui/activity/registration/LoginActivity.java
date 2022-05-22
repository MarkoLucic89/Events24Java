package com.lucic.cubes.events24.ui.activity.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.data.SharedPrefs;
import com.lucic.cubes.events24.databinding.ActivityLoginBinding;
import com.lucic.cubes.events24.ui.activity.BasicActivity;

public class LoginActivity extends BasicActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        binding.editTextEmail.setText(SharedPrefs.getInstance(this).getEmail());
        setListeners();
    }


    private void setListeners() {
        binding.buttonLogin.setOnClickListener(view -> login());
        binding.buttonRegistration.setOnClickListener(view -> register());
        binding.textViewForgotPassword.setOnClickListener(view -> forgotPassword());
    }

    private void forgotPassword() {
        Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void checkIsVisible() {

    }

    private void register() {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    private void login() {
        String email = binding.editTextEmail.getText().toString().trim();
        String password = binding.editTextPassword.getText().toString().trim();
        SharedPrefs.getInstance(this).saveEmail(email);

        if (email.isEmpty() || password.isEmpty()) {
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            SharedPrefs.getInstance(LoginActivity.this).setNotificationStatus(true);
                            FirebaseMessaging.getInstance().subscribeToTopic("general");
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}