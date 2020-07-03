package com.example.mymedseller10;

import android.net.Uri;

public class medicineitem {
    private String Date;
    private String ids;
    private String Details;
    private String userid;
    private String Address;
    private String name;
    private String contact;
    private String state;
    private int Price;
    private Uri imgs;
    private String location;
    private String imgUri;
    private String bill;

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public medicineitem(){}

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
    public medicineitem(String Date,String ids,String Details, String userid, String Address, String name, String contact, String state, int Price, Uri imgs,String bill)
{
    this.ids=ids;
    this.Date=Date;
    this.Details=Details;
    this.userid=userid;
    this.Address=Address;
    this.name=name;
    this.contact=contact;
    this.state=state;
    this.Price=Price;
  this.imgs=imgs;
  this.bill=bill;
}
    public Uri getImgs() {
        return imgs;
    }

    public void setImgs(Uri imgs) {
        this.imgs = imgs;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
}
