package com.cs426.tripadvisor;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintStream;
import java.util.ArrayList;


public class AttractionListActivity extends Activity {
    private static final String fav_file = "favorites_list";
    private int attractionType;
    private ListView attraction_list;
    private EditText inputSearch;
    private LinearLayout attraction_detail;
    private LinearLayout aux_detail;
    private ImageView attraction_detail_iv;
    private TextView attraction_name_tv;
    private TextView address_tv;
    private TextView phone_tv;
    private TextView website_tv;
    private TextView aux_tv;
    private TextView desAndTime_tv;
    private ListView aux_lv;
    private RatingBar ratingBar;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_attraction_list);

        Intent intent = getIntent();
        attractionType = intent.getIntExtra("chosen", 0);

        attraction_list = (ListView) findViewById(R.id.attraction_lv);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        attraction_detail = (LinearLayout) findViewById(R.id.attraction_detail);
        aux_detail = (LinearLayout) findViewById(R.id.aux_list);
        attraction_detail_iv = (ImageView) findViewById(R.id.detail_iv);
        attraction_name_tv = (TextView) findViewById(R.id.attractionName_tv);
        address_tv = (TextView) findViewById(R.id.address_tv);
        phone_tv = (TextView) findViewById(R.id.phone_tv);
        website_tv = (TextView) findViewById(R.id.web_tv);
        aux_tv = (TextView) findViewById(R.id.aux_tv);
        desAndTime_tv = (TextView) findViewById(R.id.desAndTime_tv);
        aux_lv = (ListView) findViewById(R.id.auxListView);
        ratingBar = (RatingBar) findViewById(R.id.rate_bar);


        attraction_name_tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String url = "https://www.youtube.com/results?search_query=" + ((TextView)v).getText();
                url = url.replace(' ', '+');
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
                catch(ActivityNotFoundException e) {
                    e.printStackTrace();
                return true;
                }
                return true;
            }
        });
        // initialize name list
        final ArrayList<String> attractions = new ArrayList<String>();
        switch (attractionType) {
            case 0:
                for (int i = 0; i < ((MyApplication) getApplicationContext()).hotelList.size(); ++i) {
                    attractions.add(((MyApplication) getApplicationContext()).hotelList.get(i).getName());
                }
                break;
            case 1:
                for (int i = 0; i < ((MyApplication) getApplicationContext()).parkList.size(); ++i) {
                    attractions.add(((MyApplication) getApplicationContext()).parkList.get(i).getName());
                }
                break;
            case 3:
                for (int i = 0; i < ((MyApplication) getApplicationContext()).restaurantList.size(); ++i) {
                    attractions.add(((MyApplication) getApplicationContext()).restaurantList.get(i).getName());
                }
                break;
            case 4:
                for (int i = 0; i < ((MyApplication) getApplicationContext()).shopList.size(); ++i) {
                    attractions.add(((MyApplication) getApplicationContext()).shopList.get(i).getName());
                }
                break;
            case 5:
                for (int i = 0; i < ((MyApplication) getApplicationContext()).theaterList.size(); ++i) {
                    attractions.add(((MyApplication) getApplicationContext()).theaterList.get(i).getName());
                }
                break;
        }

        adapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, R.id.attraction_name, attractions);
        attraction_list.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        attraction_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "have " + ((MyApplication) getApplicationContext()).parkList.size(),Toast.LENGTH_LONG).show();
                showItem(attractions.indexOf(adapter.getItem(position)));
            }
        });

        attraction_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String key = "" + attractionType + position;
                switch (attractionType) {
                    case 0:
                        ((MyApplication) getApplicationContext()).hotelList.get((attractions.indexOf(adapter.getItem(position)))).setFavorite(true);

                        if(((MyApplication) getApplicationContext()).fav_list.indexOf(key) == -1) {
                            try {
                                PrintStream fout = new PrintStream(openFileOutput(fav_file, MODE_APPEND));
                                fout.println(key);
                                fout.close();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                        }

                        break;
                    case 1:
                        ((MyApplication) getApplicationContext()).parkList.get((attractions.indexOf(adapter.getItem(position)))).setFavorite(true);
                        if(((MyApplication) getApplicationContext()).fav_list.indexOf(key) == -1) {
                            try {
                                PrintStream fout = new PrintStream(openFileOutput(fav_file, MODE_APPEND));
                                fout.println(key);
                                fout.close();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 3:
                        ((MyApplication) getApplicationContext()).restaurantList.get((attractions.indexOf(adapter.getItem(position)))).setFavorite(true);
                        if(((MyApplication) getApplicationContext()).fav_list.indexOf(key) == -1) {
                            try {
                                PrintStream fout = new PrintStream(openFileOutput(fav_file, MODE_APPEND));
                                fout.println(key);
                                fout.close();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 4:
                        ((MyApplication) getApplicationContext()).shopList.get((attractions.indexOf(adapter.getItem(position)))).setFavorite(true);
                        if(((MyApplication) getApplicationContext()).fav_list.indexOf(key) == -1) {
                            try {
                                PrintStream fout = new PrintStream(openFileOutput(fav_file, MODE_APPEND));
                                fout.println(key);
                                fout.close();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 5:
                        ((MyApplication) getApplicationContext()).theaterList.get((attractions.indexOf(adapter.getItem(position)))).setFavorite(true);
                        if(((MyApplication) getApplicationContext()).fav_list.indexOf(key) == -1) {
                            try {
                                PrintStream fout = new PrintStream(openFileOutput(fav_file, MODE_APPEND));
                                fout.println(key);
                                fout.close();
                            }
                            catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
                Toast.makeText(getApplicationContext(), "Location added to favorite =)", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void showItem(int index) {

        switch (attractionType) {
            case 0: {
                ArrayList<Hotel> cur_arr = ((MyApplication) getApplicationContext()).hotelList;
                final Hotel cur = cur_arr.get(index);
                attraction_name_tv.setText(cur.getName());
                attraction_detail_iv.setImageBitmap(decodeResource(getResources(), cur.getPhoto()));
                address_tv.setText(cur.getAddress());
                phone_tv.setText(cur.getPhone());
                website_tv.setText(cur.getWebsite());
                desAndTime_tv.setText(cur.getDescription() + "\n" +
                        "Opening Time: " + cur.getOpen_time() + "\n" +
                        "Closing Time: " + cur.getClose_time() + "\n");
                aux_tv.setText("Tap to view room types");
                attraction_detail.setVisibility(View.VISIBLE);

                aux_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AuxListItemAdapter auxListItemAdapter = new AuxListItemAdapter(AttractionListActivity.this, new String[cur.getRoomTypes().size()], cur.getRoomTypes(), true);
                        aux_lv.setAdapter(auxListItemAdapter);
                        attraction_detail.setVisibility(View.INVISIBLE);
                        aux_detail.setVisibility(View.VISIBLE);
                        aux_detail.bringToFront();
                    }
                });
                ratingBar.setRating(cur.getRating());
                break;
            }
            case 1: {
                ArrayList<Park> cur_arr = ((MyApplication) getApplicationContext()).parkList;
                Park cur = cur_arr.get(index);
                attraction_name_tv.setText(cur.getName());
                attraction_detail_iv.setImageBitmap(decodeResource(getResources(), cur.getPhoto()));
                address_tv.setText(cur.getAddress());
                phone_tv.setText(cur.getPhone());
                website_tv.setText(cur.getWebsite());
                desAndTime_tv.setText(cur.getDescription() + "\n" +
                        "Opening Time: " + cur.getOpen_time() + "\n" +
                        "Closing Time: " + cur.getClose_time());
                aux_tv.setText("Ticket Price: " + cur.getTicket_price());
                attraction_detail.setVisibility(View.VISIBLE);
                ratingBar.setRating(cur.getRating());
                break;
            }
            case 3: {
                ArrayList<Restaurant> cur_arr = ((MyApplication) getApplicationContext()).restaurantList;
                final Restaurant cur = cur_arr.get(index);
                attraction_name_tv.setText(cur.getName());
                attraction_detail_iv.setImageBitmap(decodeResource(getResources(), cur.getPhoto()));
                address_tv.setText(cur.getAddress());
                phone_tv.setText(cur.getPhone());
                website_tv.setText(cur.getWebsite());
                desAndTime_tv.setText(cur.getDescription() + "\n" +
                        "Opening Time: " + cur.getOpen_time() + "\n" +
                        "Closing Time: " + cur.getClose_time());
                aux_tv.setText("Tap to view dishes");
                attraction_detail.setVisibility(View.VISIBLE);
                ratingBar.setRating(cur.getRating());
                aux_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AuxListItemAdapter auxListItemAdapter = new AuxListItemAdapter(AttractionListActivity.this, new String[cur.getMenu().size()], cur.getMenu(), 'n');
                        aux_lv.setAdapter(auxListItemAdapter);
                        attraction_detail.setVisibility(View.INVISIBLE);
                        aux_detail.setVisibility(View.VISIBLE);
                        aux_detail.bringToFront();
                    }
                });
                break;
            }
            case 4: {
                ArrayList<Shop> cur_arr = ((MyApplication) getApplicationContext()).shopList;
                final Shop cur = cur_arr.get(index);
                attraction_name_tv.setText(cur.getName());
                attraction_detail_iv.setImageBitmap(decodeResource(getResources(), cur.getPhoto()));
                address_tv.setText(cur.getAddress());
                phone_tv.setText(cur.getPhone());
                website_tv.setText(cur.getWebsite());
                desAndTime_tv.setText(cur.getDescription() + "\n" +
                        "Opening Time: " + cur.getOpen_time() + "\n" +
                        "Closing Time: " + cur.getClose_time());
                aux_tv.setText("Tap to view product categories");
                attraction_detail.setVisibility(View.VISIBLE);
                ratingBar.setRating(cur.getRating());
                aux_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AuxListItemAdapter auxListItemAdapter = new AuxListItemAdapter(AttractionListActivity.this, new String[cur.getProductCategories().size()], cur.getProductCategories());
                        aux_lv.setAdapter(auxListItemAdapter);
                        attraction_detail.setVisibility(View.INVISIBLE);
                        aux_detail.setVisibility(View.VISIBLE);
                        aux_detail.bringToFront();
                    }
                });
                break;
            }
            case 5: {
                ArrayList<Theater> cur_arr = ((MyApplication) getApplicationContext()).theaterList;
                Theater cur = cur_arr.get(index);
                attraction_name_tv.setText(cur.getName());
                attraction_detail_iv.setImageBitmap(decodeResource(getResources(), cur.getPhoto()));
                address_tv.setText(cur.getAddress());
                phone_tv.setText(cur.getPhone());
                website_tv.setText(cur.getWebsite());
                desAndTime_tv.setText(cur.getDescription() + "\n" +
                        "Opening Time: " + cur.getOpen_time() + "\n" +
                        "Closing Time: " + cur.getClose_time());
                aux_tv.setText("Ticket Price: " + cur.getTicket_price());
                attraction_detail.setVisibility(View.VISIBLE);
                ratingBar.setRating(cur.getRating());
                break;
            }
        }

    }

    public void click_close_attraction_detail(View view) {
        attraction_detail.setVisibility(View.INVISIBLE);
    }

    //decode bitmap to save memory
    private static Bitmap decodeResource(Resources res, int id) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        for (options.inSampleSize = 1; options.inSampleSize <= 32; options.inSampleSize++) {
            try {
                bitmap = BitmapFactory.decodeResource(res, id, options);
                Log.d("logging", "Decoded successfully for sampleSize " + options.inSampleSize);
                break;
            } catch (OutOfMemoryError outOfMemoryError) {
                // If an OutOfMemoryError occurred, we continue with for loop and next inSampleSize value
                Log.e("logging", "outOfMemoryError while reading file for sampleSize " + options.inSampleSize
                        + " retrying with higher value");
            }
        }
        return bitmap;
    }

    public void click_close_aux_list(View view) {
        aux_detail.setVisibility(View.INVISIBLE);
        attraction_detail.setVisibility(View.VISIBLE);
    }

    public void clickWeb(View view) {
        if(!website_tv.getText().toString().equals(" ")) {
            String url = website_tv.getText().toString();
            try {
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
            catch(ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void click_back(View view) {
        finish();
    }
}
