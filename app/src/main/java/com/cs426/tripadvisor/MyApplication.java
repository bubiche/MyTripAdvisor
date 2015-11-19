package com.cs426.tripadvisor;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {

    public ArrayList<Park> parkList = null;
    public ArrayList<Hotel> hotelList = null;
    public ArrayList<Region> regionList = null;
    public ArrayList<Restaurant> restaurantList = null;
    public ArrayList<Shop> shopList = null;
    public ArrayList<Theater> theaterList = null;
    public ArrayList<String> fav_list = null;

    public MyApplication() {
        parkList = new ArrayList<Park>();
        hotelList = new ArrayList<Hotel>();
        regionList = new ArrayList<Region>();
        restaurantList = new ArrayList<Restaurant>();
        shopList = new ArrayList<Shop>();
        theaterList = new ArrayList<Theater>();
        fav_list = new ArrayList<String>();
    }
}