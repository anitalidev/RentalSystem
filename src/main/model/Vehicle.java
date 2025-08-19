package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// A class representing a vehicle with an ID, type, rental rate ($ per hour), availability, location, and rental history
public class Vehicle implements Writable {

    private int id;
    private String type;
    private int rentalRate;
    private boolean available;
    private String location;
    private List<String> rentalHistory;

    // EFFECTS: constructs a vehicle of given type, with given ID, with given rental rate, that is available,
    //          at given location, and an empty rental history
    public Vehicle(int id, String type, int rentalRate, String location) {
        this.id = id;
        this.type = type;
        this.rentalRate = rentalRate;
        available = true;
        this.location = location;
        rentalHistory = new ArrayList<String>();
    }

    // REQUIRES: vehicle is available
    // MODIFIES: this
    // EFFECTS: adds name of renter to end of rental history and set availability to false
    public void rent(String name) {
        rentalHistory.add(name);
        available = false;
    }

    // MODIFIES: this
    // EFFECTS: sets availability to true
    public void returnVehicle() {
        available = true;
    }

    // EFFECTS: returns cost to rent vehicle for given time (hours)
    public int cost(int time) {
        return rentalRate * time;
    }

    // REQUIRES: availability is false and list of renters is not empty.
    // EFFECTS: Returns name of renter
    public String getRenter() {
        return rentalHistory.get(rentalHistory.size() - 1);
    }

    // EFFECTS: Returns vehicle location
    public String getLocation() {
        return location;
    }

    // EFFECTS: Returns type of vehicle
    public String getType() {
        return type;
    }

    // EFFECTS: Returns the rental rate
    public int getRentalRate() {
        return rentalRate; 
    }

    // EFFECTS: Returns the availability
    public boolean getAvailability() {
        return available;
    }

    // EFFECTS: Returns the vehicle ID
    public int getID() {
        return id; 
    }

    // EFFECTS: Returns the rental history in order
    public List<String> getRentalHistory() {
        return rentalHistory; 
    }

    // MODIFIES: this
    // EFFECTS: Changes the rental rate to given rate
    public void setRentalRate(int rentalRate) {
        this.rentalRate = rentalRate;
    }

    // MODIFIES: this
    // EFFECTS: Sets vehicle to not available
    public void makeUnavailable() {
        available = false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject vehicleJson = new JSONObject();

        vehicleJson.put("id", id);
        vehicleJson.put("type", type);
        vehicleJson.put("rentalrate", rentalRate);
        vehicleJson.put("available", available);
        vehicleJson.put("location", location);
        vehicleJson.put("rentalhistory", historyToJson());

        return vehicleJson;
    }

    // EFFECTS: returns names in rental history list as a JSON array
    private JSONArray historyToJson() {
        JSONArray historyJson = new JSONArray();

        for (String name : rentalHistory) {
            historyJson.put(name);
        }

        return historyJson;
    }
}
