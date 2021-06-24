package com.example.socialbookclub.BookClub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.socialbookclub.Profile;
import com.example.socialbookclub.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivityGroup extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    private TextView title,author,book,description;
    private EditText edtAddComment;
    private ImageButton addcomment;
    private NestedScrollView mScrollView;
    private RecyclerView mRecyclerView;
    private BookClub mBookClub;
    private FirebaseAuth authFirebase;
    private FirebaseUser user;
    private FirebaseDatabase authDatabase,authDatabase2;
    private DatabaseReference databaseRef,databaseRef2;
    private Profile profileClub;
    private ArrayList  <Profile> profileComment;
    private ArrayList <String> comments;
    private AdapterComment mAdapterProfile;
    private  int numberClub;
    private String uidVista, nameVista;
    private String uidComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScrollView=new NestedScrollView(this);
        mRelativeLayout=new RelativeLayout(this);
//        mRelativeLayout.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView=new RecyclerView(this);
        addcomment=new ImageButton(this);
        edtAddComment=new EditText(this);

        title=new TextView(this);
        author=new TextView(this);
        book=new TextView(this);
        description=new TextView(this);

        //dati dell utente che osserviamo
        uidVista=getIntent().getStringExtra("uid");
        nameVista=getIntent().getStringExtra("author");


        authFirebase = FirebaseAuth.getInstance();
        user = authFirebase.getCurrentUser();
        //dati utente loggato
        profileClub=new Profile();
        profileClub.setUid(user.getUid());

        numberClub=getIntent().getIntExtra("n",0);
        mBookClub=new BookClub();

        //title
        title.setText(getIntent().getStringExtra("title"));
        title.setTextColor(Color.BLACK);
        title.setTextSize(30);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        title.setId(View.generateViewId());
        RelativeLayout.LayoutParams paramsTitle=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTitle.setMargins(20,20,20,20);
        mRelativeLayout.addView(title,paramsTitle);


        //creatore
        author.setTextSize(20);
        author.setText("Creator:   "+ nameVista);
        author.setId(View.generateViewId());
        RelativeLayout.LayoutParams paramsauthor=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsauthor.setMargins(20,20,20,20);
        paramsauthor.addRule(RelativeLayout.BELOW,title.getId());
        mRelativeLayout.addView(author,paramsauthor);

        //book
        book.setTextSize(20);
        book.setId(View.generateViewId());
        RelativeLayout.LayoutParams paramsbook=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsbook.setMargins(20,20,20,20);
        paramsbook.addRule(RelativeLayout.BELOW,author.getId());
        mRelativeLayout.addView(book,paramsbook);
        //description
        description.setHint("Inserire descrizione");
        description.setTextSize(20);
        description.setMaxLines(20);
        description.setId(View.generateViewId());
        RelativeLayout.LayoutParams paramsdescription=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsdescription.setMargins(20,20,20,20);
        paramsdescription.addRule(RelativeLayout.BELOW,book.getId());
        mRelativeLayout.addView(description,paramsdescription);


        //edittext
        edtAddComment.setMaxLines(35);
        edtAddComment.setHint("Comment");
        edtAddComment.setId(View.generateViewId());
        RelativeLayout.LayoutParams paramsedt=new RelativeLayout.LayoutParams(1000,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsedt.setMargins(20,0,20,20);
        paramsedt.addRule(RelativeLayout.BELOW,description.getId());
        mRelativeLayout.addView(edtAddComment,paramsedt);


        //bottone
        addcomment.setImageResource(R.drawable.ic_addcomment);
        addcomment.setBackgroundColor(Color.WHITE);
        RelativeLayout.LayoutParams paramsbutton=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsbutton.setMargins(20,20,20,0);
        paramsbutton.addRule(RelativeLayout.BELOW,description.getId());
        paramsbutton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        paramsbutton.addRule(RelativeLayout.RIGHT_OF,edtAddComment.getId());
        mRelativeLayout.addView(addcomment,paramsbutton);

        //recycle view
        RelativeLayout.LayoutParams paramsRecyclerView=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsRecyclerView.setMargins(10,20,10,10);
        paramsRecyclerView.addRule(RelativeLayout.BELOW,edtAddComment.getId());
        mRecyclerView.setNestedScrollingEnabled(false);
        mRelativeLayout.addView(mRecyclerView,paramsRecyclerView);


        mScrollView.addView(mRelativeLayout);
        setContentView(mScrollView);

        authFirebase = FirebaseAuth.getInstance();
        authDatabase = FirebaseDatabase.getInstance();
        authDatabase2= FirebaseDatabase.getInstance();
        databaseRef2=authDatabase2.getReference("comments").child(uidVista).child(String.valueOf(numberClub+1));
       profileComment=new ArrayList<>();
        readProfile();

        comments=new ArrayList<>();

        readDatabase();

        addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment();
                edtAddComment.setText("");
            }
        });

    }
    private void addComment() {


        int n=mBookClub.getNcomments()+1;

        HashMap <String,String>h=new HashMap();
        h.put("text",edtAddComment.getText().toString());
        h.put("name",profileClub.getName());
        h.put("img",profileClub.getImg());

        databaseRef2.child("commment"+n).setValue(h);

    }
    public void readProfile() {

        databaseRef = authDatabase.getReference("users");
        Profile p=new Profile();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s;int n=1;Profile p;
                for (DataSnapshot ds : snapshot.getChildren()) {

                    p=ds.getValue(Profile.class);
                    if(p.getUid().compareTo(user.getUid())==0)
                        profileClub=p;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu_activity,menu);
        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.settings:
                setting();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void readDatabase(){
        databaseRef = authDatabase.getReference("bookClub").child(uidVista);

        databaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    BookClub b= ds.getValue(BookClub.class);

                    if(b.getNumberCLub()!=null)
                        if(b.getNumberCLub().compareTo(String.valueOf(numberClub+1))==0){

                        mBookClub=b;
                        description.setText("Description:\n"+mBookClub.getDescription());
                        book.setText("Book:   " + mBookClub.getBook());

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //prendiamo i commenti
        databaseRef2.addValueEventListener(new ValueEventListener() {
            String s;int n;String u;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                n=1;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    s="" + ds.child("text").getValue();
                    String e=("" + ds.child("email").getValue());
                    String ui=("" + ds.child("uid").getValue());
                    String na=("" + ds.child("name").getValue());
                    String i=(""+ds.child("img").getValue());
                    Profile p=new Profile(na,i,ui,e);
                    profileComment.add(p);
                    if(n>comments.size()){
                    comments.add(s);
                    mBookClub.addComments();
                    uidComment=u;
                   }
                    ++n;
                    mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivityGroup.this,1));
                    mAdapterProfile=new AdapterComment(comments,MainActivityGroup.this,profileComment);
                    mRecyclerView.setAdapter(mAdapterProfile);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void setting(){

        LinearLayout linearLayout=new LinearLayout(this);
        EditText edtDescription=new EditText(this);

        edtDescription.setMaxLines(10);

        edtDescription.setHint("Inserire Descrizione");


        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(edtDescription);

        AlertDialog.Builder createClub=new AlertDialog.Builder(this);
        createClub.setTitle("Inserisci descrizione");
        createClub.setView(linearLayout);

        createClub.setNegativeButton("Modifica",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                description.setText(edtDescription.getText().toString());

                databaseRef.child("bookClub"+(numberClub+1)).
                        child("description").setValue(description.getText().toString());


                dialog.dismiss();
            }
        });

        createClub.setPositiveButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        createClub.create().show();

    }



}