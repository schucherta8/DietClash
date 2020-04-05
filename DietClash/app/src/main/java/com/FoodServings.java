package com;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="food")
public class FoodServings {

    //Primary Key
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    //Category Entry
    @ColumnInfo(name = "category")
    private String category;

    //Category Entry
    @ColumnInfo(name = "amount")
    private int amount;

    //Category Entry
    @ColumnInfo(name = "date")
    private String date;

    public FoodServings(String category, int amount, String date){
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
