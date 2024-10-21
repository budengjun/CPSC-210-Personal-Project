package ui;

import java.util.Scanner;

import model.Game;

import model.Library;


public class SteamApp {
    private Game gameA;
    private Game gameB;
    private Game gameC;
    private Library library;
    private Scanner input;

    // EFFECTS: runs the teller application
    public SteamApp() {
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
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes steam account
    private void init() {
        gameA = new Game("Baldur's Gate 3", 60, 54, 80.1);
        gameB = new Game("Grand Theft Auto V", 39.9, 77, 61.2);
        gameC = new Game("Dead By Daylight", 18.8, 264, 57.5);
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
        System.out.println("\tq -> quit");
    }

    private void doAdd() {
        Game selected = selectGame();
        library.addGame(selected);
    }

    private void doRemove() {
        Game selected = selectGame();
        library.removeGame(selected);
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
            int u = game.getNumUnlockedAchievements();
            int a = game.getNumAchievements();
            double ua = Math.min(1.5, u / a + 1);
            double sellPriceForOneGame = c / 100 * p * ua;
            sellPrice = sellPrice + sellPriceForOneGame;
        }
        System.out.println(sellPrice);
    }

    // EFFECTS: prompts user to select a game and returns it
    private Game selectGame() {
        String selection = "";  // force entry into loop

        while (!(selection.equals("b") || selection.equals("g") || selection.equals("d"))) {
            System.out.println("b for Baldur's Gate 3");
            System.out.println("g for Grand Theft Auto V");
            System.out.println("d for Dead By Daylight");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("b")) {
            return gameA;
        } else if (selection.equals("g")) {
            return gameB;
        } else {
            return gameC;
        }
    }
}

