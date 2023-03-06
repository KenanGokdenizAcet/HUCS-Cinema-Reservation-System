import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.File;

public class FilmPane {
    public static Scene scene;
    public static Button playStop, skipBack, skipForward, back, OK, restart;
    public static Slider slider;


    public static Scene createScene(Movie movie) {
        String moviePath = movie.getFullPath();
        int movieDuration = movie.getDuration();
        String movieName = movie.getName();
        double movieScore = movie.getScore();

        VBox filmPane = new VBox(10);

        HBox filmBox = new HBox(30);
        HBox filmText = new HBox();
        Label film = new Label(movie.getName() + "(" + movie.getDuration() + ")");
        filmText.getChildren().addAll(film);

        Label message = new Label("");


        // creating rectangle to showing movie score
        StackPane imdbScore = new StackPane();
        Rectangle rectangle = new Rectangle(44,20);
        rectangle.setFill(Color.rgb(247, 200, 69));
        rectangle.setArcWidth(5.0);
        rectangle.setArcHeight(5.0);
        Label score = new Label(Double.toString(movieScore) + "/10");
        imdbScore.getChildren().addAll(rectangle, score);
        score.setAlignment(Pos.CENTER);

        filmBox.getChildren().addAll(filmText, imdbScore);
        filmBox.setAlignment(Pos.CENTER);


        // showing the trailer on the screen
        Media media = new Media(new File(moviePath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaPlayer.setVolume(0.5);
        mediaView.setFitHeight(288);
        mediaView.setFitWidth(512);


        HBox hbox = new HBox(10);
        VBox mediaButtons = new VBox(10);
        playStop = new Button(">");
        playStop.setPrefWidth(40);
        playStop.setOnAction(e -> {
            if (playStop.getText().equals(">")) {
                mediaPlayer.play();
                playStop.setText("||");
            } else {
                playStop.setText(">");
                mediaPlayer.pause();
            }
        });
        skipBack = new Button("<<");
        skipBack.setOnAction(event -> { // skipping 5 seconds back
            double duration = mediaPlayer.getCurrentTime().toMillis() - 5000;
            // setting the media's time to 5 seconds before its current time
            mediaPlayer.seek(Duration.millis(duration));
        });
        skipBack.setPrefWidth(40);
        skipForward = new Button(">>");
        skipForward.setOnAction( event -> { // skipping 5 seconds forward
            double duration = mediaPlayer.getCurrentTime().toMillis() + 5000;
            // setting the media's time to 5 seconds after its current time
            mediaPlayer.seek(Duration.millis(duration));
        });
        skipForward.setPrefWidth(40);
        restart = new Button("|<<");
        restart.setOnAction(event -> { // to restart video
            mediaPlayer.seek(Duration.millis(0));
        });
        restart.setPrefWidth(40);
        slider = new Slider(0, 1.0, 0.5); // volume slider
        slider.setOrientation(Orientation.VERTICAL); // setting volume slider vertical
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            // changing the trailer's sound level according to the position of volume slider
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume((Double) newValue);
            }
        });
        mediaButtons.getChildren().addAll(playStop, skipBack, skipForward, restart,slider);
        mediaButtons.setAlignment(Pos.CENTER);


        HBox hbox2 = new HBox(30);
        HBox hbox3 = new HBox(10);
        ChoiceBox<Hall> halls = new ChoiceBox<>(); // movie's halls
        movie.addHallsToChoiceBox(halls); // adding halls to halls choice box
        halls.getSelectionModel().selectFirst();
        halls.setPrefWidth(80);
        OK = new Button("OK");
        hbox3.getChildren().addAll(halls, OK);
        back = new Button("Back");
        hbox2.getChildren().addAll(back, hbox3);
        hbox2.setAlignment(Pos.CENTER);

        back.setOnAction(e -> {
            mediaPlayer.stop();
            Main.window.setScene(HomepageRegularUser.createScene(Main.user));
        });

        OK.setOnAction(e -> { // setting the scene to selected hall
            Hall selectedHall = halls.getValue();
            if (selectedHall == null) {
                message.setText("ERROR: There is no hall!");
                SoundAlert.playSound();
            } else {
                mediaPlayer.stop();
                Main.window.setScene(HallPane.createScene(selectedHall));
            }
        });


        hbox.getChildren().addAll(mediaView, mediaButtons);
        hbox.setAlignment(Pos.CENTER);

        filmPane.getChildren().addAll(filmBox, hbox, hbox2, message);
        filmPane.setAlignment(Pos.CENTER);

        scene = new Scene(filmPane, 600, 400);

        return scene;
    }
}
