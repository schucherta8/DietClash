package com.diet.dietclash.FoodDB;

import android.provider.BaseColumns;

public final class FoodServingsContract {
    private FoodServingsContract(){}

    /* Inner class that defines the table contents */
    public static class FoodServings implements BaseColumns {
        public static final String TABLE_NAME = "servings";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_START_DATE = "start";
        public static final String COLUMN_NAME_END_DATE = "end";
    }
}
