package ui;

import javax.swing.*;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class SteamAppGUI extends JFrame {
    private ControlPanel cp;
    
    // Constructor to set up the GUI
    public SteamAppGUI() {
        super("Steam Account Manager");
        creatSplashImage();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());
        
        // Create UI Components
        cp = new ControlPanel();
        add(cp.getControlPanel(), BorderLayout.SOUTH);
        add(cp.getGameJScrollPane(), BorderLayout.CENTER);
        addSteamLogo(this);
        centreOnScreen();
        setVisible(true);
    }

    private void creatSplashImage() {
        // Create the splash screen
        JWindow splashScreen = new JWindow();

        // Set up the main application content
        JLabel welcomeLabel = new JLabel("Welcome to Steam!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.BLUE);
        splashScreen.add(welcomeLabel);

        // Configure frame properties
        splashScreen.setSize(600, 600); // Adjust as needed
        splashScreen.setLocationRelativeTo(null); // Center the frame
        splashScreen.setVisible(true); // Show the frame
 
        // Add the JLabel to the JWindow
        splashScreen.getContentPane().add(addImage(), BorderLayout.CENTER);
 
        // Set the size of the splash screen
        splashScreen.setSize(600, 600); // Match scaled image size
 
        // Center the splash screen on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        splashScreen.setLocation(screenSize.width / 2 - splashScreen.getWidth() / 2,
                screenSize.height / 2 - splashScreen.getHeight() / 2);
 
        // Make the splash screen visible
        splashScreen.setVisible(true);

        // Display the splash screen for 3 seconds
        Timer timer = new Timer(3000, e -> {
            splashScreen.setVisible(false);
            splashScreen.dispose(); });

        timer.setRepeats(false); // Run only once
        timer.start();

        // Make the application start after 3 seconds
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e1) {
            //
        }
    }

    // Helper method to add an image to splash screen
    private JLabel addImage() {
        // Load the image
        ImageIcon splashImage = new ImageIcon("data/steamsplash1.jpg"); // Adjust path as needed
 
        // Optionally scale the image to fit the window
        Image scaledImage = splashImage.getImage().getScaledInstance(604, 856, Image.SCALE_SMOOTH); // Adjust size
        splashImage = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(splashImage);
        return imageLabel;
    }

    private void addSteamLogo(JFrame frame) {
        // Load the image from the file
        ImageIcon steamLogo = new ImageIcon("data/steamapp.jpg"); // Adjust path if necessary
    
        // Optionally scale the image to fit the panel (if needed)
        Image scaledImage = steamLogo.getImage().getScaledInstance(600, 315, Image.SCALE_SMOOTH); // Resize to 100x100
        steamLogo = new ImageIcon(scaledImage);
    
        // Create a JLabel with the image icon
        JLabel logoLabel = new JLabel(steamLogo);
    
        // Add the label to the panel
        frame.add(logoLabel, BorderLayout.NORTH);
    }
    
    // Centres frame on desktop
	// modifies: this
	// effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }
    
    // Main method to run the application
    public static void main(String[] args) {
        new SteamAppGUI();
    }
}