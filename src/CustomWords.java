import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class CustomWords {
    private String filename;

    public CustomWords() {
        this.filename = "custom.txt";
    }

    // writes the word into the custom.txt text file
    public void customAddWord(String word) throws FileNotFoundException {
        FileReader reader = null;
        try {
            String var = new File("custom.txt").getAbsolutePath();
            reader = new FileReader("./categories/" + filename);
        } catch (FileNotFoundException e) {
            System.out.println("Error, file could not be found!");
            System.exit(-1);
        }

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream(new File("./categories/custom.txt").getAbsolutePath(), true));
            writer.println(word);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // creates custom word JFrame which allows the user to enter a word to guess before starting the game
    public void displayCustomFrame() {
        JFrame customFrame = new JFrame("Enter Custom Words");

        customFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        customFrame.getContentPane().setLayout(new BoxLayout(customFrame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel customPanel = new JPanel();
        customPanel.setLayout(new GridLayout(6,3));
        customPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        customFrame.getContentPane().add(customPanel);

        JLabel customLabel = new JLabel("Enter a word to add: ");
        JTextField customField = new JTextField();
        JButton okButton = new JButton("Enter");
        okButton.addActionListener(new CustomWordsListener(this, customField));
        JButton playGameButton = new JButton("Play Game");
        playGameButton.addActionListener(new ButtonListener("custom.txt"));

        customPanel.add(customLabel);
        customPanel.add(customField);
        customPanel.add(okButton);
        customPanel.add(playGameButton);

        customFrame.add(customPanel);

        customFrame.pack();
        customFrame.setVisible(true);
    }
}

class CustomWordsListener implements ActionListener {
    // constructor is blank
    CustomWords customWordObject;
    JTextField customField;

    public CustomWordsListener(CustomWords customWordObject, JTextField customField) {
        this.customField = customField;
        this.customWordObject = customWordObject;
    }

    // performs this code when the OK JButton is clicked
    @Override
    public void actionPerformed(ActionEvent e) {
        String customWord = null;

        try {
            customWord = customField.getText();
            customWordObject.customAddWord(customWord);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }
}
