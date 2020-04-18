package com.diet.dietclash;

public class MediumMonster extends AbstractMonster {


    public MediumMonster(MONSTER_TYPE type, int health, int meatServings, int fruitServings,
                         int dairyServings, int veggieServings, String expiration, boolean defeated) {
        super(type, health, meatServings, fruitServings, dairyServings, veggieServings,
                expiration, defeated);
    }

    public MediumMonster(MONSTER_TYPE type, int health, int meatServings, int fruitServings,
                       int dairyServings, int veggieServings, String expiration, boolean defeated,
                       int multiplier) {
        super(type, health, meatServings, fruitServings, dairyServings, veggieServings,
                expiration, defeated, multiplier);
    }

}
