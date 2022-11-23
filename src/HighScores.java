import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HighScores {
    private String name;
    private int numGuesses;
    private String category;

    public HighScores(String name, int numGuesses, String category) {
        this.name = name;
        this.numGuesses = numGuesses;
        this.category = category;
    }

    public HighScores(Scanner sc) throws IOException {
        String path = System.getProperty("user.home") + File.separator + "HangmanScores";
        try {
            File scoresFile = new File(path);
            if (scoresFile.exists()) {
                sc = new Scanner(scoresFile);
                while (sc.hasNextLine()) {
                    this.name = sc.nextLine();
                    this.numGuesses = Integer.parseInt(sc.nextLine());
                    this.category = sc.nextLine();
                }
            } else {
                boolean success = scoresFile.createNewFile();
                if (!success) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // checks to see if directory was successfully created
        // needs to catch the exception that the directory could not be created
        boolean dirCreated = new File(path).mkdirs();
        if (dirCreated) {

        }

        // gets the highscores file if it exists, if not,
        // creates a new scores file in the user's hangman directory
        File scoresFile = new File(path + "/HighScores.txt");
        if (!scoresFile.exists()) {
            boolean success = scoresFile.createNewFile();
            if (!success) {
                // catch
            }
        }
    }

    // compares the score the scores from the file if it exists, if not, writes it into the file
    public void compareScores() throws FileNotFoundException {
        String path = System.getProperty("user.home") + File.separator + "HangmanScores";
        File scoresFile = new File(path);
        ArrayList<HighScores> hsList= new ArrayList<>();
        try {
            if (scoresFile.exists()) {
                Scanner sc = new Scanner(scoresFile);
                while (sc.hasNextLine()) {
                    HighScores hs = new HighScores(sc.nextLine(), Integer.parseInt(sc.nextLine()), sc.nextLine());
                    hsList.add(hs);
                }
            } else {
                boolean success = scoresFile.createNewFile();
                if (!success) {
                    // catch exception that file could not be created
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (HighScores hs : hsList) {
            if (this.category.equals(hs.getCategory())); {
                if (this.numGuesses>hs.getNumGuesses());
                // save all high scores from the list, making a new list, and then
                // overwrite the new high scores into the file
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public int getNumGuesses() {
        return this.numGuesses;
    }

    public String getCategory() {
        return this.category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumGuesses(int num) {
        this.numGuesses = num;
    }

    public void setCategory() {
        this.category = category;
    }
}
