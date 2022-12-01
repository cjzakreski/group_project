import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// used to interact with the categories folder and its .txt files
public class Categories {
    private String filename;
    private FileReader reader;
    private ArrayList<String> wordList;

    // constructor takes a filename parameter
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

    // gets a randomized word from the selected category filename
    public String getWord() {
        Random rand = new Random();
        int upper = wordList.size();
        int index = rand.nextInt(upper);
        String targetWord = wordList.get(index);

        return targetWord;
    }

    // gets the word most recently entered to the custom.txt file
    public String getCustomWord(){
        int index = (wordList.size())-1;
        String targetWord = wordList.get(index);

        return targetWord;
    }
}

