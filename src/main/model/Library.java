package model;

import java.util.List;

import java.util.ArrayList;

// a library would have a gamelist inside
public class Library {
    private List<Game> gameList;


    //EFFECTS: construct a library
    public Library() {
        gameList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add a given game to gamelist
    public void addGame(Game game) {
        gameList.add(game);
    }

    //REQUIRES: the given game is in gamelist
    //MODIFIES: this
    //EFFECTS: remove a given game from gamelist
    public void removeGame(Game game) {
        gameList.remove(game);
    }

    //EFFECTS: return gamelist
    public List<Game> getGameList() {
        return gameList;
    }

    //MODIFIES: this
    //EFFECTS: play a given game and mark it as played, 
    //at the same time increase number of unlocked achievements by n times
    public void playGames(Game game, int n) {
        game.markAsPlayed();
        game.playGame(n);
    }
}
