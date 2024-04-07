package com.example.findyourroom.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.findyourroom.R;
import com.example.findyourroom.User.MainFragment.DashboardAddPost;
import com.example.findyourroom.User.MainFragment.DashboardHome;
import com.example.findyourroom.User.MainFragment.DashboardProfile;
import com.example.findyourroom.User.MainFragment.DashboardSettings;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Dashboard extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;
    DashboardHome homeDashboard;
    DashboardProfile dashboardProfile;
    DashboardAddPost dashboardAddPost;
    DashboardSettings dashboardSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        homeDashboard = new DashboardHome();
        dashboardProfile = new DashboardProfile();
        dashboardAddPost = new DashboardAddPost();
        dashboardSettings = new DashboardSettings();

        // navigation view
        chipNavigationBar = findViewById(R.id.navigation_bar);
        chipNavigationBar.setItemSelected(R.id.home,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeDashboard).commit();
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeDashboard).commit();
                        break;
                    case R.id.add_room:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,dashboardAddPost).commit();
                        break;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,dashboardProfile).commit();
                        break;
                    case R.id.setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,dashboardSettings).commit();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Dashboard.this.finish();
    }
}