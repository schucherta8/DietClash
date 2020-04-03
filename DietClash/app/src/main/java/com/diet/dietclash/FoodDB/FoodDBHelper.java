package com.diet.dietclash.FoodDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Food.db";
    private static final String CREATE_STRING = "CREATE TABLE "+ FoodEntryContract.FoodEntry.TABLE_NAME +
            " ("+ FoodEntryContract.FoodEntry._ID + " INTEGER PRIMARY KEY," +
            FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY+" TEXT," +
            FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT+" INTEGER," +
            FoodEntryContract.FoodEntry.COLUMN_NAME_DATE+" TEXT)";
    private static final String DROP_STRING = "DROP TABLE IF EXISTS "+ FoodEntryContract.FoodEntry.TABLE_NAME;

    public FoodDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_STRING);
        onCreate(db);
    }
}
