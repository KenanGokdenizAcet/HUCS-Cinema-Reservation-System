import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;

public class HallPane {

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


        Label message = new Label(""); // to show seat's row and column number
        Label message2 = new Label(""); // to show whether seat are bought, refunded

        VBox vbox = new VBox(10);
        VBox rows = new VBox(10);
        for (int i = 0; i < hall.row; i++) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER);
            for (int j = 0; j < hall.column; j++) {
                Seat seat = hall.seats[i][j];
                Button button = createSeatButton(seat); // creating button for row and column index
                button.setOnMouseEntered( e -> { // to show seat row and column number when mouse is on the button
                    message.setText((seat.rowIndex + 1) + "-" + (seat.columnIndex + 1));
                });
                button.setOnAction(e -> {
                    if (seat.isEmpty()) {
                        seat.owner = Main.user; // Main.user is the user that is able to buy the seat
                        if (seat.owner.clubMember) { // if owner is a club member
                            // applying discount
                            seat.price = (int) Math.round(hall.price*(100-Main.discountPercentage)/100.0);
                        } else {
                            seat.price = hall.price;
                        }
                        message2.setText("Seat at " + (seat.rowIndex + 1) + "-" + (seat.columnIndex + 1)
                                + " is bought for " + seat.owner + " "+ seat.price + "TL successfully!!");
                        Main.user.myTickets.add(seat.hall);
                        // setting the button's image to reserved seat image
                        ImageView imageView = new ImageView(new File(reservedSeat).toURI().toString());
                        imageView.setFitHeight(40);
                        imageView.setFitWidth(40);
                        button.setGraphic(imageView);
                    } else { //(seat.owner == Main.user)
                        seat.owner.myTickets.remove(hall);
                        seat.owner = null;
                        message2.setText("Seat at " + (seat.rowIndex + 1) + "-" + (seat.columnIndex + 1)
                                + " is refunded successfully!");
                        Main.user.myTickets.remove(seat.movie);
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
        vbox.getChildren().addAll(text,rows, message, message2);
        vbox.setAlignment(Pos.CENTER);
        hallPaneAdmin.add(vbox, 1,1);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(back);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        hallPaneAdmin.add(buttonBox, 1,2);

        hallPaneAdmin.setAlignment(Pos.CENTER);

        back.setOnAction(e -> {
            Main.window.setScene(FilmPane.createScene(hall.movie));
        });


        // setting the scene width and height according the number of row and column
        double width = hall.column*70 + 10;
        if (width < 350) {
            width = 350;
        }
        double height = hall.row*70 + 100;

        scene = new Scene(hallPaneAdmin, width, height);

        return scene;
    }


    // creates new button, sets its image and returns it
    public static Button createSeatButton(Seat seat) {
        ImageView imageView = new ImageView(new File(emptySeat).toURI().toString());
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        Button button;
        if (seat.isEmpty()) { // if seat is empty
            button = new Button();
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            button.setGraphic(imageView); // setting empty seat image
        } else if (Main.user == seat.owner){ // if owner is the user that have logged in
            imageView = new ImageView(new File(reservedSeat).toURI().toString()); // reserved seat image
            button = new Button();
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            button.setGraphic(imageView);
        } else { // if owner is another user
            imageView = new ImageView(new File(reservedSeat).toURI().toString());
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            button = new Button();
            button.setGraphic(imageView);
            button.setDisable(true); // to disable the button
        }

        return button;
    }
}
