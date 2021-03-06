package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.diet.dietclash.FoodDB.FoodDBHelper;
import com.diet.dietclash.FoodDB.FoodDungeonContract;
import com.diet.dietclash.FoodDB.FoodServingsContract;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

    TextView warning;

    //Radio Buttons
    RadioButton daily;
    RadioButton weekly;

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
    private boolean currentDailyGoal = false;
    private boolean currentWeeklyGoal = false;

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

        daily = findViewById(R.id.daily);
        weekly = findViewById(R.id.weekly);

        warning = findViewById(R.id.warning);

        getStarted(); //sets all servings to default if no entries in db. Else, read from db.
        showServings(); //display servings
        ToggleWarning(null);
    }

    public void ToggleWarning(View view) {
        if(daily.isChecked()) {
            if(currentDailyGoal) {
                warning.setText("Creating a new daily goal will erase your previous one.");
            } else {
                warning.setText("");
            }
        }

        if(weekly.isChecked()) {
            if(currentWeeklyGoal) {
                warning.setText("Creating a new weekly goal will erase your previous one.");
            } else {
                warning.setText("");
            }
        }
    }

    /**
     * Set all serving sizes to the default serving sizes defined.
     */
    public void getStarted(){
        //Read from the db.
        readDb();
        //If no entries in the DB, or servings set to zero, set to default.
        if(myDbVeggies==-1) {
            myVeggies = VEGGIES_SERVING_DEFAULT;
        }
        if(myDbFruit==-1){
            myFruit = FRUIT_SERVING_DEFAULT;
        }
        if(myDbMeat==-1) {
            myMeat = MEAT_SERVING_DEFAULT;
        }
        if(myDbDairy==-1){
            myDairy = DAIRY_SERVING_DEFAULT;
        }
    }

    private void readDb(){
        //db
        helper = new FoodDBHelper(getApplicationContext());
        db = helper.getWritableDatabase();

        // reading values in db
        String[] projection = {FoodServingsContract.FoodServings.COLUMN_NAME_MEAT,
                FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT,
                FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY,
                FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE};

        //first, check for a daily goal
        Cursor dailyCursor = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection,
                FoodServingsContract.FoodServings.COLUMN_NAME_DURATION_DAYS+"=? AND "+
                        FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE+"=?",
                new String[] {"1", new SimpleDateFormat("yyyy-MM-dd").format(new Date())}, null, null, null, "1");
        //we now have the daily goal set for today, if any

        currentDailyGoal = dailyCursor.getCount() > 0;

        //check for weekly
        String[] args = new String[8]; //duration and 7 days
        args[0] = "7";
        String selectString = FoodServingsContract.FoodServings.COLUMN_NAME_DURATION_DAYS+"=? AND (";
        for(int i = 0; i < 7; ++i) {
            args[i+1] = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - (i * 1000 * 60 * 60 * 24)));
            selectString = selectString + FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE+"=?";
            if(i == 6) {
                selectString = selectString + ")";
            } else {
                selectString = selectString + " OR ";
            }
        }
        Cursor weeklyCursor = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection, selectString, args, null, null, null, "1");

        currentWeeklyGoal = weeklyCursor.getCount() > 0;

        if(!currentDailyGoal && !currentWeeklyGoal) {
            myDbMeat = myDbFruit = myDbDairy = myDbVeggies = -1; //show we could not read from db
        }

        if(currentWeeklyGoal && !currentDailyGoal) {
            //we have a weekly goal, but not a daily one. Show it's weekly
            weekly.setChecked(true);
            daily.setChecked(false);
            while(weeklyCursor.moveToNext()) { //should only happen once
                myDbMeat = weeklyCursor.getInt(weeklyCursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_MEAT));
                myDbFruit = weeklyCursor.getInt(weeklyCursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT));
                myDbDairy = weeklyCursor.getInt(weeklyCursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY));
                myDbVeggies = weeklyCursor.getInt(weeklyCursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE));
            }
        }

        if(currentDailyGoal) {
            while(dailyCursor.moveToNext()) { //should only happen once
                myDbMeat = dailyCursor.getInt(dailyCursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_MEAT));
                myDbFruit = dailyCursor.getInt(dailyCursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT));
                myDbDairy = dailyCursor.getInt(dailyCursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY));
                myDbVeggies = dailyCursor.getInt(dailyCursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE));
            }
        }

        //Set to the values stored in the db
        myMeat = myDbMeat;
        myVeggies = myDbVeggies;
        myFruit = myDbFruit;
        myDairy = myDbDairy;

        dailyCursor.close();
        weeklyCursor.close();
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
        //Store assign their new values
        try {
            myVeggies = Integer.parseInt(veggiesServingsView.getText().toString());
        } catch (Error e) {
            myVeggies = 0;
        }
        try {
            myFruit = Integer.parseInt(fruitServingsView.getText().toString());
        } catch (Error e) {
            myFruit = 0;
        }
        try {
            myMeat = Integer.parseInt(meatServingsView.getText().toString());
        } catch (Error e) {
            myMeat = 0;
        }
        try {
            myDairy = Integer.parseInt(dairyServingsView.getText().toString());
        } catch (Error e) {
            myDairy = 0;
        }
        //update the db
        ContentValues values = new ContentValues();
        values.put(FoodServingsContract.FoodServings.COLUMN_NAME_MEAT, myMeat);
        values.put(FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT, myFruit);
        values.put(FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY, myDairy);
        values.put(FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE, myVeggies);

        //assume it's all weekly or daily
        int duration = 7;
        if(daily.isChecked()) {
            duration = 1;
        }

        String args[];
        String where = FoodServingsContract.FoodServings.COLUMN_NAME_DURATION_DAYS+"=? AND ";
        if(duration == 1) {
            where = where + FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE+"=?";
            args = new String[]{"1", new SimpleDateFormat("yyyy-MM-dd").format(new Date())};
        } else {
            where = where + "(";
            args = new String[8];
            args[0] = "7";
            for(int i = 0; i < 7; ++i) {
                args[i+1] = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - (i * 1000 * 60 * 60 * 24)));
                where = where + FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE+"=?";
                if(i == 6) {
                    where = where + ")";
                } else {
                    where = where + " OR ";
                }
            }
        }

        int changed = db.update(FoodServingsContract.FoodServings.TABLE_NAME, //update servings
                values, where, args);
        if(changed == 0) {
            //there was no goal to update - let's insert one
            values.put(FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            values.put(FoodServingsContract.FoodServings.COLUMN_NAME_DURATION_DAYS, duration);
            db.insert(FoodServingsContract.FoodServings.TABLE_NAME, null, values);
        }
        if (duration == 7){
            //Create a set of Monsters to choose from
            MONSTER_TYPE[] monsters = MONSTER_TYPE.values();
            //Generate a random monster
            Random r = new Random();
            MONSTER_TYPE monsterType = monsters[r.nextInt(monsters.length)];
            //Calculate date
            int week = 1000 * 60 * 60 * 24 * 7;
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date(System.currentTimeMillis() + week));
            Monster monster = MonsterFactory.generateMonster(
                    monsterType,myMeat+myFruit+myDairy+myVeggies,
                    myMeat,myFruit,myDairy,myVeggies, date,false);

            //Monster Information
            ContentValues monsterContentValues = new ContentValues();
            monsterContentValues.put(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_MONSTER_TYPE,
                    monster.getType().toString()
            );
            monsterContentValues.put(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_MAX_HEALTH,
                    monster.getHealth()
            );
            monsterContentValues.put(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_MEAT_SERVINGS,
                    monster.getMeatServings()
            );
            monsterContentValues.put(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_FRUIT_SERVINGS,
                    monster.getFruitServings()
            );
            monsterContentValues.put(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_DAIRY_SERVINGS,
                    monster.getDairyServings()
            );
            monsterContentValues.put(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_VEGGIE_SERVINGS,
                    monster.getVeggieServings()
            );
            monsterContentValues.put(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_EXPIRATION,
                    monster.getExpiration()
            );
            monsterContentValues.put(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_DEFEATED,
                    Boolean.compare(monster.isDefeated(),false)
            );
            //Insert Monster record into Dungeon table
            db.insert(FoodDungeonContract.FoodDungeon.TABLE_NAME,
                    null,monsterContentValues);
        }
        //Display new results
        showServings();

        if(daily.isChecked()) {
            currentDailyGoal = true;
        }

        if(weekly.isChecked()) {
            currentWeeklyGoal = true;
        }

        //Feedback to user
        Snackbar.make(view, "Saved serving goal", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

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
