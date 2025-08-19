package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

public class TestRentalSystem {

    RentalSystem testSystem;
    RentalLocation testLocation1 = new RentalLocation("ICCS", 10);
    RentalLocation testLocation2 = new RentalLocation("SCARFE", 2);

    @BeforeEach
    void runBefore() {
        testSystem = new RentalSystem();
    }


    @Test
    void testConstructor() {
        List<RentalLocation> emptyList = new ArrayList<RentalLocation>();
        assertEquals(testSystem.getLocations(), emptyList);
    }

    @Test
    void testAddLocation() {
        List<RentalLocation> expectedList = new ArrayList<RentalLocation>();
        testSystem.addLocation(testLocation1);
        expectedList.add(testLocation1);
        assertEquals(testSystem.getLocations(), expectedList);
        testSystem.addLocation(testLocation2);
        expectedList.add(testLocation2);
        assertEquals(testSystem.getLocations(), expectedList);
        testSystem.addLocation(testLocation2);
        expectedList.add(testLocation2);
        assertEquals(testSystem.getLocations(), expectedList);
    }

    @Test
    void testRemoveLocation() {
        testSystem.addLocation(testLocation1);
        testSystem.addLocation(testLocation2);
        testSystem.addLocation(testLocation1);
        List<RentalLocation> expectedList = new ArrayList<RentalLocation>();
        expectedList.add(testLocation1);
        expectedList.add(testLocation2);
        expectedList.add(testLocation1);

        testSystem.removeLocation(1);
        expectedList.remove(1);

        assertEquals(testSystem.getLocations(), expectedList);

        testSystem.removeLocation(1);
        expectedList.remove(1);

        assertEquals(testSystem.getLocations(), expectedList);

        testSystem.removeLocation(0);
        expectedList.remove(0);

        assertEquals(testSystem.getLocations(), expectedList);
    }

    @Test
    void testGetLocation() {
        List<RentalLocation> expectedList = new ArrayList<RentalLocation>();
        testSystem.addLocation(testLocation1);
        expectedList.add(testLocation1);
        testSystem.addLocation(testLocation2);
        expectedList.add(testLocation2);
        testSystem.addLocation(testLocation2);
        expectedList.add(testLocation2);
        assertEquals(testSystem.getLocations(), expectedList);
    } 
    
    @Test
    void testJsonBasicSystem() {
        JSONObject testJson = testSystem.toJson();

        assertTrue(testJson.getJSONArray("locations").isEmpty());
    }

    @Test
    void testJsonGeneralSystem() {
        testSystem.addLocation(testLocation1);
        JSONObject testJson = testSystem.toJson();
        assertEquals(testJson.getJSONArray("locations").length(), 1);
        assertEquals(testJson.getJSONArray("locations").getJSONObject(0).get("location"), "ICCS");
        assertTrue(testJson.getJSONArray("locations").getJSONObject(0).getJSONArray("vehicles").isEmpty());

        testSystem.addLocation(testLocation2);
        testJson = testSystem.toJson();
        assertEquals(testJson.getJSONArray("locations").length(), 2);
        assertEquals(testJson.getJSONArray("locations").getJSONObject(0).get("location"), "ICCS");
        assertEquals(testJson.getJSONArray("locations").getJSONObject(1).get("location"), "SCARFE");
        assertTrue(testJson.getJSONArray("locations").getJSONObject(1).getJSONArray("vehicles").isEmpty());
    }

    @Test
    void testJsonLocationWithVehicles() {
        testSystem.addLocation(testLocation1);
        JSONObject testJson = testSystem.toJson();
        assertEquals(testJson.getJSONArray("locations").length(), 1);
        assertEquals(testJson.getJSONArray("locations").getJSONObject(0).get("location"), "ICCS");
        assertTrue(testJson.getJSONArray("locations").getJSONObject(0).getJSONArray("vehicles").isEmpty());

        testLocation1.addVehicle("Car", 10);

        testJson = testSystem.toJson();
        assertEquals(testJson.getJSONArray("locations").getJSONObject(0).getJSONArray("vehicles").length(), 1);
        assertEquals(testJson.getJSONArray("locations").getJSONObject(0)
                    .getJSONArray("vehicles").getJSONObject(0).get("id"), 0);

        testLocation1.addVehicle("Bus", 20);

        testJson = testSystem.toJson();
        assertEquals(testJson.getJSONArray("locations").getJSONObject(0).getJSONArray("vehicles").length(), 2);
        assertEquals(testJson.getJSONArray("locations").getJSONObject(0)
                    .getJSONArray("vehicles").getJSONObject(0).get("id"), 0);
        assertEquals(testJson.getJSONArray("locations").getJSONObject(0)
                    .getJSONArray("vehicles").getJSONObject(1).get("id"), 1);
        assertEquals(testJson.getJSONArray("locations").getJSONObject(0)
                    .getJSONArray("vehicles").getJSONObject(0).get("type"), "Car");
        assertEquals(testJson.getJSONArray("locations").getJSONObject(0)
                    .getJSONArray("vehicles").getJSONObject(1).get("type"), "Bus");    
    }
}
