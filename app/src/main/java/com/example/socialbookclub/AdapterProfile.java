package com.example.socialbookclub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterProfile extends RecyclerView.Adapter<ViewHolderProfileList>{

    private final ArrayList <Profile> profileArrayList;
    private final Context context;


    public AdapterProfile(ArrayList<Profile> profileArrayList, Context context) {
        this.profileArrayList = profileArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderProfileList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        //ora noi dobbiamo usare layout inflator per gonfiare viewholder su recycleview
        View view=layoutInflater.inflate(R.layout.listprofile_viewholder,parent,false);
        return new ViewHolderProfileList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProfileList holder, int position) {

        holder.name.setText(profileArrayList.get(position).getName());
        holder.name.setTextColor(Color.BLACK);

        holder.email.setText(profileArrayList.get(position).getEmail());

        String imgString=profileArrayList.get(position).getImg();
        if(imgString.equals("R.drawable.ic_anonymous_foreground")){
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.ic_anonymous_foreground);
            holder.imgProfile.setImageBitmap(Profile.getclip(bitmap));

        }
        else{
            //decode base64 string to image
            byte[] byteImage= Base64.decode(imgString, Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
            holder.imgProfile.setImageBitmap(Profile.getclip(decodedImage));}

        holder.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context,MainActivity2.class);
                i.putExtra("email", profileArrayList.get(position).getEmail());
                i.putExtra("uid",profileArrayList.get(position).getUid());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return profileArrayList.size();
    }
}

