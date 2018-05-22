package com.example.eliotpcw.cleaningproject.Model;

/**
 * Created by eliotpcw on 11.04.2018.
 */

public class Orders {
    private String Title;
    private String Location;
    private String Date;
    private String Time;
    private String SquareM;
    private String Price;
    private String UserPhoneNumber;
    private String ImageUrl;

    public Orders(String title, String location, String date, String time, String squareM, String price, String userPhoneNumber, String imageUrl) {
        Title = title;
        Location = location;
        Date = date;
        Time = time;
        SquareM = squareM;
        Price = price;
        UserPhoneNumber = userPhoneNumber;
        ImageUrl = imageUrl;
    }

    public Orders(){}

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getUserPhoneNumber() {
        return UserPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        UserPhoneNumber = userPhoneNumber;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getSquareM() {
        return SquareM;
    }

    public void setSquareM(String squareM) {
        SquareM = squareM;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}