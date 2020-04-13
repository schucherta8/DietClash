package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.diet.dietclash.FoodDB.FoodDBHelper;
import com.diet.dietclash.FoodDB.FoodServingsContract;
import com.google.android.material.snackbar.Snackbar;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyProgress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_progress);
        //daily views
        TextView meatDaily = findViewById(R.id.meat_daily);
        TextView fruitDaily = findViewById(R.id.fruit_daily);
        TextView dairyDaily = findViewById(R.id.dairy_daily);
        TextView veggieDaily = findViewById(R.id.veggie_daily);

        //end date view
        TextView ends = findViewById(R.id.ends);

        //weekly views
        TextView meatWeekly = findViewById(R.id.meat_weekly);
        TextView fruitWeekly = findViewById(R.id.fruit_weekly);
        TextView dairyWeekly = findViewById(R.id.dairy_weekly);
        TextView veggieWeekly = findViewById(R.id.veggie_weekly);

        //load in db
        FoodDBHelper helper = new FoodDBHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();

        // reading values in db
        String[] projection = {FoodServingsContract.FoodServings.COLUMN_NAME_MEAT,
                FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT,
                FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY,
                FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE,
                FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE};

        int dailyMeatGoal = 0;
        int dailyFruitGoal = 0;
        int dailyDairyGoal = 0;
        int dailyVeggieGoal = 0;
        //first, check for a daily goal
        Cursor cursor = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection,
                FoodServingsContract.FoodServings.COLUMN_NAME_DURATION_DAYS+"=? AND "+
                        FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE+"=?",
                new String[] {"1", new SimpleDateFormat("yyyy-MM-dd").format(new Date())}, null, null, null, "1");
        while(cursor.moveToNext()) {
            dailyMeatGoal = cursor.getInt(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_MEAT));
            dailyFruitGoal = cursor.getInt(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT));
            dailyDairyGoal = cursor.getInt(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY));
            dailyVeggieGoal = cursor.getInt(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE));
        }

        int weeklyMeatGoal = 0;
        int weeklyFruitGoal = 0;
        int weeklyDairyGoal = 0;
        int weeklyVeggieGoal = 0;
        String weeklyEnds = "N/A";
        //check for weekly goal
        String[] args = new String[8]; //duration and 7 days
        args[0] = "7";
        String selectString = FoodServingsContract.FoodServings.COLUMN_NAME_DURATION_DAYS+"=? AND (";
        for(int i = 0; i < 7; ++i) {
            args[i+1] = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - (i * 1000 * 60 * 60)));
            selectString = selectString + FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE+"=?";
            if(i == 6) {
                selectString = selectString + ")";
            } else {
                selectString = selectString + " OR ";
            }
        }
        cursor = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection, selectString, args, null, null, null, "1");
        while(cursor.moveToNext()) {
            weeklyMeatGoal = cursor.getInt(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_MEAT));
            weeklyFruitGoal = cursor.getInt(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT));
            weeklyDairyGoal = cursor.getInt(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY));
            weeklyVeggieGoal = cursor.getInt(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE));
            String tmp = cursor.getString(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date endDate = null;
            try {
                endDate = format.parse(tmp);
            } catch(ParseException e) {
                Snackbar.make(getCurrentFocus(), "Parsing Error", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            if(endDate != null) {
                long time = endDate.getTime();
                time += 7 * 1000 * 60 * 60 * 24;
                endDate.setTime(time);
                weeklyEnds = format.format(endDate);
            }
        }

        //TODO: get data from db
        int dailyEatenMeat = 0;
        int dailyEatenFruit = 0;
        int dailyEatenDairy = 0;
        int dailyEatenVeggie = 0;

        //TODO: get data from db
        int weeklyEatenMeat = 0;
        int weeklyEatenFruit = 0;
        int weeklyEatenDairy = 0;
        int weeklyEatenVeggie = 0;

        //we've got our ints, now to set text
        meatDaily.setText("Meat: "+dailyEatenMeat+"/"+dailyMeatGoal);
        fruitDaily.setText("Fruit: "+dailyEatenFruit+"/"+dailyFruitGoal);
        dairyDaily.setText("Dairy: "+dailyEatenDairy+"/"+dailyDairyGoal);
        veggieDaily.setText("Veggies: "+dailyEatenVeggie+"/"+dailyVeggieGoal);

        ends.setText("Ends: "+weeklyEnds);

        meatWeekly.setText("Meat: "+weeklyEatenMeat+"/"+weeklyMeatGoal);
        fruitWeekly.setText("Fruit: "+weeklyEatenFruit+"/"+weeklyFruitGoal);
        dairyWeekly.setText("Dairy: "+weeklyEatenDairy+"/"+weeklyDairyGoal);
        veggieWeekly.setText("Veggies: "+weeklyEatenVeggie+"/"+weeklyVeggieGoal);
    }
}
