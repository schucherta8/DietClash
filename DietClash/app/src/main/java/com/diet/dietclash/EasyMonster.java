package com.diet.dietclash;

public class EasyMonster extends AbstractMonster {


    public EasyMonster(MONSTER_TYPE type, int health, int meatServings, int fruitServings,
                       int dairyServings, int veggieServings, String expiration, boolean defeated) {
        super(type, health + 4, meatServings+1, fruitServings+1,
                dairyServings+1, veggieServings+1, expiration, defeated);
    }

}
