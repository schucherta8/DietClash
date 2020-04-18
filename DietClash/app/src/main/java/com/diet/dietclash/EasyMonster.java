package com.diet.dietclash;

public class EasyMonster extends AbstractMonster {


    public EasyMonster(MONSTER_TYPE type, int health, int meatServings, int fruitServings,
                       int dairyServings, int veggieServings, String expiration, boolean defeated) {
        super(type, health, meatServings, fruitServings, dairyServings, veggieServings,
                expiration, defeated);
    }

    public EasyMonster(MONSTER_TYPE type, int health, int meatServings, int fruitServings,
                       int dairyServings, int veggieServings, String expiration, boolean defeated,
                       int multiplier) {
        super(type, health, meatServings, fruitServings, dairyServings, veggieServings,
                expiration, defeated, multiplier);
    }

}
