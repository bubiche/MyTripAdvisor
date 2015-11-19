package com.cs426.tripadvisor;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.effect.Effect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;


public class PlaceAddActivity extends Activity {

    private double lng = 0;
    private double lat = 0;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private EditText et_name = null;
    private EditText et_address = null;
    private EditText et_phone = null;
    protected String youraddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_place_add);
        imageView = (ImageView) findViewById(R.id.iv_photo);
        et_name = (EditText) findViewById(R.id.name_et);
        et_address = (EditText) findViewById(R.id.address_et);
        et_phone = (EditText) findViewById(R.id.phone_et);
    }

    public void getLatLongFromGivenAddress() {
        youraddress = youraddress.replace(' ', '+');
        String uri = "http://maps.google.com/maps/api/geocode/json?address=" + youraddress + "&sensor=false";
        HttpGet httpGet = new HttpGet(uri);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void click_take_pic(View view) {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

    public void add_click(View view) {
        youraddress = et_address.getText().toString();
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                getLatLongFromGivenAddress();
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                Intent ret = new Intent();
                ret.putExtra("addLat", lat);
                ret.putExtra("addLng", lng);
                ret.putExtra("addName", et_name.getText().toString());
                setResult(RESULT_OK, ret);
                finishActivity();
            }
        }.execute();
        Toast.makeText(getApplicationContext(), "Latitude: " + lat + " Longtitude: " + lng, Toast.LENGTH_SHORT).show();

    }

    void finishActivity() {
        this.finish();
    }
}
