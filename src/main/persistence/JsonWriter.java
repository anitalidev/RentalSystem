package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import model.RentalLocation;
import model.RentalSystem;
import model.Vehicle;

// Attribution: Structure inspired by Json Demo Project
// A writer that writes the JSON representation of Rental System
public class JsonWriter {
    // Attribution: The following fields are based on Json Demo Project
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;
    
    // EFFECTS: constructs a writer that writes to given destination
    public JsonWriter(String destination) {

    }
    
    // Attribution: Json Demo Project
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file 
    //          can't be opened for writing;
    public void open() throws FileNotFoundException {
        
    }

    // MODIFIES: this
    // EFFECTS: writes JSON interpretation of a rental system to file
    public void writeSystem(RentalSystem locations) {

    }

    // Attribution: Json Demo Project
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        
    }

    // Attribution: Json Demo Project
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        
    }

}
