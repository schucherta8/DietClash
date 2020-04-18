package com.diet.dietclash;

public class MediumMonster extends AbstractMonster {


    public MediumMonster(MONSTER_TYPE type, int health, int meatServings, int fruitServings,
                         int dairyServings, int veggieServings, String expiration, boolean defeated) {
        super(type, health+12, meatServings+3, fruitServings+3,
                dairyServings+3, veggieServings+3, expiration, defeated);
    }

}
