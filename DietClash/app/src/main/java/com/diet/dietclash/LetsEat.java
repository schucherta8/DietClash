package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.diet.dietclash.FoodDB.FoodAchievementsContract;
import com.diet.dietclash.FoodDB.FoodDBHelper;
import com.diet.dietclash.FoodDB.FoodDungeonContract;
import com.diet.dietclash.FoodDB.FoodEntryContract;
import com.diet.dietclash.FoodDB.FoodServingsContract;
import com.diet.dietclash.util.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
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

    //Weekly Servings Goals
    private int weeklyMeatGoal;
    private int weeklyFruitGoal;
    private int weeklyDairyGoal;
    private int weeklyVeggieGoal;

    //Achievements for Food Consumption
    private int startSomewhereGoal; //one personal goal
    private int slowSteadyGoal; //four goals

    private int startSomewhereProgress;
    private int slowSteadyProgress;

    private boolean goalMetAtStart;

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

        //first, check if we've already completed a goal
        goalMetAtStart = isGoalMet();

        //set inputs to zero.
        resetInput();
        //update text with reset and db read
        refreshAll();
    }

    private boolean isGoalMet() {
        getWeeklyEatenQuantities();
        getWeeklyFoodGoals();
        return weeklyMeatGoal != -1 &&
                weeklyEatenMeat >= weeklyMeatGoal &&
                weeklyEatenFruit >= weeklyFruitGoal &&
                weeklyEatenDairy >= weeklyDairyGoal &&
                weeklyEatenVeggie >= weeklyVeggieGoal;
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
    private void updateWeeklyTotal(){
        String update = "Weekly Total: "+String.valueOf(weeklyEatenMeat);
        dbWeeklyMeatText.setText(update);
        update = "Weekly Total: "+String.valueOf(weeklyEatenFruit);
        dbWeeklyFruitText.setText(update);
        update = "Weekly Total: "+String.valueOf(weeklyEatenDairy);
        dbWeeklyDairyText.setText(update);
        update = "Weekly Total: "+String.valueOf(weeklyEatenVeggie);
        dbWeeklyVeggieText.setText(update);
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
        updateMonsterHealth();
        resetInput();
        refreshAll();
        cancelNotification();
        Snackbar.make(view, "Saved food information", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void cancelNotification() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent cancelIntent = new Intent(getApplicationContext(), NotificationReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 101, cancelIntent, 0);

        alarmManager.cancel(pendingIntent);
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
        getWeeklyEatenQuantities(); //collect weekly consumption from db -- TESTED
        getWeeklyFoodGoals(); //collect weekly goals from db - Pulled from another activity
        updateWeeklyTotal(); //update the weekly consumption view -- TESTED
        //look at progress for first and second achievements
        assessAchievement();
    }

    private void assessAchievement(){
        //get progress so far by reading from db
        getFoodAchievements(); //collect the achievements from the db

        if(weeklyMeatGoal != -1 && !goalMetAtStart &&
                weeklyEatenMeat >= weeklyMeatGoal &&
                weeklyEatenFruit >= weeklyFruitGoal &&
                weeklyEatenDairy >= weeklyDairyGoal &&
                weeklyEatenVeggie >= weeklyVeggieGoal) {
            //we've set a new goal - increment!
            ++startSomewhereProgress;
            ++slowSteadyProgress;
            updateAchievementProgress();
        }
    }

    private void getWeeklyFoodGoals(){
        //initialize goals as being -1 for no goal
        weeklyMeatGoal = weeklyDairyGoal = weeklyFruitGoal = weeklyVeggieGoal = -1;

        String[] projection = {FoodServingsContract.FoodServings.COLUMN_NAME_MEAT,
                FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT,
                FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY,
                FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE,
                FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE};
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
        Cursor cursor = db.query(FoodServingsContract.FoodServings.TABLE_NAME, projection, selectString, args, null, null, null, "1");
        while(cursor.moveToNext()) {
            weeklyMeatGoal = cursor.getInt(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_MEAT));
            weeklyFruitGoal = cursor.getInt(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT));
            weeklyDairyGoal = cursor.getInt(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY));
            weeklyVeggieGoal = cursor.getInt(cursor.getColumnIndexOrThrow(FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE));
        }

        cursor.close();
    }

    private void getWeeklyEatenQuantities(){
        String[] projection = {FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY, FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT};

        String[] args = new String[7]; //7 days
        String selectString = "";
        for(int i = 0; i < 7; ++i) {
            args[i] = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - (i * 1000 * 60 * 60 * 24)));
            selectString = selectString + FoodEntryContract.FoodEntry.COLUMN_NAME_DATE+"=?";
            if(i != 6) {
                selectString = selectString + " OR ";
            }
        }
        weeklyEatenMeat = weeklyEatenVeggie = weeklyEatenFruit = weeklyEatenDairy = 0;
        Cursor cursor = db.query(FoodEntryContract.FoodEntry.TABLE_NAME, projection, selectString, args, null, null, null);
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

    private void getFoodAchievements(){
        /**
         * Representation of Achievement Table:
         * ------------------------------------------------------------------------------------------------------
         * |achievement_title              |achievement_description      |achievement_progress|achievement_goal|achievement_complete|
         * ------------------------------------------------------------------------------------------------------------------------
         * "Gotta start somewhere!"        | "Complete a personal goal." |       0           |    1            |              false|
         * ------------------------------------------------------------------------------------------------------------------------
         * "Slow and steady wins the race!" | Complete 4 weekly goals."   |       0           |   4            |              false|
         * ------------------------------------------------------------------------------------------------------------------------
         * "Look at me now!"               |"Complete 12 weekly goals"   |       0          |   12            |              false|
         * ------------------------------------------------------------------------------------------------------------------------
         */
        String[] projection = {
                FoodAchievementsContract.FoodAchievements.COLUMN_NAME_PROGRESS,
                FoodAchievementsContract.FoodAchievements.COLUMN_NAME_GOAL};
        //Collect the first achievement goal and progress
        String[] args = {"Gotta start somewhere!"};
        //SELECT achievement_progress, achievement_goal FROM achievements
        //WHERE achievement_title = 'Gotta start somewhere!';
        Cursor cursor = db.query(
                FoodAchievementsContract.FoodAchievements.TABLE_NAME,
                projection,
                FoodAchievementsContract.FoodAchievements.COLUMN_NAME_TITLE+"=?",
                args, null, null, null);
        while(cursor.moveToNext()) {
            startSomewhereProgress = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_PROGRESS));
            startSomewhereGoal = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_GOAL));

            }
        //Collect the second achievement goal and progress
        String[] args2 = {"Slow and steady wins the race!"};
        //SELECT achievement_progress, achievement_goal FROM achievements
        //WHERE achievement_title = 'Slow and steady wins the race!';
        cursor = db.query(
                FoodAchievementsContract.FoodAchievements.TABLE_NAME,
                projection,
                FoodAchievementsContract.FoodAchievements.COLUMN_NAME_TITLE+"=?",
                args2, null, null, null);
        while(cursor.moveToNext()) {
            slowSteadyProgress = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_PROGRESS));
            slowSteadyGoal = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_GOAL));

        }
        cursor.close();
    }

    private void updateAchievementProgress(){
        //Update the first achievement goal progress.
        ContentValues value = new ContentValues();
        value.put(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_PROGRESS, startSomewhereProgress);
        String[] args = {"Gotta start somewhere!"};
        //UPDATE achievements
        //SET achievement_progress = progress
        //WHERE achievement_title = 'Gotta start somewhere!';
        db.update(
                FoodAchievementsContract.FoodAchievements.TABLE_NAME,
                value,
                FoodAchievementsContract.FoodAchievements.COLUMN_NAME_TITLE+"=?",
                args);

        //Update the second achievement goal progress.
        value = new ContentValues();
        value.put(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_PROGRESS, slowSteadyProgress);
        String[] args2 = {"Slow and steady wins the race!"};
        //UPDATE achievements
        //SET achievement_progress = progress
        //WHERE achievement_title = 'Slow and steady wins the race!';
        db.update(
                FoodAchievementsContract.FoodAchievements.TABLE_NAME,
                value,
                FoodAchievementsContract.FoodAchievements.COLUMN_NAME_TITLE+"=?",
                args2);
    }

    /**
     * Update the monsters health
     * TODO: NOT WORKING
     */
    private void updateMonsterHealth() {
        //Values needed to create the query
        String TABLE_NAME = FoodDungeonContract.FoodDungeon.TABLE_NAME;
        String[] COLUMNS = {FoodDungeonContract.FoodDungeon.COLUMN_NAME_MONSTER_TYPE,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_MAX_HEALTH,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_MEAT_SERVINGS,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_FRUIT_SERVINGS,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_DAIRY_SERVINGS,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_VEGGIE_SERVINGS,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_EXPIRATION,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_DEFEATED};

        //Most recent Monster
        String SORT_ORDER = FoodDungeonContract.FoodDungeon.COLUMN_NAME_EXPIRATION+ " DESC";

        //Query the table for the serving goal per food group.
        Cursor cursor = db.query(TABLE_NAME,COLUMNS,null,null,
                null,null,SORT_ORDER,"1");
        Monster monster = null;
        if (cursor.moveToNext()){
            String monsterType = cursor.getString(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_MONSTER_TYPE));
            int maxHealth = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_MAX_HEALTH));
            int meatServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_MEAT_SERVINGS));
            int fruitServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_FRUIT_SERVINGS));
            int dairyServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_DAIRY_SERVINGS));
            int veggieServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_VEGGIE_SERVINGS));
            String expiration = cursor.getString(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_EXPIRATION));
            int defeated = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_DEFEATED));
            switch (MONSTER_TYPE.valueOf(monsterType)){
                case EASY:
                    monster = new EasyMonster(MONSTER_TYPE.valueOf(monsterType),
                            maxHealth,meatServingGoal,fruitServingGoal,dairyServingGoal,veggieServingGoal,
                            expiration,defeated == 1);
                case MEDIUM:
                    monster = new MediumMonster(MONSTER_TYPE.valueOf(monsterType),
                            maxHealth,meatServingGoal,fruitServingGoal,dairyServingGoal,veggieServingGoal,
                            expiration,defeated == 1);
                case HARD:
                    monster = new HardMonster(MONSTER_TYPE.valueOf(monsterType),
                            maxHealth,meatServingGoal,fruitServingGoal,dairyServingGoal,veggieServingGoal,
                            expiration,defeated == 1);
            }
        }
        if(monster != null){
            //Check expiration
            String expiredDate =  monster.getExpiration();
            String todayDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date(System.currentTimeMillis()));
            try{
                Date expiration = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(expiredDate);
                Date today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(todayDate);
                boolean isExpired = expiration.before(today);
                if(!isExpired){
                    //Subtract entry from monster total servings
                    int monsterMeat = monster.getMeatServings() - meatCount;
                    int monsterFruit = monster.getFruitServings() - fruitCount;
                    int monsterDairy = monster.getDairyServings() - dairyCount;
                    int monsterVeggie = monster.getVeggieServings() - veggieCount;
                    if(monsterMeat <= 0){
                        monster.setMeatServings(0);
                    }else{
                        monster.setMeatServings(monsterMeat);
                    }
                    if(monsterFruit <= 0){
                        monster.setFruitServings(0);
                    }else{
                        monster.setFruitServings(monsterFruit);
                    }
                    if(monsterDairy <= 0){
                        monster.setDairyServings(0);
                    }else{
                        monster.setDairyServings(monsterDairy);
                    }
                    if(monsterVeggie <= 0){
                        monster.setVeggieServings(0);
                    }else{
                        monster.setVeggieServings(monsterVeggie);
                    }
                    //If monster is killed, set defeated to true
                    if(monster.getHealthRemainder() <= 0){
                        monster.setDefeated(true);
                    }

                    //Update record in database
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(
                            FoodDungeonContract.FoodDungeon.COLUMN_NAME_MEAT_SERVINGS,
                            monster.getMeatServings()
                    );
                    contentValues.put(
                            FoodDungeonContract.FoodDungeon.COLUMN_NAME_FRUIT_SERVINGS,
                            monster.getFruitServings()
                    );
                    contentValues.put(
                            FoodDungeonContract.FoodDungeon.COLUMN_NAME_DAIRY_SERVINGS,
                            monster.getDairyServings()
                    );
                    contentValues.put(
                            FoodDungeonContract.FoodDungeon.COLUMN_NAME_VEGGIE_SERVINGS,
                            monster.getVeggieServings()
                    );
                    if(monster.isDefeated()){
                        contentValues.put(FoodDungeonContract.FoodDungeon.COLUMN_NAME_DEFEATED,1);
                    }
                    String WHERE = FoodDungeonContract.FoodDungeon.COLUMN_NAME_EXPIRATION+"=?";
                    db.update(TABLE_NAME,contentValues,WHERE,new String[]{expiredDate});
                }

            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        cursor.close();
    }
}
