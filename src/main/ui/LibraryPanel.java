package ui;

import javax.swing.*;
import java.awt.*;

import model.Game;

import model.Library;

public class LibraryPanel extends JPanel {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    public LibraryPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.GRAY);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

        //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);
        return p;
    }
}
