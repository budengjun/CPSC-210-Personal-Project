package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import model.Game;
import model.Library;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;

import java.util.List;

public class ControlPanel extends JPanel {
    private JPanel controlPanel;
    private JScrollPane scrollPane;
    private static final String JSON_STORE = "./data/library.json";
    private JTextArea gameListDisplay;
    private Library library;
    private List<String> gamesName;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public ControlPanel() {
        library = new Library();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        controlPanel = new JPanel();
        createControlPanel();
        createGameListPanel();
        loadLibraryOption();
    }

    // Creates the panel to display the game list
    private void createGameListPanel() {
        gameListDisplay = new JTextArea();
        gameListDisplay.setEditable(false);
        scrollPane = new JScrollPane(gameListDisplay);
    }

    public void createControlPanel() {
        controlPanel.setLayout(new GridLayout(2, 4));

        // Add Game Button
        JButton addGameButton = new JButton("Add Game");
        addGameButton.addActionListener(e -> addGameDialog());
        controlPanel.add(addGameButton, BorderLayout.SOUTH);

        // View Games Button
        JButton viewGamesButton = new JButton("View Library");
        viewGamesButton.addActionListener(e -> viewGameListDisplay());
        controlPanel.add(viewGamesButton, BorderLayout.SOUTH);

        // Play Game Button
        JButton playGameButton = new JButton("Play Game");
        playGameButton.addActionListener(e -> playGameDialog());
        controlPanel.add(playGameButton, BorderLayout.SOUTH);

        // Remove Game Button
        JButton removeGameButton = new JButton("Remove Game");
        removeGameButton.addActionListener(e -> removeGameDialog());
        controlPanel.add(removeGameButton, BorderLayout.SOUTH);

        // Estimate Steam Account Value Button
        JButton estimateValueButton = new JButton("Estimate Value");
        estimateValueButton.addActionListener(e -> estimateAccountValue());
        controlPanel.add(estimateValueButton, BorderLayout.SOUTH);

        // Save Library Button
        JButton saveLibraryButton = new JButton("Save Library");
        saveLibraryButton.addActionListener(e -> saveLibrary());
        controlPanel.add(saveLibraryButton, BorderLayout.SOUTH);

        // Load Library Button
        JButton loadLibraryButton = new JButton("Load Library");
        loadLibraryButton.addActionListener(e -> loadLibrary());
        controlPanel.add(loadLibraryButton, BorderLayout.SOUTH);

        // Quit Button
        createQuitButton();
    }

    // Creates a quit button
    public void createQuitButton() {
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> quitApplication());
        controlPanel.add(quitButton, BorderLayout.SOUTH);
    }

    // Adds a game to the library through a dialog
    private void addGameDialog() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField achievementsField = new JTextField();
        JTextField popularIndexField = new JTextField();
        Object[] message = {
                "Game Name:", nameField,
                "Price:", priceField,
                "Achievements:", achievementsField,
                "Popular index", popularIndexField,
        };
        int option = JOptionPane.showConfirmDialog(controlPanel, message, "Add Game", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int achievements = Integer.parseInt(achievementsField.getText());
            double popularIndex = Double.parseDouble(popularIndexField.getText());
            library.addGame(new Game(name, price, achievements, popularIndex));
            updateGameListDisplay();
        }
    }

    // Plays a game through a dialog
    private void playGameDialog() {
        JTextField selectTimesField = new JTextField();
        String gameName = selectGame();
        Object[] message = {
                "How many times you want to play?", selectTimesField,
        };
        int option = JOptionPane.showConfirmDialog(controlPanel, message, "Play Game", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (gameName != null) {
                int timesToPlay = Integer.parseInt(selectTimesField.getText());
                for (Game game : library.getGameList()) {
                    if (game.getName().equalsIgnoreCase(gameName)) {
                        library.playGames(game, timesToPlay);
                        game.markAsPlayed();
                        JOptionPane.showMessageDialog(controlPanel,
                                "You played " + timesToPlay + " times: " + gameName);
                    }
                }
            }
        }
    }

    // Removes a game from the library through a dialog
    private void removeGameDialog() {
        String gameName = selectGame();
        if (gameName != null) {
            for (Game game : library.getGameList()) {
                if (game.getName().equalsIgnoreCase(gameName)) {
                    library.removeGame(game);
                    JOptionPane.showMessageDialog(controlPanel, "Removed: " + gameName);
                    updateGameListDisplay();
                }
            }
        }
    }

    // Displays the account value
    private void estimateAccountValue() {
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
            sellPrice += (c / 100 * ua) + p;
        }
        JOptionPane.showMessageDialog(controlPanel, "Steam Account Value: $" + sellPrice);
    }

    // Updates the game list display
    private void updateGameListDisplay() {
        gameListDisplay.setText("");
        for (Game game : library.getGameList()) {
            gameListDisplay.append(game.getName() + " - $" + game.getPrice() + "\n");
        }

    }

    // Views the game list
    private void viewGameListDisplay() {
        gamesName = new ArrayList<>();
        for (Game game : library.getGameList()) {
            gamesName.add(game.getName());
        }
        JOptionPane.showMessageDialog(controlPanel, gamesName);
        Object[] gameNames = library.getNameGameList().toArray();
        String gameName = (String) JOptionPane.showInputDialog(controlPanel, "Select a game:", "View Game",
                JOptionPane.PLAIN_MESSAGE, null, gameNames, gameNames.length > 0 ? gameNames[0] : null);
        if (gameName != null) {
            for (Game game : library.getGameList()) {
                if (game.getName().equalsIgnoreCase(gameName)) {
                    JOptionPane.showMessageDialog(controlPanel,
                            "Name: " + game.getName()
                                    + "\n" + " Price: " + game.getPrice() + "\n" + " Number of achievements: "
                                    + game.getNumAchievements() + "\n"
                                    + " Millions of players online: " + game.getPopularIndex());
                }
            }
        }
    }

    // Prompts user to save before quitting
    private void quitApplication() {
        int option = JOptionPane.showConfirmDialog(controlPanel, "Save before quitting?", "Quit",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            saveLibrary();
        }
        System.exit(0);
    }

    // Saves the library to a file
    private void saveLibrary() {
        try {
            jsonWriter.open();
            jsonWriter.write(library);
            jsonWriter.close();
            JOptionPane.showMessageDialog(controlPanel, "Library saved successfully!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(controlPanel, "Unable to save library: " + e.getMessage());
        }
    }

    // Loads the library from a file
    private void loadLibrary() {
        try {
            library = jsonReader.read();
            updateGameListDisplay();
            JOptionPane.showMessageDialog(controlPanel, "Library loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(controlPanel, "Unable to load library: " + e.getMessage());
        }
    }

    // Load library option at startup
    private void loadLibraryOption() {
        int option = JOptionPane.showConfirmDialog(
                controlPanel, "Load existing library?", "Load Library", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            loadLibrary();
        }
    }

    // Helper method to select a game from the library
    private String selectGame() {
        Object[] gameNames = library.getNameGameList().toArray();
        return (String) JOptionPane.showInputDialog(controlPanel, "Select a game:", "Select Game",
                JOptionPane.PLAIN_MESSAGE, null, gameNames, gameNames.length > 0 ? gameNames[0] : null);
    }

    public JPanel getControlPanel() {
        return controlPanel;
    }

    public JScrollPane getGameJScrollPane() {
        return scrollPane;
    }

}
