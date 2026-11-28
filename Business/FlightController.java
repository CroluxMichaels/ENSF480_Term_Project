package Business;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FlightController {
    public FlightController() {

    }
    public ArrayList<Flight> searchFlights(String origin, String destination, LocalDate date, String airlineCode) throws SQLException {
        // TODO

        ArrayList<Flight> flights = new ArrayList<>();
        return flights;
    }
}
