import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class RemoveFilmPane {
    public static Scene scene;
    public static Button back, OK;

    public static Scene createScene() {

        VBox removeFilmPane = new VBox(10);

        Label instruction = new Label("Select the film that you desire to remove and then click OK.");

        ChoiceBox<Movie> films = new ChoiceBox<>(); // choice box for the films
        films.setPrefWidth(180);
        Movie.addMoviesToChoiceBox(films); // to add films to choice box
        films.getSelectionModel().selectFirst();


        HBox buttons = new HBox(10);
        back = new Button("Back");
        OK = new Button("OK");
        buttons.getChildren().addAll(back, OK);
        buttons.setAlignment(Pos.CENTER);

        Label message = new Label("");

        removeFilmPane.getChildren().addAll(instruction, films, buttons, message);
        removeFilmPane.setAlignment(Pos.CENTER);

        back.setOnAction(e -> Main.window.setScene(HomepageAdmin.createScene(Main.user)));
        OK.setOnAction(e -> {
            Movie selectedFilm = films.getValue();

            if (selectedFilm == null) {
                message.setText("ERROR: There is no movie!");
                SoundAlert.playSound();
            } else {
                for (Map.Entry<String, Hall> set : selectedFilm.halls.entrySet()) { // to remove movie's hall
                    selectedFilm.halls.remove(set.getValue());
                }
                Movie.movies.remove(selectedFilm.getName()); // to remove the film from movies hashmap
                message.setText("SUCCESS: Film removes successfully!");
            }
        });


        scene = new Scene(removeFilmPane, 340,140);

        return scene;
    }
}
