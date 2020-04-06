package com.diet.dietclash.FoodDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Food.db";

    /**
     * CREATE_STRING Constant:
     * FoodEntryContract Initialization
     * CREATE TABLE food{
     *     id Int,
     *     category TEXT,
     *     amount Int,
     *     date TEXT;
     * }
     */
    private static final String CREATE_STRING = "CREATE TABLE "+ FoodEntryContract.FoodEntry.TABLE_NAME +
            " ("+ FoodEntryContract.FoodEntry._ID + " INTEGER PRIMARY KEY," +
            FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY+" TEXT," +
            FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT+" INTEGER," +
            FoodEntryContract.FoodEntry.COLUMN_NAME_DATE+" TEXT)";


    /**
     * CREATE_STRING_SERVINGS Constant:
     * FoodServingsContract Initialization
     * CREATE TABLE servings{
     *     id Int,
     *     category TEXT,
     *     amount Int;
     * }
     */
    private static final String CREATE_STRING_SERVINGS = "CREATE TABLE "+ FoodServingsContract.FoodServings.TABLE_NAME +
            " ("+ FoodEntryContract.FoodEntry._ID + " INTEGER PRIMARY KEY," +
            FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY+" TEXT," +
            FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT+" INTEGER)";

    /**
     * DROP_STRING Constant:
     * FoodEntryContract Drop Table
     * DROP TABLE IF EXISTS food;
     */
    private static final String DROP_STRING = "DROP TABLE IF EXISTS "+ FoodEntryContract.FoodEntry.TABLE_NAME;

    /**
     * DROP_STRING_SERVINGS Constant:
     * FoodServingsContraction Drop Table
     * DROP TABLE IF EXISTS servings;
     */
    private static final String DROP_STRING_SERVINGS = "DROP TABLE IF EXISTS "+ FoodServingsContract.FoodServings.TABLE_NAME;

    public FoodDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create the servings table respecting referential integrity
        db.execSQL(CREATE_STRING_SERVINGS);
        //Create the food table
        db.execSQL(CREATE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the servings table respecting referential integrity
        db.execSQL(DROP_STRING_SERVINGS);
        //Drop the food table
        db.execSQL(DROP_STRING);
        onCreate(db);
    }
}
