package model;

import org.json.JSONObject;

import persistence.Writable;

//represent a game and its variables
public class Game implements Writable {
    private String name;
    private double price;
    private final int numAchievements;
    private int numUnlockedAchievements;
    private boolean playingStatus;
    private double popularIndex;

    //EFFECTS: build a constructor for game, with the given name, price, number of achievements and popular index
    public Game(String name, double price, int numAchievements, double popularIndex) {
        this.name = name;
        this.price = price;
        this.numAchievements = numAchievements;
        this.numUnlockedAchievements = 0;
        this.playingStatus = false;
        this.popularIndex = popularIndex;
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

    public double getPopularIndex() {
        return popularIndex;

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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        json.put("numAchievements", numAchievements);
        json.put("popularIndex", popularIndex);
        return json;
    }
}