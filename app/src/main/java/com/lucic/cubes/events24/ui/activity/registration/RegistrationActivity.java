package com.lucic.cubes.events24.ui.activity.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.data.model.User;
import com.lucic.cubes.events24.databinding.ActivityRegistrationBinding;
import com.lucic.cubes.events24.ui.activity.BasicActivity;
import com.lucic.cubes.events24.ui.view.EventsMessage;

public class RegistrationActivity extends BasicActivity {

    private ActivityRegistrationBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initFirebase();
        setListeners();
        updateLoadingUI(false);
    }

    private void initFirebase() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }


    private void setListeners() {
        binding.buttonLogin.setOnClickListener(v -> login());
        binding.buttonRegistration.setOnClickListener(v -> registration());
        binding.textViewForgotPassword.setOnClickListener(v -> forgotPassword());
    }

    private void forgotPassword() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private void registration() {

        String name = binding.editTextName.getText().toString();
        String surname = binding.editTextSurname.getText().toString();
        String email = binding.editTextEmail.getText().toString();
        String password = binding.editTextPassword.getText().toString();
        String passwordRepeat = binding.editTextRepeatPassword.getText().toString();

        if (email.length() > 0 && password.length() > 0 && password.equalsIgnoreCase(passwordRepeat)) {

            updateLoadingUI(true);

            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            User user = new User(
                                    task.getResult().getUser().getUid(),
                                    name,
                                    surname,
                                    email);

                            db.collection("users")
                                    .add(user)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            EventsMessage.showMessage(getApplicationContext(), getString(R.string.text_registration_success));
                                            goToHomeActivity();
                                            updateLoadingUI(false);
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            updateLoadingUI(false);

                                        }
                                    });




                        } else {
                            updateLoadingUI(false);
                        }

                    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    EventsMessage.showMessage(getApplicationContext(), e.getMessage());

                }
            });

        }


    }

    private void goToHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    private void login() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void updateLoadingUI(boolean isLoading) {

            binding.editTextEmail.setEnabled(!isLoading);
            binding.editTextPassword.setEnabled(!isLoading);
            binding.editTextRepeatPassword.setEnabled(!isLoading);
            binding.textViewForgotPassword.setEnabled(!isLoading);
            binding.buttonLogin.setEnabled(!isLoading);
            binding.buttonRegistration.setEnabled(!isLoading);

        if (isLoading) {
            binding.buttonRegistration.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.buttonRegistration.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }

    }
}