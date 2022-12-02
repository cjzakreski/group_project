import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * This class is used to add custom words to the game
 */
public class CustomWords {
    private String word;
    private String filename;

    /**
     * This method makes sure the custom.txt file is being used
     */
    public CustomWords() {
        this.filename = "custom.txt";
    }

    /**
     * This method appends the word entered by the user to the end of the file
     * @param word the word to be added
     * @throws FileNotFoundException if the custom.txt file does not exist
     */
    public void customAddWord(String word) throws FileNotFoundException {
        FileReader reader = null;
        try {
            String var = new File("custom.txt").getAbsolutePath();
            reader = new FileReader("./categories/" + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error, file could not be found!");
            System.exit(-1);
        }

        PrintWriter writer;
        try {
            writer = new PrintWriter(new FileOutputStream(new File("./categories/custom.txt").getAbsolutePath(), true));
            writer.println(word);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sets up a display to enter one or more custom words and then play the game with custom words
     */
    public void displayCustomFrame() {
        JFrame customFrame = new JFrame("Enter Custom Words");

        customFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        customFrame.getContentPane().setLayout(new BoxLayout(customFrame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel customPanel = new JPanel();
        customPanel.setLayout(new GridLayout(6,3));
        customPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        customFrame.getContentPane().add(customPanel);

        JLabel customLabel = new JLabel("Enter a word to add and press \"Enter Word\": ");
        JTextField customField = new JTextField();
        JButton okButton = new JButton("Enter Word");
        okButton.addActionListener(new CustomWordsListener(this, customField));
        JButton randomizeButton = new JButton("Randomize Custom Words!");
        randomizeButton.addActionListener(new ButtonListener("custom.txt"));
        JButton playGameButton = new JButton("Play Game with Word!");
        playGameButton.addActionListener(new ButtonListener("custom.txt"));

        customPanel.add(customLabel);
        customPanel.add(customField);
        customPanel.add(okButton);
        customPanel.add(randomizeButton);
        customPanel.add(playGameButton);

        customFrame.add(customPanel);

        customFrame.pack();
        customFrame.setVisible(true);
    }
}

/**
 * Used when the Enter Word button is pressed to get the word from the text field
 */
class CustomWordsListener implements ActionListener {
    // constructor is blank
    CustomWords customWordObject;
    JTextField customField;

    /**
     * This method sets up the text field and custom word object to create a new object
     * @param customWordObject the new Object that will be created to add it to the class
     * @param customField the text field that the word was entered in
     */
    public CustomWordsListener(CustomWords customWordObject, JTextField customField) {
        this.customField = customField;
        this.customWordObject = customWordObject;
    }

    /**
     * This method pulls the text from the text field and calls the customAddWord method to place it in the file
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String customWord;
        try {
            // converts the JTextField into a String
            customWord = customField.getText();

            // adds the custom word to the custom.txt file
            customWordObject.customAddWord(customWord);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }
}
