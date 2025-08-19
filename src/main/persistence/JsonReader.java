package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.RentalLocation;
import model.RentalSystem;
import model.Vehicle;

// Attribution: Structure inspired by Json Demo Project
// A reader that reads the JSON representation of a Rental System stored in a file
public class JsonReader {

    private String fileLocation;

    // EFFECTS: constructs reader to read from file with given file location
    public JsonReader(String fileLocation) {
        this.fileLocation = fileLocation; 
    }

    // Attribution: Json Demo Project
    // EFFECTS: reads list of rental locations from file and returns it;
    // throws IOException if an error occurs when attempting to read data from file
    public RentalSystem read() throws IOException {
        String jsonString = readFile();
        JSONObject jsonObject = new JSONObject(jsonString);
        return parseSystem(jsonObject);
    }

    // Attribution: Largely based on Json Demo Project
    // EFFECTS: reads file at file location as string and returns it
    private String readFile() throws IOException {
        StringBuilder jsonDataBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(fileLocation), StandardCharsets.UTF_8)) {
            stream.forEach(s -> jsonDataBuilder.append(s));
        }
        return jsonDataBuilder.toString();
    }

    // EFFECTS: Parses rental system from JSON Object and returns it
    private RentalSystem parseSystem(JSONObject jsonObject) {
        RentalSystem system = new RentalSystem();

        JSONArray locations =jsonObject.getJSONArray("locations");
        int size = locations.length();
        for (int i = 0; i < size; i++) {
            addLocation(system, locations.getJSONObject(i));
        }

        return system;
    }

    // MODIFIES: locations
    // EFFECTS: Parses rental location from JSON Object and adds it to given rental system
    private void addLocation(RentalSystem system, JSONObject jsonObject) {
        String location = jsonObject.getString("location");
        int maxCapacity = jsonObject.getInt("maxcapacity");
        int nextid = jsonObject.getInt("nextid");

        RentalLocation rentalLocation = new RentalLocation(location, maxCapacity);

        addVehicles(rentalLocation, jsonObject.getJSONArray("vehicles"));

        rentalLocation.setNextID(nextid);
        
    }

    // MODIFIES: location
    // EFFECTS: Parses vehicles in JSON Array and adds them to given rental location
    private void addVehicles(RentalLocation location, JSONArray jsonArray) {
        int size = jsonArray.length();
        for (int i = 0; i < size; i++) {
            addVehicle(location, jsonArray.getJSONObject(i));
        }
    }

    // MODIFIES: location
    // EFFECTS: Parses vehicle in JSON Object and adds it to given rental location
    private void addVehicle(RentalLocation location, JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String type = jsonObject.getString("type");
        int rentalRate = jsonObject.getInt("rentalrate");
        boolean available = jsonObject.getBoolean("available");
        String sLocation = jsonObject.getString("location");

        Vehicle vehicle = new Vehicle(id, type, rentalRate, sLocation);

        addHistory(vehicle, jsonObject.getJSONArray("rentalhistory"));

        if (available) {
            vehicle.returnVehicle();
        }
        else {
            vehicle.makeUnavailable();
        }

    }

    // MODIFIES: vehicle
    // EFFECTS: Parses names from JSON Array and adds them to given vehicle's rental history
    private void addHistory(Vehicle vehicle, JSONArray jsonArray) {

    }
}
