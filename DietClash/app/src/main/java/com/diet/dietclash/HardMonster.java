package com.diet.dietclash;

public class HardMonster extends AbstractMonster {


    public HardMonster(MONSTER_TYPE type, int health, int meatServings, int fruitServings,
                       int dairyServings, int veggieServings, String expiration, boolean defeated) {
        super(type, health, meatServings+6, fruitServings+6,
                dairyServings+6, veggieServings+6, expiration, defeated);
    }
}
