package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.RentalLocation;
import model.RentalSystem;
import model.Vehicle;

class JsonReaderTest extends JsonTest {

    @Test
    void testReadNotExistantFile() {
        JsonReader testReader = new JsonReader(".data/fileDoesNotExist.json");
        try {
            RentalSystem testSystem = testReader.read();
            fail("Expected IOException to be thrown");
        } catch (IOException e) {
            // this is expected. 
        }
    }

    @Test
    void testReadEmptySystem() {
        JsonReader testReader = new JsonReader("./data/testEmptySystemReader.json");
        try {
            RentalSystem testSystem = testReader.read();

            assertEquals(testSystem.getLocations().size(), 0);
            
        } catch (IOException e) {
            fail("File could not be read (IOexception).");
        }
    }


    @Test
    void testReadExampleSystem() {
        JsonReader testReader = new JsonReader("./data/testSystemReader.json");
        try {
            RentalSystem testSystem = testReader.read();

            assertEquals(testSystem.getLocations().size(), 3);

            // First Location
            List<Vehicle> expectedVehicles = new ArrayList<Vehicle>();
            RentalLocation testLocation = testSystem.getLocations().get(0);
            checkLocation("ANGU", 10, 0, expectedVehicles, testLocation);
            
            expectedVehicles = new ArrayList<Vehicle>();
            Vehicle testVehicle = new Vehicle(0, "Car", 10, "SWNG");
            expectedVehicles.add(testVehicle);
            testLocation = testSystem.getLocations().get(1);
            checkLocation("SWNG", 2, 1, expectedVehicles, testLocation);

            expectedVehicles = makeVehicles();

            testLocation = testSystem.getLocations().get(2);
            checkLocation("Osborne", 5, 3, expectedVehicles, testLocation);
            
        } catch (IOException e) {
            fail("File could not be read (IOexception).");
        }
    }

    private List<Vehicle> makeVehicles() {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();
        Vehicle vehicle;

        vehicle = new Vehicle(0, "Bus", 20, "Osborne");
        vehicle.rent("Julia");
        vehicle.returnVehicle();
        vehicle.rent("Mark");
        vehicle.returnVehicle();
        vehicles.add(vehicle);
            
        vehicle = new Vehicle(2, "Bike", 5, "Osborne");
        vehicle.rent("Sam");
        vehicles.add(vehicle);

        return vehicles;
    }
}
