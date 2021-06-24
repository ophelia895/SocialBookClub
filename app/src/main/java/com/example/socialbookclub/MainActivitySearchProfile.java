package com.example.socialbookclub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivitySearchProfile extends AppCompatActivity {


    private ProgressBar progressBar;
    private EditText searchEdt;
    private ImageButton searchBtn;
    private RecyclerView mRecyclerView;
    private FirebaseDatabase authDatabase;
    private DatabaseReference databaseRef;
    private FirebaseAuth authFirebase;
    private FirebaseUser user;
    private ArrayList <Profile> mProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_search_profile);

        authFirebase = FirebaseAuth.getInstance();
        user = authFirebase.getCurrentUser();
        authDatabase = FirebaseDatabase.getInstance();

        // initializing our views.
        progressBar = findViewById(R.id.idLoadingPB2);
        searchEdt = findViewById(R.id.idEdtSearchBooks2);
        searchBtn = findViewById(R.id.idBtnSearch2);

        searchEdt.getBackground().mutate().setColorFilter(getResources().
                getColor(android.R.color.holo_purple), PorterDuff.Mode.SRC_ATOP);


        // initializing on click listener for our button.
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                mProfiles=new ArrayList<>();

                // checking if our edittext field is empty or not.
                if (searchEdt.getText().toString().isEmpty()) {
                    searchEdt.setError("Please enter search query");
                    return;
                }
                // if the search query is not empty then we are
                // calling get book info method to load all
                // the books from the API.
                getProfiles(searchEdt.getText().toString());
            }

        });


    }

    private void getProfiles(String toString) {

        progressBar.setVisibility(View.GONE);
        databaseRef = authDatabase.getReference("users");
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()) {
                    Profile profile=new Profile();
                    int n=1;
                    String name="" + ds.child("name").getValue();
                    if(name.compareTo(toString)==0){
                        profile.setName(name);
                        profile.setEmail("" + ds.child("email").getValue());
                        profile.setImg("" + ds.child("img").getValue());
                        profile.setUid("" + ds.child("uid").getValue());

                        mProfiles.add(profile);
                        // below line is use to pass our
                        // array list in adapter class.
                        AdapterProfile adapter = new AdapterProfile(mProfiles, MainActivitySearchProfile.this);

                        // below line is use to add linear layout
                        // manager for our recycler view.
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivitySearchProfile.this, RecyclerView.VERTICAL, false);
                        mRecyclerView = findViewById(R.id.idRVBooks2);

                        // in below line we are setting layout manager and
                        // adapter to our recycler view.
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setAdapter(adapter);

                    }
                }

                if(mProfiles.size()==0){
                    Toast.makeText(MainActivitySearchProfile.this, "No profile Found", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}
