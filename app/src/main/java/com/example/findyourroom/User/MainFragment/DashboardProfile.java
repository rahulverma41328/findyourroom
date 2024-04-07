package com.example.findyourroom.User.MainFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findyourroom.Database.UploadPost;
import com.example.findyourroom.HelperClass.AllPostItemsHelper;
import com.example.findyourroom.HelperClass.UserPostItemsAdapter;
import com.example.findyourroom.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardProfile extends Fragment {

    CircleImageView userProfile;
    TextView username,userDescription;
    ImageView editProfile;
    RecyclerView userPost;
    FirebaseDatabase database;
    FirebaseAuth auth;
    UserPostItemsAdapter adapter;
    ArrayList<UploadPost> allUserLocation = new ArrayList<>();
    FirebaseStorage storage;

    public DashboardProfile() {
        // Required empty public constructor
    }


    public static DashboardProfile newInstance(String param1, String param2) {
        DashboardProfile fragment = new DashboardProfile();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dashboard_profile, container, false);

        userProfile = view.findViewById(R.id.user_image);
        username = view.findViewById(R.id.username);
        userDescription = view.findViewById(R.id.user_description);
        editProfile = view.findViewById(R.id.edit_profile_image);
        userPost = view.findViewById(R.id.user_post);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        userPost.setLayoutManager(new LinearLayoutManager(getContext()));
        userPost.setHasFixedSize(true);
        userPost.setNestedScrollingEnabled(true);
        allUserLocation.clear();
        // setting username ,profile
        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AllPostItemsHelper itemsHelper = snapshot.getValue(AllPostItemsHelper.class);
                Picasso.get().load(itemsHelper.getProfilePhoto()).placeholder(R.drawable.user_profile).into(userProfile);
                username.setText(itemsHelper.getUsername());
                userDescription.setText(itemsHelper.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Profile photo not uploaded", Toast.LENGTH_SHORT).show();

            }
        });

        // setting user posts
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        Query query = databaseReference.orderByChild("postedBy").equalTo(auth.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    UploadPost post = dataSnapshot.getValue(UploadPost.class);
                    allUserLocation.add(post);
                    post.setPostId(dataSnapshot.getKey());
                    adapter = new UserPostItemsAdapter(allUserLocation,getContext());
                    userPost.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,10);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            Uri uri = data.getData();
            userProfile.setImageURI(uri);

            final StorageReference reference = storage.getReference().child("cover_photo").child(auth.getUid());
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "photo saved", Toast.LENGTH_SHORT).show();
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(auth.getUid()).child("ProfilePhoto").setValue(uri.toString());
                        }
                    });
                }
            });

        }
    }
}