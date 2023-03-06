import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class AddHallPane {

    public static Scene scene;
    public static Button back, OK;
    public static ChoiceBox<Integer> rowBox;
    public static ChoiceBox<Integer> columnBox;

    public static Scene createScene(Movie movie) {

        VBox addHallPane = new VBox(10);

        Label filmText = new Label(movie.getName() + "(" + movie.getDuration()+ ")");
        filmText.setAlignment(Pos.CENTER);


        VBox vbox = new VBox(20);
        Label rowLabel = new Label("Row:");
        Label columnLabel = new Label("Column:");
        Label nameLabel = new Label("Name:");
        Label priceLabel = new Label("Price:");
        vbox.getChildren().addAll(rowLabel, columnLabel, nameLabel, priceLabel);
        vbox.setAlignment(Pos.CENTER_LEFT);


        VBox vbox2 = new VBox(10);
        ObservableList<Integer> numbers = FXCollections.observableArrayList(3,4,5,6,7,8,9,10);
        rowBox = new ChoiceBox<>();
        rowBox.setPrefWidth(65);
        rowBox.setItems(numbers);
        rowBox.getSelectionModel().selectFirst();
        columnBox = new ChoiceBox<>();
        columnBox.setPrefWidth(65);
        columnBox.setItems(numbers);
        columnBox.getSelectionModel().selectFirst();
        TextField nameTF = new TextField();
        TextField priceTF = new TextField();
        vbox2.getChildren().addAll(rowBox, columnBox, nameTF, priceTF);
        vbox2.setAlignment(Pos.CENTER);


        HBox hbox = new HBox(20);
        hbox.getChildren().addAll(vbox, vbox2);
        hbox.setAlignment(Pos.CENTER);

        HBox buttons = new HBox(144);
        back = new Button("Back");
        OK = new Button("OK");
        buttons.getChildren().addAll(back, OK);
        buttons.setAlignment(Pos.CENTER);

        Label message = new Label("");


        addHallPane.getChildren().addAll(filmText, hbox,buttons,message);
        addHallPane.setAlignment(Pos.CENTER);


        back.setOnAction(e -> Main.window.setScene(FilmPaneAdmin.createScene(movie)));

        OK.setOnAction(e -> {
            int rowNumber = rowBox.getSelectionModel().getSelectedItem();
            int columnNumber = columnBox.getSelectionModel().getSelectedItem();
            String hallName = nameTF.getText();
            int price;

            if (hallName.equals("")) { // if hal name is empty
                SoundAlert.playSound();
                message.setText("ERROR: Hall name could not be empty!");
            } else {
                if (priceTF.getText().equals("")) { // if price is empty
                    SoundAlert.playSound();
                    message.setText("ERROR: Price could not be empty!");
                } else {
                    try { // trying to convert price input to integer;
                        price = Integer.parseInt(priceTF.getText());
                        if (price <= 0) {
                            SoundAlert.playSound();
                            message.setText("ERROR: Price must be positive Integer!");
                        } else {
                            if (movie.isExist(hallName)) {
                                SoundAlert.playSound();
                                message.setText("ERROR: This hall name already exists!");
                            } else {
                                message.setText("SUCCESS: Hall successfully created!");
                                // creating new hall object and add to object with its name to halls hashmap of the hall's movie
                                movie.halls.put(hallName, new Hall(movie,hallName, price,rowNumber, columnNumber, true));
                            }
                        }
                    } catch (Exception exception) {
                        SoundAlert.playSound();
                        message.setText("ERROR: Price must be positive Integer!");
                    }
                }
            }
        });

        scene = new Scene(addHallPane, 280, 270);

        return scene;

    }

}
