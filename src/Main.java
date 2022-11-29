import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// class ButtonListener is used when a category JButton is selected
class ButtonListener implements ActionListener{
    private String filename = null;
    private ArrayList<String> wrongLetterBank;
    private int guessCount;
    private int wrongGuessCount;

    // constructor takes parameters: filename, guessCount, and wrongGuessCount; initializes the ArrayList wrongLetterBank
    public ButtonListener(String fileName, int guessCount, int wrongGuessCount){
        this.filename = fileName;
        this.wrongLetterBank = new ArrayList<>();
        this.guessCount = guessCount;
        this.wrongGuessCount = wrongGuessCount;
    }

    // performs this code when the category JButton is selected
    @Override
    public void actionPerformed(ActionEvent e){

        // testing purposes: JOptionPane.showMessageDialog(null, "We entered this button");

        // creates a Categories object; parameter: String of the selected category filename
        Categories c = new Categories(filename);

        // uses Categories method getWord to randomly selected a targetword; stores this targetword as a String
        String targetWord = c.getWord();

        JOptionPane.showMessageDialog(null, "Now we will start the Hangman Game!");

        // JFrame display for the Hangman Game
        JFrame game = new JFrame("Hangman Game");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.getContentPane().setLayout(new BoxLayout(game.getContentPane(), BoxLayout.Y_AXIS));

        // creates a Gallows image and adds it to a JLabel
        ImageIcon image= new ImageIcon(new ImageIcon("Start.png").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
        JLabel gallowsLabel = new JLabel();
        gallowsLabel.setIcon(image);

        // creates a JPanel for wrong letter guesses
        JPanel wrongGuessPanel = new JPanel();
        wrongGuessPanel.setLayout(new GridLayout(6,5));
        wrongGuessPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel wrongAnswer = new JLabel("Wrong Letters"); // JLabel header for wrongGuessPanel
        wrongGuessPanel.add(wrongAnswer);

        // testing purposes - displays the targetword
        JLabel target = new JLabel(targetWord);
        game.getContentPane().add(target);

        // creates a JPanel for guessing a letter
        JPanel guessLetterPanel = new JPanel();
        guessLetterPanel.setLayout(new GridLayout(6,5));
        guessLetterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel alphabetHeader = new JLabel("Guess a letter:"); // JLabel header for guessLetterPanel
        guessLetterPanel.add(alphabetHeader);

        // creates a JPanel for guessing a word
        JPanel guessWordPanel = new JPanel();
        guessWordPanel.setLayout(new GridLayout(1,5));
        guessWordPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 40, 10));
        JLabel wordGuessHeader = new JLabel("Guess a word:"); // JLabel header for guessWordPanel
        JTextField enteredGuess = new JTextField(); // creates a JTextField for the user to enter a word guess
        String guessString = enteredGuess.getText();; // gets the input (word guess) from JTextField and converts it to a String; stores this as a String

        // creates a JPanel for displaying the word guess
        JPanel displayWordGuess = new JPanel();
        displayWordGuess.setLayout(new GridLayout(1,20));
        displayWordGuess.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for(int i=0; i < targetWord.length(); i++) {
            String targetLetter = Character.toString(targetWord.charAt(i));
            if(targetLetter.equals(" ")) {
                JLabel space = new JLabel(" ");
                displayWordGuess.add(space);
            } else if(targetLetter.equals(guessString)) {
                JLabel space = new JLabel(targetLetter);
                displayWordGuess.add(space);
            } else {
                JLabel space = new JLabel("*");
                displayWordGuess.add(space);
            }
        }

        // ArrayList to store the current state of the guessed words; automatically adds whitespaces
        ArrayList<String> currentGuessWord = new ArrayList<>();

        for(int i=0; i< targetWord.length();i++) {
            String targetLetter = Character.toString(targetWord.charAt(i));
            if(targetLetter.equals(" ")) {
                currentGuessWord.add(" ");
            } else {
                currentGuessWord.add("*");
            }
        }


        // creates a JButton to press to guess a word
        JButton wordGuess = new JButton("Guess Word");

        // adds ActionListener called WordGuessListener that checks if the word guess is correct
        wordGuess.addActionListener(new WordGuessListener(targetWord, enteredGuess, displayWordGuess, currentGuessWord, wrongGuessCount));
        guessWordPanel.add(wordGuessHeader);
        guessWordPanel.add(enteredGuess);
        guessWordPanel.add(wordGuess);

