import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RemoveHallPane {
    public static Scene scene;
    public static Button back, OK;

    public static Scene createScene(Movie movie) {

        VBox removeHallPane = new VBox(10);

        Label instruction = new Label("Select the hall that you desire to remove and then click OK.");

        ChoiceBox<Hall> halls = new ChoiceBox<>();
        halls.setPrefWidth(180);
        movie.addHallsToChoiceBox(halls);
        halls.getSelectionModel().selectFirst();


        HBox buttons = new HBox(10);
        back = new Button("Back");
        OK = new Button("OK");
        buttons.getChildren().addAll(back, OK);
        buttons.setAlignment(Pos.CENTER);

        Label message = new Label("");

        removeHallPane.getChildren().addAll(instruction, halls, buttons,message);
        removeHallPane.setAlignment(Pos.CENTER);

        back.setOnAction(e -> Main.window.setScene(FilmPaneAdmin.createScene(movie)));
        OK.setOnAction(e -> {
            Hall selectedHall = halls.getValue();

            if (selectedHall == null) {
                message.setText("ERROR: There is no hall!");
                SoundAlert.playSound();
            } else {
                movie.halls.remove(selectedHall.name); // to remove the hall from its movie's halls hashmap
                message.setText("SUCCESS: Hall removes successfully!");
            }
        });


        scene = new Scene(removeHallPane, 340,140);

        return scene;
    }
}
