package com.example.bloggingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements Adapter.OnArticleListener {

    private FirebaseAuth mAuth;

    NavController navController;
    private DatabaseReference mDatabase;
    Adapter adapter;
    RecyclerView recyclerView;
    ArrayList<Item> mList = new ArrayList<>();
    boolean reload = true;
    TextView name,emailID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();

        if(reload)
            mDatabase = FirebaseDatabase.getInstance().getReference().child("articles");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mList.clear();
                for (DataSnapshot  item : dataSnapshot.getChildren())
                {
                    String authorName = item.child("authorUID").getValue(String.class);
                    if(authorName.equals(mAuth.getCurrentUser().getDisplayName())) {
                        String articleName = item.child("articleName").getValue(String.class);
                        String articleColor = item.child("articleColor").getValue(String.class);
                        String NumLikes = item.child("NumLikes").getValue(String.class);
                        String[] tags = new String[3];
                        int i = 0;
                        for (DataSnapshot tag : item.child("tags").getChildren()) {
                            tags[i] = tag.getValue(String.class);
                            i++;
                        }
                        mList.add(new Item(articleColor, articleName, authorName, NumLikes, tags));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        recyclerView = rootView.findViewById(R.id.rvList2);
        name = rootView.findViewById(R.id.name);
        emailID = rootView.findViewById(R.id.emailID);
        adapter = new Adapter(getActivity(),mList,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        name.setText("Name: " + mAuth.getCurrentUser().getDisplayName());
        emailID.setText("Email: " + mAuth.getCurrentUser().getEmail());
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onArticleClicked(int position,View view) {
        reload = false;
        ProfileFragmentDirections.ActionProfileFragmentToArticleFragment action = ProfileFragmentDirections.actionProfileFragmentToArticleFragment();
        action.setArticleNum(position);
        Navigation.findNavController(view).navigate(action);
    }
}