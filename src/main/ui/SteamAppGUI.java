package ui;

import javax.swing.*;
import java.awt.*;

public class SteamAppGUI extends JFrame {
    private ControlPanel cp;
    
    // Constructor to set up the GUI
    public SteamAppGUI() {
        super("Steam Account Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        
        // Create UI Components
        cp = new ControlPanel();
        add(cp.getControlPanel(), BorderLayout.SOUTH);
        add(cp.getGameJScrollPane(), BorderLayout.CENTER);
        
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
    
    // Main method to run the application
    public static void main(String[] args) {
        new SteamAppGUI();
    }
}

