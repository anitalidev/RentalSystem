package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// A class representing a rental system with a list of rental locations
public class RentalSystem implements Writable {

    private List<RentalLocation> locations;

    // EFFECTS: Constructs a rental system with no locations
    public RentalSystem() {
        EventLog.getInstance().logEvent(new Event("Rental System created"));

        locations = new ArrayList<RentalLocation>();
    }

    // REQUIRES: location != null
    // MODIFIES: this
    // EFFECTS: adds given location to end of list of rental locations
    public void addLocation(RentalLocation location) {
        locations.add(location);
    }

    // REQUIRES: locationIndex < size of list of rental locations
    // MODIFIES: this
    // EFFECTS: removes rental location at given index in list of rental locations
    public void removeLocation(int locationIndex) {
        EventLog.getInstance().logEvent(new Event("Location at " + locations.get(locationIndex).getLocation()
                                        + " removed"));
        locations.remove(locationIndex);
    }

    // EFFECTS: returns list of rental locations in order added
    public List<RentalLocation> getLocations() {
        return locations;
    }

    @Override
    public JSONObject toJson() {
        JSONObject systemJson = new JSONObject();

        systemJson.put("locations", locationsToJson());

        return systemJson;
    }

    // EFFECTS: returns locations in list of locations as a JSON array
    private JSONArray locationsToJson() {
        JSONArray locationsJson = new JSONArray();

        for (RentalLocation location : locations) {
            locationsJson.put(location.toJson());
        }
        
        return locationsJson;
    }
}
