package com.example.socialbookclub.BookClub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialbookclub.MainActivity2;
import com.example.socialbookclub.Profile;
import com.example.socialbookclub.R;

import java.util.ArrayList;

public class AdapterComment extends RecyclerView.Adapter<ViewHolderComment>{

    private final ArrayList<String> commentArray;
    private final Context context;
    private final  ArrayList<Profile> mProfile;

    public AdapterComment(ArrayList<String> commentArray, Context context,ArrayList<Profile> mProfile) {
        this.commentArray = commentArray;
        this.context = context;
        this.mProfile=mProfile;
    }

    @NonNull
    @Override
    public ViewHolderComment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        //ora noi dobbiamo usare layout inflator per gonfiare viewholder su recycleview
        View view=layoutInflater.inflate(R.layout.comment_viewholder,parent,false);
        return new ViewHolderComment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderComment holder, int position) {
        holder.txt.setText(mProfile.get(position).getName());
        holder.txt2.setText(commentArray.get(position));

        if(mProfile.get(position).getImg().equals("R.drawable.ic_anonymous_foreground")){
            holder.img.setImageResource(R.drawable.ic_anonymous_foreground);
        }
        else{
            //decode base64 string to image
            byte[] byteImage = Base64.decode(mProfile.get(position).getImg(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
            holder.img.setImageBitmap(Profile.getclip(decodedImage));}


        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context, MainActivity2.class);
                i.putExtra("name", mProfile.get(position).getName());
                i.putExtra("uid",mProfile.get(position).getUid());
                i.putExtra("email",mProfile.get(position).getEmail());
                i.putExtra("img",mProfile.get(position).getImg());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentArray.size();
    }

}
