package com.diet.dietclash;
//More modular by making a Integral Achivement class and Time Achievement class

/**
 * This class represents an Achievement entity that contains an achievement's
 * title, description, and progress.
 */
public class Achievement {

    private String title;
    private String description;
    private int progress;
    private int goal;
    private boolean isCompleted;

    /**
     * Constructor for Achievement object
     * @param title is a string that contains an achievement's title
     * @param description is a string that contains an achievement's description
     * @param progress is an integer that contains the progress to an achievement's goal
     * @param goal is an integer that contains the goal of an achievement
     */
    public Achievement(String title, String description,
                       int progress,int goal, boolean isCompleted){
        this.title = title;
        this.description = description;
        this.progress = progress;
        this.goal = goal;
        this.isCompleted = isCompleted;
    }

    public void setProgress(int progress){
        this.progress = progress;
    }
    public void setGoal(int goal){
        this.goal = goal;
    }
    public void setIsCompleted(boolean isCompleted){this.isCompleted = isCompleted;}
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public int getProgress(){
        return progress;
    }
    public int getGoal(){
        return goal;
    }
    public boolean getIsCompleted(){return isCompleted;}

    /**
     * This method computes the progress percentage towards completing an achievement.
     *
     * @return the progress percentage.
     * @throws ArithmeticException if dividing by zero.
     * @throws IllegalStateException if progress is negative
     */
    public double getPercentage() throws ArithmeticException, IllegalStateException {
        if(progress < 0){throw new IllegalStateException("Cannot be negative");}
        if(goal <= 0){throw new ArithmeticException("Cannot divide by zero");}
        return (progress / goal) * 100;
    }


}
