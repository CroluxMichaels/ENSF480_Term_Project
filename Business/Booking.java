package Business;

import java.sql.SQLException;
import Business.Payment;

public class Booking {
    private int id;
    private Aircraft aircraft;
    private Route route;
    private boolean paid;

    public int getId() {
        return id;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public Route getRoute(){
        return route;
    }

    public boolean getPaid(){
        return paid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void paymentChecker(int payID) throws SQLException{
        String str = Payment.retrieveOutcome(payID);
        if (str == "APPROVED") paid = true;
        else paid = false;
        return;
    }

    public Booking(int id, Aircraft aircraft, Route route){
        this.id = id;
        this.aircraft = aircraft;
        this.route = route;
    }
}
