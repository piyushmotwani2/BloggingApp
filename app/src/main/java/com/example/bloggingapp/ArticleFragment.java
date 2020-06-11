package com.example.bloggingapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ArticleFragment extends Fragment {

    private DatabaseReference mDatabase;
    TextView articleContent,authorName,articleName;
    int position = 0;
    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_article, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("articles");
        articleContent = rootView.findViewById(R.id.articleContent);
        authorName = rootView.findViewById(R.id.authorName);
        articleName = rootView.findViewById(R.id.articleName);
        position = ArticleFragmentArgs.fromBundle(getArguments()).getArticleNum();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String author = dataSnapshot.child(String.valueOf(position)).child("authorUID").getValue(String.class);
                String content = dataSnapshot.child(String.valueOf(position)).child("articleContent").getValue(String.class);
                String name = dataSnapshot.child(String.valueOf(position)).child("articleName").getValue(String.class);
                //Log.d(TAG, "Value is: " + value);
                authorName.setText(author);
                articleContent.setText(content);
                articleName.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }
}