package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class GetStarted extends AppCompatActivity {

    //Text View Objects for VEGGIES, FRUIT, MEAT, and DAIRY.
    TextView veggiesTextView;
    TextView fruitTextView;
    TextView meatTextView;
    TextView dairyTextView;

    //Text View Objects for Servings.
    TextView veggiesServingsView;
    TextView fruitServingsView;
    TextView meatServingsView;
    TextView dairyServingsView;

    private static final int VEGGIES_SERVING_DEFAULT = 4;
    private static final int FRUIT_SERVING_DEFAULT = 4;
    private static final int MEAT_SERVING_DEFAULT = 2;
    private static final int DAIRY_SERVING_DEFAULT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_get_started);

        //Food Groups.
        veggiesTextView = findViewById(R.id.veggiesTextView);
        fruitTextView = findViewById(R.id.fruitTextView);
        meatTextView = findViewById(R.id.meatTextView);
        dairyTextView = findViewById(R.id.dairyTextView);

        //Servings.
        veggiesServingsView = findViewById(R.id.veggiesServingView);
        fruitServingsView = findViewById(R.id.fruitServingsView);
        meatServingsView = findViewById(R.id.meatServingsView);
        dairyServingsView = findViewById(R.id.dairyServingsView);
        getStarted(); //sets all servings to default for now. Will need to pull from DB later.
    }

    public void getStarted(){
        veggiesServingsView.setText(String.valueOf(VEGGIES_SERVING_DEFAULT));
        fruitServingsView.setText(String.valueOf(FRUIT_SERVING_DEFAULT));
        meatServingsView.setText(String.valueOf(MEAT_SERVING_DEFAULT));
        dairyServingsView.setText(String.valueOf(DAIRY_SERVING_DEFAULT));
    }

    public void save(View view){
        //Will need to store in DB later.
        Snackbar.make(view, "Saving serving sizes", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void reset(View view){
        //Will need to update in DB later.
        Snackbar.make(view, "Resetting serving sizes", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        getStarted();
    }
}
