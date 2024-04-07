package com.example.findyourroom.HelperClass;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyourroom.PostDetails;
import com.example.findyourroom.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.common.base.MoreObjects;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class AllPostItemsAdapter extends FirebaseRecyclerAdapter<AllPostItemsHelper,AllPostItemsAdapter.myviewholder> {

    FirebaseAuth auth = FirebaseAuth.getInstance();
    Context context;
    public AllPostItemsAdapter(@NonNull FirebaseRecyclerOptions<AllPostItemsHelper> options,Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull AllPostItemsHelper model) {


        Picasso.get().load(model.getPostImage()).placeholder(R.drawable.default_image).into(holder.postImg);
        holder.Description.setText(model.getPostDescription());
        holder.location.setText(model.getLocation());
        holder.Rate.setText(model.getRate());
        FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AllPostItemsHelper allPostItemsHelper = snapshot.getValue(AllPostItemsHelper.class);
                Picasso.get().load(allPostItemsHelper.getProfilePhoto()).placeholder(R.drawable.user_profile).into(holder.userProfile);
                holder.postUsername.setText(allPostItemsHelper.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(context, PostDetails.class);
                    intent.putExtra("postId", model.getPostId());
                    intent.putExtra("postedBy", model.getPostedBy());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_post_card_design,parent,false);
        return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder{

        ImageView postImg,userProfile;
        TextView location,Description,Rate,postUsername,moreDetails;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            postImg = itemView.findViewById(R.id.post_image);
            location = itemView.findViewById(R.id.post_location);
            Description = itemView.findViewById(R.id.post_desc);
            Rate = itemView.findViewById(R.id.room_rate);
            userProfile = itemView.findViewById(R.id.username_profile);
            postUsername = itemView.findViewById(R.id.username);
            moreDetails = itemView.findViewById(R.id.more_details);

        }
    }

}
