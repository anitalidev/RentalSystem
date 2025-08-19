package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.RentalLocation;
import model.RentalSystem;
import model.Vehicle;

// Vehicle Rental application that helps users manage their rental system.
public class RentalApp {

    Scanner scanner = new Scanner(System.in);
    RentalSystem system;
    boolean running;

    // EFFECTS: runs the rental application
    public RentalApp() {
        system = new RentalSystem();
        running = true;
        runRental();
    }

    // MODIFIES: this
    // EFFECTS: processes user input for managing Rental Application
    private void runRental() {
        if (!running) {
            return;
        }
        displayMainMenu();
        String command = scanner.nextLine();
        switch (command) {
            case "v" -> displayLocations();
            case "a" -> handleNewLocation();
            case "l" -> handleLocations();
            case "r" -> handleRemoveLocation();
            case "q" -> quitApplication();
            default -> invalidInput();
        }
        runRental();
    }

    // MODIFIES: this
    // EFFECTS: processes user input for managing a rental location
    private void runLocation(RentalLocation location) {
        printDivider();
        displayLocationMenu();

        String command = scanner.nextLine();
        boolean quit = false;

        switch (command) {
            case "v" -> displayLocation(location);
            case "l" -> handleVehicles(location);
            case "a" -> handleNewVehicle(location);
            case "r" -> handleRemoveVehicle(location);
            case "q" -> quit = true;
            default -> invalidInput();
        }

        if (quit) {
            return;
        }
        runLocation(location);
    }

    // MODIFIES: this, location
    // EFFECTS: handles user input for managing vehicles at a location
    private void handleVehicles(RentalLocation location) {
        System.out.println("What is the ID of the vehicle you would like to manage?");
        int id = handleIntInput();
        scanner.nextLine();
        Vehicle vehicle = location.findVehicle(id);
        if (vehicle == null) {
            System.out.println("Could not find vehicle.");
            return;
        }
        handleVehicle(vehicle);
    }

    // MODIFIES: this, vehicle
    // EFFECTS: handles user input for managing a vehicle
    private void handleVehicle(Vehicle vehicle) {
        printDivider();
        displayVehicleMenu();
        String command = scanner.nextLine();
        boolean quit = false;

        switch (command) {
            case "v" -> displayVehicle(vehicle);
            case "r" -> rentVehicle(vehicle);
            case "a" -> returnVehicle(vehicle);
            case "h" -> displayRentalHistory(vehicle);
            case "q" -> quit = true;
            default -> invalidInput();
        }

        if (quit) {
            return;
        }
        handleVehicle(vehicle);
    }

    // MODIFIES: vehicle
    // EFFECTS: processes the returning of a vehicle
    public void returnVehicle(Vehicle vehicle) {
        if (vehicle.getAvailability()) {
            System.out.println("This vehicle is not being rented");
        } else {
            vehicle.returnVehicle();
            System.out.println("This vehicle has been returned.");
        }
    }
    

    // MODIFIES: vehicle
    // EFFECTS: handles user input for renting a vehicle
    public void rentVehicle(Vehicle vehicle) {
        if (!vehicle.getAvailability()) {
            System.out.println("This vehicle is currently being rented");
        } else {
            System.out.println("What is the name of the person renting the vehicle?");
            String name = scanner.nextLine();
            vehicle.rent(name);
            System.out.println(name + " is now renting this vehicle.");
        }
    }

    // MODIFIES: this
    // EFFECTS: handles user input for managing locations
    private void handleLocations() {
        printDivider();
        System.out.println("Where is the rental location you would like to manage?");
        String location = scanner.nextLine();
        int foundLocation = findLocation(location);
        if (foundLocation != -1) {
            runLocation(system.getLocations().get(foundLocation));
            return;
        }
        System.out.println("Location not found at: " + location);
    }

    // MODIFIES: this
    // EFFECTS: handles user input for attempting to add a new location
    private void handleNewLocation() {
        printDivider();
        System.out.println("Where would you like to add a location?");
        String locate = scanner.nextLine();
        int foundLocation = findLocation(locate);
        if (foundLocation != -1) {
            System.out.println("You are already managing this location.");
            return;
        }
        System.out.println("What is the max capacity of this location?");
        int maxCapacity = handleIntInput();
        scanner.nextLine();
        system.addLocation(new RentalLocation(locate, maxCapacity));
        System.out.println("New location at: " + locate + ", with max capacity: " + maxCapacity);
    }

    // MODIFIES: this
    // EFFECTS: handles user input for attempting to removing a location
    private void handleRemoveLocation() {
        printDivider();
        System.out.println("Where is the location you would like to remove?");
        String locate = scanner.nextLine();
        int foundLocation = findLocation(locate);
        if (foundLocation == -1) {
            System.out.println("You don't have a location at " + locate + ".");
            return;
        }
        system.removeLocation(foundLocation);
        System.out.println("Location at " + locate + " removed.");
    }

