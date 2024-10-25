package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import persistence.JsonReader;
import persistence.JsonWriter;


import model.Game;

import model.Library;


public class SteamApp {
    private Game gameA;
    private Game gameB;
    private Game gameC;
    private Game gameD;
    private Game gameE;
    private Game gameF;
    private Library library;
    private Scanner input;
    private static final String JSON_STORE = "./data/library.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the teller application
    public SteamApp() throws FileNotFoundException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runSteam();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runSteam() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("p")) {
            doPlay();
        } else if (command.equals("a")) {
            doAdd();
        } else if (command.equals("r")) {
            doRemove();
        } else if (command.equals("v")) {
            doView();
        } else if (command.equals("e")) {
            doEstimate();
        } else if (command.equals("s")) {
            doSee();
        } else if (command.equals("sa")) {
            saveLibrary();
        } else if (command.equals("l")) {
            loadLibrary();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes steam account
    private void init() {
        gameA = new Game("Baldur's Gate 3", 60, 54, 80.1);
        gameB = new Game("Grand Theft Auto V", 39.99, 77, 61.2);
        gameC = new Game("Dead By Daylight", 18.89, 264, 57.5);
        gameD = new Game("Cyberpunk 2077", 75, 57, 48.4);
        gameE = new Game("Red Dead Redemption 2", 79.99, 51, 83.3);
        gameF = new Game("Sekiro: Shadows Die Twice", 59.99, 34, 20.6);
        library = new Library();
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add game");
        System.out.println("\tr -> remove game");
        System.out.println("\tp -> play game");
        System.out.println("\ts -> see games in your gamelist");
        System.out.println("\tv -> view popular index");
        System.out.println("\te -> estimate value of your steam account");
        System.out.println("\tsa -> save library to file");
        System.out.println("\tl -> load library from file");
        System.out.println("\tq -> quit");
    }

    private void doAdd() {
        Game selected = selectGame();
        if (!library.getGameList().contains(selected)) {
            library.addGame(selected);
        }
    }

    private void doRemove() {
        Game selected = selectGame();
        if (library.getGameList().contains(selected)) {
            library.removeGame(selected);
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts to play 10 times
    private void doPlay() {
        Game selected = selectGame();
        library.playGames(selected, 10);
        
        
    }

    // MODIFIES: this
    // EFFECTS: conducts to view how popular the given game is
    private void doView() {
        Game selected = selectGame();
        double c = selected.getPopularIndex();
        System.out.println("There are " + c + " thousand(s) online players are playing at this time");

    }

    private void doSee() {
        System.out.println(library.getNameGameList());
    }

    // MODIFIES: this
    // EFFECTS: conducts to estimate the value of steam account

    private void doEstimate() {
        double sellPrice = 0;
        for (Game game : library.getGameList()) {
            double c = game.getPopularIndex();
            double p = game.getPrice();
            if (p == 0) {
                p = 50;
            }
            int u = game.getNumUnlockedAchievements();
            int a = game.getNumAchievements();
            double ua = u / a;
            if (u == 0) {
                ua = 0.5;
            }
            double sellPriceForOneGame = c / 100 * ua + p;
            sellPrice = sellPrice + sellPriceForOneGame;
        }
        System.out.println(sellPrice);
    }

    // EFFECTS: prompts user to select a game and returns it
    private Game selectGame() {
        String selection = "";  // force entry into loop
        while (!(selection.equals("b") || selection.equals("g") || selection.equals("d")
            || selection.equals("c") || selection.equals("r") || selection.equals("s"))) {
            System.out.println("b for Baldur's Gate 3");
            System.out.println("g for Grand Theft Auto V");
            System.out.println("d for Dead By Daylight");
            System.out.println("c for Cyberpunk 2077");
            System.out.println("r for Red Dead Redemption 2");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("b")) {
            return gameA;
        } else if (selection.equals("g")) {
            return gameB;
        } else if (selection.equals("d")) {
            return gameC;
        } else if (selection.equals("c")) {
            return gameD;
        } else {
            return gameE;
        }
    }

    // EFFECTS: saves the library to file
    private void saveLibrary() {
        try {
            jsonWriter.open();
            jsonWriter.write(library);
            jsonWriter.close();
            System.out.println("Saved " + "library" + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads library from file
    private void loadLibrary() {
        try {
            library = jsonReader.read();
            System.out.println("Loaded " + "library" + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}

