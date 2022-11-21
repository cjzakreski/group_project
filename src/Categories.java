import java.io.FileReader;
import java.util.ArrayList;

public class Categories {
    private String filename;
    private FileReader reader;
    private ArrayList<String> wordlist;

    public Categories(String filename) {
        this.filename = filename;
    }

    public String getWord() {
        return "";
    }
}
