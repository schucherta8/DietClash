package com.diet.dietclash.FoodDB;

import android.provider.BaseColumns;

public final class FoodDungeonContract {
    private FoodDungeonContract() {};

    /**
     * Representation of Dungeon Table:
     * ---------------------------------------------------------------------------------------------------------
     * |monster_type|max_health|meat_servings|fruit_servings|dairy_servings|veggie_servings|expiration|defeated|
     * ---------------------------------------------------------------------------------------------------------
     */
    public static class FoodDungeon implements BaseColumns {
        public static final String TABLE_NAME = "dungeon";
        public static final String COLUMN_NAME_MONSTER_TYPE = "monster_type";
        public static final String COLUMN_NAME_MAX_HEALTH = "max_health";
        public static final String COLUMN_NAME_MEAT_SERVINGS = "meat_servings";
        public static final String COLUMN_NAME_FRUIT_SERVINGS = "fruit_servings";
        public static final String COLUMN_NAME_DAIRY_SERVINGS = "dairy_servings";
        public static final String COLUMN_NAME_VEGGIE_SERVINGS = "veggie_servings";
        public static final String COLUMN_NAME_EXPIRATION = "expiration";
        public static final String COLUMN_NAME_DEFEATED = "defeated";
    }
}
