package com.diet.dietclash;

public interface Monster {
    public int getHealthRemainder();

    public void setHealth(int health);

    public int getHealth();

    public void setType(MONSTER_TYPE type);

    public MONSTER_TYPE getType();

    public void setMeatServings(int meatServings);

    public int getMeatServings();

    public void setFruitServings(int fruitServings);

    public int getFruitServings();

    public void setDairyServings(int dairyServings);

    public int getDairyServings();

    public void setVeggieServings(int veggieServings);

    public int getVeggieServings();

    public void setExpiration(String expiration);

    public String getExpiration();

    public void setDefeated(boolean defeated);

    public boolean isDefeated();
}
