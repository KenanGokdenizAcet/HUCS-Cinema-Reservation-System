public class Hall {
    public final int row, column, price;
    public final String name;
    public final Movie movie;
    public Seat[][] seats;

    public Hall(Movie movie ,String name, int price, int row, int column, boolean createSeat) {
        this.name = name;
        this.row = row;
        this.column = column;
        this.price = price;
        this.movie = movie;

        this.seats = new Seat[row][column]; // creating Seat array according to row and column number

        // if createSeat is true, seats will be created when a new hall is created
        if (createSeat) {
            for (int r = 0; r < row; r++) { // r is row number
                for (int c = 0; c < column; c++) { // c is column number
                    seats[r][c] = new Seat(movie, this, r, c); // creating new seat
                }
            }
        }

    }

    @Override
    public String toString() {
        return name;
    }
}
