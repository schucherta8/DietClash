package com.diet.dietclash;

public class EasyMonster extends AbstractMonster {


    public EasyMonster(MONSTER_TYPE type, int health, int meatServings, int fruitServings,
                       int dairyServings, int veggieServings, String expiration, boolean defeated) {
        super(type, health + 5, meatServings, fruitServings,
                dairyServings, veggieServings, expiration, defeated);
    }

    @Override
    public int getHealthRemainder() {
        int remainderHealth = health;
        if(meatServings > 0){
            remainderHealth -= meatServings;
        }
        if(fruitServings > 0){
            remainderHealth -= fruitServings;
        }
        if(dairyServings > 0){
            remainderHealth -= dairyServings;
        }
        if(veggieServings > 0){
            remainderHealth -= veggieServings;
        }
        return remainderHealth;
    }

}
