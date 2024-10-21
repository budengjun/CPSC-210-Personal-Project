package model;

public class Game {
    private String name;
    private double price;
    private int numAchievements;
    private boolean playingStatus;


    public Game(String name, double price, int numAchievements) {
        this.name = name;
        this.price = price;
        this.numAchievements = numAchievements;
        this.playingStatus = false;
}

    public String getName() {
        return name;
}

    public double getPrice() {
        return price;
}

    public int getNumAchievements() {
        return numAchievements;
}

    public boolean getplayingStatus() {
        return playingStatus;
    }

    public void markAsNotPlayed() {
        playingStatus = false;
    }

    public void markAsPlayed() {
        playingStatus = true;
    }

}