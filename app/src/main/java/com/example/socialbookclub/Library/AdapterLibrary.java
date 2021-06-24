package com.example.socialbookclub.Library;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialbookclub.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterLibrary extends RecyclerView.Adapter <ViewHolderLibrary> {

    private final ArrayList<Book> bookArrayList;
    private final Context context;
    public AdapterLibrary(ArrayList<Book> bookInfoArrayList, Context context) {

        this.bookArrayList = bookInfoArrayList;
        this.context=context;

    }

    @NonNull
    @Override
    public ViewHolderLibrary onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //parent si riferisce alla activity che recyclerview dovrà mostrare
        //metodo che crea e restituisce la nostra viewHolder.  Il metodo crea e inizializza il ViewHoldere i suoi associati View,
        // ma non riempie il contenuto della vista: ViewHolder non è stato ancora associato a dati specifici.
        // Create a new view, which defines the UI of the list item
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        //ora noi dobbiamo usare layout inflator per gonfiare viewholder su recycleview
        View view=layoutInflater.inflate(R.layout.library_viewholder,parent,false);
        return new ViewHolderLibrary(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderLibrary holder, int position) {
        //Metodo per associare a ViewHolder i dati. Il metodo recupera i dati appropriati e
        // utilizza i dati per compilare il layout del titolare della visualizzazione.

        // Ottieni l'elemento dal tuo set di dati in questa posizione
        // e sostituisci il contenuto della vista con quell'elemento

        //PARAMETRI: reference a VIewHolder
        //per legare i dati al recycler view e lasciare che che il recycleview mostri specifici dati
        //che noi prendiamo dal database
        Book bookInfo = bookArrayList.get(position);
        holder.title.setText(bookInfo.getTitle());
        holder.publisher.setText(bookInfo.getPublisher());
        holder.pageCount.setText("No of Pages : " + bookInfo.getPageCount());
        holder.date.setText(bookInfo.getPublishedDate());

        holder.imgbook.setBackgroundColor(Color.GRAY);
        // below line is use to set image from URL in our image view.
        if(bookInfo.getThumbnail().length()>0){
            Picasso.get().load(bookInfo.getThumbnail()).fit().error(R.drawable.ic_book_foreground).into(holder.imgbook);
        }
        else
            holder.imgbook.setImageResource(R.drawable.ic_book_foreground);

        // below line is use to add on click listener for our item of recycler view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inside on click listener method we are calling a new activity
                // and passing all the data of that item in next intent.
                Intent i = new Intent(context, MainActivityBook.class);
                i.putExtra("title", bookInfo.getTitle());
                i.putExtra("subtitle", bookInfo.getSubtitle());
                i.putExtra("authors", bookInfo.getAuthors());
                i.putExtra("publisher", bookInfo.getPublisher());
                i.putExtra("publishedDate", bookInfo.getPublishedDate());
                i.putExtra("description", bookInfo.getDescription());
                i.putExtra("pageCount", bookInfo.getPageCount());
                i.putExtra("thumbnail", bookInfo.getThumbnail());
                i.putExtra("previewLink", bookInfo.getPreviewLink());
                i.putExtra("infoLink", bookInfo.getInfoLink());
                i.putExtra("buyLink", bookInfo.getBuyLink());

                // after passing that data we are
                // starting our new  intent.
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }

}
