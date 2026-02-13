package ui.pages;

import javax.swing.*;

import model.Event;
import model.EventLog;
import model.RentalLocation;
import model.RentalSystem;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

// MainMenu that handles GUI for managing a rental system and keeps track of the system
public class MainMenu extends Menu {

    private RentalSystem system;
    
    // EFFECTS: creates the main menu frame
    public MainMenu(RentalSystem system) {
        super("Rental Management Application - Main Menu");

        setVisible(true);

        this.system = system;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: handles the frame for the main menu
    public void runMenu() {
        clear();
        placeButtons();
    }

    // MODIFIES: this
    // EFFECTS: places buttons on frame
    private void placeButtons() {
        addLocationHandlers();
        addSaveLoadHandlers();
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Adds location handling related buttons to frame
    private void addLocationHandlers() {

        JButton displayLocations = new JButton("Display Locations");
        displayLocations.addActionListener(e -> displayLocations());

        JButton handleNewLocation = new JButton("Add a new Location");
        handleNewLocation.addActionListener(e -> handleNewLocation());

        JButton handleLocations = new JButton("Manage a Location");
        handleLocations.addActionListener(e -> manageLocation());

        JButton handleRemoveLocation = new JButton("Remove a Location");
        handleRemoveLocation.addActionListener(e -> handleRemoveLocation());
        
        buttonPanel.add(displayLocations);
        buttonPanel.add(handleNewLocation);
        buttonPanel.add(handleLocations);
        buttonPanel.add(handleRemoveLocation);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(buttonPanel);

        this.add(topPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds save, load, and quit buttons to frame
    private void addSaveLoadHandlers() {
        JButton saveLocations = new JButton("Save Locations");
        saveLocations.addActionListener(e -> handleSave()); 

        JButton loadLocations = new JButton("Load Locations");
        loadLocations.addActionListener(e -> handleLoad()); 
        
        JButton quitApplication = new JButton("Quit Application");
        quitApplication.addActionListener(e -> quitApplication()); 

        buttonPanel.add(saveLocations);
        buttonPanel.add(loadLocations);
        buttonPanel.add(quitApplication);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(buttonPanel);

        this.add(topPanel, BorderLayout.CENTER);
    }

    // EFFECTS: displays list of locations to user
    private void displayLocations() {
        List<RentalLocation> locations = system.getLocations();

        if (locations.size() == 0) {
            JOptionPane.showMessageDialog(this, "You have no locations.");
            return;
        }

        StringBuilder message = new StringBuilder();

        for (RentalLocation location : locations) {
            message.append("Location: " + location.getLocation() + ", Max Capacity: " 
                            + location.getMaxCapacity() + "\n");
        }

        JOptionPane.showMessageDialog(this,message);
    }

    // MODIFIES: system
    // EFFECTS: handles GUI for attempting to add a new location
    private void handleNewLocation() {
        question = "Where is the location you would like to add?";
        JDialog dialog = makeDialogue();
        dialog.setVisible(true);

        int foundLocation = findLocation(userInput);
        String neLocation = userInput;
        
        if (foundLocation != -1) {
            JOptionPane.showMessageDialog(this, "You are already managing this location.");
            return;
        }

        question = "What is the maximum capacity of this location?";
        dialog = makeDialogue();
        dialog.setVisible(true);

        try {
            int id = Integer.valueOf(userInput);
            RentalLocation location = new RentalLocation(neLocation, id);
            system.addLocation(location);
            JOptionPane.showMessageDialog(this, "New location at " + neLocation + " with max capacity: " + 0); 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "\"" + userInput + "\"" + " isn't a number. Couldn't create location.");
        }
    }

    // MODIFIES: system
    // EFFECTS: handles GUI for attempting to removing a location
    private void handleRemoveLocation() {
        question = "Where is the location you would like to remove?";
        JDialog dialog = makeDialogue();
        dialog.setVisible(true);

        int foundLocation = findLocation(userInput);
        
        if (foundLocation == -1) {
            JOptionPane.showMessageDialog(this, "You don't have a location at " + userInput + ".");
            return;
        }
        system.removeLocation(foundLocation);
        JOptionPane.showMessageDialog(this, "Location at " + userInput + " removed.");
    }

    // EFFECTS: finds location in list at given location
    //          if found, returns position in list of locations
    //          returns -1 if could not be found.
    private int findLocation(String location) {
        List<RentalLocation> locations = system.getLocations();
        int size = locations.size();
        for (int i = 0; i < size; i++) {
            if (locations.get(i).getLocation().equals(location)) {
                return i;
            }
        }
        return -1;
    }

    // EFFECTS: handles the saving of system to system.json
    private void handleSave() {
        try {
            JsonWriter writer = new JsonWriter("./data/system.json");
            writer.open();
            writer.writeSystem(system);
            writer.close();
            JOptionPane.showMessageDialog(this, "System saved.");

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Could not find file.");
        }
    }

    // MODIFIES: system
    // EFFECTS: handles the loading of system from system.json
    private void handleLoad() {
        JsonReader reader = new JsonReader("./data/system.json");
        try {
            system = reader.read();
            JOptionPane.showMessageDialog(this, "System loaded.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not find file.");
        }
    }

    // MODIFIES: system
    // EFFECTS: handles the GUI for managing a location
    private void manageLocation() {
        question = "Where is the location you would like to manage?";
        JDialog dialog = makeDialogue();
        dialog.setVisible(true);

        int foundLocation = findLocation(userInput);
        
        if (foundLocation == -1) {
            JOptionPane.showMessageDialog(this, "You don't have a location at " + userInput + ".");
            return;
        } else {
            RentalLocation managing = system.getLocations().get(foundLocation);
            setVisible(false);
            LocationMenu nextMenu = new LocationMenu(this, managing);
            nextMenu.runMenu();
        }
    }

    // EFFECTS: prints event log to console
    private void printLog() {
        EventLog eventLog = EventLog.getInstance();

        for (Event event: eventLog) {
            System.out.println(event.getDescription());
        }
    }

    // MODIFIES: this
    // EFFECTS: handles GUI for asking if user wants to save state, shows closing message, and closes frame
    public void quitApplication() {
        question = "Would you like to save your locations before closing the program? Enter \"yes\" if so.";
        JDialog dialog = makeDialogue();
        dialog.setVisible(true);

        if (userInput.equals("yes")) {
            handleSave();
        }

        JOptionPane.showMessageDialog(this, "See you next time!");
        dispose();

        printLog();
    }
}
