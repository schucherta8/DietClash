package com.diet.dietclash;

public abstract class AbstractMonster implements Monster {
    protected MONSTER_TYPE type;
    protected int health;
    protected int meatServings;
    protected int fruitServings;
    protected int dairyServings;
    protected int veggieServings;
    protected String expiration;
    protected boolean defeated;

    protected AbstractMonster(MONSTER_TYPE type, int health, int meatServings,int fruitServings,
                           int dairyServings, int veggieServings, String expiration, boolean defeated){
        this.type = type;
        this.health = health;
        this.meatServings = meatServings;
        this.fruitServings = fruitServings;
        this.dairyServings = dairyServings;
        this.veggieServings = veggieServings;
        this.expiration = expiration;
        this.defeated = defeated;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setType(MONSTER_TYPE type) {
        this.type = type;
    }

    public MONSTER_TYPE getType() {
        return type;
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

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setDefeated(boolean defeated) {
        this.defeated = defeated;
    }

    public boolean isDefeated() {
        return defeated;
    }
}
