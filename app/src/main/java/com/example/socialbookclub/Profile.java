package com.example.socialbookclub;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

public class Profile {

    private String uid;
    private String name;
    private String email;
    private String img;
//    private ArrayList <BookClub> bookClubArray;


    public Profile(){
    }

    public Profile(String uid){

        this.uid =uid;
//        this.bookClubArray=new ArrayList<>();

    }

    public Profile(String name, String img,String uid,String email) {
        this.name = name;
        this.email=email;
        this.uid=uid;
        this.img = img;
//
    }

    public Profile(String name,  String img,String uid) {
        this.name = name;
        this.img = img;
        this.uid = uid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUid() {
        return uid;
    }



    public void setUid(String uid) {
        this.uid = uid;
    }
//    public ArrayList<BookClub> getBookClub() {
//        return bookClubArray;
//    }
//
//    public void setBookClub(ArrayList <BookClub> bookClubArray) {
//        this.bookClubArray=bookClubArray;
//    }

    public static Bitmap getclip(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        // paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2,
                bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }



}
