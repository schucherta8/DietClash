package com.diet.dietclash.FoodDB;

import android.provider.BaseColumns;

public final class FoodEntryContract {
    private FoodEntryContract() {}

    /* Inner class that defines the table contents */
    public static class FoodEntry implements BaseColumns {
        public static final String TABLE_NAME = "food";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
