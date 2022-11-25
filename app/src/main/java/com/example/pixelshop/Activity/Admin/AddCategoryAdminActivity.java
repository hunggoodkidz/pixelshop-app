package com.example.pixelshop.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.pixelshop.Model.SanPhamModels;
import com.example.pixelshop.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AddCategoryAdminActivity extends AppCompatActivity {
    private static final int LIBRARY_PICKER = 12312;
    EditText edtNsx, edtTenSp, edtTien, edtBh, edtSl, edtType, edtMt;
    ImageView imageView , btnBack;
    Button btnDm, btnDel, btnEdit ;
    private Spinner spinerthongke;
    private List<String> list;
    FirebaseFirestore db;
    private String image = "";
    ProgressDialog dialog;
    private SanPhamModels sanPhamModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_admin);


        findViewById(R.id.image_add).setOnClickListener(view -> {
            startActivityForResult(new Intent(AddCategoryAdminActivity.this, AddCategoryActivity.class), 100);
        });
    }
}