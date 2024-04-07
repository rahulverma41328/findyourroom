package com.example.findyourroom.LoginSignup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findyourroom.Database.UserHelperClass;
import com.example.findyourroom.HelperClass.AllPostItemsHelper;
import com.example.findyourroom.HelperClass.UserPostItemsHelper;
import com.example.findyourroom.R;
import com.example.findyourroom.User.Dashboard;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBarTopEdgeTreatment;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class CompleteProfile extends AppCompatActivity {

    ImageView userProfileImg;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    EditText description;
    UserHelperClass userHelperClass;
    Button completeProfile;
    TextInputLayout userLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        // hooks
        userProfileImg = findViewById(R.id.user_profile);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        description = findViewById(R.id.description);
        userHelperClass = new UserHelperClass();
        completeProfile = findViewById(R.id.complete_profile_btn);
        userLocation = findViewById(R.id.set_location);

        String desc = description.getText().toString();
        String locate = userLocation.getEditText().getText().toString();
        if (!desc.isEmpty() && !locate.isEmpty()) {
            completeProfile.setEnabled(true);
        }
        //methods

        userProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,11);
            }
        });

        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    UserPostItemsHelper userPostItemsHelper = snapshot.getValue(UserPostItemsHelper.class);
                    Picasso.get().load(userPostItemsHelper.getProfilePhoto()).placeholder(R.drawable.user_profile).into(userProfileImg);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CompleteProfile.this, "photo not saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // setting user profile in firebase


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            Uri uri = data.getData();
            userProfileImg.setImageURI(uri);
            completeProfile.setEnabled(true);
            final StorageReference reference = storage.getReference().child("cover_photo")
                    .child(auth.getUid());
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CompleteProfile.this, "Profile photo saved", Toast.LENGTH_SHORT).show();
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(auth.getUid()).child("profilePhoto").setValue(uri.toString());
                        }
                    });
                }
            });
        }
    }



    public void goToDashboard(View view) {

       Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT",true);
        UserPostItemsHelper itemsHelper = new UserPostItemsHelper();
        String _desc = description.getText().toString().trim();
        String _location = userLocation.getEditText().getText().toString().trim();
        itemsHelper.setDescription(_desc);
        itemsHelper.setLocation(_location);
        DatabaseReference reference =  database.getReference("Users");
        reference.child(auth.getUid()).child("description").setValue(itemsHelper.getDescription());
        reference.child(auth.getUid()).child("location").setValue(itemsHelper.getLocation());
        startActivity(intent);
        finish();
    }

}