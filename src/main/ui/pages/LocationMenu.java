package ui.pages;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.RentalLocation;
import model.Vehicle;

// LocationMenu that handles GUI for managing a rental location and keeps track of the location and previous menu
public class LocationMenu extends Menu {

    RentalLocation location;
    MainMenu returnMenu;
    
    // EFFECTS: creates the location menu frame
    public LocationMenu(MainMenu menu, RentalLocation location) {
        super("Rental Management Application - Location Menu");

        setVisible(true);

        this.location = location;
        this.returnMenu = menu;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: handles the frame for the location menu
    public void runMenu() {
        clear();
        placeVehicleButtons();
        placeMenuButtons();
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: places buttons related to managing vehicles on frame
    private void placeVehicleButtons() {
        JButton displayVehicles = new JButton("Display Vehicles");
        displayVehicles.addActionListener(e -> displayVehicles());

        JButton displayAvailableVehicles = new JButton("Display Available Vehicles");
        displayAvailableVehicles.addActionListener(e -> displayAvailableVehicles());

        JButton handleNewVehicle = new JButton("Add a new Vehicle");
        handleNewVehicle.addActionListener(e -> handleNewVehicle());

        JButton handleRemoveVehicle = new JButton("Remove a Vehicle");
        handleRemoveVehicle.addActionListener(e -> handleRemoveVehicle());

        buttonPanel.add(displayVehicles);
        buttonPanel.add(displayAvailableVehicles);
        buttonPanel.add(handleNewVehicle);
        buttonPanel.add(handleRemoveVehicle);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(buttonPanel);

        this.add(topPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: places buttons related to changing menus on frame
    private void placeMenuButtons() {
        JButton handleVehicles = new JButton("Manage a Vehicle");
        handleVehicles.addActionListener(e -> manageVehicle());

        JButton returnToMain = new JButton("Return to Main Menu");
        returnToMain.addActionListener(e -> returnToMain()); 

        buttonPanel.add(handleVehicles);
        buttonPanel.add(returnToMain);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(buttonPanel);

        this.add(topPanel, BorderLayout.CENTER);
    }

    // EFFECTS: displays list of vehicles with getAvailability() true to user
    private void displayAvailableVehicles() {
        List<Vehicle> vehicles = location.getVehicles();

        StringBuilder message = new StringBuilder();

        for (Vehicle vehicle : vehicles) {
            if (vehicle.getAvailability()) {
                message.append("Vehicle type: " + vehicle.getType() + ", Rental Rate: " + vehicle.getRentalRate() 
                               + "$/hr, ID: " + vehicle.getID());
            }
        }

        if (message.length() == 0) {
            JOptionPane.showMessageDialog(this, "You have no available vehicles.");
            return;
        }

        JOptionPane.showMessageDialog(this,message);
    }

    // EFFECTS: displays list of vehicles to user
    private void displayVehicles() {
        List<Vehicle> vehicles = location.getVehicles();

        if (vehicles.size() == 0) {
            JOptionPane.showMessageDialog(this, "You have no vehicles.");
            return;
        }

        StringBuilder message = new StringBuilder();

        for (Vehicle vehicle : vehicles) {
            message.append("Vehicle type: " + vehicle.getType() + ", Rental Rate: " + vehicle.getRentalRate() 
                           + "$/hr, ID: " + vehicle.getID());
        }

        JOptionPane.showMessageDialog(this,message);
    }

    // MODIFIES: system
    // EFFECTS: handles the GUI for managing a vehicle
    private void manageVehicle() {
        question = "What is the ID of the vehicle you would like to manage?";
        JDialog dialog = makeDialogue();
        dialog.setVisible(true);

        try {
            int id = Integer.valueOf(userInput);

            Vehicle vehicle = location.findVehicle(id);

            if (vehicle == null) {
                JOptionPane.showMessageDialog(this, "You don't have a vehicle with ID: " + userInput + ".");
                return;
            }

            setVisible(false);
            VehicleMenu nextMenu = new VehicleMenu(vehicle, this);
            nextMenu.runMenu();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "\"" + userInput + "\"" + " isn't a number. Couldn't remove vehicle.");
        }
    }

    // MODIFIES: location
    // EFFECTS: handles GUI for attempting to add a new vehicle
    public void handleNewVehicle() {
        question = "What is the type of vehicle you would like to add?";
        JDialog dialog = makeDialogue();
        dialog.setVisible(true);

        String vehicleType = userInput;

        question = "What is the rental rate of this vehicle?";
        dialog = makeDialogue();
        dialog.setVisible(true);

        try {
            int rentalRate = Integer.valueOf(userInput);
            int id = location.addVehicle(vehicleType, rentalRate);
            
            JOptionPane.showMessageDialog(this, "New vehicle of type " + vehicleType + " with ID:" + id); 
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "\"" + userInput + "\"" + " isn't a number. Couldn't create vehicle.");
        }
    }

    // MODIFIES: location
    // EFFECTS: handles GUI for attempting to remove a vehicle
    public void handleRemoveVehicle() {
        question = "Where the ID of the vehicle you would like to remove?";
        JDialog dialog = makeDialogue();
        dialog.setVisible(true);

        try {
            int id = Integer.valueOf(userInput);

            boolean removed = location.removeVehicle(id);

            if (!removed) {
                JOptionPane.showMessageDialog(this, "You don't have a vehicle with ID: " + userInput + ".");
                return;
            }

            JOptionPane.showMessageDialog(this, "Vehicle with ID: " + userInput + ", removed.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "\"" + userInput + "\"" + " isn't a number. Couldn't remove vehicle.");
        }
    }

    // MODIFIES: this
    // EFFECTS: handles GUI for returning to the main menu.
    public void returnToMain() {
        dispose();
        returnMenu.showMenu();
    }
}
