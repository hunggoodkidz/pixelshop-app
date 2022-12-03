package com.example.pixelshop.Activity.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.example.pixelshop.Presenter.UserPreSenter;
import com.example.pixelshop.Presenter.UserView;
import com.example.pixelshop.R;
import com.example.pixelshop.Activity.HomeActivity;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity  implements UserView , View.OnClickListener {
    private Button btndangky;
    private EditText editemail,editpass,editpass_repeat;
    private UserPreSenter userPreSenter;
    private TextView hint;
    private TextInputLayout TXTL_signup_HoVaTen, TXTL_signup_TenDN, TXTL_signup_Email, TXTL_signup_MKRepeat, TXTL_signup_MatKhau;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{7,15}" +                // at least 4 characters
                    "$");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        InitWidget();
        Init();

    }

    private void Init() {
        userPreSenter = new UserPreSenter(this);
        btndangky.setOnClickListener(this);
        findViewById(R.id.btn_back_to_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hint = findViewById(R.id.hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void InitWidget() {
        btndangky = findViewById(R.id.btndangky);
        editemail=findViewById(R.id.editEmail);
        editpass = findViewById(R.id.editmatkhau);
        editpass_repeat = findViewById(R.id.editmatkhau_repeat);
        TXTL_signup_MatKhau = (TextInputLayout) findViewById(R.id.txtl_signup_MatKhau);
        TXTL_signup_Email= (TextInputLayout) findViewById(R.id.txtl_signup_Email);
        TXTL_signup_MKRepeat= (TextInputLayout) findViewById(R.id.txtl_signup_MatKhauRepeat);

    }

    @Override
    public void OnLengthEmail() {
        Toast.makeText(this, "Email không để trống", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnValidEmail() {
        Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnPass() {
        Toast.makeText(this, "Mật khẩu không để trống", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void OnSucess() {
        startActivity(new Intent(this, HomeActivity.class));
        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void OnAuthEmail() {
        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();

        Toast.makeText(this, "Hãy vào gmail để xác thực tài khoản của bạn !", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void OnFail() {
        Toast.makeText(this, "Tài khoản đã được đăng ký !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnPassNotSame() {
        Toast.makeText(this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (!validatePassWord() | !validatePassWordRepeat() | !validateEmail() ) {
            return;
        }
        switch (v.getId()){
            case  R.id.btndangky:
                String email=TXTL_signup_Email.getEditText().getText().toString();
                String pass =TXTL_signup_MatKhau.getEditText().getText().toString().trim();
                String repass =TXTL_signup_MKRepeat.getEditText().getText().toString().trim();
                userPreSenter.HandleRegist(email,pass,repass);


        }
    }
    private boolean validatePassWord() {
        String val = TXTL_signup_MatKhau.getEditText().getText().toString().trim();

        String thongbao = "Không được để trống";
        if (val.isEmpty()) {
            TXTL_signup_MatKhau.setError(thongbao);
            return false;
        } else if (!PASSWORD_PATTERN.matcher(val).matches()) {
            TXTL_signup_MatKhau.setError("Mật khẩu hợp lệ trong khoảng 7-15 kí tự");
            return false;
        }else if (        Pattern.compile("^" +
                "(?=.*[@#$%^&+=])" +     // at least 1 special character
                //"(?=\\S+$)" +            // no white spaces
                //".{7,15}" +                // at least 4 characters
                "$").matcher(val).matches()) {
            TXTL_signup_MatKhau.setError("Mật khẩu yêu cầu có 1 kí tự đặc biệt");
            return false;
        } else {
            TXTL_signup_MatKhau.setError(null);
            TXTL_signup_MatKhau.setErrorEnabled(false);
            return true;
        }

    }
    private boolean validatePassWordRepeat() {
        String thongbao = "Không được để trống";
        String val = TXTL_signup_MKRepeat.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            TXTL_signup_MKRepeat.setError(thongbao);
            return false;
        } else {
            TXTL_signup_MatKhau.setError(null);
            TXTL_signup_MatKhau.setErrorEnabled(false);
            return true;
        }
    }

    //region Validate field
//    private boolean validateFullName() {
//        String val = TXTL_signup_HoVaTen.getEditText().getText().toString().trim();
//
//        if (val.isEmpty()) {
//            TXTL_signup_HoVaTen.setError(getResources().getString(string.not_empty));
//            return false;
//        }else if (val.length() > 100) {
//            TXTL_signup_HoVaTen.setError("Phải nhỏ hơn 100 ký tự");
//            return false;
//        } else {
//            TXTL_signup_HoVaTen.setError(null);
//            TXTL_signup_HoVaTen.setErrorEnabled(false);
//            return true;
//        }
//    }
//
//    private boolean validateUserName() {
//        String val = TXTL_signup_TenDN.getEditText().getText().toString().trim();
//        String checkspaces = "\\A\\w{1,50}\\z";
//        //String specialcharacter = "[a-zA-Z.? ]*";
//
//        if (val.isEmpty()) {
//            TXTL_signup_TenDN.setError(getResources().getString(string.not_empty));
//            return false;
//        } else if (val.length() > 20) {
//            TXTL_signup_TenDN.setError("Phải nhỏ hơn 20 ký tự");
//            return false;
//        } else if (!val.matches(checkspaces)) {
//            TXTL_signup_TenDN.setError("Phải định dạng đúng kí tự");
//            return false;
//        } else {
//            TXTL_signup_TenDN.setError(null);
//            TXTL_signup_TenDN.setErrorEnabled(false);
//            return true;
//        }
//    }
//
    private boolean validateEmail() {
        String thongbao = "Không được để trống";
        String val = TXTL_signup_Email.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            TXTL_signup_Email.setError(thongbao);
            return false;
        } else if (!val.matches(checkspaces)) {
            TXTL_signup_Email.setError("Email không hợp lệ!");
            return false;
        } else {
            TXTL_signup_Email.setError(null);
            TXTL_signup_Email.setErrorEnabled(false);
            return true;
        }
    }
//
//    private boolean validatePhone() {
//        String val = TXTL_signup_SDT.getEditText().getText().toString().trim();
//
//        if (val.isEmpty()) {
//            TXTL_signup_SDT.setError(getResources().getString(string.not_empty));
//            return false;
//        } else if (val.length() < 10 || val.length() > 11) {
//            TXTL_signup_SDT.setError("Số điện thoại không hợp lệ!");
//            return false;
//        } else {
//            TXTL_signup_SDT.setError(null);
//            TXTL_signup_SDT.setErrorEnabled(false);
//            return true;
//        }
//    }
//

//    //endregion
}

