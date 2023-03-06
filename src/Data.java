import java.util.ArrayList;
import java.util.Map;

public class Data {

    public static void load() {
        ArrayList<String[]> lines = new ArrayList<>();
        String dataFilePath = Main.localDir + "/assets/data/backup.dat";
        try {
            lines = FileOperator.readFile(dataFilePath, "\t");

            for (String[] line : lines) {
                if (line[0].equals("user")) {
                    String userName = line[1];
                    String password = line[2];
                    String isClubMemberStr = line[3];
                    boolean isClubMember = isClubMemberStr.equals("true");

                    if (line[4].equals("true")) {
                        new Admin(userName, password, isClubMember);
                    } else {
                        new User(userName, password, isClubMember);
                    }
                } else if (line[0].equals("film")) {
                    String filmName = line[1];
                    String filmTrailerPath = line[2];
                    int filmDuration = Integer.parseInt(line[3]);

                    Movie.movies.put(filmName,new Movie(filmName, filmTrailerPath, filmDuration)); //creating new movie object

                } else if (line[0].equals("hall")) {
                    Movie film = Movie.movies.get(line[1]); //getting movie from movies hashmap
                    String hallName = line[2];
                    int price = Integer.parseInt(line[3]);
                    int row = Integer.parseInt(line[4]);
                    int column = Integer.parseInt(line[5]);

                    film.halls.put(hallName, new Hall(film, hallName, price, row, column, false));

                } else if (line[0].equals("seat")) {
                    Movie film = Movie.movies.get(line[1]);
                    Hall hall = film.halls.get(line[2]);
                    int rowNumber = Integer.parseInt(line[3]);
                    int columnNumber = Integer.parseInt(line[4]);
                    int price = Integer.parseInt(line[6]);
                    if (!line[5].equals("null")) {
                        User owner = User.users.get(line[5]);
                        hall.seats[rowNumber][columnNumber] = new Seat(film, hall, rowNumber, columnNumber, owner, price);
                        owner.myTickets.add(hall);
                    } else {
                        hall.seats[rowNumber][columnNumber] = new Seat(film, hall, rowNumber, columnNumber);
                    }
                }
            }
        } catch (Exception e) {
            FileOperator.writeFile("admin\tpassword\tfalse\ttrue", "backup.dat");
            new Admin("admin", User.hashPassword("password"), true);
        }

        String propertiesFilePath = Main.localDir + "/assets/data/properties.dat";
        ArrayList<String> properties = FileOperator.readFile(propertiesFilePath);

        try {
            assert properties != null;
            for (String line : properties) {

                String[] splitLine = line.split("=");
                if (splitLine[0].startsWith("title")) {
                    Main.title = splitLine[1];
                } else if (splitLine[0].startsWith("maximum")) {
                    Main.maximumErrorWithoutGettingBlocked = Integer.parseInt(splitLine[1]);
                } else if (splitLine[0].startsWith("discount")) {
                    Main.discountPercentage = Integer.parseInt(splitLine[1]);
                } else if (splitLine[0].startsWith("block")) {
                    Main.blockTime = Integer.parseInt(splitLine[1]);
                }
            }
        } catch (Exception ignored) {

        }
    }


    public static void backUp() {
        String backUpPath = Main.localDir + "/assets/data/backup.dat";

        String data = "";

        // to store users' data
        for (Map.Entry<String, User> set : User.users.entrySet()) {
            String line = "";
            User user = set.getValue();

            line = "user" + "\t" + user.getUsername() + "\t" + user.getPassword() + "\t" + user.clubMember
                    + "\t" + user.userType + "\n";

            data += line;
        }

        // to store movies' data
        for (Map.Entry<String, Movie> set : Movie.movies.entrySet()) {
            String line = "";
            Movie movie = set.getValue();

            line = "film\t" + movie.getName() + "\t" + movie.getPath() + "\t" + movie.getDuration() + "\n";

            data += line;
        }

        // to store halls' data
        for (Map.Entry<String, Movie> set : Movie.movies.entrySet()) {
            for (Map.Entry<String, Hall> s : set.getValue().halls.entrySet()) {
                String line = "";
                Hall hall = s.getValue();

                line = "hall\t" + hall.movie.getName() + "\t" + hall.name + "\t" + hall.price + "\t"
                        + hall.row + "\t" + hall.column + "\n";

                data += line;
            }
        }


        // to store seats' data
        for (Map.Entry<String, Movie> set : Movie.movies.entrySet()) {
            for (Map.Entry<String, Hall> s : set.getValue().halls.entrySet()) {
                Hall hall = s.getValue();
                for (int rowIndex = 0 ; rowIndex < hall.row ; rowIndex ++) {
                    for (int columnIndex = 0 ; columnIndex < hall.column ; columnIndex ++){
                        Seat seat = hall.seats[rowIndex][columnIndex];
                        String ownerString;

                        if (seat.owner == null) {
                            ownerString = null;
                        } else {
                            ownerString = seat.owner.getUsername();
                        }
                        String line = "seat\t" + seat.movie.getName() + "\t" + seat.hall.name + "\t"
                                + seat.rowIndex + "\t" + seat.columnIndex + "\t" + ownerString + "\t"
                                + seat.price + "\n";

                        data += line;
                    }
                }
            }
        }

        FileOperator.writeFile(data, backUpPath);
    }
}
