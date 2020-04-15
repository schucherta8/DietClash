package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.diet.dietclash.FoodDB.FoodDBHelper;
import com.diet.dietclash.FoodDB.FoodEntryContract;
import com.diet.dietclash.FoodDB.FoodServingsContract;
import com.diet.dietclash.util.Constants;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.snackbar.Snackbar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyProgress extends AppCompatActivity {

    TextView meatDaily;
    TextView fruitDaily;
    TextView dairyDaily;
    TextView veggieDaily;
    TextView ends;
    TextView meatWeekly;
    TextView fruitWeekly;
    TextView dairyWeekly;
    TextView veggieWeekly;

    int dailyMeatGoal;
    int dailyFruitGoal;
    int dailyDairyGoal;
    int dailyVeggieGoal;
    int weeklyMeatGoal;
    int weeklyFruitGoal;
    int weeklyDairyGoal;
    int weeklyVeggieGoal;

    int dailyEatenMeat;
    int dailyEatenFruit;
    int dailyEatenDairy;
    int dailyEatenVeggie;
    int weeklyEatenMeat;
    int weeklyEatenFruit;
    int weeklyEatenDairy;
    int weeklyEatenVeggie;

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_progress);

        //load in db
        FoodDBHelper helper = new FoodDBHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();
        setupTextViews();
        loadDb(db);
        setupBarChart();
    }

    private void setupTextViews(){
        //daily views
        meatDaily = findViewById(R.id.meat_daily);
        fruitDaily = findViewById(R.id.fruit_daily);
        dairyDaily = findViewById(R.id.dairy_daily);
        veggieDaily = findViewById(R.id.veggie_daily);

        //end date view
        ends = findViewById(R.id.ends);

        //weekly views
        meatWeekly = findViewById(R.id.meat_weekly);
        fruitWeekly = findViewById(R.id.fruit_weekly);
        dairyWeekly = findViewById(R.id.dairy_weekly);
        veggieWeekly = findViewById(R.id.veggie_weekly);
    }

    private void setupBarChart(){
        // BEGIN INITIALIZING CHART

        barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(weeklyEatenMeat/weeklyMeatGoal, 0));
        entries.add(new BarEntry(weeklyEatenFruit/weeklyFruitGoal, 1));
        entries.add(new BarEntry(weeklyEatenVeggie/weeklyVeggieGoal, 2));
        entries.add(new BarEntry(weeklyEatenDairy/weeklyDairyGoal, 3));

        BarDataSet bardataset = new BarDataSet(entries, "Food Groups");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Meat");
        labels.add("Fruit");
        labels.add("Veggie");
        labels.add("Dairy");

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of labels into chart
        barChart.setDescription("Percentage Weekly Food Consumption");  // set the description
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);
    }

    private void loadDb(SQLiteDatabase db){
        // reading values in db
        String[] projection = {FoodServingsContract.FoodServings.COLUMN_NAME_MEAT,
                FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT,
                FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY,
                FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE,
                FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE};

        dailyMeatGoal = 0;
        dailyFruitGoal = 0;
        dailyDairyGoal = 0;
        dailyVeggieGoal = 0;
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

        weeklyMeatGoal = 0;
        weeklyFruitGoal = 0;
        weeklyDairyGoal = 0;
        weeklyVeggieGoal = 0;
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

        cursor.close();

        //update eaten values
        int[] eaten = getEatenQuantities(db);

        //we've got our ints, now to set text
        meatDaily.setText("Meat: "+eaten[0]+"/"+dailyMeatGoal);
        fruitDaily.setText("Fruit: "+eaten[1]+"/"+dailyFruitGoal);
        dairyDaily.setText("Dairy: "+eaten[2]+"/"+dailyDairyGoal);
        veggieDaily.setText("Veggies: "+eaten[3]+"/"+dailyVeggieGoal);

        ends.setText("Ends: "+weeklyEnds);

        meatWeekly.setText("Meat: "+eaten[0]+"/"+weeklyMeatGoal);
        fruitWeekly.setText("Fruit: "+eaten[1]+"/"+weeklyFruitGoal);
        dairyWeekly.setText("Dairy: "+eaten[2]+"/"+weeklyDairyGoal);
        veggieWeekly.setText("Veggies: "+eaten[3]+"/"+weeklyVeggieGoal);
    }

    private int[] getEatenQuantities(SQLiteDatabase db) {
        dailyEatenMeat = 0;
        dailyEatenFruit = 0;
        dailyEatenDairy = 0;
        dailyEatenVeggie = 0;
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
                    dailyEatenMeat = amount;
                    break;
                case Constants.VEGGIE_CATEGORY:
                    dailyEatenVeggie = amount;
                    break;
                case Constants.FRUIT_CATEGORY:
                    dailyEatenFruit = amount;
                case Constants.DAIRY_CATEGORY:
                    dailyEatenDairy = amount;
                    break;
            }
        }

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
                case Constants.DAIRY_CATEGORY:
                    weeklyEatenDairy += amount;
                    break;
            }
        }

        cursor.close();

        return new int[]{dailyEatenMeat, dailyEatenFruit, dailyEatenDairy, dailyEatenVeggie,
                weeklyEatenMeat, weeklyEatenFruit, weeklyEatenDairy, weeklyEatenVeggie};
    }
}
