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

    //Default serving sizes
    private static final int VEGGIES_SERVING_DEFAULT = 4;
    private static final int FRUIT_SERVING_DEFAULT = 4;
    private static final int MEAT_SERVING_DEFAULT = 2;
    private static final int DAIRY_SERVING_DEFAULT = 3;

    //Declared serving sizes
    private int myVeggies;
    private int myFruit;
    private int myMeat;
    private int myDairy;

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
        showServings(); //display servings
    }

    /**
     * Set all serving sizes to the default serving sizes defined.
     */
    public void getStarted(){
        myVeggies = VEGGIES_SERVING_DEFAULT;
        myFruit = FRUIT_SERVING_DEFAULT;
        myMeat = MEAT_SERVING_DEFAULT;
        myDairy = DAIRY_SERVING_DEFAULT;
    }

    /**
     * Display the serving sizes to the user.
     */
    public void showServings(){
        veggiesServingsView.setText(String.valueOf(myVeggies));
        fruitServingsView.setText(String.valueOf(myFruit));
        meatServingsView.setText(String.valueOf(myMeat));
        dairyServingsView.setText(String.valueOf(myDairy));
    }

    //TODO: Will need to store in DB later.

    /**
     * Save the user input.
     * @param view
     */
    public void save(View view){

        //Find all servings views
        veggiesServingsView = findViewById(R.id.veggiesServingView);
        fruitServingsView = findViewById(R.id.fruitServingsView);
        meatServingsView = findViewById(R.id.meatServingsView);
        dairyServingsView = findViewById(R.id.dairyServingsView);

        //Store assign their new values
        myVeggies = Integer.parseInt(veggiesServingsView.getText().toString());
        myFruit = Integer.parseInt(fruitServingsView.getText().toString());
        myMeat = Integer.parseInt(meatServingsView.getText().toString());
        myDairy = Integer.parseInt(dairyServingsView.getText().toString());

        //Display new results
        showServings();

        //Feedback to user
        Snackbar.make(view, "Saving serving sizes", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    //TODO: Will need to update the DB later.

    /**
     * Reset the user serving sizes back to default.
     * @param view
     */
    public void reset(View view){

        //Reset
        getStarted();

        //Show results
        showServings();

        //Feedback to user
        Snackbar.make(view, "Resetting serving sizes", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
