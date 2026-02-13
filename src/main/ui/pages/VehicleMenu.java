package ui.pages;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Vehicle;

// VehicleMenu that handles GUI for managing a vehicle and keeps track of the vehicle and previous menu
public class VehicleMenu extends Menu {
    Vehicle vehicle;
    LocationMenu returnMenu;
    
    // EFFECTS: creates the vehicle menu frame
    public VehicleMenu(Vehicle vehicle, LocationMenu menu) {
        super("Rental Management Application - Vehicle Menu");

        setVisible(true);

        this.vehicle = vehicle;
        this.returnMenu = menu;
    }

    @Override
    // MODIFIES: this
    // EFFECTS: handles the frame for the vehicle menu
    public void runMenu() {
        clear();
        placeButtons();
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: places buttons on frame
    public void placeButtons() {
        JButton displayHistory = new JButton("Display Rental History");
        displayHistory.addActionListener(e -> displayRentalHistory());

        JButton rentVehicle = new JButton("Mark Vehicle as Rented");
        rentVehicle.addActionListener(e -> handleRentVehicle());

        JButton returnVehicle = new JButton("Return this Vehicle");
        returnVehicle.addActionListener(e -> handleReturnVehicle());

        JButton returnToLocation = new JButton("Return to Location Menu");
        returnToLocation.addActionListener(e -> returnToLocation()); 

        buttonPanel.add(displayHistory);
        buttonPanel.add(rentVehicle);
        buttonPanel.add(returnVehicle);
        buttonPanel.add(returnToLocation);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(buttonPanel);

        this.add(topPanel, BorderLayout.CENTER);
    }

    // EFFECTS: displays rental history of vehicle to user
    private void displayRentalHistory() {
        List<String> rentalHistory = vehicle.getRentalHistory();

        if (rentalHistory.size() == 0) {
            JOptionPane.showMessageDialog(this, "This vehicle has never been rented.");
            return;
        }

        StringBuilder message = new StringBuilder();

        for (String name : rentalHistory) {
            message.append("Rented by: " + name + "\n");
        }

        JOptionPane.showMessageDialog(this, message);
    }

    // MODIFIES: vehicle
    // EFFECTS: handles GUI for attempting to rent a vehicle
    public void handleRentVehicle() {
        if (!vehicle.getAvailability()) {
            JOptionPane.showMessageDialog(this, "Vehicle is already currently being rented.");
            return;
        }

        question = "What is the name of the person renting this vehicle?";
        JDialog dialog = makeDialogue();
        dialog.setVisible(true);

        vehicle.rent(userInput);
        JOptionPane.showMessageDialog(this, "Vehicle is now being rented by" + userInput + ".");
    }

    // MODIFIES: vehicle
    // EFFECTS: handles GUI for attempting to return a vehicle
    public void handleReturnVehicle() {
        if (vehicle.getAvailability()) {
            JOptionPane.showMessageDialog(this, "Vehicle is not currently being rented.");
            return;
        }
        vehicle.returnVehicle();
        JOptionPane.showMessageDialog(this, "Successfully marked vehicle as returned.");
    }

    // MODIFIES: this
    // EFFECTS: handles GUI for returning to the location menu.
    public void returnToLocation() {
        dispose();
        returnMenu.showMenu();
    }
}
