package com.example.socialbookclub.Library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivityBook extends AppCompatActivity {

    String title, subtitle, publisher, publishedDate, description, thumbnail, previewLink, infoLink;
    int pageCount;
    private ArrayList<String> authors;
    private LinearLayout mLinearLayout;
    ScrollView mScrollView;
    TextView txttitle, txtsubtitle, txtpublisher, txtdesc, txtpage, txtpublishDate, txtauthors;
    Button previewBtn, buyBtn;
    private ImageView imgbook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLinearLayout=new LinearLayout(this);
        txttitle=new TextView(this);
        txtsubtitle=new TextView(this);
        txtpublisher=new TextView(this);
        txtdesc=new TextView(this);
        txtpage=new TextView(this);
        txtpublishDate=new TextView(this);
        previewBtn=new Button(this);
        imgbook=new ImageView(this);
        txtauthors=new TextView(this);
        authors=new ArrayList<String>();
        mScrollView=new ScrollView(this);

        mLinearLayout.setOrientation(LinearLayout.VERTICAL);

        title = getIntent().getStringExtra("title");
        subtitle = getIntent().getStringExtra("subtitle");
        publisher = getIntent().getStringExtra("publisher");
        publishedDate = getIntent().getStringExtra("publishedDate");
        description = getIntent().getStringExtra("description");
        pageCount = getIntent().getIntExtra("pageCount", 0);
        thumbnail = getIntent().getStringExtra("thumbnail");
        previewLink = getIntent().getStringExtra("previewLink");
        infoLink = getIntent().getStringExtra("infoLink");
        authors=getIntent().getStringArrayListExtra("authors");

        txtauthors.setText("Authors: ");
        for(int i=0;i< authors.size();++i) {
            if(i!=authors.size()-1)
                txtauthors.append(authors.get(i) + ", ");
            else
                txtauthors.append(authors.get(i));
        }
        txtauthors.setTextColor(Color.BLACK);
        txttitle.setText(title);
        txttitle.setTextColor((Color.parseColor("#FFC107")));
        txttitle.setTextSize(20);
        txttitle.setGravity(Gravity.CENTER_HORIZONTAL);
        txtsubtitle.setText(subtitle);
        txtsubtitle.setGravity(Gravity.CENTER_HORIZONTAL);
        txtpublisher.setText(publisher);
        txtpublishDate.setText("Published On : " + publishedDate);
        txtpublishDate.setTextColor(Color.BLACK);
        txtdesc.setText(description);
        txtdesc.setTextColor(Color.BLACK);
        txtdesc.isVerticalScrollBarEnabled();
        LinearLayout.LayoutParams paramsdesc=new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsdesc.setMargins(20,20,20,20);
        txtpage.setText("No Of Pages : " + pageCount);
        txtpage.setTextColor(Color.BLACK);
        LinearLayout.LayoutParams paramsimg=new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,800);
        paramsimg.setMargins(20,20,20,20);
        imgbook.setBackgroundColor(Color.parseColor("#FFC107"));
        Picasso.get().load(thumbnail).into(imgbook);

        // adding on click listener for our preview button.
        previewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (previewLink.isEmpty()) {
                    // below toast message is displayed when preview link is not present.
                    Toast.makeText(MainActivityBook.this, "No preview Link present", Toast.LENGTH_SHORT).show();
                    return;
                }
                // if the link is present we are opening
                // that link via an intent.
                Uri uri = Uri.parse(previewLink);
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });
        mLinearLayout.addView(txttitle);
        mLinearLayout.addView(txtsubtitle);
        mLinearLayout.addView(imgbook,paramsimg);
        mLinearLayout.addView(txtauthors);
        mLinearLayout.addView(txtpublisher);
        mLinearLayout.addView(txtpage);
        mLinearLayout.addView(txtpublishDate);
        mLinearLayout.addView(txtdesc,paramsdesc);
        mScrollView.addView(mLinearLayout);
        setContentView(mScrollView);
    }
}