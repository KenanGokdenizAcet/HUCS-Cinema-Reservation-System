import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class HomepageRegularUser {
    public static Scene scene;
    public static Button OK, logOut, profile;

    public static Scene createScene(User user) {

        VBox homepagePaneAdmin = new VBox(10);

        VBox texts = new VBox(1);
        Label greetings = new Label("Welcome " + user.getUsername() +" "+ user.getStatus() + "!");
        Label instruction = new Label("Select a film and the click OK to continue.");
        Label message = new Label("");
        texts.getChildren().addAll(greetings, instruction);
        texts.setAlignment(Pos.CENTER);


        HBox hbox = new HBox(10);
        ChoiceBox<Movie> movieList = new ChoiceBox<>(); // choice box for the movies
        movieList.setStyle("-fx-pref-width: 290");
        Movie.addMoviesToChoiceBox(movieList); // adding the movies to the movieList choice box
        movieList.getSelectionModel().selectFirst();
        OK = new Button("OK");
        hbox.getChildren().addAll(movieList, OK);
        hbox.setAlignment(Pos.CENTER);


        HBox hbox2 = new HBox(210);
        profile = new Button("Profile");
        logOut = new Button("LOG OUT");
        hbox2.getChildren().addAll(profile, logOut);
        hbox2.setAlignment(Pos.CENTER);


        profile.setOnAction(e -> Main.window.setScene(ProfilePane.createScene(user))); // setting the scene prfile pane
        OK.setOnAction(e -> { // getting the movie from choice box and setting the scene selected movies film pane
            Movie selectedMovie = movieList.getValue();
            if (selectedMovie == null) {
                message.setText("ERROR: There is no movie!");
                SoundAlert.playSound();
            } else {
                Main.window.setScene(FilmPane.createScene(selectedMovie));
            }
        });
        logOut.setOnAction(e -> Main.window.setScene(LoginPane.createScene())); // to log out

        homepagePaneAdmin.getChildren().addAll(texts, hbox, hbox2, message);
        homepagePaneAdmin.setAlignment(Pos.CENTER);

        scene = new Scene(homepagePaneAdmin, 380, 180);

        return scene;
    }

}
