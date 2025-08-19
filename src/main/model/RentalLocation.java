package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// A class representing a rental location with a location, max capacity, next vehicle ID and list of vehicles
public class RentalLocation implements Writable {

    private String location;
    private int maxCapacity;
    private int nextID;
    private List<Vehicle> vehicles;

    // EFFECTS: constructs a rental location at the given location with given max capacity,
    //          0 current capacity, next vehicle ID of 0, and no vehicles.
    public RentalLocation(String location, int maxCapacity) {
        this.location = location;
        this.maxCapacity = maxCapacity;
        nextID = 0;
        vehicles = new ArrayList<Vehicle>();

    }

    // MODIFIES: this
    // EFFECTS: IF current capacity < max capacity, creates new vehicle with next vehicle ID, and given parameters.
    //             Adds one to current capacity, increments next ID, and adds created vehicle to list of vehicles. 
    //             Returns assigned ID
    //          OTHERWISE returns -1    
    public int addVehicle(String type, int rentalRate) {
        if (hasSpace()) {
            Vehicle newVehicle = new Vehicle(nextID, type, rentalRate, location);
            nextID++;
            vehicles.add(newVehicle);
            return nextID - 1;
        } else {
            return -1;
        }
    }

    // MODIFIES: this
    // EFFECTS: IF vehicle with given id is in list, removes vehicle from list of vehicles,
    //          subtracts one from current capacity, and returns true. OTHERWISE returns false
    public boolean removeVehicle(int id) {
        int size = vehicles.size();
        for (int i = 0; i < size; i++) {
            if (vehicles.get(i).getID() == id) {
                vehicles.remove(i);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Returns whether rental location has reached max capcity 
    public boolean hasSpace() {
        return maxCapacity > vehicles.size();
    }

    // EFFECTS: Finds vehicle with given id in list. 
    //          IF found returns that vehicle, OTHERWISE returns null
    public Vehicle findVehicle(int id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getID() == id) {
                return vehicle;
            }
        }
        return null;
    }

    // EFFECTS: returns the rental location's location
    public String getLocation() {
        return location; 
    }

    // EFFECTS: returns the rental location's max capacity
    public int getMaxCapacity() {
        return maxCapacity;
    }

    // EFFECTS: returns the rental location's current capacity
    public int getCurrentCapacity() {
        return vehicles.size();
    }

    // EFFECTS: Returns the next ID
    public int getNextID() {
        return nextID;
    }

    // REQUIRES: this.nextID <= nextID
    // MODIFIES: this
    // EFFECTS: Sets next id to given id
    public void setNextID(int nextID) {
        this.nextID = nextID;
    }

    // EFFECTS: returns the rental location's vehicles
    public List<Vehicle> getVehicles() {
        return vehicles; 
    }

    // MODIFIES: this
    // EFFECTS: sets the rental location's location to the new, given location
    public void setLocation(String location) {
        this.location = location;
    }

    // REQUIRES: currentCapacity <= maxCapacity
    // MODIFIES: this
    // EFFECTS: sets the rental location's max capacity to the new, given max capacity
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public JSONObject toJson() {
        JSONObject locationJson = new JSONObject();

        locationJson.put("location", location);
        locationJson.put("maxcapacity", maxCapacity);
        locationJson.put("nextid", nextID);
        locationJson.put("vehicles", vehiclesToJson());

        return locationJson;
    }

    // EFFECTS: returns vehicles in list of vehicles as a JSON array
    private JSONArray vehiclesToJson() {
        JSONArray vehiclesJson = new JSONArray();

        for (Vehicle vehicle : vehicles) {
            vehiclesJson.put(vehicle.toJson());
        }

        return vehiclesJson;
    }
}