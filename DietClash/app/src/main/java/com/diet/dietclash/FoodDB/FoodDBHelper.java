package com.diet.dietclash.FoodDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 5;
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
            " ("+ FoodServingsContract.FoodServings._ID + " INTEGER PRIMARY KEY," +
            FoodServingsContract.FoodServings.COLUMN_NAME_MEAT+" INTEGER," +
            FoodServingsContract.FoodServings.COLUMN_NAME_FRUIT+" INTEGER," +
            FoodServingsContract.FoodServings.COLUMN_NAME_DAIRY+" INTEGER," +
            FoodServingsContract.FoodServings.COLUMN_NAME_VEGGIE+" INTEGER," +
            FoodServingsContract.FoodServings.COLUMN_NAME_START_DATE+" TEXT," +
            FoodServingsContract.FoodServings.COLUMN_NAME_DURATION_DAYS+" INTEGER)";

    /**
     * CREATE TABLE achievements {
     *     id Int,
     *     title Text,
     *     description Text,
     *     progress Int,
     *     goal Int,
     *     completed Int(0 or 1);
     * }
     * ------------------------------------------------------------------------------------------------------
     * |achievement_title|achievement_description|achievement_progress|achievement_goal|achievement_completed|
     * ------------------------------------------------------------------------------------------------------
     * |       TEXT      |          TEXT         |      INTEGER       |     INTEGER    |     INTEGER{0,1}   |
     * ------------------------------------------------------------------------------------------------------
     */
    private static final String CREATE_STRING_ACHIEVEMENTS = "CREATE TABLE "
            + FoodAchievementsContract.FoodAchievements.TABLE_NAME
            + " ("+ FoodAchievementsContract.FoodAchievements._ID + " INTEGER PRIMARY KEY,"
            + FoodAchievementsContract.FoodAchievements.COLUMN_NAME_TITLE + " TEXT,"
            + FoodAchievementsContract.FoodAchievements.COLUMN_NAME_DESCRIPTION + " TEXT,"
            + FoodAchievementsContract.FoodAchievements.COLUMN_NAME_PROGRESS + " INTEGER,"
            + FoodAchievementsContract.FoodAchievements.COLUMN_NAME_GOAL + " INTEGER,"
            + FoodAchievementsContract.FoodAchievements.COLUMN_NAME_COMPLETED + " INTEGER)";

    /**
     * Representation of Dungeon Table:
     * ---------------------------------------------------------------------------------------------------------
     * |monster_type|max_health|meat_servings|fruit_servings|dairy_servings|veggie_servings|expiration|defeated|
     * ---------------------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------------------
     * |     TEXT   |  INTEGER |  INTEGER     |   INTEGER   |   INTEGER    |    INTEGER    | TEXT  |INTEGER{0,1}|
     * ---------------------------------------------------------------------------------------------------------
     */
    private static final String CREATE_STRING_DUNGEON = "CREATE TABLE "
            + FoodDungeonContract.FoodDungeon.TABLE_NAME
            + " (" + FoodDungeonContract.FoodDungeon._ID + " INTEGER PRIMARY KEY,"
            + FoodDungeonContract.FoodDungeon.COLUMN_NAME_MONSTER_TYPE + " TEXT,"
            + FoodDungeonContract.FoodDungeon.COLUMN_NAME_MAX_HEALTH + " INTEGER,"
            + FoodDungeonContract.FoodDungeon.COLUMN_NAME_MEAT_SERVINGS + " INTEGER,"
            + FoodDungeonContract.FoodDungeon.COLUMN_NAME_FRUIT_SERVINGS + " INTEGER,"
            + FoodDungeonContract.FoodDungeon.COLUMN_NAME_DAIRY_SERVINGS + " INTEGER,"
            + FoodDungeonContract.FoodDungeon.COLUMN_NAME_VEGGIE_SERVINGS + " INTEGER,"
            + FoodDungeonContract.FoodDungeon.COLUMN_NAME_EXPIRATION + " TEXT,"
            + FoodDungeonContract.FoodDungeon.COLUMN_NAME_DEFEATED + " INTEGER)";
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

    /**
     * DROP_STRING_ACHIEVEMENTS Constant:
     * FoodAchievementsContract Drop table
     * DROP TABLE IF EXISTS achievements;
     */
    private static final String DROP_STRING_ACHIEVEMENTS = "DROP TABLE IF EXISTS "
            + FoodAchievementsContract.FoodAchievements.TABLE_NAME;

    /**
     * DROP_STRING_DUNGEON Constant:
     * FoodDungeonContract Drop table
     * DROP TABLE IF EXISTS dungeon;
     */
    private static final String DROP_STRING_DUNGEON = "DROP TABLE IF EXISTS "
            + FoodDungeonContract.FoodDungeon.TABLE_NAME;

    public FoodDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create dungeon table
        db.execSQL(CREATE_STRING_DUNGEON);
        //Create achievements table
        db.execSQL(CREATE_STRING_ACHIEVEMENTS);
        //Create the servings table respecting referential integrity
        db.execSQL(CREATE_STRING_SERVINGS);
        //Create the food table
        db.execSQL(CREATE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop the Dungeon Table
        db.execSQL(DROP_STRING_DUNGEON);
        //Drop the achievements table
        db.execSQL(DROP_STRING_ACHIEVEMENTS);
        //Drop the servings table respecting referential integrity
        db.execSQL(DROP_STRING_SERVINGS);
        //Drop the food table
        db.execSQL(DROP_STRING);
        onCreate(db);
    }
}