    // EFFECTS: displays menu of general options to user
    private void displayMainMenu() {
        printDivider();
        System.out.println("What would you like to do?");
        System.out.println("v: View my rental locations");
        System.out.println("l: Manage a speciifc rental location");
        System.out.println("a: Add a new rental location");
        System.out.println("r: Remove a rental location");
        System.out.println("q: Quit application");
    }

    // EFFECTS: displays menu of location managing options to user
    private void displayLocationMenu() {
        printDivider();
        System.out.println("What would you like to do?");
        System.out.println("v: View this rental location");
        System.out.println("l: Manage a vehicle at this location");
        System.out.println("a: Add a new rental vehicle");
        System.out.println("r: Remove a rental vehicle");
        System.out.println("q: Return to main system");
    }

    // EFFECTS: displays menu of vehicle managing options to user
    private void displayVehicleMenu() {
        printDivider();
        System.out.println("What would you like to do?");
        System.out.println("v: View general details about this vehicle");
        System.out.println("r: Mark vehicle as rented");
        System.out.println("a: Mark this vehicle as returned");
        System.out.println("h: View rental history of this vehicle");
        System.out.println("q: Return to location");
    }

    // MODIFIES: location
    // EFFECTS: handles the creation of a new vehicle for given location
    private void handleNewVehicle(RentalLocation location) {
        printDivider();
        if (!location.hasSpace()) {
            System.out.println("This location is at max capacity! You can't add more vehicles.");
            return;
        }
        System.out.println("What type of vehicle would you like to add?");
        String type = scanner.nextLine();

        System.out.println("What is the rental rate for this vehicle (in $/hr)");
        int rentalRate = handleIntInput();
        scanner.nextLine();

        int id = location.addVehicle(type, rentalRate);
        System.out.println("New vehicle of type: " + type + ", at location with ID: " + id);
    }

    // EFFECTS: returns the next integer entered by user
    private int handleIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    // MODIFIES: location
    // EFFECTS: handles the removal of a vehicle for given location
    private void handleRemoveVehicle(RentalLocation location) {
        printDivider();
        System.out.println("What is the ID of the vehicle you like to remove?");
        int id = handleIntInput();
        scanner.nextLine();

        Vehicle locate = location.findVehicle(id);

        if (locate == null) {
            System.out.println("You do not have a vehicle at this location with ID: " + id);
        } else if (location.removeVehicle(id)) {
            System.out.println("Vehicle successfully removed");
        } else {
            System.out.println("Could not remove vehicle"); // should never happen.
        }
    }

    // EFFECTS: displays rental history of given vehicle
    private void displayRentalHistory(Vehicle vehicle) {
        List<String> rentalHistory = vehicle.getRentalHistory();
        System.out.println("RENTAL HISTORY");
        for (String renter : rentalHistory) {
            System.out.println("Renter: " + renter);
        }
    }

    // EFFECTS: Displays details about given location
    private void displayLocation(RentalLocation location) {
        System.out.println("Rental location: " + location.getLocation());
        displayVehicles(location.getVehicles());
    } 

    // EFFECTS: displays list of locations to user
    private void displayLocations() {
        printDivider();
        List<RentalLocation> locations = system.getLocations();
        if (locations.size() == 0) {
            System.out.println("You have no locations.");
            return;
        }
        for (RentalLocation location : locations) {
            System.out.println("Location: " + location.getLocation() + ", Max Capacity: " + location.getMaxCapacity());
        }
    }

    // EFFECTS: displays details about a vehicle
    private void displayVehicle(Vehicle toDisplay) {
        printDivider();
        System.out.println("VEHICLE DETAILS:");
        System.out.println("Type: " + toDisplay.getType());
        System.out.println("Rental Rate: " + toDisplay.getRentalRate() + " $/hr");
        System.out.println("ID: " + toDisplay.getID());
        if (toDisplay.getAvailability()) {
            System.out.println("This vehicle is currently available.");
        } else {
            System.out.println("This vehicle is currently being rented by " + toDisplay.getRenter());
        }
    }

    // EFFECTS: displays details about vehicles at a location
    private void displayVehicles(List<Vehicle> toDisplay) {
        System.out.println("VEHICLES:");
        for (Vehicle vehicle : toDisplay) {
            System.out.println("Type: " + vehicle.getType() + ", ID: " + vehicle.getID());
        }
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

    // EFFECTS: prints out a line of periods to act as a divider
    private void printDivider() {
        System.out.println(".........................................");
    }

    // EFFECTS: informs user of an invalid input
    private void invalidInput() {
        System.out.println("Not a valid input.");
    }

    // MODIFIES: this
    // EFFECTS: prints message and marks program as stopped
    public void quitApplication() {
        System.out.println("See you next time!");
        running = false;
    }
}