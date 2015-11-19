package com.cs426.tripadvisor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;


public class WelcomeActivity extends Activity {
    private static final String fav_file = "favorites_list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);


        ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        shareButton.setShareContent( new ShareLinkContent.Builder()
                .setContentTitle("Trip Advisor")
                .setContentDescription(
                        "Trip Advisor is so great!")
                .setContentUrl(Uri.parse("http://courses.fit.hcmus.edu.vn/atp/"))
                .build());

        try {
            InputSource is = new InputSource(getResources().openRawResource(R.raw.shops));
            XMLParser parser = new XMLParser(this);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser sp = factory.newSAXParser();
            XMLReader reader = sp.getXMLReader();
            reader.setContentHandler(parser);
            reader.parse(is);
        }
        catch(Exception e) {
            Log.i("XML ERROR", "Something is wrong when reading xml files");
        }

        try {
            InputSource is = new InputSource(getResources().openRawResource(R.raw.hotels));
            XMLParser parser = new XMLParser(this);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser sp = factory.newSAXParser();
            XMLReader reader = sp.getXMLReader();
            reader.setContentHandler(parser);
            reader.parse(is);

        }
        catch(Exception e) {
            Log.i("XML ERROR", "Something is wrong when reading xml files");
        }

        try {
            InputSource is = new InputSource(getResources().openRawResource(R.raw.regions));
            XMLParser parser = new XMLParser(this);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser sp = factory.newSAXParser();
            XMLReader reader = sp.getXMLReader();
            reader.setContentHandler(parser);
            reader.parse(is);
        }
        catch(Exception e) {
            Log.i("XML ERROR", "Something is wrong when reading xml files");
        }

        try {
            InputSource is = new InputSource(getResources().openRawResource(R.raw.parks));
            XMLParser parser = new XMLParser(this);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser sp = factory.newSAXParser();
            XMLReader reader = sp.getXMLReader();
            reader.setContentHandler(parser);
            reader.parse(is);

        }
        catch(Exception e) {
            Log.i("XML ERROR", "Something is wrong when reading xml files");
        }

        try {
            InputSource is = new InputSource(getResources().openRawResource(R.raw.theater));
            XMLParser parser = new XMLParser(this);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser sp = factory.newSAXParser();
            XMLReader reader = sp.getXMLReader();
            reader.setContentHandler(parser);
            reader.parse(is);
        }
        catch(Exception e) {
            Log.i("XML ERROR", "Something is wrong when reading xml files");
        }

        try {
            InputSource is = new InputSource(getResources().openRawResource(R.raw.restaurants));
            XMLParser parser = new XMLParser(this);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser sp = factory.newSAXParser();
            XMLReader reader = sp.getXMLReader();
            reader.setContentHandler(parser);
            reader.parse(is);
        }
        catch(Exception e) {
            Log.i("XML ERROR", "Something is wrong when reading xml files");
        }

        File file = getBaseContext().getFileStreamPath(fav_file);
        if(!file.exists()) {
            try {
                PrintStream fout = new PrintStream(openFileOutput(fav_file, Context.MODE_PRIVATE));
                fout.println("dummy");
                fout.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Scanner scan = new Scanner(openFileInput(fav_file));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                Log.i("READ FILE", "READ: " + line);
                ((MyApplication) getApplicationContext()).fav_list.add(line);
            }
            scan.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        initFavorite();
    }

    public void onClickFindByType(View view) {
        Intent intent = new Intent(this, CategoryListActivity.class);
        startActivity(intent);
    }

    public void onClickNearMe(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void onClickRegion(View view) {
        Intent intent = new Intent(this, RegionListActivity.class);
        startActivity(intent);
    }

    public void onClickPlacePicker(View view) {
        Intent intent = new Intent(this, PlacePickerActivity.class);
        startActivity(intent);
    }

    public void onClickFavorite(View view) {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

    public void onClickAddPlace(View view) {
        Intent intent = new Intent(this, PlaceAddActivity.class);
        startActivity(intent);
    }

    public void initFavorite() {
        for(int i = 1; i < ((MyApplication) getApplicationContext()).fav_list.size(); ++i) {
            int type = Character.getNumericValue(((MyApplication) getApplicationContext()).fav_list.get(i).charAt(0));
            int index = Integer.parseInt(((MyApplication) getApplicationContext()).fav_list.get(i).substring(1));

            switch (type) {
                case 0:
                    ((MyApplication) getApplicationContext()).hotelList.get(index).setFavorite(true);
                    break;
                case 1:
                    ((MyApplication) getApplicationContext()).parkList.get(index).setFavorite(true);
                    break;
                case 3:
                    ((MyApplication) getApplicationContext()).restaurantList.get(index).setFavorite(true);
                    break;
                case 4:
                    ((MyApplication) getApplicationContext()).shopList.get(index).setFavorite(true);
                    break;
                case 5:
                    ((MyApplication) getApplicationContext()).theaterList.get(index).setFavorite(true);
                    break;
            }
        }
    }
}
