package Business;

public class Booking {
    private int id;
    private Aircraft aircraft;
    private Route route;

    public int getId() {
        return id;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public Route getRoute(){
        return route;
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

    public Booking(int id, Aircraft aircraft, Route route){
        this.id = id;
        this.aircraft = aircraft;
        this.route = route;
    }
}
