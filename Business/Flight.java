package Business;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Flight {
    private int flightId;
    private String flightDesignator;
    private Aircraft aircraft;
    private Route route;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private BigDecimal fare;
    private int seatCapacity;
    private boolean scheduled;

    public int getFlightId() {
        return flightId;
    }

    public String getFlightDesignator() {
        return flightDesignator;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public Route getRoute() {
        return route;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public Flight(int flightId, String flightDesignator, Aircraft aircraft, Route route, LocalDateTime departureTime,
                  LocalDateTime arrivalTime, BigDecimal fare, int seatCapacity, boolean scheduled){
        this.flightId = flightId;
        this.flightDesignator = flightDesignator;
        this.aircraft = aircraft;
        this.route = route;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.fare = fare;
        this.seatCapacity = seatCapacity;
        this.scheduled = scheduled;
    }
}
