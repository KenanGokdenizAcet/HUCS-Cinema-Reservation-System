import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application{
    public static Stage window;
    public static User user = null;
    public static String title;
    public static int discountPercentage;
    public static int maximumErrorWithoutGettingBlocked;
    public static int blockTime;
    public static String localDir = new File("").getAbsolutePath();

    public static void main(String[] args) {
        Data.load();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;
        window.setTitle(title);
        Image icon = new Image(new File(localDir + "/assets/icons/logo.png").toURI().toString()); // icon image of app
        window.getIcons().add(icon);

        window.setScene(LoginPane.createScene());

        window.show();

        window.setOnCloseRequest(event -> Data.backUp()); // when close button are clicked, system will back up
    }

}
