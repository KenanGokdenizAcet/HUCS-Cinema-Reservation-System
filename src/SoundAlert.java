import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class SoundAlert {
    public static Media sound = new Media(new File(Main.localDir + "/assets/effects/error.mp3").toURI().toString());
    public static int count = 0;
    public static Scene scene = null;

    public static void soundError(Button button, Label message) {

        playSound(); // to play sound
        count ++;

        // if user makes mistake maximum error, system will be blocked,
        // system will be blocked after every mistake that is after system have been blocked
        if (count >= Main.maximumErrorWithoutGettingBlocked) {
            message.setText("ERROR: Please wait until end of the " + Main.blockTime + " seconds to make a new operation!" );
            button.setDisable(true); // to disable the login button
            PauseTransition pt = new PauseTransition(new Duration(1000*Main.blockTime));
            pt.setOnFinished(e -> { // after block time
                message.setText("");
                button.setDisable(false); // to activate the login button
            });
            pt.play();
        }


    }

    // to play sound
    public static void playSound() {
        MediaPlayer soundPlayer = new MediaPlayer(sound);
        soundPlayer.play();
    }
}
