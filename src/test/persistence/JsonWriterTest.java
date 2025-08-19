package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.RentalLocation;
import model.RentalSystem;
import model.Vehicle;

class JsonWriterTest extends JsonTest {
    @Test
    void testWriteNotExistantFile() {
        try {
            RentalSystem testSystem = new RentalSystem();
            JsonWriter testWriter = new JsonWriter(".data/fileDoesNotExist.json");
            testWriter.open();
            fail("Expected IOException to be thrown");
        } catch (IOException e) {
            // this is expected. 
        }
    }

    @Test
    void testWriteEmptySystem() {
        try {
            RentalSystem testSystem = new RentalSystem();
            JsonWriter testWriter = new JsonWriter(".data/testEmptySystemWriter.json");
            testWriter.open();
            testWriter.writeSystem(testSystem);
            testWriter.close();

            JsonReader testReader = new JsonReader(".data/testEmptySystemWriter.json");
            RentalSystem readSystem = testReader.read();

            assertEquals(readSystem.getLocations().size(), 0);
        } catch (IOException e) {
            fail("File could not be read (IOexception).");
        }
    }


    @Test
    void testWriteExampleSystem() {
        try {
            RentalSystem actualSystem = prepareSystem();
            JsonWriter testWriter = new JsonWriter(".data/testSystemWriter.json");
            testWriter.open();
            testWriter.writeSystem(actualSystem);
            testWriter.close();

            JsonReader testReader = new JsonReader(".data/testSystemWriter.json");
            RentalSystem testSystem = testReader.read();

            assertEquals(testSystem.getLocations().size(), 3);

            // First Location
            ArrayList<Vehicle> expectedVehicles = new ArrayList<Vehicle>();
            RentalLocation testLocation = testSystem.getLocations().get(0);
            checkLocation("The Nest", 3, 0, expectedVehicles, testLocation);
            
            expectedVehicles = new ArrayList<Vehicle>();
            Vehicle testVehicle = new Vehicle(0, "Skateboard", 2, "LSK");
            testVehicle.rent("Joe");
            testVehicle.returnVehicle();
            expectedVehicles.add(testVehicle);
            testVehicle = new Vehicle(0, "Roller Skates", 3, "LSK");
            testVehicle.rent("Maya");
            expectedVehicles.add(testVehicle);
            testLocation = testSystem.getLocations().get(1);
            checkLocation("LSK", 7, 2, expectedVehicles, testLocation);

            expectedVehicles = new ArrayList<Vehicle>();

            testVehicle = new Vehicle(0, "eBike",10, "Hebb");
            testVehicle.rent("Skyler");
            testVehicle.returnVehicle();
            testVehicle.rent("Coryo");
            testVehicle.returnVehicle();
            testVehicle.rent("Lisa");
            expectedVehicles.add(testVehicle);
            
            testVehicle = new Vehicle(4, "Scooter", 8, "Hebb");
            testVehicle.rent("Barley");
            testVehicle.returnVehicle();
            expectedVehicles.add(testVehicle);

            testLocation = testSystem.getLocations().get(2);
            checkLocation("Hebb", 5, 5, expectedVehicles, testLocation);
        } catch (IOException e) {
            fail("File could not be read (IOexception).");
        }
    }

    private RentalSystem prepareSystem() {
        RentalSystem testSystem = new RentalSystem();
        RentalLocation location1 = new RentalLocation("The Nest", 3);
        RentalLocation location2 = new RentalLocation("LSK", 7);
        RentalLocation location3 = new RentalLocation("Hebb", 5);

        testSystem.addLocation(location1);
        testSystem.addLocation(location2);
        testSystem.addLocation(location3);

        location2.addVehicle("Skateboard", 2);
        location2.addVehicle("Roller Skates", 3);
        location3.addVehicle("eBike", 10);
        location3.setNextID(3);
        location3.addVehicle("Scooter", 8);

        location2.getVehicles().get(0).rent("Joe");
        location2.getVehicles().get(0).returnVehicle();;
        location2.getVehicles().get(1).rent("Maya");

        location3.getVehicles().get(0).rent("Skyler");
        location3.getVehicles().get(0).returnVehicle();
        location3.getVehicles().get(0).rent("Coryo");
        location3.getVehicles().get(0).returnVehicle();
        location3.getVehicles().get(0).rent("Lisa");
        location3.getVehicles().get(1).rent("Barley");
        location3.getVehicles().get(1).returnVehicle();

        return testSystem;
    }
}
