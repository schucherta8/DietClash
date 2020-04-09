package com.diet.dietclash.FoodDB;

import android.provider.BaseColumns;

public final class FoodServingsContract {
    private FoodServingsContract(){}

    /* Inner class that defines the table contents */
    public static class FoodServings implements BaseColumns {
        public static final String TABLE_NAME = "servings";
        public static final String COLUMN_NAME_MEAT = "meat";
        public static final String COLUMN_NAME_FRUIT = "fruit";
        public static final String COLUMN_NAME_DAIRY = "dairy";
        public static final String COLUMN_NAME_VEGGIE = "veggie";
        public static final String COLUMN_NAME_START_DATE = "start";
        public static final String COLUMN_NAME_DURATION_DAYS = "duration";
    }
}
