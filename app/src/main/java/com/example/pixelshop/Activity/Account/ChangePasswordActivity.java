package com.example.pixelshop.Activity.Account;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pixelshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity  {

    private EditText password, repassword;
    private Button changePasswordButton;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        password = findViewById(R.id.editmatkhau);
        repassword = findViewById(R.id.editmatkhau_repeat);

        changePasswordButton = (Button)findViewById(R.id.btndoimatkhau);

        auth = FirebaseAuth.getInstance();
        Init();

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validatePassword();
            }
        });

    }
    private void Init() {
        findViewById(R.id.btn_back_to_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private boolean validatePassword() {
        String passwordInput = password.getText().toString().trim();
        String ConfitmpasswordInput = repassword.getText().toString().trim();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (passwordInput.isEmpty()) {
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!passwordInput.equals(ConfitmpasswordInput)) {
            Toast.makeText(this, "Mật khẩu buộc phải trùng khớp", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            user.updatePassword(passwordInput)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangePasswordActivity.this,"Mật khẩu của bạn đã đổi thành công",Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(ChangePasswordActivity.this,"Bị trục trặc j r tr",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
            return true;
        }
    }
}