import java.io.*;
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
        while (sc.hasNextLine()) {
            this.name = sc.nextLine();
            this.numGuesses = Integer.parseInt(sc.nextLine());
            this.category = sc.nextLine();
        }
    }

    // compares the score the scores from the file if it exists, if not, writes it into the file
    public void compareScores() throws IOException {
        HighScores newScore = new HighScores(this.name, this.numGuesses, this.category);
        String path = System.getProperty("user.home") + File.separator + "HangmanHighScores.txt";
        File scoresFile = new File(path);
        if (!scoresFile.exists()) {
            scoresFile.createNewFile();
        }

        ArrayList<HighScores> hsList= new ArrayList<>();
        try {
            Scanner sc = new Scanner(scoresFile);
            while (sc.hasNextLine()) {
                HighScores hs = new HighScores(sc);
                hsList.add(hs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // loops through to compare the highscore to the highscores in the list if it is not empty
        // inserting it at the correct ranking
        for (HighScores hs : hsList) {
            if (this.numGuesses > hs.getNumGuesses()) {
                hsList.add(hsList.indexOf(hs), newScore);
                break;
            }
        }
        if (hsList.size()>=5) {
            hsList.remove(5);
        }

        // overwrite the scores of hsList into the highscores file
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(path, false));
            for (HighScores hs : hsList) {
                writer.println(hs.getName());
                writer.println(hs.getNumGuesses());
                writer.println(hs.getCategory());
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // this could return an arraylist to print on the display window
    public void displayHighScores() {
        ArrayList<HighScores> hsList= new ArrayList<>();
        try {
            Scanner sc = new Scanner(scoresFile);
            while (sc.hasNextLine()) {
                HighScores hs = new HighScores(sc);
                hsList.add(hs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0; i<hsList.size(); i++) {
            HighScore hs = hsList.get(i);
            String labels = String.format("%-25s %-6s %-25s", "Name", "Score", "Category");
            System.out.println(labels);
            String hsPrint = String.format("%-25s %-6s %-25s", hs.getName(), hs.getNumGuesses(), hs.getCategory());
            System.out.println((i+1) + hsPrint);
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

    public void addGuess() {
        this.numGuesses++;
    }
}
