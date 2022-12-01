import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

// used to interact with the "Custom" JButton
public class CustomWords {
    private String word;
    private String filename;

    // set the filename to custom.txt
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

    // creates custom word JFrame which allows the user to enter a word to guess before starting the game
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

// class CustomWordsListener is used when Enter Word JButton is selected
class CustomWordsListener implements ActionListener {
    // constructor is blank
    CustomWords customWordObject;
    JTextField customField;

    // takes CustomWords object parameter and JTextField parameter
    public CustomWordsListener(CustomWords customWordObject, JTextField customField) {
        this.customField = customField;
        this.customWordObject = customWordObject;
    }

    // performs this code when the Enter Word JButton is selected
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
