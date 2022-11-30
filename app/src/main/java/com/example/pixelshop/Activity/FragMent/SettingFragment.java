package com.example.pixelshop.Activity.FragMent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pixelshop.Activity.Account.ChangePasswordActivity;
import com.example.pixelshop.Activity.Account.SignInActivity;
import com.example.pixelshop.Activity.Admin.HomeAdminActivity;
import com.example.pixelshop.Activity.Bill.CartActivity;
import com.example.pixelshop.Activity.Home2Activity;
import com.example.pixelshop.Activity.HomeActivity;
import com.example.pixelshop.Activity.SearchActivity;
import com.example.pixelshop.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;



public class SettingFragment extends Fragment {

    View view;


    private NavigationView navigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Fragment fm;
    private FirebaseAuth firebaseAuth;
    private EditText editsearch;
    private ImageView search;
    private TextView tvusername,tvemail;
    private CircleImageView imaProfile;

    public  static CountDownTimer countDownTimer;

    private FirebaseFirestore db;

    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }


    private void setProFile() {
        db= FirebaseFirestore.getInstance();
        //tvemail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        db.collection("thongtinUser").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Profile")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            if(documentSnapshot!=null){
                                try{
                                    tvusername.setText(documentSnapshot.getString("hoten").length()>0 ?
                                            documentSnapshot.getString("hoten") : "");

                                    if(documentSnapshot.getString("avatar").length()>0){
                                        Picasso.get().load(documentSnapshot.getString("avatar").trim()).into(imaProfile);
                                    }
                                }catch (Exception e){
                                    Log.d("ERROR",e.getMessage());
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        InitWidget();
        setProFile();
        Init();


        return view;
    }
    private void Init(){

        view.findViewById(R.id.logout).setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), SignInActivity.class));
            getActivity().finish();
        });
        view.findViewById(R.id.clickProfile).setOnClickListener(view -> {
            new FragMent_Home();
        });
        view.findViewById(R.id.clickSetting).setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
        });
        view.findViewById(R.id.clickGioHang).setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), CartActivity.class));
        });
        view.findViewById(R.id.adminpanel).setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), HomeAdminActivity.class));
        });

    }
    private void InitWidget() {

        tvusername = view.findViewById(R.id.nameText);
        imaProfile = view.findViewById(R.id.profile_image);


    }
}