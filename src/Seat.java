public class Seat {
    public final Hall hall;
    public final int rowIndex;
    public final int columnIndex;
    public final Movie movie;
    public int price = -1;
    public User owner = null;


    public Seat(Movie movie, Hall hall, int rowIndex, int columnIndex) {
        this.movie = movie;
        this.hall = hall;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public Seat(Movie movie, Hall hall, int rowIndex, int columnIndex, User owner, int price) {
        this(movie, hall, rowIndex, columnIndex);
        this.owner = owner;
        this.price =price;
        owner.myTickets.add(hall);
    }

    // to check whether seat is empty
    public boolean isEmpty() {
        return owner == null;
    }

    // returns seat status
    public String getSeatStatus() {
        if (owner == null ) { // if seat is empty
            return "Not bought yet";
        } else {
            return "Bought by " + owner + " for " + price + "TL!";
        }
    }
}
