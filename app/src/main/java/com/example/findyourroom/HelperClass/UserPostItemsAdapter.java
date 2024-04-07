package com.example.findyourroom.HelperClass;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyourroom.Database.UploadPost;
import com.example.findyourroom.PostDetails;
import com.example.findyourroom.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserPostItemsAdapter extends RecyclerView.Adapter<UserPostItemsAdapter.ProfileItemsViewHolder>  {

    ArrayList<UploadPost> AllItemsUserLocation;
    Context context;
    DatabaseReference reference;
    public UserPostItemsAdapter(ArrayList<UploadPost> allItemsUserLocation, Context context) {
        AllItemsUserLocation = allItemsUserLocation;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_post_card_design,parent,false);
        ProfileItemsViewHolder itemsViewHolder = new ProfileItemsViewHolder(view);
        return itemsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileItemsViewHolder holder, int position) {


        UploadPost userUploadPost = AllItemsUserLocation.get(position);
        Picasso.get().load(userUploadPost.getPostImage()).placeholder(R.drawable.default_image).into(holder.userPostImg);
        holder.desc.setText(userUploadPost.getPostDescription());
        holder.rate.setText(userUploadPost.getRate());

        holder.deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,view);
                popupMenu.inflate(R.menu.delete_post);
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.userPostImg.getContext());
                builder.setTitle("Delete Post");
                builder.setMessage("Are you sure you want to delete the post");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getItemId() == R.id.delete_post) {
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseDatabase.getInstance().getReference().child("posts").child(userUploadPost.getPostId()).removeValue();
                                    Toast.makeText(context, "Post Deleted", Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        }
                        return false;
                    }

                });
                popupMenu.show();
            }
        });

        holder.moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostDetails.class);
                intent.putExtra("postId",userUploadPost.getPostId());
                intent.putExtra("postedBy",userUploadPost.getPostedBy());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        FirebaseDatabase.getInstance().getReference().child("Users").child(userUploadPost.getPostedBy()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AllPostItemsHelper user = snapshot.getValue(AllPostItemsHelper.class);
                Picasso.get().load(user.getProfilePhoto()).placeholder(R.drawable.user_profile).into(holder.userProfileImg);
                holder.username.setText(user.getUsername());
                holder.location.setText(user.getLocation());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public int getItemCount() {
        return AllItemsUserLocation.size();
    }

    public static class ProfileItemsViewHolder extends RecyclerView.ViewHolder{

        ImageView userPostImg,deletePost;
        CircleImageView userProfileImg;
        TextView username,desc,location,moreDetails,rate;
        public ProfileItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            userPostImg = itemView.findViewById(R.id.post_image);
            deletePost = itemView.findViewById(R.id.delete_post);
            userProfileImg = itemView.findViewById(R.id.username_profile);
            username = itemView.findViewById(R.id.username);
            moreDetails = itemView.findViewById(R.id.user_post_more_details);
            location = itemView.findViewById(R.id.post_location);
            desc = itemView.findViewById(R.id.post_desc);
            rate = itemView.findViewById(R.id.room_rate);
        }
    }
}
