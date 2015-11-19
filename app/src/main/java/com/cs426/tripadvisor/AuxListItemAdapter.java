package com.cs426.tripadvisor;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AuxListItemAdapter extends ArrayAdapter<String>{
    private Activity context;
    private String[] itemname;
    private ArrayList<Integer> imgid = new ArrayList<Integer>();


    //hotel
    public AuxListItemAdapter(Activity context,String[] dummy, ArrayList<Hotel.RoomType> roomTypes, boolean nothing) {
        super(context, R.layout.aux_list_layout, dummy);
        ArrayList<String> mItemName = new ArrayList<String>();
        for(int i = 0; i < roomTypes.size(); ++i) {
            String mItem = "Name: " + roomTypes.get(i).getName() + "\n" +
                            "Accomodation Information: " + roomTypes.get(i).getInfo() + "\n" +
                            "Description: " + roomTypes.get(i).getDesc() + "\n" +
                            "Price (per day): " + roomTypes.get(i).getPrice();
            mItemName.add(mItem);
            this.imgid.add(roomTypes.get(i).getPhoto());
        }

        this.context = context;
        this.itemname = mItemName.toArray(new String[mItemName.size()]);
    }

    //retaurant
    public AuxListItemAdapter(Activity context,String[] dummy, ArrayList<Restaurant.Dish> dishes, char nothing) {
        super(context, R.layout.aux_list_layout, dummy);
        ArrayList<String> mItemName = new ArrayList<String>();
        for(int i = 0; i < dishes.size(); ++i) {
            String mItem = "Name: " + dishes.get(i).getName() + "\n" +
                    "Description: " + dishes.get(i).getDescription() + "\n" +
                    "Price: " + dishes.get(i).getPrice();
            mItemName.add(mItem);
            this.imgid.add(dishes.get(i).getPhoto());
        }

        this.context = context;
        this.itemname = mItemName.toArray(new String[mItemName.size()]);
    }

    //shop
    public AuxListItemAdapter(Activity context,String[] dummy, ArrayList<Shop.ProductCategory> products) {
        super(context, R.layout.aux_list_layout, dummy);
        ArrayList<String> mItemName = new ArrayList<String>();
        for(int i = 0; i < products.size(); ++i) {
            String mItem = "Name: " + products.get(i).getName() + "\n" +
                    "Description: " + products.get(i).getDesc() + "\n";
            mItemName.add(mItem);
            this.imgid.add(products.get(i).getPhoto());
        }

        this.context = context;
        this.itemname = mItemName.toArray(new String[mItemName.size()]);
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.aux_list_layout, null,true);

        TextView text = (TextView) rowView.findViewById(R.id.list_row_text);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_row_image);
        text.setText(itemname[position]);
        imageView.setImageBitmap(decodeResource(context.getResources(), imgid.get(position)));

        return rowView;
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
}
