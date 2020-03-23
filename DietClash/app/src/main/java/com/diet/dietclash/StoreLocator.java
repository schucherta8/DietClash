package com.diet.dietclash;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class StoreLocator extends AppCompatActivity {

    ArrayList<String> locations; // can change data type if need be
    ArrayAdapter<String> adapter;
    private ListView storeList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_locator);
        storeList = findViewById(R.id.storeList);
        locations = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locations);
        storeList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void GetStores(View view) {
        //can possibly do some smart stuff with not always totally refreshing but for now we will
        locations.clear();

        //get locations
        String query = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=<API_KEY>&location=<LAT,LONG>&type=grocery_or_supermarket&rankby=distance";
        //use this query, then parse json


        locations.add("foobar");
        adapter.notifyDataSetChanged();
    }
}
