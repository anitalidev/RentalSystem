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

    // EFFECTS: constructs a rental location at the given location with given max capacity, next vehicle ID of 0, 
    //          and no vehicles.
    public RentalLocation(String location, int maxCapacity) {
        this.location = location;
        this.maxCapacity = maxCapacity;
        nextID = 0;
        vehicles = new ArrayList<Vehicle>();
        EventLog.getInstance().logEvent(new Event("Created new Rental Location: " + location));
    }

    // MODIFIES: this
    // EFFECTS: IF hasSpace(), creates new vehicle with next vehicle ID, and given parameters.
    //             Increments next ID, and adds created vehicle to list of vehicles. Returns assigned ID
    //          OTHERWISE returns -1    
    public int addVehicle(String type, int rentalRate) {
        if (hasSpace()) {
            Vehicle newVehicle = new Vehicle(nextID, type, rentalRate, location);
            nextID++;
            vehicles.add(newVehicle);
            EventLog.getInstance().logEvent(new Event("Vehicle of type: " + type + ", and ID: "
                    + (nextID - 1) + " added to " + location));
            return nextID - 1;
        } else {
            return -1;
        }
    }

    // REQUIRES: vehicle.getLocation() == location 
    //           AND vehicle ID is not identical to that of any vehicle in list of vehicle
    // MODIFIES: this
    // EFFECTS: IF hasSpace(), adds vehicle to list of vehicles. 
    //             IF nextID is less than the given vehicle's ID, sets it to one more than the given vehicle's ID.
    //          Returns whether vehicle was added.
    public boolean addVehicle(Vehicle vehicle) {
        if (hasSpace()) {
            vehicles.add(vehicle);
            if (vehicle.getID() >= nextID) {
                nextID = vehicle.getID() + 1;
            }
            EventLog.getInstance().logEvent(new Event("Vehicle of type: " + vehicle.getType() + ", and ID: "
                    + vehicle.getID() + " added to " + location));
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: IF vehicle with given id is in list, removes vehicle from list of vehicles, and returns true. 
    //          OTHERWISE returns false
    public boolean removeVehicle(int id) {
        int size = vehicles.size();
        for (int i = 0; i < size; i++) {
            if (vehicles.get(i).getID() == id) {
                vehicles.remove(i);
                EventLog.getInstance().logEvent(new Event("Vehicle of id: " + id + " removed from " + location));
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

    // REQUIRES: vehicles.size() <= maxCapacity
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