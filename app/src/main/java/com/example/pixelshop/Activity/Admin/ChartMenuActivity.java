package com.example.pixelshop.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pixelshop.Model.SanPhamModels;
import com.example.pixelshop.Presenter.GioHangPreSenter;
import com.example.pixelshop.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;

public class ChartMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_menu);

        findViewById(R.id.cThongKeDoanhThu).setOnClickListener(this);
        findViewById(R.id.cThongKeDonHang).setOnClickListener(this);
        Init();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.cThongKeDonHang: startActivity(new Intent( ChartMenuActivity.this,ChartBillActivity.class));break;
            case R.id.cThongKeDoanhThu: startActivity(new Intent( ChartMenuActivity.this,StaticThongKe.class));break;
        }
    }

    private void Init() {

        findViewById(R.id.backadmin).setOnClickListener(view -> {
            finish();
        });


    }
}