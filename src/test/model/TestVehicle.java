package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestVehicle {

    RentalLocation testLocation = new RentalLocation("ICCS", 10);
    RentalLocation testLocation2 = new RentalLocation("SCARFE", 20);
    Vehicle testVehicle1;
    Vehicle testVehicle2;

    @BeforeEach
    void runBefore() {
        testVehicle1 = new Vehicle(1, "Car", 50, testLocation.getLocation());
        testVehicle2 = new Vehicle(6, "Bike", 20, testLocation2.getLocation());
    }

    @Test
    void testConstructor() {
        assertEquals(testVehicle1.getType(), "Car");
        assertEquals(testVehicle1.getID(), 1);
        assertEquals(testVehicle1.getRentalRate(), 50);
        assertTrue(testVehicle1.getAvailability());
        assertEquals(testVehicle1.getLocation(), testLocation.getLocation());
        assertEquals(testVehicle1.getRentalHistory().size(), 0);

        assertEquals(testVehicle2.getType(), "Bike");
        assertEquals(testVehicle2.getID(), 6);
        assertEquals(testVehicle2.getRentalRate(), 20);
        assertTrue(testVehicle2.getAvailability());
        assertEquals(testVehicle2.getLocation(), testLocation2.getLocation());
        assertEquals(testVehicle2.getRentalHistory().size(), 0);
    }

    @Test
    void testRent() {
        List<String> rentalHistory;
        assertEquals(testVehicle1.getRentalHistory().size(), 0);

        testVehicle1.rent("Joe");
        assertFalse(testVehicle1.getAvailability());
        rentalHistory = testVehicle1.getRentalHistory();
        assertEquals(rentalHistory.size(), 1);
        assertEquals(rentalHistory.get(0), "Joe");

        testVehicle1.returnVehicle();

        testVehicle1.rent("Kimin");
        assertFalse(testVehicle1.getAvailability());
        rentalHistory = testVehicle1.getRentalHistory();
        assertEquals(rentalHistory.size(), 2);
        assertEquals(rentalHistory.get(0), "Joe");
        assertEquals(rentalHistory.get(1), "Kimin");
    }

    @Test
    void testReturnVehicle() {
        assertTrue(testVehicle1.getAvailability());
        testVehicle1.makeUnavailable();
        assertFalse(testVehicle1.getAvailability());
        testVehicle1.returnVehicle();
        assertTrue(testVehicle1.getAvailability());
        testVehicle1.returnVehicle();
        assertTrue(testVehicle1.getAvailability());

    }

    @Test
    void testCost() {
        assertEquals(testVehicle1.cost(3), 150);
        assertEquals(testVehicle1.cost(5), 250);

        assertEquals(testVehicle2.cost(2), 40);
        assertEquals(testVehicle2.cost(6), 120);
    }

    @Test
    void testGetRenter() {
        testVehicle1.rent("Joe");
        assertFalse(testVehicle1.getAvailability());
        assertEquals(testVehicle1.getRenter(), "Joe");
        testVehicle1.returnVehicle();

        testVehicle1.rent("Liano");
        assertFalse(testVehicle1.getAvailability());
        assertEquals(testVehicle1.getRenter(), "Liano");
        testVehicle1.returnVehicle();

        testVehicle1.rent("Pixin");
        assertFalse(testVehicle1.getAvailability());
        assertEquals(testVehicle1.getRenter(), "Pixin");
        testVehicle1.returnVehicle();
    }

    @Test
    void testGetType() {
        assertEquals(testVehicle1.getType(), "Car");
        assertEquals(testVehicle2.getType(), "Bike");
    }

    @Test
    void testGetRentalRate() {
        assertEquals(testVehicle1.getRentalRate(), 50);
        assertEquals(testVehicle2.getRentalRate(), 20);
        testVehicle1.setRentalRate(33);
        assertEquals(testVehicle1.getRentalRate(), 33);
    }

    @Test
    void testGetAvailability() {
        assertTrue(testVehicle1.getAvailability());
        testVehicle1.makeUnavailable();
        assertFalse(testVehicle1.getAvailability());
        testVehicle1.returnVehicle();
        assertTrue(testVehicle1.getAvailability());
    }
    
    @Test
    void testGetLocation() {
        assertEquals(testVehicle1.getLocation(), testLocation.getLocation());
        assertEquals(testVehicle2.getLocation(), testLocation2.getLocation());
    }

    @Test
    void testGetID() {
        assertEquals(testVehicle1.getID(), 1);
        assertEquals(testVehicle2.getID(), 6);
    }

    @Test
    void testGetRentalHistory() {
        List<String> rentalHistory;
        assertEquals(testVehicle1.getRentalHistory().size(), 0);

        testVehicle1.rent("Joe");
        rentalHistory = testVehicle1.getRentalHistory();
        assertEquals(rentalHistory.size(), 1);
        assertEquals(rentalHistory.get(0), "Joe");

        testVehicle1.returnVehicle();

        testVehicle1.rent("Kimin");
        rentalHistory = testVehicle1.getRentalHistory();
        assertEquals(rentalHistory.size(), 2);
        assertEquals(rentalHistory.get(0), "Joe");
        assertEquals(rentalHistory.get(1), "Kimin");
    }

    @Test
    void testSetRentalRate() {
        assertEquals(testVehicle1.getRentalRate(), 50);
        assertEquals(testVehicle2.getRentalRate(), 20);
        testVehicle1.setRentalRate(33);
        assertEquals(testVehicle1.getRentalRate(), 33);
        testVehicle1.setRentalRate(172);
        assertEquals(testVehicle1.getRentalRate(), 172);
    }

    @Test
    void testMakeUnAvailable() {
        assertTrue(testVehicle1.getAvailability());
        testVehicle1.makeUnavailable();
        assertFalse(testVehicle1.getAvailability());
        testVehicle1.makeUnavailable();
        assertFalse(testVehicle1.getAvailability());
    }

    @Test
    void testJsonBasicVehicle() {
        JSONObject testJson = testVehicle1.toJson();
        assertEquals(testJson.get("id"), testVehicle1.getID());
        assertEquals(testJson.get("type"), testVehicle1.getType());
        assertEquals(testJson.get("rentalrate"), testVehicle1.getRentalRate());
        assertEquals(testJson.get("available"), testVehicle1.getAvailability());
        assertEquals(testJson.get("location"), testVehicle1.getLocation());

        JSONArray testArray = testJson.getJSONArray("rentalhistory");
        assertTrue(testArray.isEmpty());
    }

    @Test
    void testJsonGeneralVehicle() {
        
        JSONObject testJson = testVehicle1.toJson();
        assertEquals(testJson.get("id"), testVehicle1.getID());
        assertEquals(testJson.get("type"), testVehicle1.getType());
        assertEquals(testJson.get("rentalrate"), testVehicle1.getRentalRate());
        assertEquals(testJson.get("available"), testVehicle1.getAvailability());
        assertEquals(testJson.get("location"), testVehicle1.getLocation());

        JSONArray rentalHistory = testJson.getJSONArray("rentalhistory");
        assertTrue(rentalHistory.isEmpty());
        
        testVehicle1.rent("Julia");

        testJson = testVehicle1.toJson();
        rentalHistory = testJson.getJSONArray("rentalhistory");

        assertEquals(testJson.get("available"), testVehicle1.getAvailability());
        assertEquals(rentalHistory.get(0), testVehicle1.getRentalHistory().get(0));
        assertEquals(rentalHistory.length(), testVehicle1.getRentalHistory().size());

        testVehicle1.returnVehicle();
        testVehicle1.rent("Sam");
        testVehicle1.returnVehicle();
        
        testJson = testVehicle1.toJson();
        rentalHistory = testJson.getJSONArray("rentalhistory");

        assertEquals(testJson.get("available"), testVehicle1.getAvailability());
        assertEquals(rentalHistory.get(0), testVehicle1.getRentalHistory().get(0));
        assertEquals(rentalHistory.get(1), testVehicle1.getRentalHistory().get(1));
        assertEquals(rentalHistory.length(), testVehicle1.getRentalHistory().size());
    }
}
