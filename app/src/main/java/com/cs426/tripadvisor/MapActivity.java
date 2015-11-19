package com.cs426.tripadvisor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.u1aryz.android.lib.newpopupmenu.PopupMenu;
import com.u1aryz.android.lib.newpopupmenu.PopupMenu.OnItemSelectedListener;

public class MapActivity extends Activity implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnMarkerDragListener, OnItemSelectedListener {

    private final static int DETAILS = 0;
    private final static int DIRECTIONS = 1;
    private final static int REQUEST_ADD_PLACE = 42;

    private GoogleMap mMap = null;
    private double phoneLong = 106.6943626;
    private double phoneLat = 10.768451;
    private int startLocation = 0;
    private float start_lng = 0;
    private float start_lat = 0;
    private Marker curMarker = null;
    private double addLat = 0;
    private double addLng = 0;
    private ArrayList<Marker> markers = new ArrayList<Marker>();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        startLocation = intent.getIntExtra("start", 0);
        start_lat = intent.getFloatExtra("chosenLat", 0);
        start_lng = intent.getFloatExtra("chosenLong", 0);

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

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc == null) {
            // fall back to network if GPS is not available
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (loc != null) {
            phoneLat = loc.getLatitude();
            phoneLong = loc.getLongitude();
        }

        MapFragment mf = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mf.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap map) {  // map is loaded but not laid out yet
        mMap = map;
        map.setOnMapLoadedCallback(this);      // calls onMapLoaded when layout done
    }

    @Override
    public void onMapLoaded() {
        ArrayList<Hotel> hotels = ((MyApplication)getApplicationContext()).hotelList;
        ArrayList<Restaurant> restaurants = ((MyApplication)getApplicationContext()).restaurantList;
        ArrayList<Shop> shops = ((MyApplication)getApplicationContext()).shopList;
        ArrayList<Park> parks = ((MyApplication)getApplicationContext()).parkList;
        ArrayList<Theater> theaters = ((MyApplication)getApplicationContext()).theaterList;

        for(int i = 0; i < hotels.size(); ++i){
            Hotel hotel = hotels.get(i);
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(hotel.getLat(), hotel.getLng())).snippet("0" + i).draggable(true)
                    .title(hotel.getName()).icon(BitmapDescriptorFactory.fromBitmap(decodeResource(getResources(), R.drawable.hotel_icon))));
            markers.add(marker);
        }

        for(int i = 0; i < restaurants.size(); ++i){
            Restaurant restaurant = restaurants.get(i);
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(restaurant.getLat(), restaurant.getLng())).snippet("3" + i).draggable(true)
                    .title(restaurant.getName()).icon(BitmapDescriptorFactory.fromBitmap(decodeResource(getResources(), R.drawable.res_icon))));
            markers.add(marker);
        }

        for(int i = 0; i < shops.size(); ++i){
            Shop shop = shops.get(i);
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(shop.getLat(), shop.getLng())).snippet("4" + i).draggable(true)
                    .title(shop.getName()).icon(BitmapDescriptorFactory.fromBitmap(decodeResource(getResources(), R.drawable.shop_icon))));
            markers.add(marker);
        }

        for(int i = 0; i < parks.size(); ++i){
            Park park = parks.get(i);
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(park.getLat(), park.getLng())).snippet("1" + i).draggable(true)
                    .title(park.getName()).icon(BitmapDescriptorFactory.fromBitmap(decodeResource(getResources(), R.drawable.park_icon))));
            markers.add(marker);
        }

        for (int i = 0; i < theaters.size(); ++i) {
            Theater theater = theaters.get(i);
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(theater.getLat(), theater.getLng())).snippet("5" + i).draggable(true)
                    .title(theater.getName()).icon(BitmapDescriptorFactory.fromBitmap(decodeResource(getResources(), R.drawable.theater_icon))));
            markers.add(marker);
        }

        mMap.addMarker(new MarkerOptions().position(new LatLng(phoneLat, phoneLong)).title("Current Position"));

        if(startLocation == 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(phoneLat, phoneLong)));
        }
        else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(start_lat, start_lng)));
        }

        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMarkerDragListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        if(!marker.getTitle().equals("Current Position")) {
            curMarker = marker;

            PopupMenu menu = new PopupMenu(this);
            menu.setHeaderTitle(marker.getTitle());

            menu.setOnItemSelectedListener(this);

            menu.add(DETAILS, R.string.attr_det).setIcon(getResources().getDrawable(R.drawable.ic_details));
            menu.add(DIRECTIONS, R.string.di).setIcon(getResources().getDrawable(R.drawable.ic_directions));
            menu.setWidth(480);
            menu.show(null);
            /////////////////////////////////////


        }
        return true;
    }
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

    public void click_close_attraction_detail(View view) {
        attraction_detail.setVisibility(View.INVISIBLE);
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

    public void showItem(int attractionType, int index) {

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
                        "Closing Time: " + cur.getClose_time());
                aux_tv.setText("Tap to view room types");
                attraction_detail.setVisibility(View.VISIBLE);
                ratingBar.setRating(cur.getRating());
                aux_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AuxListItemAdapter auxListItemAdapter = new AuxListItemAdapter(MapActivity.this, new String[cur.getRoomTypes().size()], cur.getRoomTypes(), true);
                        aux_lv.setAdapter(auxListItemAdapter);
                        attraction_detail.setVisibility(View.INVISIBLE);
                        aux_detail.setVisibility(View.VISIBLE);
                        aux_detail.bringToFront();
                    }
                });
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
                        AuxListItemAdapter auxListItemAdapter = new AuxListItemAdapter(MapActivity.this, new String[cur.getMenu().size()], cur.getMenu(), 'n');
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
                        AuxListItemAdapter auxListItemAdapter = new AuxListItemAdapter(MapActivity.this, new String[cur.getProductCategories().size()], cur.getProductCategories());
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

    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask(this);
        asyncTask.execute(map);
    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints)
    {
        Polyline newPolyline;
        PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.RED);
        for(int i = 0 ; i < directionPoints.size() ; i++)
        {
            rectLine.add(directionPoints.get(i));
        }
        newPolyline = mMap.addPolyline(rectLine);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        // TODO Auto-generated method stub
        findDirections(phoneLat, phoneLong, marker.getPosition().latitude, marker.getPosition().longitude,GMapV2Direction.MODE_DRIVING);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onMarkerDrag(Marker marker) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onItemSelected(com.u1aryz.android.lib.newpopupmenu.MenuItem item) {
        switch (item.getItemId()) {
            case DETAILS:
                int type = Character.getNumericValue(curMarker.getSnippet().charAt(0));
                int index = Integer.parseInt(curMarker.getSnippet().substring(1));
                showItem(type, index);
                break;

            case DIRECTIONS:
                findDirections(phoneLat, phoneLong, curMarker.getPosition().latitude, curMarker.getPosition().longitude,GMapV2Direction.MODE_DRIVING);
                break;

        }
    }

    public void addPlace(MenuItem item) {
        Intent intent = new Intent(this, PlaceAddActivity.class);
        startActivityForResult(intent, REQUEST_ADD_PLACE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD_PLACE && resultCode == RESULT_OK && data != null) {
            addLat = data.getDoubleExtra("addLat",0);
            addLng = data.getDoubleExtra("addLng", 0);
            String addName = data.getStringExtra("addName");
            mMap.addMarker(new MarkerOptions().position(new LatLng(addLat, addLng)).snippet("10").draggable(true)
                    .title(addName).icon(BitmapDescriptorFactory.fromBitmap(decodeResource(getResources(), R.drawable.new_icon))));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(addLat, addLng)));
        }
    }

    public void show_hotels(MenuItem item) {
        for(int i = 0; i < markers.size(); ++i) {
            if(Character.getNumericValue(markers.get(i).getSnippet().charAt(0)) == 0)
                markers.get(i).setVisible(true);
            else
                markers.get(i).setVisible(false);
        }
    }

    public void show_parks(MenuItem item) {
        for(int i = 0; i < markers.size(); ++i) {
            if(Character.getNumericValue(markers.get(i).getSnippet().charAt(0)) == 1)
                markers.get(i).setVisible(true);
            else
                markers.get(i).setVisible(false);
        }
    }

    public void show_res(MenuItem item) {
        for(int i = 0; i < markers.size(); ++i) {
            if(Character.getNumericValue(markers.get(i).getSnippet().charAt(0)) == 3)
                markers.get(i).setVisible(true);
            else
                markers.get(i).setVisible(false);
        }
    }

    public void show_shops(MenuItem item) {
        for(int i = 0; i < markers.size(); ++i) {
            if(Character.getNumericValue(markers.get(i).getSnippet().charAt(0)) == 4)
                markers.get(i).setVisible(true);
            else
                markers.get(i).setVisible(false);
        }
    }

    public void show_the(MenuItem item) {
        for(int i = 0; i < markers.size(); ++i) {
            if(Character.getNumericValue(markers.get(i).getSnippet().charAt(0)) == 5)
                markers.get(i).setVisible(true);
            else
                markers.get(i).setVisible(false);
        }
    }

    public void search_click(MenuItem item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Attraction Search");
        alert.setMessage("Please input attraction name");

        final EditText name = new EditText(this);
        alert.setView(name);

        alert.setPositiveButton("Search!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                for (int i = 0; i < markers.size(); ++i) {
                    if (markers.get(i).getTitle().equals(name.getText().toString())) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markers.get(i).getPosition(), 14));
                        dialog.cancel();
                        return;
                    }
                }
                Toast.makeText(getApplicationContext(), "Attraction not found :(", Toast.LENGTH_LONG).show();

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });

        alert.show();
    }
}
