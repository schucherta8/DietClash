package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.diet.dietclash.FoodDB.FoodDBHelper;
import com.diet.dietclash.FoodDB.FoodServingsContract;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

public class GetStarted extends AppCompatActivity {

    static final String MEAT_CATEGORY = "meat";
    static final String VEGGIE_CATEGORY = "veggie";
    static final String FRUIT_CATEGORY = "fruit";
    static final String DAIRY_CATEGORY = "dairy";

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

    //DB serving size
    private int myDbVeggies;
    private int myDbFruit;
    private int myDbMeat;
    private int myDbDairy;

    //Declared serving sizes
    private int myVeggies;
    private int myFruit;
    private int myMeat;
    private int myDairy;

    //SQL Database
    private FoodDBHelper helper;
    private SQLiteDatabase db;

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
        getStarted(); //sets all servings to default if no entries in db. Else, read from db.
        showServings(); //display servings
    }

    /**
     * Set all serving sizes to the default serving sizes defined.
     */
    public void getStarted(){
        //Read from the db.
        readDb();
        //If no entries in the DB, or servings set to zero, set to default.
        if(myDbVeggies==0) {
            myVeggies = VEGGIES_SERVING_DEFAULT;
        }
        if(myDbFruit==0){
            myFruit = FRUIT_SERVING_DEFAULT;
        }
        if(myDbMeat==0) {
            myMeat = MEAT_SERVING_DEFAULT;
        }
        if(myDbDairy==0){
            myDairy = DAIRY_SERVING_DEFAULT;
        }
    }

    private void readDb(){
        //db
        helper = new FoodDBHelper(getApplicationContext());
        db = helper.getWritableDatabase();

      // reading values in db
        String[] projection = {FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY, FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT};

        Cursor cMeat = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection,
                FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY+"=?",
                new String[] {MEAT_CATEGORY},
                null,null,null);

        Cursor cVeggies = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection,
                FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY+"=?",
                new String[] {VEGGIE_CATEGORY},
                null,null,null);

        Cursor cFruit = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection,
                FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY+"=?",
                new String[] {FRUIT_CATEGORY},
                null,null,null);

        Cursor cDairy = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection,
                FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY+"=?",
                new String[] {DAIRY_CATEGORY},
                null,null,null);

        while(cMeat.moveToNext()){
            String categoryMeat = cMeat.getString(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY));
            myDbMeat = cMeat.getInt(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT));
        }

        while(cVeggies.moveToNext()){
            String categoryVeggies = cVeggies.getString(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY));
            myDbVeggies = cVeggies.getInt(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT));
        }

        while(cFruit.moveToNext()){
            String categoryFruit = cFruit.getString(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY));
            myDbFruit = cFruit.getInt(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT));
        }

        while(cDairy.moveToNext()){
            String categoryDairy = cDairy.getString(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY));
            myDbDairy= cDairy.getInt(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT));
        }

        cMeat.close();
        cVeggies.close();
        cFruit.close();
        cDairy.close();

        //Set to the values stored in the db
        myMeat = myDbMeat;
        myVeggies = myDbVeggies;
        myFruit = myDbFruit;
        myDairy = myDbDairy;

    }

    private int getQuantity(String category) {
        switch (category) {
            case MEAT_CATEGORY:
                return myMeat;
            case VEGGIE_CATEGORY:
                return myVeggies;
            case DAIRY_CATEGORY:
                return myDairy;
            case FRUIT_CATEGORY:
                return myFruit;
            default:
                return -1;
        }
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

    /**
     * Save the user input.
     * @param view
     */
    public void save(View view){
        //Collect all serving goals. If not present, we will insert them later.
        HashMap<String, Integer> servings = new HashMap<String, Integer>();

        //Categories for looping
        String[] categories = {MEAT_CATEGORY, VEGGIE_CATEGORY, FRUIT_CATEGORY, DAIRY_CATEGORY};

        //DB Read with Projections
        String[] projection = {FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY, FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT};

        Cursor cMeat = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection,
                FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY+"=?",
                new String[] {MEAT_CATEGORY},
                null,null,null);

        Cursor cVeggies = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection,
                FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY+"=?",
                new String[] {VEGGIE_CATEGORY},
                null,null,null);

        Cursor cFruit = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection,
                FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY+"=?",
                new String[] {FRUIT_CATEGORY},
                null,null,null);

        Cursor cDairy = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection,
                FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY+"=?",
                new String[] {DAIRY_CATEGORY},
                null,null,null);

        //Collect the amount for each category and store in hash map

        while(cMeat.moveToNext()){
            String category = cMeat.getString(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY));
            int amount = cMeat.getInt(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT));
            if(amount == -1) {
                //this should never happen; something is wrong
                Snackbar.make(view, "Bad value in db", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                continue;
            }
            servings.put(category, amount);
        }

        while(cVeggies.moveToNext()){
            String category = cVeggies.getString(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY));
            int amount = cVeggies.getInt(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT));
            if(amount == -1) {
                //this should never happen; something is wrong
                Snackbar.make(view, "Bad value in db", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                continue;
            }
            servings.put(category, amount);
        }

        while(cFruit.moveToNext()){
            String category = cFruit.getString(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY));
            int amount = cFruit.getInt(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT));
            if(amount == -1) {
                //this should never happen; something is wrong
                Snackbar.make(view, "Bad value in db", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                continue;
            }
            servings.put(category, amount);
        }

        while(cDairy.moveToNext()){
            String category = cDairy.getString(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY));
            int amount = cDairy.getInt(cMeat.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT));
        if(amount == -1) {
            //this should never happen; something is wrong
            Snackbar.make(view, "Bad value in db", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            continue;
        }
        servings.put(category, amount);
        }

        //Close all cursors
        cMeat.close();
        cVeggies.close();
        cFruit.close();
        cDairy.close();

        //Insert any entries in the DB if they do not exist
        for(String c : categories) {
            if(!servings.containsKey(c)) {
                //no entry in db - insert new one
                ContentValues values = new ContentValues();
                values.put(FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY, c);
                values.put(FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT, getQuantity(c));
                db.insert(FoodServingsContract.FoodServings.TABLE_NAME, null, values);
            }
        }

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

        /*//update the db
        for(Map.Entry<String, Integer> entry : servings.entrySet()) {
            String category = entry.getKey();
            int amount = entry.getValue();
            ContentValues values = new ContentValues();
            //store amount tied to category key.
            values.put(FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT, amount);
            db.update(FoodServingsContract.FoodServings.TABLE_NAME, //update servings
                    values, //set amount = amount
                    FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY+"?", //WHERE Category =?
                    new String[] {category});
        }*/

        for(String c: servings.keySet()) {
            ContentValues values = new ContentValues();
            //store amount tied to category key.
            values.put(FoodServingsContract.FoodServings.COLUMN_NAME_AMOUNT, servings.get(c));
            //argument is the category type
            String [] args = {c};
            db.update(FoodServingsContract.FoodServings.TABLE_NAME, values,
                    FoodServingsContract.FoodServings.COLUMN_NAME_CATEGORY+"=?",args);
        }


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
