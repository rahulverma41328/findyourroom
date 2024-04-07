package com.example.findyourroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findyourroom.Database.UserHelperClass;
import com.example.findyourroom.HelperClass.AllPostItemsHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class PostDetails extends AppCompatActivity {

    ImageView postImg,userProfile;
    TextView username,location,description,phoneNo;
    String postId,postedBy;
    Intent intent;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        // hooks
        postImg = findViewById(R.id.detail_image);
        userProfile = findViewById(R.id.user_image);
        username = findViewById(R.id.username);
        location = findViewById(R.id.post_location);
        description = findViewById(R.id.detail_description);
        phoneNo = findViewById(R.id.phone_no);
        intent = getIntent();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        postId = intent.getStringExtra("postId");
        postedBy = intent.getStringExtra("postedBy");

        database.getReference().child("posts").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AllPostItemsHelper itemsHelper = snapshot.getValue(AllPostItemsHelper.class);
                Picasso.get().load(Objects.requireNonNull(itemsHelper).getPostImage()).placeholder(R.drawable.default_image).into(postImg);
                description.setText(itemsHelper.getPostDescription());
                phoneNo.setText(itemsHelper.getPhoneNo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database.getReference().child("Users").child(postedBy).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AllPostItemsHelper user = snapshot.getValue(AllPostItemsHelper.class);
                Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user_profile).into(userProfile);
                username.setText(user.getUsername());
                location.setText(user.getLocation());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}