package com.lucic.cubes.events24.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lucic.cubes.events24.R;
import com.lucic.cubes.events24.data.DataContainer;
import com.lucic.cubes.events24.data.model.User;
import com.lucic.cubes.events24.databinding.ActivityEditProfileBinding;
import com.lucic.cubes.events24.ui.adapter.EventsDetailAdapter;
import com.lucic.cubes.events24.ui.view.EventsMessage;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends BasicActivity {

    private static final int PICK_IMAGE_GALLERY = 1111;
    private static final int PICK_IMAGE_CAMERA = 1112;
    private static final int STORAGE_PERMISSION_CODE = 1113;
    private static final int CAMERA_PERMISSION_CODE = 1114;

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    private String documentId;

    private Uri mProfileImageUri;
    private byte[] mProfileImageBytes;

    private ActivityEditProfileBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initFirebase();
        setCurrentUser();
        setListeners();

    }


    private void setListeners() {
        binding.imageViewProfileImage.setOnClickListener(view -> pickImage());
        binding.buttonEditUser.setOnClickListener(view -> saveProfileChanges());
    }

    private void saveProfileChanges() {

        if (mProfileImageUri != null) {
            uploadImageFromGallery();
        } else if (mProfileImageBytes != null) {
            uploadImageFromCamera();
        } else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK) {

            mProfileImageUri = data.getData();
            Picasso.get().load(mProfileImageUri).into(binding.imageViewProfileImage);

        } else if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {

            assert data != null;
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");

            binding.imageViewProfileImage.setImageBitmap(bitmap);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            mProfileImageBytes = baos.toByteArray();

        }
    }


    private void uploadImageFromCamera() {

        String profileFolder = auth.getUid().toString();
        String imageName = System.currentTimeMillis() + ".jpg";

        storage
                .getReference()
                .child("images/profile/" + profileFolder + "/" + imageName)
                .putBytes(mProfileImageBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DataContainer.user.imageUrl = uri.toString();

                        updateUser();
                    }
                });

                EventsMessage.showMessage(getApplicationContext(), "Success uploaded image.");
            }
        }).addOnFailureListener(e -> EventsMessage.showMessage(getApplicationContext(), "Error on upload image: " + e.getMessage()));
    }

    private void uploadImageFromGallery() {

        String profileFolder = auth.getUid().toString();
        String imageName = Long.toString(System.currentTimeMillis()) + "jpg";

        storage
                .getReference()
                .child("images/profile/" + profileFolder + "/" + imageName)
                .putFile(mProfileImageUri).addOnSuccessListener(taskSnapshot -> {

            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(uri -> {
                DataContainer.user.imageUrl = uri.toString();
                updateUser();
            });

        }).addOnFailureListener(e -> EventsMessage.showMessage(getApplicationContext(), "Error on upload image: " + e.getMessage()));
    }

    private void pickImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle(this.getString(R.string.dialog_pick_image_title));
        builder.setMessage(this.getString(R.string.dialog_pick_image_message));
        builder.setPositiveButton(this.getString(R.string.dialog_pick_image_gallery), (dialogInterface, i) -> {

            if (hasReadExternalStoragePermission()) {
                openGallery();
            } else {
                requestReadExternalStoragePermission();
            }

        });

        builder.setNegativeButton(this.getString(R.string.dialog_pick_image_camera), (dialogInterface, i) -> {

            if (hasCameraPermission()) {
                openCamera();
            } else {
                requestCameraPermission();
            }

        });
        builder.setCancelable(true);
        builder.show();
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
        )) {

            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_permission_title))
                    .setMessage(getString(R.string.dialog_permission_camera_message))
                    .setPositiveButton(getString(R.string.ok), (dialogInterface, i) ->
                            ActivityCompat.requestPermissions(
                                    this,
                                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE
                            ))
                    .setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss())
                    .create()
                    .show();

        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE
            );
        }

    }

    private boolean hasReadExternalStoragePermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadExternalStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )) {

            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_permission_title))
                    .setMessage(getString(R.string.dialog_permission_storage_message))
                    .setPositiveButton(getString(R.string.ok), (dialogInterface, i) ->
                            ActivityCompat.requestPermissions(
                                    this,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    STORAGE_PERMISSION_CODE
                            ))
                    .setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss())
                    .create()
                    .show();

        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE
            );
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        } else if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }

    }


    private void openCamera() {
        mProfileImageBytes = null;

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PICK_IMAGE_CAMERA);

    }

    private void openGallery() {
        mProfileImageBytes = null;

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE_GALLERY);

    }

    private void updateUser() {

        String name = binding.editTextName.getText().toString();
        String surname = binding.editTextSurname.getText().toString();
        String address = binding.editTextAddress.getText().toString();
        String phone = binding.editTextPhone.getText().toString();
        String city = binding.editTextPhone.getText().toString();

        User user = DataContainer.user;
        user.name = name;
        user.surname = surname;
        user.address = address;
        user.phone = phone;
        user.city = city;

        db.collection("users").document(documentId).set(user)
                .addOnSuccessListener(unused -> {
                    updateUserUi(user);
                    finish();
                });

    }

    private void setCurrentUser() {
        String userId = auth.getCurrentUser().getUid();

        db.collection("users")
                .whereEqualTo("id", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    if (queryDocumentSnapshots.getDocuments().size() == 1) {
                        User user = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);

                        documentId = queryDocumentSnapshots.getDocuments().get(0).getId();

                        updateUserUi(user);
                        DataContainer.user = user;
                    }

                }).addOnFailureListener(e -> EventsMessage.showMessage(getApplicationContext(), e.getMessage()));
    }

    private void updateUserUi(User user) {
        if (user.imageUrl != null && user.imageUrl.length() > 0) {
            Picasso.get().load(user.imageUrl).into(binding.imageViewProfileImage);
        }
        binding.editTextName.setText(user.name);
        binding.editTextSurname.setText(user.surname);
        binding.editTextAddress.setText(user.address);
        binding.editTextPhone.setText(user.phone);
        binding.editTextCity.setText(user.city);
    }

    private void initFirebase() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }
}