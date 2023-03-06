import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;

public class AddFilmPane {
    public static Scene scene;
    public static Button back, OK;

    public static Scene createScene(User user) {

        VBox addFilmPane = new VBox(10);

        Label instruction = new Label("Please give name, relative path and duration of film.");
        instruction.setAlignment(Pos.CENTER);

        VBox vbox1 = new VBox(10);
        TextField filmName = new TextField();
        TextField trailerPath = new TextField();
        TextField filmDuration = new TextField();
        vbox1.getChildren().addAll(filmName, trailerPath, filmDuration);


        VBox vbox2 = new VBox(20);
        Label name = new Label("Name:");
        Label path = new Label("Trailer (Path):");
        Label duration = new Label("Duration (m):");
        vbox2.getChildren().addAll(name, path, duration);
        vbox2.setAlignment(Pos.CENTER_LEFT);

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(vbox2, vbox1);
        hBox.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(164);
        back = new Button("Back");
        OK = new Button("OK");
        buttons.getChildren().addAll(back, OK);
        buttons.setAlignment(Pos.CENTER);

        Label message = new Label("");
        message.setAlignment(Pos.CENTER);

        addFilmPane.getChildren().addAll(instruction, hBox, buttons, message);
        addFilmPane.setAlignment(Pos.CENTER);

        OK.setOnAction(event -> {
            String nameOfFilm = filmName.getText();
            String durationOfFilm = filmDuration.getText();
            String pathOfFilm = trailerPath.getText();

            if (nameOfFilm.equals("")) { // if film name is empty
                SoundAlert.playSound();
                message.setText("ERROR: Film name could not be empty!");
            } else {
                if (pathOfFilm.equals("")) { // if trailer path is empty
                    SoundAlert.playSound();
                    message.setText("ERROR: Trailer path could not be empty!");
                } else {
                    if (durationOfFilm.equals("")) { // if duration is empty
                        SoundAlert.playSound();
                        message.setText("ERROR: Duration could not be empty!");
                    } else {
                        try { // trying to convert duration of film to integer
                            int drtn = Integer.parseInt(durationOfFilm);
                            if (drtn <= 0) {
                                SoundAlert.playSound();
                                message.setText("ERROR: Duration has to be positive integer!");
                            } else {
                                String fullPath = Main.localDir + "/assets/trailers/" + pathOfFilm;
                                File trailerFile = new File(fullPath);
                                if (trailerFile.exists()) {
                                    if (Movie.movies.get(nameOfFilm) != null) { // if film name already exists
                                        SoundAlert.playSound();
                                        message.setText("ERROR: The film already exists!");
                                    } else {
                                        // creating new movie object
                                        Movie.movies.put(nameOfFilm, new Movie(nameOfFilm, pathOfFilm, drtn));
                                        message.setText("SUCCESS: Film added successfully!");
                                    }
                                } else {
                                    SoundAlert.playSound();
                                    message.setText("ERROR: There is not such a trailer!");
                                }
                            }
                        } catch (Exception e) {
                            SoundAlert.playSound();
                            message.setText("ERROR: Duration has to be positive integer!");
                        }
                    }
                }
            }
        });


        back.setOnAction(e -> Main.window.setScene(HomepageAdmin.createScene(user)));


        scene = new Scene(addFilmPane, 330, 214);

        return scene;
    }
}
