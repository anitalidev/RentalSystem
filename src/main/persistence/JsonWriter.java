package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import model.RentalSystem;

// Attribution: Structure inspired by Json Demo Project
// A writer that writes the JSON representation of Rental System
public class JsonWriter {
    // Attribution: The following fields are based on Json Demo Project
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;
    
    // Attribution: Code from Json Demo Project
    // EFFECTS: constructs a writer that writes to given destination
    public JsonWriter(String destination) {
        this.destination = destination;
    }
    
    // Attribution: Code from Json Demo Project
    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file 
    //          can't be opened for writing;
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON interpretation of a rental system to file
    public void writeSystem(RentalSystem locations) {
        saveToFile(locations.toJson().toString(TAB));
    }

    // Attribution: Code from Json Demo Project
    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // Attribution: Code from Json Demo Project
    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
