package com.diet.dietclash;

public class Monster {
    private int health;
    private int meatServings;
    private int fruitServings;
    private int dairyServings;
    private int veggieServings;
    private int duration;

    public Monster(MONSTER_TYPE type, int fruitServings, int meatServings,
                   int dairyServings, int veggieServings,int duration){
        this.meatServings = meatServings;
        this.fruitServings = fruitServings;
        this.dairyServings = dairyServings;
        this.veggieServings = veggieServings;
        this.duration = duration;
    }

    public int getHealth() {
        return health;
    }

    public void setMeatServings(int meatServings) {
        this.meatServings = meatServings;
    }

    public int getMeatServings() {
        return meatServings;
    }

    public void setFruitServings(int fruitServings) {
        this.fruitServings = fruitServings;
    }

    public int getFruitServings() {
        return fruitServings;
    }

    public void setDairyServings(int dairyServings) {
        this.dairyServings = dairyServings;
    }

    public int getDairyServings() {
        return dairyServings;
    }

    public void setVeggieServings(int veggieServings) {
        this.veggieServings = veggieServings;
    }

    public int getVeggieServings() {
        return veggieServings;
    }
}
