package com.cs426.tripadvisor;

import android.content.Context;
import android.widget.ArrayAdapter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class XMLParser extends DefaultHandler {

    private Context context;
    private StringBuilder builder;
    private Hotel tmpHotel = null;
    private Park tmpPark = null;
    private Region tmpRegion = null;
    private Restaurant tmpRestaurant = null;
    private Shop tmpShop = null;
    private Theater tmpTheater = null;

    private Hotel.RoomType roomType = null;
    private Restaurant.Dish dish = null;
    private Shop.ProductCategory productCategory = null;



    int documentType = 0; // 0: Hotel, 1: Park, 2: Region, 3: Restaurant, 4: Shop, 5: Theater

    public XMLParser(Context context) {
        this.context = context;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        builder=new StringBuilder();

        switch (localName) {
            case "hotels":
                documentType = 0;
                break;
            case "parks":
                documentType = 1;
                break;
            case "regions":
                documentType = 2;
                break;
            case "restaurants":
                documentType = 3;
                break;
            case "shops":
                documentType = 4;
                break;
            case "theaters":
                documentType = 5;
                break;
            case "hotel":
                tmpHotel = new Hotel(context);
                break;
            case "park":
                tmpPark = new Park(context);
                break;
            case "region":
                tmpRegion = new Region();
                break;
            case "restaurant":
                tmpRestaurant = new Restaurant(context);
                break;
            case "shop":
                tmpShop = new Shop(context);
                break;
            case "theater":
                tmpTheater = new Theater(context);
                break;
            case "room_type":
                roomType = tmpHotel.new RoomType();
                break;
            case "dish":
                dish = tmpRestaurant.new Dish();
                break;
            case "product":
                productCategory = tmpShop.new ProductCategory();
                break;
            default:
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (localName) {
            case "hotel":
                ((MyApplication) context.getApplicationContext()).hotelList.add(tmpHotel);
                break;
            case "park":
                ((MyApplication) context.getApplicationContext()).parkList.add(tmpPark);
                break;
            case "restaurant":
                ((MyApplication) context.getApplicationContext()).restaurantList.add(tmpRestaurant);
                break;
            case "shop":
                ((MyApplication) context.getApplicationContext()).shopList.add(tmpShop);
                break;
            case "theater":
                ((MyApplication) context.getApplicationContext()).theaterList.add(tmpTheater);
                break;
            case "room_type":
                tmpHotel.addRoomType(roomType);
                break;
            case "dish":
                tmpRestaurant.addDish(dish);
                break;
            case "product":
                tmpShop.addProduct(productCategory);
                break;
            case "name":
                if (documentType == 0) {
                    tmpHotel.setName(builder.toString());
                } else if (documentType == 1) {
                    tmpPark.setName(builder.toString());
                } else if (documentType == 2) {
                    tmpRegion.setName(builder.toString());
                } else if (documentType == 3) {
                    tmpRestaurant.setName(builder.toString());
                } else if (documentType == 4) {
                    tmpShop.setName(builder.toString());
                } else if (documentType == 5) {
                    tmpTheater.setName(builder.toString());
                }
                break;
            case "description":
                if (documentType == 0) {
                    tmpHotel.setDescription(builder.toString());
                } else if (documentType == 1) {
                    tmpPark.setDescription(builder.toString());
                } else if (documentType == 3) {
                    tmpRestaurant.setDescription(builder.toString());
                } else if (documentType == 4) {
                    tmpShop.setDescription(builder.toString());
                } else if (documentType == 5) {
                    tmpTheater.setDescription(builder.toString());
                }
                break;
            case "photo_name":
                if (documentType == 0) {
                    tmpHotel.setPhoto(context.getResources().getIdentifier(builder.toString(), "drawable", context.getPackageName()));
                } else if (documentType == 1) {
                    tmpPark.setPhoto(context.getResources().getIdentifier(builder.toString(), "drawable", context.getPackageName()));
                } else if (documentType == 3) {
                    tmpRestaurant.setPhoto(context.getResources().getIdentifier(builder.toString(), "drawable", context.getPackageName()));
                } else if (documentType == 4) {
                    tmpShop.setPhoto(context.getResources().getIdentifier(builder.toString(), "drawable", context.getPackageName()));
                } else if (documentType == 5) {
                    tmpTheater.setPhoto(context.getResources().getIdentifier(builder.toString(), "drawable", context.getPackageName()));
                }
                break;
            case "rating":
                if (documentType == 0) {
                    tmpHotel.setRating(Integer.parseInt(builder.toString()));
                } else if (documentType == 1) {
                    tmpPark.setRating(Integer.parseInt(builder.toString()));
                } else if (documentType == 3) {
                    tmpRestaurant.setRating(Integer.parseInt(builder.toString()));
                } else if (documentType == 4) {
                    tmpShop.setRating(Integer.parseInt(builder.toString()));
                } else if (documentType == 5) {
                    tmpTheater.setRating(Integer.parseInt(builder.toString()));
                }
                break;
            case "address":
                if (documentType == 0) {
                    tmpHotel.setAddress(builder.toString());
                } else if (documentType == 1) {
                    tmpPark.setAddress(builder.toString());
                } else if (documentType == 3) {
                    tmpRestaurant.setAddress(builder.toString());
                } else if (documentType == 4) {
                    tmpShop.setAddress(builder.toString());
                } else if (documentType == 5) {
                    tmpTheater.setAddress(builder.toString());
                }
                break;
            case "phone":
                if (documentType == 0) {
                    tmpHotel.setPhone(builder.toString());
                } else if (documentType == 1) {
                    tmpPark.setPhone(builder.toString());
                } else if (documentType == 3) {
                    tmpRestaurant.setPhone(builder.toString());
                } else if (documentType == 4) {
                    tmpShop.setPhone(builder.toString());
                } else if (documentType == 5) {
                    tmpTheater.setPhone(builder.toString());
                }
                break;
            case "website":
                if (documentType == 0) {
                    tmpHotel.setWebsite(builder.toString());
                } else if (documentType == 1) {
                    tmpPark.setWebsite(builder.toString());
                } else if (documentType == 3) {
                    tmpRestaurant.setWebsite(builder.toString());
                } else if (documentType == 4) {
                    tmpShop.setWebsite(builder.toString());
                } else if (documentType == 5) {
                    tmpTheater.setWebsite(builder.toString());
                }
                break;
            case "open_time":
                if (documentType == 0) {
                    tmpHotel.setOpen_time(builder.toString());
                } else if (documentType == 1) {
                    tmpPark.setOpen_time(builder.toString());
                } else if (documentType == 3) {
                    tmpRestaurant.setOpen_time(builder.toString());
                } else if (documentType == 4) {
                    tmpShop.setOpen_time(builder.toString());
                } else if (documentType == 5) {
                    tmpTheater.setOpen_time(builder.toString());
                }
                break;
            case "close_time":
                if (documentType == 0) {
                    tmpHotel.setClose_time(builder.toString());
                } else if (documentType == 1) {
                    tmpPark.setClose_time(builder.toString());
                } else if (documentType == 3) {
                    tmpRestaurant.setClose_time(builder.toString());
                } else if (documentType == 4) {
                    tmpShop.setClose_time(builder.toString());
                } else if (documentType == 5) {
                    tmpTheater.setClose_time(builder.toString());
                }
                break;
            case "region":
                if (documentType == 0) {
                    tmpHotel.setRegion(builder.toString());
                } else if (documentType == 1) {
                    tmpPark.setRegion(builder.toString());
                } else if(documentType == 2) {
                    ((MyApplication) context.getApplicationContext()).regionList.add(tmpRegion);
                } else if (documentType == 3) {
                    tmpRestaurant.setRegion(builder.toString());
                } else if (documentType == 4) {
                    tmpShop.setRegion(builder.toString());
                } else if (documentType == 5) {
                    tmpTheater.setRegion(builder.toString());
                }
                break;
            case "long":
                if (documentType == 0) {
                    tmpHotel.setLng(Float.parseFloat(builder.toString()));
                } else if (documentType == 1) {
                    tmpPark.setLng(Float.parseFloat(builder.toString()));
                } else if (documentType == 3) {
                    tmpRestaurant.setLng(Float.parseFloat(builder.toString()));
                } else if (documentType == 4) {
                    tmpShop.setLng(Float.parseFloat(builder.toString()));
                } else if (documentType == 5) {
                    tmpTheater.setLng(Float.parseFloat(builder.toString()));
                }
                break;
            case "lat":
                if (documentType == 0) {
                    tmpHotel.setLat(Float.parseFloat(builder.toString()));
                } else if (documentType == 1) {
                    tmpPark.setLat(Float.parseFloat(builder.toString()));
                } else if (documentType == 3) {
                    tmpRestaurant.setLat(Float.parseFloat(builder.toString()));
                } else if (documentType == 4) {
                    tmpShop.setLat(Float.parseFloat(builder.toString()));
                } else if (documentType == 5) {
                    tmpTheater.setLat(Float.parseFloat(builder.toString()));
                }
                break;
            case "rname":
                roomType.setName(builder.toString());
                break;
            case "info":
                roomType.setInfo(builder.toString());
                break;
            case "desc":
                if (documentType == 0) {
                    roomType.setDesc(builder.toString());
                } else if (documentType == 4) {
                    productCategory.setDesc(builder.toString());
                }
                break;
            case "photo":
                if (documentType == 0) {
                    roomType.setPhoto(context.getResources().getIdentifier(builder.toString(), "drawable", context.getPackageName()));
                } else if (documentType == 3) {
                    dish.setPhoto(context.getResources().getIdentifier(builder.toString(), "drawable", context.getPackageName()));
                } else if (documentType == 4) {
                    productCategory.setPhoto(context.getResources().getIdentifier(builder.toString(), "drawable", context.getPackageName()));
                }
                break;
            case "price":
                if (documentType == 0) {
                    roomType.setPrice(Float.parseFloat(builder.toString()));
                } else if (documentType == 3) {
                    dish.setPrice(Float.parseFloat(builder.toString()));
                }
                break;
            case "dish_name":
                dish.setName(builder.toString());
                break;
            case "des":
                dish.setDescription(builder.toString());
                break;
            case "pname":
                productCategory.setName(builder.toString());
                break;
            case "longtitude":
                tmpRegion.setLng(Float.parseFloat(builder.toString()));
                break;
            case "latitude":
                tmpRegion.setLat(Float.parseFloat(builder.toString()));
                break;
            case "city":
                tmpRegion.setCity(builder.toString());
                break;
            case "country":
                tmpRegion.setCountry(builder.toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        String tempString = new String(ch, start, length);
        builder.append(tempString);
    }
}
