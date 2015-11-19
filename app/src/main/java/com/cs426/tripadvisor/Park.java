package com.cs426.tripadvisor;

import android.content.Context;

public class Park {
    Context context;
    private String name;
    private String description;
    private int photo;
    private int rating;
    private String address;
    private String phone;
    private String website;
    private String open_time;
    private String close_time;
    private String region;
    private float lng;
    private float lat;
    private float ticket_price;
    private boolean favorite = false;

    public Park(Context context) {
        this.context = context;
        name = "";
        description = "";
        address = "";
        phone = "";
        website = "";
        open_time = "";
        close_time = "";
        region= "";
        lng = 0;
        lat = 0;
        ticket_price = 0;
    }

    public Park(Context context, String name, String description, String photo, int rating, String address, String phone, String website, String open_time, String close_time, String region, float lng, float lat, float ticket_price) {
        this.context = context;
        this.name = name;
        this.description = description;
        this.photo = context.getResources().getIdentifier(photo, "drawable", context.getPackageName());
        this.rating = rating;
        this.address = address;
        this.phone = phone;
        this.website = website;
        this.open_time = open_time;
        this.close_time = close_time;
        this.region = region;
        this.lng = lng;
        this.lat = lat;
        this.ticket_price = ticket_price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(float ticket_price) {
        this.ticket_price = ticket_price;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
