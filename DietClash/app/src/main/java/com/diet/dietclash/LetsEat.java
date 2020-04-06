package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.diet.dietclash.FoodDB.FoodDBHelper;
import com.diet.dietclash.FoodDB.FoodEntryContract;
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

    //SQL Database
    private FoodDBHelper helper;
    private SQLiteDatabase db;

    static final String MEAT_CATEGORY = "meat";
    static final String VEGGIE_CATEGORY = "veggie";
    static final String FRUIT_CATEGORY = "fruit";
    static final String DAIRY_CATEGORY = "dairy";

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

        //set inputs to zero.
        resetInput();
        //update text with reset and db read
        refreshAll();
    }

    private void readDb(){
        //db
        helper = new FoodDBHelper(getApplicationContext());
        db = helper.getWritableDatabase();

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
                case MEAT_CATEGORY:
                    dbMeatCount = amount;
                    break;
                case VEGGIE_CATEGORY:
                    dbVeggieCount = amount;
                    break;
                case FRUIT_CATEGORY:
                    dbFruitCount = amount;
                    break;
                case DAIRY_CATEGORY:
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
        String update = "Total: "+String.valueOf(dbMeatCount);
        dbMeatText.setText(update);
    }
    private void updateDbVeggie() {
        String update = "Total: "+String.valueOf(dbVeggieCount);
        dbVeggieText.setText(update);
    }
    private void updateDbFruit() {
        String update = "Total: "+String.valueOf(dbFruitCount);
        dbFruitText.setText(update);
    }
    private void updateDbDairy() {
        String update = "Total: "+String.valueOf(dbDairyCount);
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
            case MEAT_CATEGORY:
                return meatCount;
            case VEGGIE_CATEGORY:
                return veggieCount;
            case DAIRY_CATEGORY:
                return dairyCount;
            case FRUIT_CATEGORY:
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
        String[] categories = {MEAT_CATEGORY, VEGGIE_CATEGORY, FRUIT_CATEGORY, DAIRY_CATEGORY};
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
    }
}
