package com.example.socialbookclub.BookClub;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialbookclub.R;

public class ViewHolderBookClub extends RecyclerView.ViewHolder {

   Button mButton;

    public ViewHolderBookClub(@NonNull View itemView) {
        super(itemView);
        mButton=itemView.findViewById(R.id.button2);

    }
}