        // for loop reads through the alphabet String
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for(int i=0; i<alphabet.length(); i++) {
            // creates a String alphabet letter for the given index in the String
            String alphletter = String.valueOf(alphabet.charAt(i));

            // creates a JButton to press to guess a letter
            JButton letter = new JButton(alphletter);

            // adds ActionListener called LetterGuessListener that checks if the letter guess is correct
            letter.addActionListener(new LetterGuessListener(targetWord, alphletter, wrongLetterBank, wrongGuessPanel, displayWordGuess, guessCount, currentGuessWord,wrongGuessCount));

            // adds the JButton letter to the JPanel guessLetterPanel
            guessLetterPanel.add(letter);
        }

        // creates a JPanel display for the gallowsLabel and the wrongGuessPanel
        JPanel display = new JPanel();
        display.setLayout(new GridLayout(1,2));
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        display.add(gallowsLabel);
        display.add(wrongGuessPanel);

        // adds all the panels to the Hangman JFrame game - display, displayWordGuess, guessLetterPanel, and guessWordPanel
        game.getContentPane().add(display);
        game.getContentPane().add(displayWordGuess);
        game.getContentPane().add(guessLetterPanel);
        game.getContentPane().add(guessWordPanel);

        // packs JFrame and setVisible to true
        game.pack();
        game.setVisible(true);

    }
}

class WordGuessListener implements ActionListener{
    private String targetWord;
    private JTextField guess;
    private JPanel guessWord;
    private ArrayList<String> currentGuessWord;
    private int wrongGuessCount;

    public WordGuessListener(String targetWord, JTextField guess, JPanel guessWord, ArrayList<String> currentGuessWord, int wrongGuessCount){
        this.targetWord = targetWord;
        this.guess = guess;
        this.guessWord = guessWord;
        this.currentGuessWord = currentGuessWord;
        this.wrongGuessCount = wrongGuessCount;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        JOptionPane.showMessageDialog(null, "We entered letter guess listener, wrong guess count: " + wrongGuessCount);
        wrongGuessCount += 1;
        String guessString = guess.getText();;
        Guess thisGuess = new Guess(targetWord, guessString);
        boolean result = thisGuess.wordGuess();

        // creates high score JFrame
        JFrame highScore = new JFrame("High Scores");
        highScore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        highScore.getContentPane().setLayout(new BoxLayout(highScore.getContentPane(), BoxLayout.Y_AXIS));

        JLabel hslabel = new JLabel("High Scores");
        highScore.add(hslabel);

        //creates the grid layout to display high scores
        JPanel hs = new JPanel();
        hs.setLayout(new GridLayout(6,3));
        JLabel hsname = new JLabel("Name:");
        JLabel hscore = new JLabel("Score:");
        JLabel hscategory = new JLabel("Category:");
        hs.add(hsname);
        hs.add(hscore);
        hs.add(hscategory);
        hs.setVisible(true);
        highScore.add(hs);
        highScore.pack();
        highScore.pack();

        if(result == true) {
            JOptionPane.showMessageDialog(null, "The word guess is correct!");

            guessWord.removeAll();
            // checks if current guess word is the target word
            if(guessString.equals(targetWord)) {
                // if true, it opens a High Score JFrame
                JOptionPane.showMessageDialog(null, "You win!");
                highScore.setVisible(true);
            } else {
                highScore.setVisible(false);
            }

            for(int a=0;a<targetWord.length();a++) {
                JLabel letter = new JLabel(Character.toString(targetWord.charAt(a)));
                guessWord.add(letter);
            }
            guessWord.revalidate();
            guessWord.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "The word guess is incorrect!");
        }

    }
}

class LetterGuessListener implements ActionListener {
    private String targetWord;
    private String guess;
    private ArrayList<String> wrongBank;
    private JPanel wrongLetterBank;
    private JPanel guessWord;
    private int guessCount;
    private ArrayList<String> currentGuessWord;
    private int wrongGuessCount;

    public LetterGuessListener(String targetWord, String guess, ArrayList<String> wrong, JPanel wrongLetterBank, JPanel guessWord, int guessCount, ArrayList<String> currentGuessWord, int wrongGuessCount) {
        this.targetWord = targetWord;
        this.guess = guess;
        this.wrongBank = wrong;
        this.wrongLetterBank = wrongLetterBank;
        this.guessWord= guessWord;
        this.guessCount = guessCount;
        this.currentGuessWord = currentGuessWord;
        this.wrongGuessCount = wrongGuessCount;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "We entered letter guess listener, wrong guess count: " + wrongGuessCount);
        JOptionPane.showMessageDialog(null, "We entered letter guess listener, guess count: " + guessCount);
        guessCount += 1;

        Guess thisGuess = new Guess(targetWord, guess);
        boolean result = thisGuess.letterGuess();
        if (result == true) {
            JOptionPane.showMessageDialog(null, "The letter guess is correct!");
        } else {
            JOptionPane.showMessageDialog(null, "The letter guess is incorrect!");
            wrongGuessCount += 1;
            JOptionPane.showMessageDialog(null, "Guess Count:" + wrongGuessCount);
            wrongBank.add(guess);
        }


