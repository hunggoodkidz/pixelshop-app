package com.example.pixelshop.Activity.Account;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pixelshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView txtsdt,txthoten,txtdiachi,txtmail;
    private  String key = "";
    private StorageReference storageReference;
    private CircleImageView avatar;
    private ImageView btnCamera;
    private Uri image_uri;
    
    //perm constants
    private static final int LOCATION_REQUEST_CODE = 100;
    private static final int CAMERA_QUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    //image pick constants
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    //perm arrays
    private String[] locationPerms;
    private String[] cameraPerms;
    private String[] storagePerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);
        txtdiachi=(TextView) findViewById(R.id.txtdiachi);
        txthoten=(TextView)findViewById(R.id.txthoten);
        txtsdt=(TextView)findViewById(R.id.txtsdt);
        avatar = findViewById(R.id.avatar);
        txtmail=(TextView)findViewById(R.id.txtemail);
        btnCamera = findViewById(R.id.btnCamera);

        cameraPerms = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};


        Init();

        txtdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaLog(3);
            }
        });
        txthoten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaLog(1);
            }
        });
        txtsdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiaLog(2);
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                            || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},123);
                    }else{
                        PickGallary();
                    }
                }else{
                    PickGallary();
                }
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(checkCameraPermission()){
//                    PickCamera();
//                }
//                else{
//                    requestCameraPermission();
//                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
                            || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},CAMERA_QUEST_CODE);
                    }else{
                        PickCamera();
                    }
                }else{
                    PickCamera();
                }
            }
        });
    }


    private void requestCameraPermission()
    {
        ActivityCompat.requestPermissions(this,cameraPerms,CAMERA_QUEST_CODE);
    }

    private void Init() {

        db= FirebaseFirestore.getInstance();
        txtmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        db.collection("thongtinUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Profile")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {

                        if(queryDocumentSnapshots.size()>0){
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            if(documentSnapshot!=null){
                                key = documentSnapshot.getId();
                                try{
                                    txtdiachi.setText(documentSnapshot.getString("diachi").length()>0 ?
                                            documentSnapshot.getString("diachi") : "");
                                    txthoten.setText(documentSnapshot.getString("hoten").length()>0 ?
                                            documentSnapshot.getString("hoten") : "");
                                    txtsdt.setText(documentSnapshot.getString("sdt").length()>0 ?
                                            documentSnapshot.getString("sdt") : "");
                                    if(documentSnapshot.getString("avatar").length()>0){
                                        Picasso.get().load(documentSnapshot.getString("avatar").trim()).into(avatar);
                                    }
                                }catch (Exception e){

                                }

                            }else{
                                HashMap<String,String> hashMap=  new HashMap<>();
                                hashMap.put("diachi","");
                                hashMap.put("hoten","");
                                hashMap.put("sdt","");
                                hashMap.put("avatar","");
                                db.collection("thongtinUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .collection("Profile").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(@NonNull DocumentReference documentReference) {
                                                key = documentReference.getId();
                                            }
                                        });
                            }
                        }else{
                            HashMap<String,String> hashMap=  new HashMap<>();
                            hashMap.put("diachi","");
                            hashMap.put("hoten","");
                            hashMap.put("sdt","");
                            hashMap.put("avatar","");
                            db.collection("thongtinUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .collection("Profile").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(@NonNull DocumentReference documentReference) {
                                            key = documentReference.getId();
                                        }
                                    });
                        }
                    }
                });


    }

    private void PickGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,123);
    }
    private void PickCamera(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE,"Image Tittle");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION,"Image Description");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode== RESULT_OK){
            if(requestCode == 123){
            image_uri = data.getData();
            Log.d("CHECKED",image_uri+" ");
            try {
                InputStream inputStream = getContentResolver().openInputStream(image_uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datas = baos.toByteArray();
                String filename = FirebaseAuth.getInstance().getCurrentUser().getUid();
                storageReference= FirebaseStorage.getInstance("gs://pixelshop-app.appspot.com/").getReference();
                storageReference.child("Profile").child(filename+".jpg").putBytes(datas).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        if(taskSnapshot.getTask().isSuccessful()){
                            storageReference.child("Profile").child(filename+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(@NonNull Uri uri) {
                                    db.collection("thongtinUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .collection("Profile").document(key)
                                            .update("avatar",uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        avatar.setImageBitmap(bitmap);
                                                    }
                                                }
                                            });
                                }
                            });
                        }
                    }
                });
            } catch (FileNotFoundException e) {
                Log.d("CHECKED",e.getMessage());
            }
        }else if(requestCode ==  IMAGE_PICK_CAMERA_CODE){

                Log.d("CHECKED",image_uri+" ");
                try {
                    InputStream inputStream = getContentResolver().openInputStream(image_uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] datas = baos.toByteArray();
                    String filename = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    storageReference= FirebaseStorage.getInstance("gs://pixelshop-app.appspot.com/").getReference();
                    storageReference.child("Profile").child(filename+".jpg").putBytes(datas).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            if(taskSnapshot.getTask().isSuccessful()){
                                storageReference.child("Profile").child(filename+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(@NonNull Uri uri) {
                                        db.collection("thongtinUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .collection("Profile").document(key)
                                                .update("avatar",uri.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            avatar.setImageBitmap(bitmap);
                                                        }
                                                    }
                                                });
                                    }
                                });
                            }
                        }
                    });
                } catch (FileNotFoundException e) {
                    Log.d("CHECKED",e.getMessage());
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case CAMERA_QUEST_CODE:{
                if (grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted){
                        PickCamera();
                    }
                    else{
                        Toast.makeText(this,"Camera permissions are necessary", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void requestLocationPermission(){
        ActivityCompat.requestPermissions(this,locationPerms,LOCATION_REQUEST_CODE);
    }
    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }


    private void DiaLog(int i) {
        Dialog dialog = new Dialog(EditProfileActivity.this);
        dialog.setContentView(R.layout.dialog_profile);
        dialog.show();
        EditText editvalue = dialog.findViewById(R.id.editvalue);
        Button btnxacnhan = dialog.findViewById(R.id.btnxacnhan);

        switch (i){
            case  1: editvalue.setHint("Nhập họ tên");break;
            case  2:
                editvalue.setInputType(InputType.TYPE_CLASS_NUMBER);
                editvalue.setHint("Nhập số điện thoại");break;
            case  3: editvalue.setHint("Nhập dịa chỉ");break;
        }
        dialog.findViewById(R.id.cancel).setOnClickListener(view -> {
            dialog.cancel();
        });
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editvalue.getText().toString().trim();
                String keys = "";
                if(value.length()>0){
                    switch (i){
                        case 1: keys ="hoten";break;
                        case 2: keys ="sdt";break;
                        case 3: keys ="diachi";break;



                    }
                    db.collection("thongtinUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .collection("Profile").document(key)
                            .update(keys,value).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        switch (i){
                                            case  1: txthoten.setText(value);break;
                                            case  2: txtsdt.setText(value);break;
                                            case  3: txtdiachi.setText(value);break;
                                        }

                                    }
                                    dialog.cancel();
                                }
                            });
                }else{
                    Toast.makeText(EditProfileActivity.this, "Không để trống !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }












}