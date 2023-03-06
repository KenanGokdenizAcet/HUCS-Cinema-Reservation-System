import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Base64;


public class LoginPane {

    public static Scene scene;
    public static Button signUp = new Button("SIGN UP");
    public static Button login = new Button("LOGIN");


    public static Scene createScene() {

        Main.user = null;

        VBox loginPane = new VBox(10);


        Label greetings = new Label("Welcome to the HUCS Cinema Reservation System!");
        Label loginInstruction = new Label("Please enter your credentials below and click LOGIN.");
        Label singUpInstruction = new Label("You can create a new account by clicking SIGN UP button.");
        VBox instruction = new VBox(1);
        instruction.getChildren().addAll(greetings, loginInstruction, singUpInstruction);
        instruction.setAlignment(Pos.BASELINE_CENTER);


        Label usernameLabel = new Label("Username:");
        Label passwordLabel = new Label("Password:");
        VBox labels = new VBox(10);
        labels.getChildren().addAll(usernameLabel, passwordLabel);
        labels.setAlignment(Pos.CENTER_LEFT);


        TextField usernameInput = new TextField();
        usernameInput.setPromptText("username");
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("password");
        VBox inputs = new VBox(5);
        inputs.getChildren().addAll(usernameInput, passwordInput);


        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(labels, inputs);
        hbox.setAlignment(Pos.CENTER);


        Label message = new Label("");
        message.setAlignment(Pos.CENTER);


        HBox buttons = new HBox(108);
        signUp.setOnAction(e -> Main.window.setScene(SignupPane.createScene()));
        login.setOnAction(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();

            if(username.equals("")) { // if username is empty
                message.setText("ERROR: Username could not be empty!");
                SoundAlert.soundError(login, message); // to make sound error
            } else {
                message.setText(username);
                if (password.equals("")) { // if password is empty
                    message.setText("ERROR: Password could not be empty!");
                    SoundAlert.soundError(login, message);
                } else {
                    if (checkUsername(username)) { // if username exists
                        User user = User.users.get(username);
                        if (checkpassword(password, user)) { // if password is correct
                            SoundAlert.count = 0; // setting the count of block time to 0
                            if (user.userType) { // if user is an admin
                                Main.window.setScene(HomepageAdmin.createScene(user));
                                Main.user = user;
                            } else {
                                Main.window.setScene(HomepageRegularUser.createScene(user));
                                Main.user = user;
                            }
                        } else {
                            message.setText("ERROR: Wrong password!");
                            SoundAlert.soundError(login , message);
                        }
                    } else {
                        message.setText("ERROR: There is no such a credential!");
                        SoundAlert.soundError(login, message);
                    }
                }
            }

        });
        buttons.getChildren().addAll(signUp, login);
        buttons.setAlignment(Pos.CENTER);


        loginPane.getChildren().addAll(instruction, hbox, buttons, message);
        loginPane.setAlignment(Pos.CENTER);

        scene = new Scene(loginPane, 380, 200);

        return scene;
    }

    // to check whether the given username exists and return true or false
    public static boolean checkUsername(String username) {
        if (User.users.get(username) == null) { // if username does not exist
            return false;
        } else {
            return true;
        }
    }

    // checking the password for given password and user and returns true or false
    public static boolean checkpassword(String password, User user) {
        String hashPassword = User.hashPassword(password); // converting the password to hash password
        return (user.getPassword().equals(hashPassword));
    }
}

