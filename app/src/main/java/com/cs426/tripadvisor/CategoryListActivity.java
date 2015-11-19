package com.cs426.tripadvisor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;


public class CategoryListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_category_list);
    }

    public void onClickHotel(View view) {
        int chosenCat = 0;
        Intent intent = new Intent(this, AttractionListActivity.class);
        intent.putExtra("chosen", chosenCat);
        startActivity(intent);
    }

    public void onClickShop(View view) {
        int chosenCat = 4;
        Intent intent = new Intent(this, AttractionListActivity.class);
        intent.putExtra("chosen", chosenCat);
        startActivity(intent);
    }

    public void onClickRest(View view) {
        int chosenCat = 3;
        Intent intent = new Intent(this, AttractionListActivity.class);
        intent.putExtra("chosen", chosenCat);
        startActivity(intent);
    }

    public void onClickPark(View view) {
        int chosenCat = 1;
        Intent intent = new Intent(this, AttractionListActivity.class);
        intent.putExtra("chosen", chosenCat);
        startActivity(intent);
    }

    public void onClickTheater(View view) {
        int chosenCat = 5;
        Intent intent = new Intent(this, AttractionListActivity.class);
        intent.putExtra("chosen", chosenCat);
        startActivity(intent);
    }
}
