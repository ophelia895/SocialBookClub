package com.example.socialbookclub;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderProfileList extends RecyclerView.ViewHolder {
    //definsce come appaionogli elementi nella lista cioè in  recycleview, cioè definisce ggli elementi che la compongono:
    //testi , numeri, immagini , ecc..

    TextView name, email;
    ImageView imgProfile;

    public ViewHolderProfileList(@NonNull View itemView) {
        super(itemView);
        name= itemView.findViewById(R.id.txtNameProfileList);
        email= itemView.findViewById(R.id.txtemailProfileList);
        imgProfile = itemView.findViewById(R.id.imgProfile);
    }

}