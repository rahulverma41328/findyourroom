package com.example.findyourroom.User.MainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findyourroom.HelperClass.AllPostItemsAdapter;
import com.example.findyourroom.HelperClass.AllPostItemsHelper;
import com.example.findyourroom.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;


public class DashboardHome extends Fragment {

    SearchView searchView;
    RecyclerView allPostItems;
    AllPostItemsAdapter adapter;
    ArrayList<AllPostItemsHelper> allPostLocations;
    FirebaseAuth auth;
    FirebaseDatabase database;

    public DashboardHome() {
        // Required empty public constructor
    }


    public static DashboardHome newInstance(String param1, String param2) {
        DashboardHome fragment = new DashboardHome();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        // hooks
        allPostItems = view.findViewById(R.id.show_post);
        searchView = view.findViewById(R.id.search_bar);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        allPostLocations = new ArrayList<>();


        allPostItems.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<AllPostItemsHelper> options = new FirebaseRecyclerOptions.Builder<AllPostItemsHelper>()
                .setQuery(database.getReference().child("posts"), new com.firebase.ui.database.SnapshotParser<AllPostItemsHelper>() {
                    @NonNull
                    @Override
                    public AllPostItemsHelper parseSnapshot(@NonNull DataSnapshot snapshot) {
                        AllPostItemsHelper allPostItemsHelper = snapshot.getValue(AllPostItemsHelper.class);
                        allPostItemsHelper.setPostId(snapshot.getKey());
                        return allPostItemsHelper;
                    }
                }).build();

        adapter = new AllPostItemsAdapter(options, getContext());
        allPostItems.setAdapter(adapter);

        //methods

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });
        return view;
    }

    private void processSearch(String newText) {

        FirebaseRecyclerOptions<AllPostItemsHelper> options = new FirebaseRecyclerOptions.Builder<AllPostItemsHelper>()
                .setQuery(database.getReference().child("posts").orderByChild("location").startAt(newText).endAt(newText + "\uf8ff"), new SnapshotParser<AllPostItemsHelper>() {
                    @NonNull
                    @Override
                    public AllPostItemsHelper parseSnapshot(@NonNull DataSnapshot snapshot) {
                        AllPostItemsHelper allPostItemsHelper = snapshot.getValue(AllPostItemsHelper.class);
                        allPostItemsHelper.setPostId(snapshot.getKey());
                        return allPostItemsHelper;
                    }
                }).build();

        adapter = new AllPostItemsAdapter(options,getContext());
        adapter.startListening();
        allPostItems.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

}