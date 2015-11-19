package com.cs426.tripadvisor;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appdatasearch.GetRecentContextCall;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;


public class PlacePickerActivity extends Activity {

    private static final int PLACE_PICKER_REQUEST = 1;
    private TextView name = null;
    private TextView desc = null;
    private RatingBar ratingBar = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_place_picker);

        name = (TextView) findViewById(R.id.place_name_tv);
        desc = (TextView) findViewById(R.id.place_desc_tv);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);

        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            Context context = getApplicationContext();
            startActivityForResult(builder.build(context), PLACE_PICKER_REQUEST);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                showInfo(place);
            }
        }
    }

    public void showInfo(Place place) {
        String sName = "This place has no name";
        try {
            sName = place.getName().toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        name.setText(sName);
        String sAddress = "Address not found";
        String sPhone = "Phone not found";
        String sWeb = "No website";
        String sPrice = "Price level not found";
        float rate = 3;

        try {
            sAddress = place.getAddress().toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sPhone = place.getPhoneNumber().toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sWeb = place.getWebsiteUri().toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sPrice = String.valueOf(place.getPriceLevel());
        }
        catch (Exception e) {
            e.printStackTrace();
        }



        try {
            rate = place.getRating();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        desc.setText("Address: " + sAddress + "\n" +
                        "Phone: " + sPhone + "\n" +
                        "Website: " + sWeb + "\n" +
                        "Price level: " + sPrice + "\n");

        ratingBar.setRating(rate);
    }


}
