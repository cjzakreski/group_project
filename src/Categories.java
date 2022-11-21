import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Categories {
    private String filename;
    private FileReader reader;
    private ArrayList<String> wordList;

    public Categories(String filename) {
        this.filename = filename;

        Scanner in = null;
        try {
            reader = new FileReader(filename);
            in = new Scanner(reader);
        } catch (FileNotFoundException e) {
            System.out.println("Error, file could not be found!");
            System.exit(-1);
        }

        while(in.hasNext()) {
            String word = in.nextLine();
            wordList.add(word);
        }
    }

    public String getWord() {
        Random rand = new Random();
        int upper = wordList.size();
        int index = rand.nextInt(upper);
        String targetWord = wordList.get(index);

        return targetWord;
    }
}
