import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomWords {
    final String fileName = "custom.txt";
    private ArrayList<String> customList;

    public CustomWords() throws IOException {
        this.customList = new ArrayList<>();
        File customWordsFile = new File(fileName);
        customWordsFile.createNewFile();
        try {
            Scanner in = new Scanner(customWordsFile);
            while (in.hasNextLine()) {
                String word = in.nextLine();
                word = word.toLowerCase();
                customList.add(word);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error, file could not be found!");
            System.exit(-1);
        }
    }

    // adds the word to the arraylist and writes into the custom.txt text file
    public void addWord(String word) throws FileNotFoundException {
        this.customList.add(word);

        FileReader reader = null;
        try {
            String var = new File("custom.txt").getAbsolutePath();
            reader = new FileReader("./categories/" + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Error, file could not be found!");
            System.exit(-1);
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(new File("custom.txt").getAbsolutePath(), true));
            writer.println(word);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // creates custom word JFrame which allows the user to enter a word to guess before starting the game
    // they can choose not to enter a word only if there are words in the file
    public void displayCustomFrame() {
        JFrame customFrame = new JFrame("Enter Custom Words");

        customFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        customFrame.getContentPane().setLayout(new BoxLayout(customFrame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel customPanel = new JPanel();
        customPanel.setLayout(new GridLayout(6,3));
        customPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel customLabel = new JLabel("Enter a word to add, or exit this window to continue: ");
        JTextField customField = new JTextField();
        JButton addWordButton = new JButton("Enter Word");
        addWordButton.addActionListener(new AddWordListener(this, customField));

        customPanel.add(customLabel);
        customPanel.add(customField);
        customPanel.add(addWordButton);

        customFrame.getContentPane().add(customPanel);

        customPanel.setVisible(true);
        customFrame.setVisible(true);

        customFrame.pack();
    }

    public void getCustomList(String word) {
        this.customList.add(word);
    }
}

class AddWordListener implements ActionListener {
    CustomWords customWordObj;
    JTextField customField;

    public AddWordListener(CustomWords customWordObj, JTextField customField) {
        this.customField = customField;
        this.customWordObj = customWordObj;
    }

    // performs this code when the OK JButton is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        String customWord = customField.getText();
        try {
            customWordObj.addWord(customWord);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
