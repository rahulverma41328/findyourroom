package com.example.findyourroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findyourroom.LoginSignup.LoginScreen;
import com.example.findyourroom.User.Dashboard;

public class MainActivity extends AppCompatActivity {

    public static int SPLASH_TIMER = 3000;
    ImageView splashImg;
    TextView splashText;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Animation sideAnim, bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hooks
        splashImg = findViewById(R.id.splash_img);
        splashText = findViewById(R.id.splash_text);
        sharedPreferences = this.getSharedPreferences("login",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // animation
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        splashImg.setAnimation(sideAnim);
        splashText.setAnimation(bottomAnim);

        // methods
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMER);
    }
}