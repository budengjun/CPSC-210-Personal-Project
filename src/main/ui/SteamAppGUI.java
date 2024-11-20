package ui;

import model.Game;
import model.Library;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SteamAppGUI extends JFrame {
    private Library library;
    private static final String JSON_STORE = "./data/library.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JTextArea gameListDisplay;
    
    // Constructor to set up the GUI
    public SteamAppGUI() {
        super("Steam Account Manager");
        
        library = new Library();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        
        // Create UI Components
        createControlPanel();
        createGameListPanel();
        
        // Load Library Option on Start
        loadLibraryOption();
        
        centreOnScreen();
        setVisible(true);
    }

    // Centres frame on desktop
	// modifies: this
	// effects:  location of frame is set so frame is centred on desktop
	private void centreOnScreen() {
		Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
	}
    
    // Creates the control panel with buttons
    private void createControlPanel() {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 4));

        // Add Game Button
        JButton addGameButton = new JButton("Add Game");
        addGameButton.addActionListener(e -> addGameDialog());
        controlPanel.add(addGameButton);

        // View Games Button
        JButton viewGamesButton = new JButton("View Library");
        viewGamesButton.addActionListener(e -> updateGameListDisplay());
        controlPanel.add(viewGamesButton);

        // Play Game Button
        JButton playGameButton = new JButton("Play Game");
        playGameButton.addActionListener(e -> playGameDialog());
        controlPanel.add(playGameButton);

        // Remove Game Button
        JButton removeGameButton = new JButton("Remove Game");
        removeGameButton.addActionListener(e -> removeGameDialog());
        controlPanel.add(removeGameButton);

        // Estimate Steam Account Value Button
        JButton estimateValueButton = new JButton("Estimate Value");
        estimateValueButton.addActionListener(e -> estimateAccountValue());
        controlPanel.add(estimateValueButton);

        // Save Library Button
        JButton saveLibraryButton = new JButton("Save Library");
        saveLibraryButton.addActionListener(e -> saveLibrary());
        controlPanel.add(saveLibraryButton);

        // Load Library Button
        JButton loadLibraryButton = new JButton("Load Library");
        loadLibraryButton.addActionListener(e -> loadLibrary());
        controlPanel.add(loadLibraryButton);

        // Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> quitApplication());
        controlPanel.add(quitButton);

        add(controlPanel, BorderLayout.SOUTH);
    }

    // Creates the panel to display the game list
    private void createGameListPanel() {
        gameListDisplay = new JTextArea();
        gameListDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameListDisplay);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Adds a game to the library through a dialog
    private void addGameDialog() {
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField achievementsField = new JTextField();
        Object[] message = {
            "Game Name:", nameField,
            "Price:", priceField,
            "Percentage Unlocked Achievements:", achievementsField,
        };
        int option = JOptionPane.showConfirmDialog(this, message, "Add Game", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int achievements = Integer.parseInt(achievementsField.getText());
            library.addGame(new Game(name, price, achievements, 0));
            updateGameListDisplay();
        }
    }

    // Plays a game through a dialog
    private void playGameDialog() {
        String gameName = selectGame();
        if (gameName != null) {
            for (Game game : library.getGameList()) {
                if (game.getName().equalsIgnoreCase(gameName)) {
                    library.playGames(game, 10);
                    JOptionPane.showMessageDialog(this, "Played 10 times: " + gameName);
                    break;
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
                    JOptionPane.showMessageDialog(this, "Removed: " + gameName);
                    updateGameListDisplay();
                    break;
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
            double ua = (a > 0) ? (double) u / a : 0.5;
            sellPrice += (c / 100 * ua) + p;
        }
        JOptionPane.showMessageDialog(this, "Steam Account Value: $" + sellPrice);
    }

    // Updates the game list display
    private void updateGameListDisplay() {
        gameListDisplay.setText("");
        for (Game game : library.getGameList()) {
            gameListDisplay.append(game.getName() + " - $" + game.getPrice() + "\n");
        }
    }

    // Prompts user to save before quitting
    private void quitApplication() {
        int option = JOptionPane.showConfirmDialog(this, "Save before quitting?", "Quit", JOptionPane.YES_NO_OPTION);
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
            JOptionPane.showMessageDialog(this, "Library saved successfully!");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save library: " + e.getMessage());
        }
    }

    // Loads the library from a file
    private void loadLibrary() {
        try {
            library = jsonReader.read();
            updateGameListDisplay();
            JOptionPane.showMessageDialog(this, "Library loaded successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to load library: " + e.getMessage());
        }
    }

    // Load library option at startup
    private void loadLibraryOption() {
        int option = JOptionPane.showConfirmDialog(this, "Load existing library?", "Load Library", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            loadLibrary();
        }
    }

    // Helper method to select a game from the library
    private String selectGame() {
        Object[] gameNames = library.getNameGameList().toArray();
        return (String) JOptionPane.showInputDialog(this, "Select a game:", "Select Game",
                JOptionPane.PLAIN_MESSAGE, null, gameNames, gameNames.length > 0 ? gameNames[0] : null);
    }

    // Main method to run the application
    public static void main(String[] args) {
        new SteamAppGUI();
    }
}

