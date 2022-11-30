package com.example.pixelshop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.pixelshop.Activity.FragMent.NotifcationFragment;
import com.example.pixelshop.Activity.FragMent.SettingFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.example.pixelshop.R;
import com.example.pixelshop.ThongtinungdungFragment;
import com.example.pixelshop.Activity.Account.SignInActivity;
import com.example.pixelshop.Activity.Bill.CartActivity;
import com.example.pixelshop.Activity.FragMent.FragMent_Bill;
import com.example.pixelshop.Activity.FragMent.FragMent_Home;
import com.example.pixelshop.Activity.FragMent.FragMent_ProFile;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity  extends AppCompatActivity implements FragMent_Home.FragMent_HomeListener {
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Fragment fm;
    private FirebaseAuth firebaseAuth;
    private EditText editsearch;
    private ImageView cart;
    private TextView txSearch;
    private CircleImageView imaProfile;
    int newPosition, startingPosition;

    public  static CountDownTimer countDownTimer;

    private FirebaseFirestore db;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navibottom);
        InitWidget();
        Init();

    }


    private void Init() {    // custom thanh toolbar

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(Gravity.LEFT);
//            }
//        });
//        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,
//                R.string.Close);
//        toggle.syncState();
        fm = new FragMent_Home();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fm).commit();

         //Check user phân quyền tk đang nhap va chua dang nhap
         firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser f = firebaseAuth.getCurrentUser();

        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case  R.id.home:
                        fm = new FragMent_Home();break;
                    case  R.id.donhang:
                        fm=new FragMent_Bill();break;
                    case  R.id.notification:
                        fm = new NotifcationFragment();break;
                    case  R.id.person:
                        fm = new SettingFragment();break;
                    case  R.id.category:
                        startActivity(new Intent(HomeActivity.this, ThongKeDanhMucActivity.class));break;
                }
                if(fm!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fm).commit();
                }
                //drawerLayout.closeDrawers();
                return true;
            }
        });




    }
    private boolean loadFragment(Fragment fragment, int newPosition) {
        if (fragment != null) {
            if (startingPosition > newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                transaction.replace(R.id.framelayout, fragment);
                transaction.commit();
            }
            if (startingPosition < newPosition) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                transaction.replace(R.id.framelayout, fragment);
                transaction.commit();
            }
            startingPosition = newPosition;
            return true;
        }
        return false;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //editsearch.setText("");
        if(countDownTimer!=null){
            countDownTimer.start();
        }
    }

    private void InitWidget() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout= findViewById(R.id.drawerlayout);


    }

    @Override
    public void onButtonClick() {
        Intent intent = new Intent(HomeActivity.this,ThongKeDanhMucActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setProFile();
    }
}
