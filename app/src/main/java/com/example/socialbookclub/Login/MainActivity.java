package com.example.socialbookclub.Login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.socialbookclub.MainActivity2;
import com.example.socialbookclub.Profile;
import com.example.socialbookclub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    private EditText edtEmail,edtPassword;
    private Button btnSignUp,btnSignIn;
    private ImageButton back;
    private FirebaseAuth mAuth;
    private String password, email;
    private Profile thisProfile;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        mRelativeLayout=new RelativeLayout(this);
        edtEmail=new EditText(this);
        edtPassword=new EditText(this);
        btnSignUp=new Button(this);
        btnSignIn=new Button(this);
        back=new ImageButton(this);

        Point screenSize=new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        int screenWidth=screenSize.x;
        //email
        RelativeLayout.LayoutParams paramsEmail=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsEmail.setMargins(107,500,107,0);
        edtEmail.setId(View.generateViewId());
        edtEmail.getBackground().mutate().setColorFilter(getResources().
                getColor(android.R.color.holo_purple), PorterDuff.Mode.SRC_ATOP);
        edtEmail.setHint("Inserire email");
        edtEmail.setTextCursorDrawable(R.drawable.ic_cursor2);
        mRelativeLayout.addView(edtEmail,paramsEmail);
        edtEmail.setVisibility(View.INVISIBLE);

        //password
        RelativeLayout.LayoutParams paramsPassword=new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsPassword.addRule(RelativeLayout.BELOW,edtEmail.getId());
        paramsPassword.setMargins(107,100,107,0);
        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edtPassword.setId(View.generateViewId());
        edtPassword.setHint("Inserire password");
        edtPassword.getBackground().mutate().setColorFilter(getResources().
                getColor(android.R.color.holo_purple), PorterDuff.Mode.SRC_ATOP);
        edtPassword.setTextCursorDrawable(R.drawable.ic_cursor2);

        mRelativeLayout.addView(edtPassword,paramsPassword);
        edtPassword.setVisibility(View.INVISIBLE);

        //bottoni
        RelativeLayout.LayoutParams paramsBottonIn=new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsBottonIn.addRule(RelativeLayout.BELOW,edtPassword.getId());
        paramsBottonIn.addRule(RelativeLayout.CENTER_HORIZONTAL);
        btnSignIn.setText("Login");
        btnSignIn.setId(View.generateViewId());
        btnSignIn.setBackgroundColor(Color.parseColor("#FF6200EE"));
        btnSignIn.setTextColor(Color.WHITE);
        mRelativeLayout.addView(btnSignIn,paramsBottonIn);

        RelativeLayout.LayoutParams paramsBottonUp=new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsBottonUp.addRule(RelativeLayout.BELOW,btnSignIn.getId());
        paramsBottonUp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        btnSignUp.setText("Register");
        btnSignUp.setTextColor(Color.WHITE);
        btnSignUp.setBackgroundColor(Color.parseColor("#FF6200EE"));
        mRelativeLayout.addView(btnSignUp,paramsBottonUp);

        back.setImageResource(android.R.drawable.ic_menu_revert);
        RelativeLayout.LayoutParams paramsBottnBack=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsBottnBack.addRule(RelativeLayout.ALIGN_PARENT_START);
        paramsBottnBack.setMargins(20,5,10,10);
        mRelativeLayout.addView(back,paramsBottnBack);
        back.setVisibility(View.INVISIBLE);
        back.setBackgroundColor(Color.WHITE);

        RelativeLayout.LayoutParams paramsLayout=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(mRelativeLayout,paramsLayout);

        thisProfile=new Profile();
        thisProfile.setName("InserireNome");
        thisProfile.setImg("R.drawable.ic_anonymous_foreground");
        thisProfile.setEmail("no");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setVisibility(View.INVISIBLE);
                edtPassword.setText("");
                edtEmail.setText("");
                edtEmail.setVisibility(View.INVISIBLE);
                edtPassword.setVisibility(View.INVISIBLE);
                btnSignIn.setVisibility(View.VISIBLE);
                btnSignUp.setVisibility(View.VISIBLE);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                back.setVisibility(View.VISIBLE);
                btnSignIn.setVisibility(View.INVISIBLE);
                edtEmail.setVisibility(View.VISIBLE);
                edtPassword.setVisibility(View.VISIBLE);
                signUp();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setVisibility(View.VISIBLE);
                btnSignUp.setVisibility(View.INVISIBLE);
                edtEmail.setVisibility(View.VISIBLE);
                edtPassword.setVisibility(View.VISIBLE);
                signIn();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser= mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }

    }

    private void signUp(){

        email=edtEmail.getText().toString();
        password=edtPassword.getText().toString();


        if(email.isEmpty() || password.isEmpty()){

            Toast.makeText(MainActivity.this, "Riempiere campi",
                    Toast.LENGTH_SHORT).show();

        }
        else{
            mAuth=FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                if(task.getResult().getAdditionalUserInfo().isNewUser()){

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                                    HashMap<Object,String> hashMap= new HashMap<>();
                                    hashMap.put("email",email);
                                    hashMap.put("uid",user.getUid());
                                    hashMap.put("name",thisProfile.getName());
                                    hashMap.put("img",thisProfile.getImg());

                                    DatabaseReference myRef = database.getReference("users");
                                    myRef.child(user.getUid()).setValue(hashMap);


                                }
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.Password should be at least 6 characters",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }

    }

    private void updateUI(FirebaseUser user) {

        if(user!=null){
            Intent newWindow=new Intent(MainActivity.this, MainActivity2.class);
            newWindow.putExtra("uid",user.getUid());
            newWindow.putExtra("email",user.getEmail());
            startActivity(newWindow);}
        else{

            Toast.makeText(MainActivity.this,"Try again!",Toast.LENGTH_LONG).show();

        }


    }

    private void signIn(){
        email=edtEmail.getText().toString();
        password=edtPassword.getText().toString();


        if(email.isEmpty() || password.isEmpty()){

            Toast.makeText(MainActivity.this, "Riempiere campi",
                    Toast.LENGTH_SHORT).show();

        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });

        }
    }

}