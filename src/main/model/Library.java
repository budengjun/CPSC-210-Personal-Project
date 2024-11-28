package model;

import java.util.List;

import persistence.Writable;

import org.json.JSONArray;

import org.json.JSONObject;

import java.util.ArrayList;

// a library would have a gamelist inside
public class Library implements Writable {
    private List<Game> gameList;


    //EFFECTS: construct a library
    public Library() {
        gameList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add a given game to gamelist
    public void addGame(Game game) {
        gameList.add(game);
        EventLog.getInstance().logEvent(new Event("Added " + game.getName() + " to the game list."));
    }

    //REQUIRES: the given game is in gamelist
    //MODIFIES: this
    //EFFECTS: remove a given game from gamelist
    public void removeGame(Game game) {
        gameList.remove(game);
        EventLog.getInstance().logEvent(new Event("Removed " + game.getName() + " from the game list."));
    }

    //EFFECTS: return gamelist
    public List<Game> getGameList() {
        return gameList;
    }

    public List<String> getNameGameList() {
        List<String> list = new ArrayList<>();
        for (Game game : gameList) {
            list.add(game.getName());
        }
        return list;
    }

    //MODIFIES: this
    //EFFECTS: play a given game and mark it as played, 
    //at the same time increase number of unlocked achievements by n times
    public void playGames(Game game, int n) {
        game.markAsPlayed();
        game.playGame(n);
        EventLog.getInstance().logEvent(new Event("Played " + game.getName() + " by " + n + " times."));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("gameList", gamesToJson());
        return json;
    }

    // EFFECTS: returns things in this library as a JSON array
    private JSONArray gamesToJson() {
        JSONArray jsonArray = new JSONArray();
    
        for (Game g : gameList) {
            jsonArray.put(g.toJson());
        }
    
        return jsonArray;
    }
}
