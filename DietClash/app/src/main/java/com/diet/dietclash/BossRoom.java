package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.diet.dietclash.FoodDB.FoodDBHelper;
import com.diet.dietclash.FoodDB.FoodDungeonContract;
import com.diet.dietclash.FoodDB.FoodEntryContract;
import com.diet.dietclash.FoodDB.FoodServingsContract;

public class BossRoom extends AppCompatActivity {

    private ProgressBar monsterHealthBar;
    private TextView status;
    private TextView progress;
    private TextView total;

    private SQLiteDatabase db;

    private Monster monster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_room);

        //readFoodServingGoals();
        monsterHealthBar = findViewById(R.id.monster_healthbar);
        status = findViewById(R.id.monster_status);
        progress = findViewById(R.id.monster_health_progress);
        total = findViewById(R.id.monster_health_total);
        //If monster is not defeated
        if(false){
            //monsterHealthBar.setProgress();//Calculate health remaining
            monsterHealthBar.setMax(monster.getHealth());
            status.setText(R.string.monster_dead);
        } else{
            monsterHealthBar.setMax(monster.getHealth());
            status.setText(R.string.monster_alive);
            //progress.setText();
        }
    }

    /**
     * Read in the most recent monster added to the table.
     *
     */
   void readFoodServingGoals(){
       //Access the database
        FoodDBHelper helper = new FoodDBHelper(getApplicationContext());
        db = helper.getReadableDatabase();

        //Values needed to create the query
        String TABLE_NAME = FoodDungeonContract.FoodDungeon.TABLE_NAME;
        String[] COLUMNS = {FoodDungeonContract.FoodDungeon.COLUMN_NAME_MONSTER_TYPE,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_HEALTH,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_MEAT_SERVINGS,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_FRUIT_SERVINGS,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_DAIRY_SERVINGS,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_VEGGIE_SERVINGS,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_DURATION,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_DEFEATED};
        //Most recent Monster aka most recent start date or latest end date
        String SORT_ORDER = FoodDungeonContract.FoodDungeon.COLUMN_NAME_DURATION + " DESC";

        //Query the table for the serving goal per food group.
        Cursor cursor = db.query(TABLE_NAME,COLUMNS,null,null,
                null,null,SORT_ORDER);

       //If there exist a record in the table. Get the all serving amounts per food group.
        if(cursor.moveToNext()){
            int meatServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodServingsContract.FoodServings.COLUMN_NAME_MEAT));
            int fruitServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT));
            int dairyServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY));
            int veggieServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE));
//            monster = new Monster(MONSTER_TYPE.EASY,meatServingGoal,
//                    fruitServingGoal,dairyServingGoal,veggieServingGoal);
        }
        cursor.close();
    }

    /**
     * Update the monster health bar after each successful entry.
     *
     * If the person eats more than the serving goal, dont lose any more health
     *
     * TODO: UPDATE HEALTH BAR AFTER EVERY ENTRY SUCCESSFULLY ACCEPTED, MOVE TO LETS EAT?
     *
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

    //Reset monster? MIGHT BE HANDLED

}
