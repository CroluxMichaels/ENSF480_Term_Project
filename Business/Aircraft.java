package Business;
import java.util.ArrayList;

public class Aircraft {
    private int id;
    private String name;
    private ArrayList<Seat> seats;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = new ArrayList<>();
        this.seats.addAll(seats);
    }

    public Aircraft(int id, String name, ArrayList<Seat> seats){
        this.id = id;
        this.name = name;
        this.seats = new ArrayList<>();
        this.seats.addAll(seats);
    }
}
