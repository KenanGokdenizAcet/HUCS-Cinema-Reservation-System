import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ProfilePane {

    public static Scene scene;
    public static Button beClubMember, goBack, logOut;

    public static Scene createScene(User user) { // to create profile pane

        VBox profilePane = new VBox(10);


        HBox movieBox = new HBox(10);
        Label ticket = new Label("My Tickets");
        Button OK = new Button("OK");
        ChoiceBox<String> tickets = new ChoiceBox<>(); // movie list that movies are bought
        User.addTicketsToChoiceBox(tickets);
        tickets.setStyle("-fx-pref-width: 250");

        tickets.getSelectionModel().selectFirst();
        movieBox.getChildren().addAll(ticket, tickets, OK);
        movieBox.setAlignment(Pos.CENTER);

        HBox hbox = new HBox(); // buttons and message are in this hbox
        logOut = new Button("LOG OUT");
        goBack = new Button("Back");
        beClubMember = new Button("Be a Club Member");
        if (!user.clubMember) { // if user is not a club member
            HBox box = new HBox(60);
            box.getChildren().addAll(goBack, beClubMember, logOut);
            hbox = box;
        } else {
            HBox box = new HBox(242);
            box.getChildren().addAll(goBack,logOut);
            hbox = box;
        }
        hbox.setAlignment(Pos.CENTER);

        Label userStatus = new Label(user.getUsername() + user.getStatus());
        Label instruction = new Label("Select a movie and click OK to see your seat!");
        Label message = new Label("");


        profilePane.getChildren().addAll(userStatus, instruction, movieBox, hbox, message);
        profilePane.setAlignment(Pos.CENTER);

        goBack.setOnAction(e -> {
            if (user.userType) {
                Main.window.setScene(HomepageAdmin.createScene(user));
            } else {
                Main.window.setScene(HomepageRegularUser.createScene(user));
            }
        });

        beClubMember.setOnAction(e -> { // to make the user a club member
            user.clubMember = true;
            Main.window.setScene(ProfilePane.createScene(user)); // updating scene
            message.setText("Now you are a club member!");
        });

        logOut.setOnAction(event -> {
            Main.window.setScene(LoginPane.createScene());
        });

        OK.setOnAction(event -> {
            String selectedTicket = tickets.getValue();
            if (selectedTicket == null) {
                message.setText("ERROR: There is no ticket!");
                SoundAlert.playSound();
            } else {
                String[] splitSelectedTicket = selectedTicket.split(" - ");
                String movieName = splitSelectedTicket[0];
                String hallName = splitSelectedTicket[1];
                Movie movie = Movie.movies.get(movieName);
                Hall hall = movie.halls.get(hallName);
                if (user.userType) { // if user is admin
                    Main.window.setScene(HallPaneAdmin.createScene(hall));
                } else {
                    Main.window.setScene(HallPane.createScene(hall));
                }
            }
        });

        scene = new Scene(profilePane, 380, 156);

        return scene;
    }
}