        // clears the display for the wrong letters
        wrongLetterBank.removeAll();

        // re-adds the "Wrong Letters" header
        JLabel wrongAnswer = new JLabel("Wrong Letters");
        wrongLetterBank.add(wrongAnswer);

        for(int i=0;i<wrongBank.size();i++) {
            JLabel wrongLett = new JLabel(wrongBank.get(i));
            wrongLetterBank.add(wrongLett);
        }

        // clears the display for the current state of the guessed word
        guessWord.removeAll();

        for(int j=0;j<targetWord.length();j++) {
            if(Character.toString(targetWord.charAt(j)).equals(guess)) {
                currentGuessWord.set(j, guess);
            }
        }

        for(int h=0;h<currentGuessWord.size();h++) {
            JLabel lett = new JLabel(currentGuessWord.get(h));
            guessWord.add(lett);
        }

        wrongLetterBank.revalidate();
        wrongLetterBank.repaint();

        guessWord.revalidate();
        guessWord.repaint();


        // creates high score JFrame
        JFrame highScore = new JFrame("High Scores");
        highScore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        highScore.getContentPane().setLayout(new BoxLayout(highScore.getContentPane(), BoxLayout.Y_AXIS));

        // creates header label for High Score JFrame
        JLabel hslabel = new JLabel("High Scores");
        highScore.add(hslabel);
        highScore.pack();

        //creates the grid layout to display high scores
        JPanel hs = new JPanel();
        hs.setLayout(new GridLayout(6,3));
        JLabel hsname = new JLabel("Name:");
        JLabel hscore = new JLabel("Score:");
        JLabel hscategory = new JLabel("Category:");
        hs.add(hsname);
        hs.add(hscore);
        hs.add(hscategory);
        hs.setVisible(true);
        highScore.add(hs);
        highScore.pack();

        // turns the ArrayList of current guess word into a String
        String currentGuessWordString = "";

        for(int i=0; i< targetWord.length();i++) {
            String lett = currentGuessWord.get(i);
            currentGuessWordString += lett;
        }

        // checks if current guess word is the target word
        if(currentGuessWordString.equals(targetWord)) {
            // if true, it opens a High Score Class
            JOptionPane.showMessageDialog(null, "You win!");
            highScore.setVisible(true);
        } else {
            highScore.setVisible(false);
        }
    }
}

public class Main {

    public static void main(String[] args) {
        JFrame startMenu = new JFrame("Start Menu");
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startMenu.getContentPane().setLayout(new BoxLayout(startMenu.getContentPane(), BoxLayout.Y_AXIS));

        JLabel welcome = new JLabel("Welcome to Hangman!");
        JLabel nameLabel = new JLabel("Name:", SwingConstants.RIGHT);
        JTextField enteredName = new JTextField();

        // HighScores score = new HighScores(enteredName.getText(),0,"null");
        JLabel categoriesLabel = new JLabel("Select a category");
        JButton animals = new JButton("Animals");
        animals.addActionListener(new ButtonListener("animals.txt", 0,0));
        JButton colors = new JButton("Colors");
        colors.addActionListener(new ButtonListener("colors.txt", 0, 0));
        JButton food = new JButton("Food");
        food.addActionListener(new ButtonListener("food.txt", 0, 0));
        JButton countries = new JButton("Countries");
        countries.addActionListener(new ButtonListener("countries.txt", 0, 0));
        JButton sports = new JButton("Sports");
        food.addActionListener(new ButtonListener("sports.txt", 0, 0));
        JButton hobbies = new JButton("Hobbies");
        food.addActionListener(new ButtonListener("hobbies.txt", 0, 0));
        JButton umw = new JButton("UMW");
        umw.addActionListener(new ButtonListener("umw.txt", 0, 0));
        JButton challenge = new JButton("Challenge");
        challenge.addActionListener(new ButtonListener("challenge.txt", 0, 0));

        JPanel categories = new JPanel();
        categories.setLayout(new GridLayout(3,3));
        categories.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        categories.add(animals);
        categories.add(colors);
        categories.add(food);
        categories.add(countries);
        categories.add(sports);
        categories.add(hobbies);
        categories.add(umw);
        categories.add(challenge);

        startMenu.getContentPane().add(welcome);
        startMenu.getContentPane().add(nameLabel);
        startMenu.getContentPane().add(enteredName);
        startMenu.getContentPane().add(categoriesLabel);
        startMenu.getContentPane().add(categories);

        startMenu.pack();
        startMenu.setVisible(true);
    }
}
