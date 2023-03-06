import javafx.scene.control.ChoiceBox;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class User {

    public static HashMap<String, User> users = new HashMap<>();

    private final String username;
    private String password;
    Boolean userType = false; // admin -> true, normal user -> false
    Boolean clubMember = false;
    public HashSet<Hall> myTickets = new HashSet<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        users.put(username, this);
    }

    public User(String username, String password, boolean clubMember) {
        this(username, password);
        this.clubMember = clubMember;
    }

    public String getUsername() {
        return username;
    }


    public String getStatus() {
        if (clubMember == true && userType == false) { // if user is only club member
            return "(Club Member)";
        } else if (clubMember == true && userType == true) { // if user is admin and club member
            return ("(Admin - Club Member)");
        } else if (clubMember == false && userType == true) { // if user is only admin
            return ("(Admin)");
        } else {
            return "";
        }
    }


    public String getPassword() {
        return password;
    }

    public void updateTickets() {
        this.myTickets.removeIf(hall -> Movie.movies.get(hall.movie.getName()) == null); // if movie has been removed
        this.myTickets.removeIf(hall -> hall.movie.halls.get(hall.name) == null); // if hall has been removed
    }


    // to convert the password to hash password
    public static String hashPassword(String password) {
        byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
        byte[] md5Digest = new byte[0];
        try {
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return Base64.getEncoder().encodeToString(md5Digest);
    }

    // to add tickets to choice box
    public static void addTicketsToChoiceBox(ChoiceBox<String> choiceBox) {
        Main.user.updateTickets(); // updating tickets hash set
        for (Hall hall : Main.user.myTickets) {
            String ticket = hall.movie + " - " + hall;
            choiceBox.getItems().addAll(ticket);
        }
    }

    // to add users to users choice box
    public static void addUsersToChoiceBox(ChoiceBox<User> choiceBox) {
        for (Map.Entry<String, User> set : users.entrySet()) {
            choiceBox.getItems().addAll(set.getValue());
        }
    }

    public Boolean getClubMember() {
        return clubMember;
    }

    public Boolean getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return username;
    }
}
