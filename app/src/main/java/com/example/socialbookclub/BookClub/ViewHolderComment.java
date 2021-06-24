package com.example.socialbookclub.BookClub;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialbookclub.R;

public class ViewHolderComment extends RecyclerView.ViewHolder{

     ImageButton img;
     TextView txt;
     TextView txt2;

    public ViewHolderComment(@NonNull View itemView) {
        super(itemView);

        img=itemView.findViewById(R.id.imageButton);
        txt=itemView.findViewById(R.id.textView4);
        txt2=itemView.findViewById(R.id.textView5);



    }
}
