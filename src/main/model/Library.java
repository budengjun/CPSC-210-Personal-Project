package model;

import java.util.List;

import java.util.ArrayList;

import model.Game;

public class Library {
    private List<Game> gameList;



    public Library() {
        gameList = new ArrayList<>();
    }

    public void addGame(Game game) {
        gameList.add(game);
    }

    public void removeGame(Game game) {
        gameList.remove(game);
    }

    public List<Game> viewGameList() {
        return gameList;
    }    
}
