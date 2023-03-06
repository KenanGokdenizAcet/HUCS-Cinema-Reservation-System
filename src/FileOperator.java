import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperator {
    //writes given String to the given file name;
    public static void writeFile(String content, String fileName) {
        try {
            FileWriter file = new FileWriter(fileName);
            file.write(content);
            file.close();
        } catch (IOException e) {
        }
    }

    // reads the file and returns the file's line as an arraylist
    public static ArrayList<String> readFile(String fileName) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner f = new Scanner(file);
            while (f.hasNextLine()) {
                String line = f.nextLine();
                lines.add(line);
            }
            f.close();
            return lines;
        } catch (Exception e) {
            return null;
        }
    }

    // reads file and returns the split lines, which is split according to regex, as an arraylist
    public static ArrayList<String[]> readFile(String fileName, String regex) {
        ArrayList<String[]> linesSplit = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner f = new Scanner(file);
            while (f.hasNextLine()) {
                String line = f.nextLine();
                String[] lineSplit = line.split(regex);
                linesSplit.add(lineSplit);
            }
            f.close();
            return linesSplit;
        } catch (Exception e) {
            return null;
        }
    }
}
