package com.diet.dietclash;
//More modular by making a Integral Achivement class and Time Achievement class

/**
 * This class represents an Achievement entity that contains an achievement's
 * title, description, and progress.
 */
public class Achievement {

    private String title;
    private String description;
    private int completed;
    private int total;

    public Achievement(String title, String description, int part,int total){
        this.title = title;
        this.description = description;
        this.completed = part;
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setCompleted(int part){
        this.completed = part;
    }
    public int getCompleted(){
        return completed;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return total;
    }

    /**
     * This method computes the progress percentage towards completing an achievement.
     *
     * @return the progress percentage.
     * @throws ArithmeticException if dividing by zero.
     */
    public double getPercentage() throws ArithmeticException {
        if(total <= 0){throw new ArithmeticException("Cannot divide by zero");}
        return (completed / total) * 100;
    }
}
