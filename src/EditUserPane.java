
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class EditUserPane {

    public static Scene scene;
    public static Button back, promoteDemoteClubMember, promoteDemoteAdmin;

    public static Scene createScene() {

        VBox editUserPane= new VBox(10);

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        TableView<User> tableView = new TableView<>();
        tableView.setPrefWidth(400);
        TableColumn<User, String> column1 = new TableColumn<>("Username");
        column1.setPrefWidth(80);
        column1.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<User, Boolean> column2 = new TableColumn<>("Club Member");
        column2.setCellValueFactory(new PropertyValueFactory<>("clubMember"));
        TableColumn<User, Boolean> column3 = new TableColumn<>("Admin");
        column3.setCellValueFactory(new PropertyValueFactory<>("userType"));
        tableView.getColumns().addAll(column1, column2, column3);
        gridPane.add(tableView, 1,1);
        gridPane.setAlignment(Pos.CENTER);


        HBox buttons = new HBox(10);
        back = new Button("Back");
        promoteDemoteClubMember = new Button("Promote/Demote Club Member");
        promoteDemoteAdmin = new Button("Promote/Demote Admin");
        buttons.getChildren().addAll(back, promoteDemoteClubMember, promoteDemoteAdmin);
        gridPane.add(buttons, 1, 2);



        ObservableList<User> lst = FXCollections.observableArrayList();

        for (Map.Entry<String, User> set : User.users.entrySet()) {
            User user = set.getValue();
            if(!(user == Main.user)) {
                lst.add(user);
            }
        }

        tableView.setItems(lst);

        editUserPane.getChildren().addAll(gridPane);


        back.setOnAction(event -> Main.window.setScene(HomepageAdmin.createScene(Main.user)));

        promoteDemoteAdmin.setOnAction(event -> {
            User selectedUser = tableView.getSelectionModel().getSelectedItems().get(0);
            if (selectedUser != null) {
                if (selectedUser.userType == true) { // if user is admin
                    selectedUser.userType = false;
                } else {
                    selectedUser.userType = true;
                }
                tableView.refresh();
            }

        });

        promoteDemoteClubMember.setOnAction(event -> {
            User selectedUser = tableView.getSelectionModel().getSelectedItems().get(0);
            if (selectedUser != null) {
                if (selectedUser.clubMember == true) { // if user is a club member
                    selectedUser.clubMember = false;
                } else {
                    selectedUser.clubMember = true;
                }
                tableView.refresh();
            }
        });

        scene = new Scene(editUserPane, 440, 460);

        return scene;
    }
}
