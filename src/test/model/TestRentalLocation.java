package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestRentalLocation {

    RentalLocation testLocation1;
    RentalLocation testLocation2;

    @BeforeEach
    void runBefore() {
        testLocation1 = new RentalLocation("ICCS", 10);
        testLocation2 = new RentalLocation("SCARFE", 2);
    }

    @Test
    void testConstructor() {
        assertEquals(testLocation1.getLocation(), "ICCS");
        assertEquals(testLocation2.getLocation(), "SCARFE");

        assertEquals(testLocation1.getMaxCapacity(), 10);
        assertEquals(testLocation2.getMaxCapacity(), 2);

        assertEquals(testLocation1.getCurrentCapacity(), 0);
        assertEquals(testLocation2.getCurrentCapacity(), 0);

        assertEquals(testLocation1.getNextID(), 0);
        assertEquals(testLocation2.getNextID(), 0);

        assertEquals(testLocation1.getVehicles().size(), 0);
        assertEquals(testLocation2.getVehicles().size(), 0);
    }

    @Test
    void testAddVehicle() {
        assertEquals(testLocation1.addVehicle("Car", 35), 0);
        assertEquals(testLocation1.getVehicles().size(), 1);
        assertEquals(testLocation1.getVehicles().get(0).getType(), "Car");
        assertEquals(testLocation1.getVehicles().get(0).getID(), 0);
        assertEquals(testLocation1.getVehicles().get(0).getRentalRate(), 35);
        assertEquals(testLocation1.getCurrentCapacity(), 1);
        assertEquals(testLocation1.getMaxCapacity(), 10);
    }

    @Test
    void testAddMultipleVehicle() {
        assertEquals(testLocation1.addVehicle("Car", 10), 0);
        assertEquals(testLocation1.addVehicle("Van", 20), 1);

        assertEquals(testLocation1.getVehicles().size(), 2);
        assertEquals(testLocation1.getCurrentCapacity(), 2);
        assertEquals(testLocation1.getMaxCapacity(), 10);

        assertEquals(testLocation1.getVehicles().get(0).getType(), "Car");
        assertEquals(testLocation1.getVehicles().get(0).getID(), 0);
        assertEquals(testLocation1.getVehicles().get(0).getRentalRate(), 10);

        assertEquals(testLocation1.getVehicles().get(1).getType(), "Van");
        assertEquals(testLocation1.getVehicles().get(1).getID(), 1);
        assertEquals(testLocation1.getVehicles().get(1).getRentalRate(), 20);
    }

    @Test
    void testAddVehicleOverflow() {
        assertEquals(testLocation2.addVehicle("Car", 10), 0);
        assertEquals(testLocation2.addVehicle("Van", 20), 1);
        assertEquals(testLocation2.addVehicle("Bike", 30), -1);
        
        assertEquals(testLocation2.getVehicles().size(), 2);
        assertEquals(testLocation2.getCurrentCapacity(), 2);
        assertEquals(testLocation2.getMaxCapacity(), 2);
        assertEquals(testLocation2.getNextID(), 2);
    }

    @Test
    void testAddVehicle2() {
        Vehicle testVehicle = new Vehicle(0, "Bus", 35, "ICCS");

        assertTrue(testLocation1.addVehicle(testVehicle));
        assertEquals(testLocation1.getNextID(), 1);
        assertEquals(testLocation1.getVehicles().size(), 1);
        assertEquals(testLocation1.getVehicles().get(0).getType(), "Bus");
        assertEquals(testLocation1.getVehicles().get(0).getID(), 0);
        assertEquals(testLocation1.getVehicles().get(0).getRentalRate(), 35);
        assertEquals(testLocation1.getCurrentCapacity(), 1);
        assertEquals(testLocation1.getMaxCapacity(), 10);
    }

    @Test
    void testAddMultipleVehicle2() {
        Vehicle testVehicle = new Vehicle(0, "Bus", 35, "ICCS");
        Vehicle testVehicle2 = new Vehicle(3, "SUV", 22, "ICCS");
        Vehicle testVehicle3 = new Vehicle(2, "Car", 11, "ICCS");

        assertTrue(testLocation1.addVehicle(testVehicle));
        assertEquals(testLocation1.getNextID(), 1);
        assertEquals(testLocation1.getVehicles().size(), 1);
        assertTrue(checkVehicle(testLocation1.getVehicles().get(0), testVehicle));
        assertEquals(testLocation1.getCurrentCapacity(), 1);

        assertTrue(testLocation1.addVehicle(testVehicle2));
        assertEquals(testLocation1.getNextID(), 4);
        assertEquals(testLocation1.getVehicles().size(), 2);
        assertTrue(checkVehicle(testLocation1.getVehicles().get(1), testVehicle2));
        assertEquals(testLocation1.getMaxCapacity(), 10);

        assertTrue(testLocation1.addVehicle(testVehicle3));
        assertEquals(testLocation1.getNextID(), 4);
        assertEquals(testLocation1.getVehicles().size(), 3);
        assertTrue(checkVehicle(testLocation1.getVehicles().get(2), testVehicle3));
        assertEquals(testLocation1.getMaxCapacity(), 10);
    }

    private boolean checkVehicle(Vehicle vehicle, Vehicle correct) {
        if (vehicle.getType() != correct.getType()) {
            return false;
        } else if (vehicle.getID() != correct.getID()) {
            return false;
        } else if (vehicle.getRentalRate() != correct.getRentalRate()) {
            return false;
        } else {
            return true;
        }
    }

    @Test
    void testAddVehicleOverflow2() {
        Vehicle testVehicle = new Vehicle(0, "Bus", 35, "ICCS");
        Vehicle testVehicle2 = new Vehicle(3, "SUV", 22, "ICCS");
        Vehicle testVehicle3 = new Vehicle(2, "Car", 11, "ICCS");

        assertTrue(testLocation2.addVehicle(testVehicle));
        assertTrue(testLocation2.addVehicle(testVehicle3));
        assertFalse(testLocation2.addVehicle(testVehicle2));
        
        assertEquals(testLocation2.getVehicles().get(0).getType(), "Bus");
        assertEquals(testLocation2.getVehicles().get(0).getID(), 0);
        assertEquals(testLocation2.getVehicles().get(0).getRentalRate(), 35);

        assertEquals(testLocation2.getVehicles().get(1).getType(), "Car");
        assertEquals(testLocation2.getVehicles().get(1).getID(), 2);
        assertEquals(testLocation2.getVehicles().get(1).getRentalRate(), 11);

        assertEquals(testLocation2.getNextID(), 3);
        assertEquals(testLocation2.getVehicles().size(), 2);
        assertEquals(testLocation2.getCurrentCapacity(), 2);
        assertEquals(testLocation2.getMaxCapacity(), 2);
    }

    @Test
    void testRemoveNoVehicles() {
        assertFalse(testLocation2.removeVehicle(0));
    }


    @Test
    void testRemoveVehicle() {
        testLocation2.addVehicle("Car", 10);
        assertTrue(testLocation2.removeVehicle(0));
        assertEquals(testLocation2.getVehicles().size(), 0);
        assertEquals(testLocation2.getCurrentCapacity(), 0);
        assertEquals(testLocation2.getMaxCapacity(), 2);
        assertEquals(testLocation2.getNextID(), 1);
        assertFalse(testLocation2.removeVehicle(0));
    }

    @Test
    void testRemoveMultipleVehicle() {
        testLocation1.addVehicle("Car", 10);
        testLocation1.addVehicle("Van", 10);

        assertTrue(testLocation1.removeVehicle(1));
        assertEquals(testLocation1.getVehicles().size(), 1);
        assertEquals(testLocation1.getCurrentCapacity(), 1);
        assertEquals(testLocation1.getMaxCapacity(), 10);
        assertEquals(testLocation1.getNextID(), 2);

        assertFalse(testLocation1.removeVehicle(1));
        assertFalse(testLocation1.removeVehicle(2));

        assertTrue(testLocation1.removeVehicle(0));
        assertEquals(testLocation1.getVehicles().size(), 0);
        assertEquals(testLocation1.getCurrentCapacity(), 0);
        assertEquals(testLocation1.getNextID(), 2);
    }

    @Test
    void testHasSpace() {
        assertTrue(testLocation2.hasSpace());
        testLocation2.addVehicle("Car", 10);
        assertTrue(testLocation2.hasSpace());
        testLocation2.addVehicle("Bus", 20);
        assertFalse(testLocation2.hasSpace());
    }

    @Test
    void testBasicFindVehicle() {
        assertNull(testLocation1.findVehicle(1));
        testLocation1.addVehicle("Car", 10);
        assertNull(testLocation1.findVehicle(1));
        assertEquals(testLocation1.findVehicle(0), testLocation1.getVehicles().get(0));
        testLocation1.addVehicle("Truck", 10);
        assertEquals(testLocation1.findVehicle(1), testLocation1.getVehicles().get(1));

        assertNull(testLocation1.findVehicle(2));
    }

    @Test
    void findRemovedVehicle() {
        assertNull(testLocation1.findVehicle(0));
        testLocation1.addVehicle("Car", 10);
        assertEquals(testLocation1.findVehicle(0), testLocation1.getVehicles().get(0));
        testLocation1.addVehicle("Bus", 10);
        testLocation1.removeVehicle(0);
        assertNull(testLocation1.findVehicle(0));
        assertEquals(testLocation1.findVehicle(1), testLocation1.getVehicles().get(0));
    }

    @Test
    void testGetLocation() {
        assertEquals(testLocation1.getLocation(), "ICCS");
        assertEquals(testLocation2.getLocation(), "SCARFE");
    }

    @Test
    void testGetMaxCapacity() {
        assertEquals(testLocation1.getMaxCapacity(), 10);
        assertEquals(testLocation2.getMaxCapacity(), 2);
    }

    @Test
    void testGetCurrentCapacity() {
        assertEquals(testLocation1.getCurrentCapacity(), 0);
        testLocation1.addVehicle("Car", 10);
        assertEquals(testLocation1.getCurrentCapacity(), 1);
        testLocation1.addVehicle("Bus", 10);
        assertEquals(testLocation1.getCurrentCapacity(), 2);
    }

    @Test
    void testGetNextID() {
        assertEquals(testLocation1.getNextID(), 0);
        testLocation1.addVehicle("Car", 10);
        assertEquals(testLocation1.getNextID(), 1);
        testLocation1.addVehicle("Bus", 10);
        assertEquals(testLocation1.getNextID(), 2);
        testLocation1.removeVehicle(0);
        assertEquals(testLocation1.getNextID(), 2);
    }

    @Test
    void testSetNextID() {
        assertEquals(testLocation1.getNextID(), 0);
        testLocation1.setNextID(5);
        assertEquals(testLocation1.getNextID(), 5);
        testLocation1.setNextID(6);
        assertEquals(testLocation1.getNextID(), 6);        
    }

    @Test
    void testGetVehicles() {
        testLocation1.addVehicle("Car", 10);

        assertEquals(testLocation1.getVehicles().size(), 1);
        assertEquals(testLocation1.getVehicles().get(0).getType(), "Car");
        assertEquals(testLocation1.getVehicles().get(0).getID(), 0);

        testLocation1.addVehicle("Van", 10);

        assertEquals(testLocation1.getVehicles().size(), 2);
        assertEquals(testLocation1.getVehicles().get(0).getType(), "Car");
        assertEquals(testLocation1.getVehicles().get(0).getID(), 0);

        assertEquals(testLocation1.getVehicles().size(), 2);
        assertEquals(testLocation1.getVehicles().get(1).getType(), "Van");
        assertEquals(testLocation1.getVehicles().get(1).getID(), 1);

        testLocation1.removeVehicle(0);

        assertEquals(testLocation1.getVehicles().size(), 1);
        assertEquals(testLocation1.getVehicles().get(0).getType(), "Van");
        assertEquals(testLocation1.getVehicles().get(0).getID(), 1);
    }

    @Test
    void testSetLocation() {
        assertEquals(testLocation1.getLocation(), "ICCS");
        testLocation1.setLocation("The Nest");
        assertEquals(testLocation1.getLocation(), "The Nest");
        testLocation1.setLocation("WESB");
        assertEquals(testLocation1.getLocation(), "WESB");
    }


    @Test
    void testSetMaxCapacity() {
        assertEquals(testLocation1.getMaxCapacity(), 10);
        testLocation1.setMaxCapacity(3);
        assertEquals(testLocation1.getMaxCapacity(), 3);
        testLocation1.setMaxCapacity(16);
        assertEquals(testLocation1.getMaxCapacity(), 16);
    }

    @Test
    void testJsonBasicLocation() {
        JSONObject testJson = testLocation1.toJson();

        assertEquals(testJson.get("location"), testLocation1.getLocation());
        assertEquals(testJson.get("maxcapacity"), testLocation1.getMaxCapacity());
        assertEquals(testJson.get("nextid"), testLocation1.getNextID());
        assertTrue(testJson.getJSONArray("vehicles").isEmpty());
    }

    @Test
    void testJsonGeneral() {
        JSONObject testJson = testLocation1.toJson();

        assertEquals(testJson.get("location"), testLocation1.getLocation());
        assertEquals(testJson.get("maxcapacity"), testLocation1.getMaxCapacity());
        assertEquals(testJson.get("nextid"), testLocation1.getNextID());
        assertTrue(testJson.getJSONArray("vehicles").isEmpty());
        
        testLocation1.addVehicle("Bus", 10);
        testLocation1.addVehicle("Car", 5);

        Vehicle vehicle1 = testLocation1.getVehicles().get(0);
        Vehicle vehicle2 = testLocation1.getVehicles().get(1);

        testJson = testLocation1.toJson();

        assertEquals(testJson.get("nextid"), testLocation1.getNextID());
        assertEquals(testJson.getJSONArray("vehicles").length(), testLocation1.getVehicles().size());
        assertEquals(testJson.getJSONArray("vehicles").getJSONObject(0).get("id"), vehicle1.getID());
        assertEquals(testJson.getJSONArray("vehicles").getJSONObject(1).get("id"), vehicle2.getID());

        testLocation1.removeVehicle(0);

        testJson = testLocation1.toJson();

        assertEquals(testJson.get("nextid"), testLocation1.getNextID());
        assertEquals(testJson.getJSONArray("vehicles").length(), testLocation1.getVehicles().size());
        assertEquals(testJson.getJSONArray("vehicles").getJSONObject(0).get("id"), vehicle2.getID());
    }
}
