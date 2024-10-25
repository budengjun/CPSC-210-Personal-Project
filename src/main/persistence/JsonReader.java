package persistence;

import model.Game;
import model.Library;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Library read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLibrary(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses library from JSON object and returns it
    private Library parseLibrary(JSONObject jsonObject) {
        Library li = new Library();
        addGames(li, jsonObject);
        return li;
    }

    // MODIFIES: li
    // EFFECTS: parses games from JSON object and adds them to library
    private void addGames(Library li, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("gameList");
        for (Object json : jsonArray) {
            JSONObject nextGame = (JSONObject) json;
            addGame(li, nextGame);
        }
    }

    // MODIFIES: li
    // EFFECTS: parses game from JSON object and adds it to gamelist
    private void addGame(Library li, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        double price = jsonObject.getDouble("price");
        int numAchievements = jsonObject.getInt("numAchievements");
        double popularIndex = jsonObject.getDouble("popularIndex");
        Game game = new Game(name, price, numAchievements, popularIndex);
        li.addGame(game);
    }
}
