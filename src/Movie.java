import javafx.scene.control.ChoiceBox;

import java.util.HashMap;
import java.util.Map;

public class Movie {

    public static HashMap<String, Movie> movies = new HashMap<>();

    private String name, fullPath, path;
    private int duration;
    private double score = 0.0;
    public HashMap<String ,Hall> halls = new HashMap<>(); // hashmap for the movie's halls

    public Movie(String name, String path, int duration) {
        this.name = name;
        this.path = path;
        this.fullPath = Main.localDir.concat("/assets/trailers/" + path); // setting trailer's full path
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    // to check whether hall exists
    public boolean isExist(String hallName) {
        for (Map.Entry<String, Hall> set : halls.entrySet()) { // for every hall in the halls hash map
            if (set.getKey().equals(hallName)) { // if hall exists
                return true;
            }
        }
        return false;
    }


    // to add the hall to given halls choice box
    public void addHallsToChoiceBox(ChoiceBox<Hall> choiceBox) {
        for (Map.Entry<String, Hall> set : halls.entrySet()) {
            choiceBox.getItems().addAll(set.getValue());
        }
    }


    // to add movies to movies choice box
    public static void addMoviesToChoiceBox(ChoiceBox<Movie> choiceBox) {
        for (Map.Entry<String, Movie> set : movies.entrySet()) {
            choiceBox.getItems().addAll(set.getValue());
        }
    }


    @Override
    public String toString() {
        return this.name;
    }

    public String getPath() {
        return path;
    }
}
