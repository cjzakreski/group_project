import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class pulls words from categories files and selects the word that will be used in the game
 */
public class Categories {
    private String filename;
    private FileReader reader;
    private ArrayList<String> wordList;

    /**
     * This method pulls all the words/phrases from a file and adds them to an array list
     * @param filename the selected category that the words will be pulled from
     */
    public Categories(String filename) {
        this.filename = filename;
        this.wordList = new ArrayList<>();
        Scanner in = null;
        try {
            String var = new File(filename).getAbsolutePath();
            reader = new FileReader("./categories/" + filename);


            in = new Scanner(reader);
        } catch (FileNotFoundException e) {
            System.out.println("Error, file could not be found!");
            System.exit(-1);
        }

        // loads all the words from the file to an ArrayList called wordList
        while (in.hasNext()) {
            String word = in.nextLine();
            word = word.toLowerCase();
            wordList.add(word);
        }
    }

    /**
     * This method gets a random word from the ArrayList of potential words/phrases
     * @return the selected random word
     */
    public String getWord() {
        Random rand = new Random();
        int upper = wordList.size();
        int index = rand.nextInt(upper);
        String targetWord = wordList.get(index);

        return targetWord;
    }

    /**
     * This method gets the word most recently entered to the custom.txt file when the user says they wish to play with a specific word.
     * @return the most recent addition to the custom.txt file
     */
    public String getCustomWord(){
        int index = (wordList.size())-1;
        String targetWord = wordList.get(index);

        return targetWord;
    }
}

