package com.example.findyourroom.User.MainFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findyourroom.Database.UploadPost;
import com.example.findyourroom.HelperClass.AllPostItemsHelper;
import com.example.findyourroom.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Objects;

public class DashboardAddPost extends Fragment {

    EditText description;
    TextInputLayout phoneNo,postLocation,roomRate;
    ImageView userProfile,postImg,uploadImg;
    TextView username;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri uri;
    Button postBtn;
    ProgressDialog dialog;
    public DashboardAddPost() {
    }


    public static DashboardAddPost newInstance(String param1, String param2) {
        DashboardAddPost fragment = new DashboardAddPost();
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

        View view = inflater.inflate(R.layout.fragment_dashboard_add_post, container, false);

        description = view.findViewById(R.id.post_desc);
        postImg = view.findViewById(R.id.post_image);
        userProfile = view.findViewById(R.id.user_profile_image);
        username = view.findViewById(R.id.post_username);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        postBtn = view.findViewById(R.id.post_btn);
        phoneNo = view.findViewById(R.id.phone_no);
        uploadImg = view.findViewById(R.id.upload_image);
        postLocation = view.findViewById(R.id.post_location);
        roomRate = view.findViewById(R.id.room_rate);
        // dialog
        dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Post Uploading");
        dialog.setMessage("please wait...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    AllPostItemsHelper postItemsHelper = snapshot.getValue(AllPostItemsHelper.class);
                    Picasso.get().load(postItemsHelper.getProfilePhoto()).placeholder(R.drawable.user_profile).into(userProfile);
                    username.setText(postItemsHelper.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,10);
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                final StorageReference reference = storage.getReference().child("posts").child(auth.getUid()).child(new Date().getTime()+"");
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                UploadPost post = new UploadPost();
                                post.setPostImage(uri.toString());
                                post.setPostedBy(auth.getUid());
                                post.setPostDescription(description.getText().toString());
                                post.setPostedAt(new Date().getTime());
                                post.setPhoneNo(Objects.requireNonNull(phoneNo.getEditText()).getText().toString());
                                post.setLocation(Objects.requireNonNull(postLocation.getEditText()).getText().toString());
                                post.setRate(Objects.requireNonNull(roomRate.getEditText()).getText().toString());
                                database.getReference().child("posts").push().setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialog.dismiss();
                                        Toast.makeText(getContext(), "Posted Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            uri = data.getData();
            postImg.setImageURI(uri);
            postBtn.setEnabled(true);
        }
    }
}