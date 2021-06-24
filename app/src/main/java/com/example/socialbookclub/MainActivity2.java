package com.example.socialbookclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.socialbookclub.BookClub.AdapterBookClub;
import com.example.socialbookclub.BookClub.BookClub;
import com.example.socialbookclub.Library.MainActivityListBook;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AdapterBookClub mAdapterProfile;
    private TextView txtProfileName,txtEmail;
    private FloatingActionButton btnAdd;

    private ImageView imgProfile;
    private Bitmap bitmap;
    private byte[] byteImage;

    private ArrayList <String> bookClub;
    private String emailString;
    private String uidUser;
    private Profile thisProfile;

    private FirebaseAuth authFirebase;
    private FirebaseDatabase authDatabase;
    private DatabaseReference databaseRef;
    private DatabaseReference databaseRef2;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtProfileName=findViewById(R.id.textView);
        imgProfile=findViewById(R.id.imageView3);
        txtEmail=findViewById(R.id.textView2);
        mRecyclerView=findViewById(R.id.rec);
        btnAdd=findViewById(R.id.floatingActionButton);


        authFirebase = FirebaseAuth.getInstance();
        user = authFirebase.getCurrentUser(); //user in uso
        uidUser=getIntent().getStringExtra("uid"); //user in vista
        emailString=getIntent().getStringExtra("email");

        txtEmail.setText(emailString);

        //bottone
        btnAdd.setVisibility(View.INVISIBLE);

        if(user.getUid().compareTo(uidUser)==0){
            btnAdd.setVisibility(View.VISIBLE);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayCreateClub();
                }
            });
        }


//--------------------------------------------

        authDatabase = FirebaseDatabase.getInstance();

        databaseRef = authDatabase.getReference("users");
        databaseRef2 = authDatabase.getReference("bookClub").child(uidUser);

        bookClub=new ArrayList<>();
        thisProfile=new Profile();

        readDatabase();

    }

    public void readDatabase() {
        Query query = databaseRef.orderByChild("email").equalTo(emailString);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s;int n=1;
                for (DataSnapshot ds : snapshot.getChildren()) {

                    thisProfile=ds.getValue(Profile.class);

                    txtProfileName.setText(thisProfile.getName());

                    if (thisProfile.getImg().equals("R.drawable.ic_anonymous_foreground")) {
//                        Bitmap bitmap = BitmapFactory.decodeResource(MainActivity2.this.getResources(),
//                                R.drawable.ic_anonymous_foreground);
                        imgProfile.setImageResource(R.drawable.ic_anonymous_foreground);
                    } else {
                        byteImage = Base64.decode(thisProfile.getImg(), Base64.DEFAULT);
                        Bitmap decodedImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
                        imgProfile.setImageBitmap(Profile.getclip(decodedImage));
                    }
                  while( !(s=""+ ds.child("bookClub"+n).getValue()).equals("null")){
                        if(n> bookClub.size()){//se il book club non è gia nell'arraylist
                            bookClub.add(s);
                            mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity2.this,3));
                            mAdapterProfile=new AdapterBookClub(bookClub,thisProfile,MainActivity2.this);
                            mRecyclerView.setAdapter(mAdapterProfile);
                        }
                        ++n;}
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(user.getUid().compareTo(uidUser)==0) {
            getMenuInflater().inflate(R.menu.my_menu_activity2, menu);
            return super.onCreateOptionsMenu(menu);
        }
        else
        {
            getMenuInflater().inflate(R.menu.my_menu_empty, menu);
            return super.onCreateOptionsMenu(menu);
        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(user.getUid().compareTo(uidUser)==0) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
            case R.id.searchBook:
                getBooksInfo();
                break;
            case R.id.settings:
                settings();
                break;
            case R.id.cercaProfili:
                getProfilesInfo();
                break;

        }}

        return super.onOptionsItemSelected(item);
    }

    private void settings() {

        LinearLayout linearLayout=new LinearLayout(this);
        EditText edtprofilename=new EditText(this);
        Button imageButton=new Button(this);
        imageButton.setText("Modifica foto");

        edtprofilename.setInputType(InputType.TYPE_CLASS_TEXT);
        edtprofilename.setText(txtProfileName.getText().toString());


        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(edtprofilename);
        linearLayout.addView(imageButton);

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Settings");
        alertDialog.setView(linearLayout);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        alertDialog.setNegativeButton("Save",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                txtProfileName.setText(edtprofilename.getText().toString());
                databaseRef.child(uidUser).child("name").setValue(txtProfileName.getText().toString());
                dialog.dismiss();
            }
        });

        alertDialog.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.create().show();

    }

    @Override
    public void onBackPressed() {//392
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        finish();
    }
    private void selectImage() {
        if (Build.VERSION.SDK_INT < 23) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1000);
        } else if (Build.VERSION.SDK_INT >= 23)
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=  PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);

            } else {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1000);
            }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            selectImage();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK && data != null) {
            Uri chosenImageData = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), chosenImageData);
                imgProfile.setImageBitmap(bitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byteImage = baos.toByteArray();
                String stringImage= Base64.encodeToString(byteImage,Base64.DEFAULT);
                databaseRef.child(uidUser).child("img").setValue(stringImage);


            } catch (Exception e) {

                e.printStackTrace();
            }

        }

    }

    private void displayCreateClub(){

        LinearLayout linearLayout=new LinearLayout(this);
        EditText edtNameClub=new EditText(this);
        EditText edtBook=new EditText(this);

        edtNameClub.setHint("Club nameClub");
        edtBook.setHint("Book nameClub");

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(edtNameClub);
        linearLayout.addView(edtBook);

        AlertDialog.Builder createClub=new AlertDialog.Builder(this);
        createClub.setTitle("Create cluBook!");
        createClub.setView(linearLayout);

        createClub.setNegativeButton("Crea",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (edtNameClub.getText().toString().isEmpty()) {
                    edtNameClub.setError("Please enter club nameClub");
                    return;}
                else if (edtBook.getText().toString().isEmpty()){
                    edtBook.setError("Please enter book nameClub");
                    return;
                }
                else{
                    int n= bookClub.size()+1;
                    DatabaseReference club=databaseRef2.child("bookClub"+n);
                    HashMap <String,String> hashMap=new HashMap<>();
                    hashMap.put("email",user.getEmail());
                    hashMap.put("nameClub",edtNameClub.getText().toString());
                    hashMap.put("book",edtBook.getText().toString());
                    hashMap.put("numberCLub",""+n);
                    hashMap.put("description","");

                    club.setValue(hashMap);

                    databaseRef.child(user.getUid()).child("bookClub"+n).setValue(edtNameClub.getText().toString());


                }
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


    private void getBooksInfo() {

        startActivity(new Intent(this, MainActivityListBook.class));

    }
    private void getProfilesInfo(){

        startActivity(new Intent(this, MainActivitySearchProfile.class));

    }

}