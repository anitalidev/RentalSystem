package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.RentalLocation;
import model.Vehicle;

// Attribution: Based on Json Demo Project
public class JsonTest {
    protected void checkLocation(String location, int maxCapacity, int nextID, 
                                List<Vehicle> vehicles, RentalLocation rentalLocation) {
        assertEquals(location, rentalLocation.getLocation());
        assertEquals(maxCapacity, rentalLocation.getMaxCapacity());
        assertEquals(nextID, rentalLocation.getNextID());
        assertEquals(location, rentalLocation.getLocation());
        checkVehicles(rentalLocation.getVehicles(), vehicles);
    }

    protected void checkVehicles(List<Vehicle> vehicles, List<Vehicle> correctVehicles) {
        assertTrue(vehicles.size() == correctVehicles.size());
        Vehicle correct;
        Vehicle read;
        int size = vehicles.size();
        for (int i = 0; i < size; i++) {
            read = vehicles.get(i);
            correct = correctVehicles.get(i);
            checkVehicle(correct.getID(), correct.getType(), correct.getRentalRate(), correct.getAvailability(), 
                        correct.getLocation(), correct.getRentalHistory(), read);
        }
    }

    protected void checkVehicle(int id, String type, int rentalRate, boolean available, String location,
                                List<String> history, Vehicle vehicle) {
        assertEquals(id, vehicle.getID());
        assertEquals(type, vehicle.getType());
        assertEquals(rentalRate, vehicle.getRentalRate());
        assertEquals(available, vehicle.getAvailability());
        assertEquals(location, vehicle.getLocation());
        checkHistory(vehicle.getRentalHistory(), history);
    }

    protected void checkHistory(List<String> history, List<String> correctHistory) {
        assertTrue(history.size() == correctHistory.size());
        int size = history.size();
        for (int i = 0; i < size; i++) {
            assertTrue(history.get(i).equals(correctHistory.get(i)));
        }
    }
}
