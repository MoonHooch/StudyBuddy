package managers;

import javax.swing.*;
import java.awt.*;

public class FrameManager {

    // Default frame settings
    public static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
    public static final Dimension MINIMUM_SIZE = new Dimension(600, 400);

    public static void configureFrame(JFrame frame, String title) {

        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Application closes upon exit
        frame.setSize(DEFAULT_SIZE);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);

    }

}