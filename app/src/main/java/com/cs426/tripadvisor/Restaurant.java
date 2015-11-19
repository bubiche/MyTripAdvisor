package com.cs426.tripadvisor;

import android.content.Context;

import java.util.ArrayList;

/*
        <name>King BBQ</name>
        <description>khong phai o parkson</description>
        <photo_name>hinh</photo_name>
        <rating>5</rating>
        <address>Hung Vuong Square</address>
        <phone>123456789</phone>
        <website>http://tera.enmasse.com/</website>
        <open_time>7:00</open_time>
        <close_time>21:00</close_time>
        <region>Kanto</region>
        <menu>
            <dish>
                <dish_name>Com</dish_name>
                <des>description</des>
                <photo>tenHinh</photo>
                <price>100</price>
            </dish>

            <dish>
                <dish_name>Canh</dish_name>
                <des>description</des>
                <photo>tenHinh</photo>
                <price>200</price>
            </dish>
        </menu>
 */
public class Restaurant {
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
    private ArrayList<Dish> menu;
    private boolean favorite = false;

    public void addDish(Dish dish) {
        menu.add(dish);
    }

    public class Dish {
        private String name;
        private String description;
        private int photo;
        private float price;

        public Dish() {
            name = "";
            description = "";
            price = 0;
        }
        public Dish(String name, String description, String photo, float price) {
            this.name = name;
            this.description = description;
            this.photo = context.getResources().getIdentifier(photo, "drawable", context.getPackageName());
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getPhoto() {
            return photo;
        }

        public float getPrice() {
            return price;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setPhoto(int photo) {
            this.photo = photo;
        }

        public void setPrice(float price) {
            this.price = price;
        }
    }

    public Restaurant(Context context) {
        this.context = context;
        this.name = "";
        this.description = "";
        this.address = "";
        this.phone = "";
        this.website = "";
        this.open_time = "";
        this.close_time = "";
        this.region = "";
        this.lng = 0;
        this.lat = 0;
        this.menu = new ArrayList<Dish>();
    }

    public Restaurant(Context context, String name, String description, String photo, int rating, String address, String phone, String website, String open_time, String close_time, String region, float lng, float lat, ArrayList<Dish> menu) {
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
        this.menu = menu;
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

    public ArrayList<Dish> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Dish> menu) {
        this.menu = menu;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
