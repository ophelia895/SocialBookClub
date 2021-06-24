package com.example.socialbookclub.BookClub;

import java.util.ArrayList;

public class BookClub {

//    private Profile profile;
    private String book;
    private String numberCLub;
    private String nameClub;
    private int ncomments;
    private String description;

    public BookClub(){

        ncomments=0;

    }

    public BookClub( String book, String nameClub, String numberCLub) {
//        this.profile = profile;
        this.book = book;
        this.nameClub = nameClub;
        this.numberCLub=numberCLub;
        ncomments=0;
    }

    public BookClub( String nameClub, String numberCLub) {
//        this.profile = profile;
        this.nameClub = nameClub;
        this.numberCLub=numberCLub;
        ncomments=0;

    }


    public String getNumberCLub() {
        return numberCLub;
    }

    public void setNumberCLub(String numberCLub) {
        this.numberCLub = numberCLub;
    }

//    public Profile getProfile() {
//        return profile;
//    }
//
//    public void setProfile(Profile profile) {
//        this.profile = profile;
//    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getNameClub() {
        return nameClub;
    }

    public void setNameClub(String nameClub) {
        this.nameClub = nameClub;
    }

    public int getNcomments() {
        return ncomments;
    }

    public void setNcomments(int ncomments) {
        this.ncomments = ncomments;
    }
    public void addComments (){ this.ncomments+=1;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
