package com.cs426.tripadvisor;

public class Region {
    private String name;
    private float lng;
    private float lat;
    private String city;
    private  String country;

    public Region(String name, float lng, float lat, String city, String country) {
        this.name = name;
        this.lng = lng;
        this.lat = lat;
        this.city = city;
        this.country = country;
    }

    public Region() {
        name = "";
        lng = 0;
        lat = 0;
        city = "";
        country = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
