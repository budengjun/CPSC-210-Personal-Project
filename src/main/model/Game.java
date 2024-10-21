package model;

    //represent a Game and its variables
public class Game {
    private String name;
    private double price;
    private final int numAchievements;
    private int numUnlockedAchievements;
    private boolean playingStatus;

    //EFFECTS: build a constructor for Game, with the given name, price and numAchievements
    public Game(String name, double price, int numAchievements) {
        this.name = name;
        this.price = price;
        this.numAchievements = numAchievements;
        this.numUnlockedAchievements = 0;
        this.playingStatus = false;
    }

    //EFFECTS: return name
    public String getName() {
        return name;
    }

    //EFFECTS: return price
    public double getPrice() {
        return price;
    }

    //EFFECTS: return number of achievements
    public int getNumAchievements() {
        return numAchievements;

    }

    //EFFECTS: return number of unlocked achievements
    public int getNumUnlockedAchievements() {
        return numUnlockedAchievements;
    }

    //EFFECTS: return status of playing or not
    public boolean getPlayingStatus() {
        return playingStatus;
    }

    //MODIFIES: this
    //EFFECTS: change status to not played
    public void markAsNotPlayed() {
        playingStatus = false;
    }

    //MODIFIES: this
    //EFFECTS: change status to played
    public void markAsPlayed() {
        playingStatus = true;
    }

    //MODIFIES: this
    //EFFECTS: play n times of game, and unlock n achievement(s)
    public void playGame(int n) {
        if (numUnlockedAchievements + n < numAchievements) {
            numUnlockedAchievements = numUnlockedAchievements + n;
        }

        else if (numUnlockedAchievements + n >= numAchievements) {
            numUnlockedAchievements = numAchievements;
        }
    }

}