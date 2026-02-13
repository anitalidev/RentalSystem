package ui;

import javax.swing.*;
import java.awt.*;

import model.RentalSystem;
import ui.pages.MainMenu;

// Vehicle Rental application that helps users manage their rental system.
public class RentalApp extends JFrame {

    private RentalSystem system;
    private MainMenu menu;


    public static final int WIDTH = 500;
    public static final int HEIGHT = 600;
    public static final String imageLocation = "resources/HomePageImage.png";

    // EFFECTS: runs the rental application
    public RentalApp() {

        system = new RentalSystem();
        menu = new MainMenu(system);
    }

    public void runApp() {
        splashScreen();

        menu.runMenu();
    }

    // EFFECTS: shows a splash screen to the user
    private void splashScreen() {
        JDialog splashScreen = new JDialog();
        splashScreen.setUndecorated(true);
        splashScreen.setSize(WIDTH, HEIGHT);

        addItems(splashScreen);
        splashScreen.setVisible(true);

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            System.out.println("Something went wrong");
        }

        splashScreen.dispose();
    }

    // EFFECTS: adds items to given dialog
    private void addItems(JDialog splashScreen) {
        JPanel items = new JPanel();

        ImageIcon imageIcon = new ImageIcon(imageLocation);
        Image originalImage = imageIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);

        JLabel iconScreen = new JLabel(imageIcon);
        items.add(iconScreen, BorderLayout.CENTER);

        JLabel textLabel = new JLabel("Loading... Please wait", SwingConstants.CENTER);
        items.add(textLabel, BorderLayout.CENTER);

        splashScreen.add(items);
    }
}