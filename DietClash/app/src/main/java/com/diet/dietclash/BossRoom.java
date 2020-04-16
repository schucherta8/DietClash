package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.diet.dietclash.FoodDB.FoodDBHelper;
import com.diet.dietclash.FoodDB.FoodEntryContract;
import com.diet.dietclash.FoodDB.FoodServingsContract;

public class BossRoom extends AppCompatActivity {

    ProgressBar monsterHealthBar;
    private SQLiteDatabase db;

    private int meatServingGoal;
    private int fruitServingGoal;
    private int dairyServingGoal;
    private int veggieServingGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_room);

        //readFoodServingGoals();
        monsterHealthBar = findViewById(R.id.progressBar);
        //setMonsterHealth();
    }

    /**
     * Read in the user's serving goals for each food group.
     * TODO: Make this on a new thread
     */
   void readFoodServingGoals(){
       //Access the database
        FoodDBHelper helper = new FoodDBHelper(getApplicationContext());
        db = helper.getReadableDatabase();

        //Values needed to create the query
        String TABLE_NAME = FoodServingsContract.FoodServings.TABLE_NAME;
        String[] COLUMNS = {FoodServingsContract.FoodServings._ID,
                FoodServingsContract.FoodServings.COLUMN_NAME_MEAT,
                FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT,
                FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY,
                FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE};
        String SORT_ORDER = FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE + " DESC";

        //Query the table for the serving goal per food group.
        Cursor cursor = db.query(TABLE_NAME,COLUMNS,null,null,
                null,null,SORT_ORDER);

        //10 veggies 10 meats 10 dairy 10 fruit = 40 health -> easy(8 + 8+ 8+ 8) = 32 health, medium = 40 health, hard =  medium + 5 = 60 health

       //If there exist a record in the table. Get the all serving amounts per food group.
        if(cursor.moveToNext()){
            meatServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodServingsContract.FoodServings.COLUMN_NAME_MEAT));
            fruitServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT));
            dairyServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY));
            veggieServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE));
        }
        cursor.close();
    }

    /**
     * Set the monster health bar to total servings from all the serving groups.
     */
    void setMonsterHealth(){
        monsterHealthBar.setMax(meatServingGoal+fruitServingGoal+dairyServingGoal+veggieServingGoal);
    }

    /**
     * Update the monster health bar after each successful entry.
     * TODO: Needs to be on a different thread
     * If the person eats more than the serving goal, dont lose any more health
     *
     * TODO: NEEDS: UPDATE HEALTH BAR AFTER EVERY ENTRY SUCCESSFULLY ACCEPTED AND
     * TODO: STORE THESE RESULTS SOMEWHERE
     */
    void updateMonsterCurrentHealth(){
        String TABLE_NAME = FoodEntryContract.FoodEntry.TABLE_NAME;
        String[] COLUMNS = {FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY,
                FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT};
        String SELECTION = FoodEntryContract.FoodEntry.COLUMN_NAME_DATE + " >= ?";
        String[] SELECTION_ARGS = {};
        String SORT_ORDER = FoodEntryContract.FoodEntry.TABLE_NAME + " DESC";
        //Where the date is todays date
        Cursor cursor = db.query(TABLE_NAME,COLUMNS,
                null,null,null,null,SORT_ORDER);

        cursor.close();
    }

    /**
     * Checks if the monster has been defeated.
     *
     * @return the status of the monster
     */
    boolean isMonsterDefeated(){
        return true;
    }

    //Reset monster? MIGHT BE HANDLED

}
