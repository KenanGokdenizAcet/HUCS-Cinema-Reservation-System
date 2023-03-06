import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SignupPane {

    public static Button login = new Button("LOGIN");
    public static Button signUp = new Button("SIGN UP");

    public static Scene createScene() {

        VBox loginPane = new VBox(10);


        Label greetings = new Label("Welcome to the HUCS Cinema Reservation System!");
        Label loginInstruction = new Label("Fill the form below to create a new account");
        Label singUpInstruction = new Label("You can go to Log in page by clicking LOG IN Button");
        VBox instruction = new VBox(1);
        instruction.getChildren().addAll(greetings, loginInstruction, singUpInstruction);
        instruction.setAlignment(Pos.BASELINE_CENTER);


        Label usernameLabel = new Label("Username:");
        Label passwordLabel1 = new Label("Password:");
        Label passwordLabel2 = new Label("Password:");
        VBox labels = new VBox(10);
        labels.getChildren().addAll(usernameLabel, passwordLabel1, passwordLabel2);
        labels.setAlignment(Pos.CENTER_LEFT);


        TextField usernameInput = new TextField();
        usernameInput.setPromptText("username");
        PasswordField passwordInput1 = new PasswordField();
        passwordInput1.setPromptText("password");
        PasswordField passwordInput2 = new PasswordField();
        VBox inputs = new VBox(5);
        inputs.getChildren().addAll(usernameInput, passwordInput1, passwordInput2);


        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(labels, inputs);
        hbox.setAlignment(Pos.CENTER);


        HBox buttons = new HBox(108);
        signUp = new Button("SIGN UP");
        login = new Button("LOGIN");
        login.setOnAction(e -> Main.window.setScene(LoginPane.createScene())); // setting the scene login pane
        buttons.getChildren().addAll(signUp, login);
        buttons.setAlignment(Pos.CENTER);


        Label message = new Label("");
        message.setAlignment(Pos.CENTER);

        loginPane.getChildren().addAll(instruction, hbox, buttons, message);
        loginPane.setAlignment(Pos.CENTER);

        signUp.setOnAction(e -> {
            String username = usernameInput.getText();
            String password1 = passwordInput1.getText();
            String password2 = passwordInput2.getText();
            if (username.equals("")) { // if username is empty
                message.setText("ERROR: Username could not be empty!");
                SoundAlert.playSound();
            } else {
                if (password1.equals("")) { // if first password is empty
                    message.setText("ERROR: Password could not be empty!");
                    SoundAlert.playSound();
                } else {
                    if (password2.equals("")) { // if second password is empty
                        message.setText("ERROR: Password could not be empty!");
                        SoundAlert.playSound();
                    } else {
                        if (password1.equals(password2)) { // if first and second password are the same
                            if (User.users.get(username) == null) { // username does not exists
                                String hashPassword = User.hashPassword(password1); // to convert to password to hash password
                                new User(username, hashPassword); // creating new user
                                message.setText("SUCCESS: You have successfully registered with your new credentials!");
                            } else {
                                message.setText("ERROR: This username already exists!");
                                SoundAlert.playSound();
                            }
                        } else {
                            message.setText("ERROR: Passwords have to be the same!");
                            SoundAlert.playSound();
                        }
                    }
                }
            }
        });

        Scene scene = new Scene(loginPane, 380, 230);

        return scene;
    }
}
