package com.example.socialbookclub.Library;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialbookclub.R;

public class ViewHolderLibrary extends RecyclerView.ViewHolder {
    //definsce come appaionogli elementi nella lista cioè in  recycleview, cioè definisce ggli elementi che la compongono:
    //testi , numeri, immagini , ecc..

    TextView title, publisher, pageCount, date;
    ImageView imgbook;

    public ViewHolderLibrary(@NonNull View itemView) {
        super(itemView);
        title= itemView.findViewById(R.id.txtTitle);
        publisher= itemView.findViewById(R.id.txtPublisher);
        pageCount = itemView.findViewById(R.id.txtPageCount);
        date = itemView.findViewById(R.id.txtDate);
        imgbook = itemView.findViewById(R.id.imgbook);
    }

}
