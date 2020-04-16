package com.diet.dietclash.FoodDB;

import android.provider.BaseColumns;

public final class FoodAchievementsContract {
    private FoodAchievementsContract(){}

    /**
     * Representation of Achievement Table:
     * ------------------------------------------------------------------------------------------------------
     * |achievement_title|achievement_description|achievement_progress|achievement_goal|achievement_complete|
     * ------------------------------------------------------------------------------------------------------
     */
    public static class FoodAchievements implements BaseColumns {
        public static final String TABLE_NAME = "achievements";
        public static final String COLUMN_NAME_TITLE = "achievement_title";
        public static final String COLUMN_NAME_DESCRIPTION = "achievement_description";
        public static final String COLUMN_NAME_PROGRESS = "achievement_progress";
        public static final String COLUMN_NAME_GOAL = "achievement_goal";
        public static final String COLUMN_NAME_COMPLETED = "achievement_completed";
    }
}
