package com.lucic.cubes.events24.ui.activity.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.databinding.ActivityForgotPasswordBinding;
import com.lucic.cubes.events24.ui.activity.BasicActivity;
import com.lucic.cubes.events24.ui.view.EventsMessage;

public class ForgotPasswordActivity extends BasicActivity {

    private ActivityForgotPasswordBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initFirebase();
        binding.buttonSend.setOnClickListener(view -> sendToEmail());
    }

    private void initFirebase() {
        auth = FirebaseAuth.getInstance();
    }

    private void sendToEmail() {

        String email = binding.editTextEmail.getText().toString();

        if (email.length() > 0) {
            auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(unused -> {
                        finish();
                        EventsMessage.showMessage(getApplicationContext(), getString(R.string.text_check_email));
                    });
        }
    }
}