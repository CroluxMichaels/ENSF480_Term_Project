package Business;

import java.util.ArrayList;

public class Customer extends Person{
    private ArrayList<Booking> bookings;

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = new ArrayList<>();
        this.bookings.addAll(bookings);
    }

    public Customer(int id, String name, String email, String phone){
        super(id, name, email, phone);
        bookings = new ArrayList<>();
    }

    public Customer(int id, String name, String email, String phone, ArrayList<Booking> bookings) {
        super(id, name, email, phone);
        this.bookings = new ArrayList<>();
        this.bookings.addAll(bookings);
    }
}
