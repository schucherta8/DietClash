package com.diet.dietclash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoreLocator extends AppCompatActivity {

    ArrayList<String> locations; // can change data type if need be
    ArrayAdapter<String> adapter;
    private ListView storeList;
    private FusedLocationProviderClient fusedLocationClient;

    private RequestQueue rq;

    private static final int DISPLAY_COUNT = 3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_locator);
        storeList = findViewById(R.id.storeList);
        locations = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locations);
        storeList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        rq = Volley.newRequestQueue(this);
    }

    public void GetStores(View view) {
        //can possibly do some smart stuff with not always totally refreshing but for now we will
        locations.clear();

        //get locations
        Task<Location> loc = fusedLocationClient.getLastLocation();
        loc.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    String query = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=<API_KEY>&location="+
                            latitude+","+
                            longitude+"&type=grocery_or_supermarket&rankby=distance";
                    
                    //use this query, then parse json
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, query,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        JSONArray results = jsonResponse.getJSONArray("results");
                                        for(int i = 0; i < DISPLAY_COUNT && i < results.length(); ++i) {
                                            JSONObject entry = results.getJSONObject(i);
                                            locations.add(entry.getString("name"));
                                            adapter.notifyDataSetChanged();
                                        }
                                    } catch(JSONException e) {
                                        Toast.makeText(getApplicationContext(), "Could not parse json of response. "+
                                                e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Could not send HTTP request. Error "+error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    rq.add(stringRequest);
                } else {
                    //error
                    Toast.makeText(getApplicationContext(), "Could not get location", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loc.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Could not get location. Error:" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
