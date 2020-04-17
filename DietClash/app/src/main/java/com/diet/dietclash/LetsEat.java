package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.diet.dietclash.FoodDB.FoodDBHelper;
import com.diet.dietclash.FoodDB.FoodEntryContract;
import com.diet.dietclash.util.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class LetsEat extends AppCompatActivity {

    //Current Counts
    private TextView meatText;
    private TextView veggieText;
    private TextView fruitText;
    private TextView dairyText;
    private int meatCount;
    private int veggieCount;
    private int fruitCount;
    private int dairyCount;

    //Total Counts
    private TextView dbMeatText;
    private TextView dbVeggieText;
    private TextView dbFruitText;
    private TextView dbDairyText;
    private int dbMeatCount;
    private int dbVeggieCount;
    private int dbFruitCount;
    private int dbDairyCount;

    //db weekly
    private TextView dbWeeklyMeatText;
    private TextView dbWeeklyVeggieText;
    private TextView dbWeeklyFruitText;
    private TextView dbWeeklyDairyText;
    private int weeklyEatenMeat;
    private int weeklyEatenFruit;
    private int weeklyEatenDairy;
    private int weeklyEatenVeggie;

    //SQL Database
    private FoodDBHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_eat);

        //initialize text and counts
        meatText = findViewById(R.id.currentMeatServing);
        veggieText = findViewById(R.id.currentVeggiesServing);
        fruitText = findViewById(R.id.currentFruitServing);
        dairyText = findViewById(R.id.currentDairyServing);

        dbMeatText = findViewById(R.id.dbMeatServing);
        dbVeggieText = findViewById(R.id.dbVeggiesServing);
        dbFruitText = findViewById(R.id.dbFruitServing);
        dbDairyText = findViewById(R.id.dbDairyServing);

        dbWeeklyMeatText = findViewById(R.id.dbWeeklyMeat);
        dbWeeklyVeggieText = findViewById(R.id.dbWeeklyVeg);
        dbWeeklyDairyText = findViewById(R.id.dbWeeklyDairy);
        dbWeeklyFruitText = findViewById(R.id.dbWeeklyFruit);

        //db
        helper = new FoodDBHelper(getApplicationContext());
        db = helper.getWritableDatabase();

        //set inputs to zero.
        resetInput();
        //update text with reset and db read
        refreshAll();
    }

    private void readDb(){
        // reading values in db. Leaving for now
        String[] projection = {FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY, FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT};
        String[] args = {new SimpleDateFormat("yyyy-MM-dd").format(new Date())};
        Cursor cursor = db.query(FoodEntryContract.FoodEntry.TABLE_NAME, projection, FoodEntryContract.FoodEntry.COLUMN_NAME_DATE+"=?", args, null, null, null);

        while(cursor.moveToNext()) {
            String category = cursor.getString(
                    cursor.getColumnIndexOrThrow(FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY));
            int amount = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT));
            switch(category) {
                case Constants.MEAT_CATEGORY:
                    dbMeatCount = amount;
                    break;
                case Constants.VEGGIE_CATEGORY:
                    dbVeggieCount = amount;
                    break;
                case Constants.FRUIT_CATEGORY:
                    dbFruitCount = amount;
                    break;
                case Constants.DAIRY_CATEGORY:
                    dbDairyCount = amount;
                    break;
            }

        }
        cursor.close();
    }

    private void updateMeat() {
        String update = "Current: "+String.valueOf(meatCount);
        meatText.setText(update);
    }
    private void updateVeggie() {
        String update = "Current: "+String.valueOf(veggieCount);
        veggieText.setText(update);
    }
    private void updateFruit() {
        String update = "Current: "+String.valueOf(fruitCount);
        fruitText.setText(update);
    }
    private void updateDairy() {
        String update = "Current: "+String.valueOf(dairyCount);
        dairyText.setText(update);
    }

    private void updateDbMeat() {
        String update = "Daily Total: "+String.valueOf(dbMeatCount);
        dbMeatText.setText(update);
    }
    private void updateDbVeggie() {
        String update = "Daily Total: "+String.valueOf(dbVeggieCount);
        dbVeggieText.setText(update);
    }
    private void updateDbFruit() {
        String update = "Daily Total: "+String.valueOf(dbFruitCount);
        dbFruitText.setText(update);
    }
    private void updateDbDairy() {
        String update = "Daily Total: "+String.valueOf(dbDairyCount);
        dbDairyText.setText(update);
    }

    public void AddMeat(View view) {
        ++meatCount;
        updateMeat();
    }

    public void AddVeggie(View view) {
        ++veggieCount;
        updateVeggie();
    }

    public void AddFruit(View view) {
        ++fruitCount;
        updateFruit();
    }

    public void AddDairy(View view) {
        ++dairyCount;
        updateDairy();
    }

    private int getQuantity(String category) {
        switch(category) {
            case Constants.MEAT_CATEGORY:
                return meatCount;
            case Constants.VEGGIE_CATEGORY:
                return veggieCount;
            case Constants.DAIRY_CATEGORY:
                return dairyCount;
            case Constants.FRUIT_CATEGORY:
                return fruitCount;
            default:
                return -1;
        }
    }

    private void resetInput(){
        meatCount = veggieCount = fruitCount = dairyCount = 0;
    }

    public void Submit(View view) {
        if(meatCount == 0 && veggieCount == 0 && fruitCount == 0 && dairyCount == 0) {
            Snackbar.make(view, "Please enter food information", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        //do save stuff
        String[] projection = {FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY, FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT};
        String[] args = {new SimpleDateFormat("yyyy-MM-dd").format(new Date())};
        Cursor cursor = db.query(FoodEntryContract.FoodEntry.TABLE_NAME, projection, FoodEntryContract.FoodEntry.COLUMN_NAME_DATE+"=?", args, null, null, null);

        HashMap<String, Integer> servings = new HashMap<String, Integer>();
        while(cursor.moveToNext()) {
            String category = cursor.getString(
                    cursor.getColumnIndexOrThrow(FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY));
            int amount = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT));
            int enteredAmount = getQuantity(category);
            if(enteredAmount == -1) {
                //this should never happen; something is wrong
                Snackbar.make(view, "Bad value in db", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                continue;
            }
            servings.put(category, amount+enteredAmount);
        }
        cursor.close();
        String[] categories = {Constants.MEAT_CATEGORY, Constants.VEGGIE_CATEGORY,
                Constants.FRUIT_CATEGORY, Constants.DAIRY_CATEGORY};
        for(String c : categories) {
            if(!servings.containsKey(c)) {
                //no entry in db - insert new one
                ContentValues values = new ContentValues();
                values.put(FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY, c);
                values.put(FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT, getQuantity(c));
                values.put(FoodEntryContract.FoodEntry.COLUMN_NAME_DATE, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                db.insert(FoodEntryContract.FoodEntry.TABLE_NAME, null, values);
            }
        }
        for(String c: servings.keySet()) {
            ContentValues values = new ContentValues();
            values.put(FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT, servings.get(c));
            String[] whereArgs = {c, new SimpleDateFormat("yyyy-MM-dd").format(new Date())};
            db.update(FoodEntryContract.FoodEntry.TABLE_NAME, values, FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY+"=? AND "
                    +FoodEntryContract.FoodEntry.COLUMN_NAME_DATE+"=?", whereArgs);
        }

        resetInput();
        refreshAll();
        Snackbar.make(view, "Saved food information", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void Reset(View view) {
        meatCount = veggieCount = fruitCount = dairyCount = 0;
        refreshAll();
        Snackbar.make(view, "Food information reset", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void refreshAll(){
        //Current
        this.updateMeat();
        this.updateVeggie();
        this.updateFruit();
        this.updateDairy();

        //Pull in data from the db.
        readDb();

        //Total
        this.updateDbMeat();
        this.updateDbVeggie();
        this.updateDbFruit();
        this.updateDbDairy();

        //Check Achievements
        checkAchievements();
    }

    private void checkAchievements(){
        //find our weekly progress for food eaten
        getWeeklyEatenQuantities();
        String update = "Weekly Total: "+String.valueOf(weeklyEatenMeat);
        dbWeeklyMeatText.setText(update);
        update = "Weekly Total: "+String.valueOf(weeklyEatenFruit);
        dbWeeklyFruitText.setText(update);
        update = "Weekly Total: "+String.valueOf(weeklyEatenDairy);
        dbWeeklyDairyText.setText(update);
        update = "Weekly Total: "+String.valueOf(weeklyEatenVeggie);
        dbWeeklyVeggieText.setText(update);
    }

    private void getWeeklyEatenQuantities(){
        String[] projection = {FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY, FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT};
        String[] args = {new SimpleDateFormat("yyyy-MM-dd").format(new Date())};
        Cursor cursor = db.query(FoodEntryContract.FoodEntry.TABLE_NAME, projection, FoodEntryContract.FoodEntry.COLUMN_NAME_DATE+"=?", args, null, null, null);

        args = new String[7]; //duration and 7 days
        String selectString = "";
        for(int i = 0; i < 7; ++i) {
            args[i] = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - (i * 1000 * 60 * 60 * 24)));
            selectString = selectString + FoodEntryContract.FoodEntry.COLUMN_NAME_DATE+"=?";
            if(i != 6) {
                selectString = selectString + " OR ";
            }
        }
        weeklyEatenMeat = 0;
        weeklyEatenFruit = 0;
        weeklyEatenDairy = 0;
        weeklyEatenVeggie = 0;
        cursor = db.query(FoodEntryContract.FoodEntry.TABLE_NAME, projection, selectString, args, null, null, null);
        while(cursor.moveToNext()) {
            String category = cursor.getString(
                    cursor.getColumnIndexOrThrow(FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY));
            int amount = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT));
            switch(category) {
                case Constants.MEAT_CATEGORY:
                    weeklyEatenMeat += amount;
                    break;
                case Constants.VEGGIE_CATEGORY:
                    weeklyEatenVeggie += amount;
                    break;
                case Constants.FRUIT_CATEGORY:
                    weeklyEatenFruit += amount;
                    break;
                case Constants.DAIRY_CATEGORY:
                    weeklyEatenDairy += amount;
                    break;
            }
        }

        cursor.close();
    }
}
