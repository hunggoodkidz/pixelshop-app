package com.example.pixelshop.Activity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pixelshop.Adapter.SanPhamAdapter;
import com.example.pixelshop.Model.SanPhamModels;
import com.example.pixelshop.Presenter.SanPhamPreSenter;
import com.example.pixelshop.Presenter.SanPhamView;
import com.example.pixelshop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity  extends AppCompatActivity implements SanPhamView{


    private FirebaseFirestore db;
    private ListView listSearch;
    private AutoCompleteTextView txtSearch;
    private Toolbar toolbar;
    private List<String> list;
    private  ArrayList<SanPhamModels> arrayList;
    private SanPhamPreSenter sanPhamPreSenter;
    private SanPhamAdapter sanPhamAdapter;
    private RecyclerView rcvSearch;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Init();


    }

    private void Init (){
        db = FirebaseFirestore.getInstance();
        rcvSearch = (RecyclerView) findViewById(R.id.rcvSearch);
        txtSearch = (AutoCompleteTextView) findViewById(R.id.txtSearch);
        list = new ArrayList<>();
        sanPhamPreSenter = new SanPhamPreSenter(this);
        arrayList  =new ArrayList<>();
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Back");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        db.collection("SanPham").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot q :  queryDocumentSnapshots){
                    list.add(q.getString("tensp"));
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1,list);
                txtSearch.setAdapter(arrayAdapter);


            }
        });
        txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = txtSearch.getText().toString().trim();
                //searchProductName(name);
                arrayList.clear();
                if(sanPhamAdapter!=null){
                    sanPhamAdapter.notifyDataSetChanged();
                }
                sanPhamPreSenter.HandlegetDataSanPham(name,2);

            }
        });
    }

    private void searchProductName(String name) {
        db.collection("SanPham").
                whereEqualTo("tensp",name).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                
                            }
                        }else{

                        }

                    }
                });

    }


    @Override
    public void getDataSanPham(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id,tensp,giatien,hinhanh,loaisp,mota,soluong,hansudung,type,trongluong));
        sanPhamAdapter = new SanPhamAdapter(this,arrayList,1);
        rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvSearch.setAdapter(sanPhamAdapter);


    }
    @Override
    public void getDataSanPhamTU(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id,tensp,giatien,hinhanh,loaisp,mota,soluong,hansudung,type,trongluong));
        sanPhamAdapter = new SanPhamAdapter(this,arrayList,3);
        rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvSearch.setAdapter(sanPhamAdapter);


    }
    @Override
    public void getDataSanPhamHQ(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id,tensp,giatien,hinhanh,loaisp,mota,soluong,hansudung,type,trongluong));
        sanPhamAdapter = new SanPhamAdapter(this,arrayList,4);
        rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvSearch.setAdapter(sanPhamAdapter);


    }
    @Override
    public void getDataSanPhamMC(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id,tensp,giatien,hinhanh,loaisp,mota,soluong,hansudung,type,trongluong));
        sanPhamAdapter = new SanPhamAdapter(this,arrayList,5);
        rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvSearch.setAdapter(sanPhamAdapter);


    }
    @Override
    public void getDataSanPhamYT(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id,tensp,giatien,hinhanh,loaisp,mota,soluong,hansudung,type,trongluong));
        sanPhamAdapter = new SanPhamAdapter(this,arrayList,6);
        rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvSearch.setAdapter(sanPhamAdapter);


    }
    @Override
    public void getDataSanPhamLau(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id,tensp,giatien,hinhanh,loaisp,mota,soluong,hansudung,type,trongluong));
        sanPhamAdapter = new SanPhamAdapter(this,arrayList,7);
        rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvSearch.setAdapter(sanPhamAdapter);


    }
    @Override
    public void getDataSanPhamGY(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        arrayList.add(new SanPhamModels(id,tensp,giatien,hinhanh,loaisp,mota,soluong,hansudung,type,trongluong));
        sanPhamAdapter = new SanPhamAdapter(this,arrayList,8);
        rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        rcvSearch.setAdapter(sanPhamAdapter);


    }
    @Override
    public void OnEmptyList() {
        Toast.makeText(this, "Không tìm thấy sản phẩm nào trong danh mục : ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDataSanPhamNB(String id, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {

    }


}
