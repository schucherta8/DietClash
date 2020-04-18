package com.diet.dietclash;

public class MonsterFactory {

    public static Monster generateMonster(MONSTER_TYPE type, int health, int meatServings,
                                          int fruitServings, int dairyServings, int veggieServings,
                                          String expiration, boolean defeated){
        switch (type){
            case EASY:
                return new EasyMonster(type,health,meatServings,fruitServings,
                        dairyServings,veggieServings,expiration,defeated,1);
            case MEDIUM:
                return new MediumMonster(type,health,meatServings,fruitServings,
                        dairyServings,veggieServings,expiration,defeated,3);
            case HARD:
                return new HardMonster(type,health,meatServings,fruitServings,
                        dairyServings,veggieServings,expiration,defeated,5);
                default:
                    return null;
        }
    }
}
