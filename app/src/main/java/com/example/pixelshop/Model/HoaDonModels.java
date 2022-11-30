package com.example.pixelshop.Model;

import android.database.Cursor;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.pixelshop.Presenter.IHoaDon;

import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HoaDonModels implements Serializable {

    private  String id;
    private  String uid;
    private  String diachi;
    private  String hoten;
    private  String ngaydat;
    private  String phuongthuc;
    private  String sdt;
    private  long tongtien;
    private  long type;
    private IHoaDon callback;
    private FirebaseFirestore db;

    public HoaDonModels(IHoaDon callback) {
        this.callback=callback;
        db=FirebaseFirestore.getInstance();
    }

    public HoaDonModels() {

    }

    public HoaDonModels(String id, String uid, String diachi, String hoten, String ngaydat, String phuongthuc, String sdt, long tongtien,long type) {
        this.id = id;
        this.uid = uid;
        this.diachi = diachi;
        this.hoten = hoten;
        this.ngaydat = ngaydat;
        this.phuongthuc = phuongthuc;
        this.sdt = sdt;
        this.tongtien = tongtien;
        this.type=type;
    }

//    public List<DonDatDTO> LayDSDonDat() {
//        List<DonDatDTO> donDatDTOS = new ArrayList<DonDatDTO>();
//        String query = "SELECT * FROM " + CreateDatabase.TBL_DONDAT;
//        Cursor cursor = database.rawQuery(query, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            DonDatDTO donDatDTO = new DonDatDTO();
//            donDatDTO.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT)));
//            donDatDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MABAN)));
//            donDatDTO.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN)));
//            donDatDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG)));
//            donDatDTO.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT)));
//            donDatDTO.setMaNV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MANV)));
//            donDatDTOS.add(donDatDTO);
//
//            cursor.moveToNext();
//        }
//        return donDatDTOS;
//    }
    public  void HandleReadData(){
        db.collection("HoaDon").whereEqualTo("UID",FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                        callback.getDataHD(d.getId(),d.getString("UID"),d.getString("diachi"),
                                d.getString("hoten"),d.getString("ngaydat"),d.getString("phuongthuc"),d.getString("sdt"),
                                d.getLong("tongtien"),d.getLong("trangthai"));
                    }
                }

            }
        });
    }

    public long getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }

    public String getPhuongthuc() {
        return phuongthuc;
    }

    public void setPhuongthuc(String phuongthuc) {
        this.phuongthuc = phuongthuc;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public long getTongtien() {
        return tongtien;
    }

    public void setTongtien(long tongtien) {
        this.tongtien = tongtien;
    }

    public List<HoaDonModels> HandleReadData2(){
        List<HoaDonModels> hoaDonModels = new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        db.collection("HoaDon")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){

                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
//                                callback.getDataHD(d.getId(),d.getString("UID"),d.getString("diachi"),
//                                        d.getString("hoten"),d.getString("ngaydat"),d.getString("phuongthuc"),d.getString("sdt"),
//                                        d.getLong("tongtien"),d.getLong("trangthai"));
                                HoaDonModels hoaDonModels1 = d.toObject(HoaDonModels.class);
                                hoaDonModels.add(hoaDonModels1);
                            }

                        }
                    }

                });
        return hoaDonModels;
    }


    public void HandleUpdateStatusBill(int i,String id) {
        db.collection("HoaDon")
                .document(id).update("trangthai",i).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    callback.OnSucess();
                }else{
                    callback.OnFail();
                }
            }
        });
    }

    public void HandleReadData(int position) {
        if(position==0){
            db.collection("HoaDon")
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.size()>0){
                        for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                            callback.getDataHD(d.getId(),d.getString("UID"),d.getString("diachi"),
                                    d.getString("hoten"),d.getString("ngaydat"),d.getString("phuongthuc"),d.getString("sdt"),
                                    d.getLong("tongtien"),d.getLong("trangthai"));
                        }
                    }

                }
            });
        }else{
            db.collection("HoaDon").whereEqualTo("trangthai",position)
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.size()>0){
                        for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                            callback.getDataHD(d.getId(),d.getString("UID"),d.getString("diachi"),
                                    d.getString("hoten"),d.getString("ngaydat"),d.getString("phuongthuc"),d.getString("sdt"),
                                    d.getLong("tongtien"),d.getLong("trangthai"));
                        }
                    }

                }
            });
        }

    }
}
