package com.example.socialbookclub.BookClub;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialbookclub.Profile;
import com.example.socialbookclub.R;

import java.util.ArrayList;

public class AdapterBookClub extends RecyclerView.Adapter<ViewHolderBookClub>{

    private final ArrayList<String> bookClubArrayList;
    private final Context context;
    private String email;
    private Profile mProfile;

    public AdapterBookClub(ArrayList<String> bookClubArrayList,Profile mprofile, Context context) {
        this.bookClubArrayList = bookClubArrayList;
        this.context = context;
        this.email=email;
        this.mProfile=mprofile;
    }

    @NonNull
    @Override
    public ViewHolderBookClub onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        //ora noi dobbiamo usare layout inflator per gonfiare viewholder su recycleview
        View view=layoutInflater.inflate(R.layout.bookclub_viewholder,parent,false);
        return new ViewHolderBookClub(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderBookClub holder, int position) {

        holder.mButton.setText(bookClubArrayList.get(position));
        holder.mButton.setTextColor(Color.BLACK);


        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context,MainActivityGroup.class);
                //in mProfile ci sono le informazioni dell'user in vista
                i.putExtra("title", bookClubArrayList.get(position));
                i.putExtra("uid",mProfile.getUid());
                i.putExtra("author",mProfile.getName());
                i.putExtra("n",position);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookClubArrayList.size();
    }


}

