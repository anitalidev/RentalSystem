package persistence;

import org.json.JSONObject;

// Attribution: Structure based on Json Demo Project
public interface Writable {
    // EFFECTS: returns object as JSON object
    JSONObject toJson();
}
