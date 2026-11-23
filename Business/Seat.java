package Business;

public class Seat {
    private int id;
    private int bookingId;
    private String number;

    public int getId() {
        return id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getNumber() {
        return number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Seat(int id, int bookingId, String number){
        this.id = id;
        this.bookingId = bookingId;
        this.number = number;
    }
}
