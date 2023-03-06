import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import javafx.scene.image.ImageView;

import java.io.File;

public class HallPaneAdmin {
    public static Scene scene;
    public static Button back = new Button("Back");
    public static String emptySeat = Main.localDir + "/assets/icons/empty_seat.png";
    public static String reservedSeat = Main.localDir + "/assets/icons/reserved_seat.png";


    public static Scene createScene(Hall hall) {

        GridPane hallPaneAdmin = new GridPane();
        hallPaneAdmin.setPadding(new Insets(10,10,10,10));
        hallPaneAdmin.setVgap(10);

        Label text = new Label(hall.movie.getName() + " (" + hall.movie.getDuration() + ") "
                + "Hall: " + hall.name);
        hallPaneAdmin.add(text, 1, 0);

        ChoiceBox<User> users = new ChoiceBox<>();
        users.setPrefWidth(120);
        User.addUsersToChoiceBox(users);
        users.getSelectionModel().selectFirst();

        Label message = new Label(""); //to show the seat's status
        Label message2 = new Label(""); // to show whether seat are bought or refunded

        VBox vbox = new VBox(10);
        VBox rows = new VBox(10);
        for (int i = 0; i < hall.row; i++) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER);
            for (int j = 0; j < hall.column; j++) {
                Seat seat = hall.seats[i][j];
                Button button = createSeatButton(seat); // creating button for row and column index
                button.setOnMouseEntered( e -> { // to show seat row and column number when mouse is on the button
                    message.setText(seat.getSeatStatus());
                });
                button.setOnAction(e -> {
                    if (seat.isEmpty()) {
                        User selectedUser = users.getValue(); // setting selectedUser to selected user from users choice box
                        seat.owner = selectedUser;
                        if (seat.owner.clubMember) {  // if owner is a club member
                            // applying discount
                            seat.price = (int) Math.round(hall.price*(100-Main.discountPercentage)/100.0);
                        } else {
                            seat.price = hall.price;
                        }
                        message2.setText("Seat at " + (seat.rowIndex + 1) + "-" + (seat.columnIndex + 1)
                                + " is bought for " + seat.owner + " "+ seat.price + "TL successfully!!");

                        selectedUser.myTickets.add(hall);
                        // setting the button's image to reserved seat image
                        ImageView imageView = new ImageView(new File(reservedSeat).toURI().toString());
                        imageView.setFitHeight(40);
                        imageView.setFitWidth(40);
                        button.setGraphic(imageView);
                    } else {
                        message2.setText("Seat at " + (seat.rowIndex + 1) + "-" + (seat.columnIndex + 1)
                                + " is refunded to " + seat.owner + " successfully!");
                        seat.owner.myTickets.remove(hall);
                        seat.owner = null;
                        // setting the button's image to empty seat image
                        ImageView imageView = new ImageView(new File(emptySeat).toURI().toString());
                        imageView.setFitHeight(40);
                        imageView.setFitWidth(40);
                        button.setGraphic(imageView);
                    }
                });
                row.getChildren().addAll(button);
            }
            rows.getChildren().addAll(row);
        }
        vbox.getChildren().addAll(text,rows, users, message, message2);
        vbox.setAlignment(Pos.CENTER);
        hallPaneAdmin.add(vbox, 1,1);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(back);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        hallPaneAdmin.add(buttonBox, 1,2);

        hallPaneAdmin.setAlignment(Pos.CENTER);

        back.setOnAction(e -> {
            Main.window.setScene(FilmPaneAdmin.createScene(hall.movie));
        });


        // setting the scene width and height according the number of row and column
        double width = hall.column*70 + 10;
        if (width < 300) {
            width = 300;
        }
        double height = hall.row*70 + 130;

        scene = new Scene(hallPaneAdmin, width, height);

        return scene;
    }

    // creates new button, sets its image and returns it
    public static Button createSeatButton(Seat seat) {
        ImageView imageView;
        if (seat.isEmpty()) { // if button is empty
            imageView = new ImageView(new File(emptySeat).toURI().toString()); // empty seat image
        } else {
            imageView = new ImageView(new File(reservedSeat).toURI().toString()); // reserverd seat image
        }

        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        Button button = new Button();
        button.setGraphic(imageView);

        return button;
    }
}
