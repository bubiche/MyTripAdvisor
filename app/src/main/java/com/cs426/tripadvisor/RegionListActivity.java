package com.cs426.tripadvisor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;


public class RegionListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_region_list);
    }

    public void hanoi_click(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("chosenLat", ((MyApplication)getApplicationContext()).regionList.get(0).getLat());
        intent.putExtra("chosenLong", ((MyApplication)getApplicationContext()).regionList.get(0).getLng());
        intent.putExtra("start", 1);
        startActivity(intent);
    }

    public void hue_click(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("chosenLat", ((MyApplication)getApplicationContext()).regionList.get(1).getLat());
        intent.putExtra("chosenLong", ((MyApplication)getApplicationContext()).regionList.get(1).getLng());
        intent.putExtra("start", 1);
        startActivity(intent);
    }


    public void hcm_click(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("chosenLat", ((MyApplication)getApplicationContext()).regionList.get(2).getLat());
        intent.putExtra("chosenLong", ((MyApplication)getApplicationContext()).regionList.get(2).getLng());
        intent.putExtra("start", 1);
        startActivity(intent);
    }

    public void cantho_click(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("chosenLat", ((MyApplication)getApplicationContext()).regionList.get(3).getLat());
        intent.putExtra("chosenLong", ((MyApplication)getApplicationContext()).regionList.get(3).getLng());
        intent.putExtra("start", 1);
        startActivity(intent);
    }
}
