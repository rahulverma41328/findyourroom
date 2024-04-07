package com.example.findyourroom.User.MainFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.findyourroom.LoginSignup.LoginScreen;
import com.example.findyourroom.R;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardSettings extends Fragment {

    TextView logoutBtn;
    FirebaseAuth auth;
    public DashboardSettings() {
        // Required empty public constructor
    }

    public static DashboardSettings newInstance(String param1, String param2) {
        DashboardSettings fragment = new DashboardSettings();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard_settiings, container, false);

        logoutBtn = view.findViewById(R.id.logout);
        auth = FirebaseAuth.getInstance();
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent = new Intent(getActivity(), LoginScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}