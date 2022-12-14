import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
/**
 * This program plays a round of hangman with either set categories or custom words that the user inputs.
 * @version 1.0
 * @author Selby Heyman
 * @author Abigail Wallace
 * @author Cameron Zakreski
 */

/**
 * This class is the primary code for running a complete hangman game once a category is selected.
 */
class ButtonListener implements ActionListener{
    private String filename;
    private ArrayList<String> wrongLetterBank;


    /**
     * Initializes the ArrayList for wrong letters
     * @param fileName the name of the category words
     */
    public ButtonListener(String fileName){
        this.filename = fileName;
        this.wrongLetterBank = new ArrayList<>();

    }

    /**
     * Begins the hangman game when certain buttons are pressed.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e){
        String targetWord;
        // creates a Categories object; parameter: String of the selected category filename
        Categories c = new Categories(filename);
        String source = (e.getSource()).toString();
        if(filename.contains("custom") && source.contains("Play Game with Word!")){
            // uses Categories method getCustomWord to get the most recent addition to custom.txt; stores this targetword as a String
            targetWord = c.getCustomWord();
        } else {
            // uses Categories method getWord to randomly select a targetword; stores this targetword as a String
            targetWord = c.getWord();
        }
        // JFrame display for the Hangman Game
        JFrame game = new JFrame("Hangman Game");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.getContentPane().setLayout(new BoxLayout(game.getContentPane(), BoxLayout.Y_AXIS));

        // creates a Gallows image and adds it to a JLabel
        ImageIcon image = new ImageIcon(new ImageIcon("images/Start.png").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
        JLabel gallowsLabel = new JLabel();
        gallowsLabel.setIcon(image);

        // creates a JPanel for wrong letter guesses
        JPanel wrongGuessPanel = new JPanel();
        wrongGuessPanel.setLayout(new GridLayout(6, 5));
        wrongGuessPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel wrongAnswer = new JLabel("Wrong Letters"); // JLabel header for wrongGuessPanel
        wrongGuessPanel.add(wrongAnswer);

        // testing purposes - displays the targetword
        /*
        JLabel target = new JLabel(targetWord);
        game.getContentPane().add(target);
         */

        // creates a JPanel for guessing a letter
        JPanel guessLetterPanel = new JPanel();
        guessLetterPanel.setLayout(new GridLayout(6, 5));
        guessLetterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel alphabetHeader = new JLabel("Guess a letter:"); // JLabel header for guessLetterPanel
        guessLetterPanel.add(alphabetHeader);

        // creates a JPanel for guessing a word
        JPanel guessWordPanel = new JPanel();
        guessWordPanel.setLayout(new GridLayout(1, 5));
        guessWordPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 40, 10));
        JLabel wordGuessHeader = new JLabel("Guess a word:"); // JLabel header for guessWordPanel
        JTextField enteredGuess = new JTextField(); // creates a JTextField for the user to enter a word guess
        String guessString = enteredGuess.getText(); // gets the input (word guess) from JTextField and converts it to a String; stores this as a String

        // creates a JPanel for displaying the word guess
        JPanel displayWordGuess = new JPanel();
        displayWordGuess.setLayout(new GridLayout(1, 20));
        displayWordGuess.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < targetWord.length(); i++) {
            String targetLetter = Character.toString(targetWord.charAt(i));
            if (targetLetter.equals(" ")) {
                JLabel space = new JLabel(" ");
                displayWordGuess.add(space);
            } else if (targetLetter.equals(guessString)) {
                JLabel space = new JLabel(targetLetter);
                displayWordGuess.add(space);
            } else {
                JLabel space = new JLabel("*");
                displayWordGuess.add(space);
            }
        }

        // ArrayList to store the current state of the guessed words; automatically adds whitespaces
        ArrayList<String> currentGuessWord = new ArrayList<>();

        for (int i = 0; i < targetWord.length(); i++) {
            String targetLetter = Character.toString(targetWord.charAt(i));
            if (targetLetter.equals(" ")) {
                currentGuessWord.add(" ");
            } else {
                currentGuessWord.add("*");
            }
        }


        // creates a JButton to press to guess a word
        JButton wordGuess = new JButton("Guess Word");

        // adds ActionListener called WordGuessListener that checks if the word guess is correct
        wordGuess.addActionListener(new WordGuessListener(targetWord, enteredGuess, displayWordGuess, currentGuessWord));
        guessWordPanel.add(wordGuessHeader);
        guessWordPanel.add(enteredGuess);
        guessWordPanel.add(wordGuess);

        // for loop reads through the alphabet String
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < alphabet.length(); i++) {
            // creates a String alphabet letter for the given index in the String
            String alphletter = String.valueOf(alphabet.charAt(i));

            // creates a JButton to press to guess a letter
            JButton letter = new JButton(alphletter);

            // adds ActionListener called LetterGuessListener that checks if the letter guess is correct
            letter.addActionListener(new LetterGuessListener(targetWord, alphletter, wrongLetterBank, wrongGuessPanel, displayWordGuess, currentGuessWord, gallowsLabel));

            // adds the JButton letter to the JPanel guessLetterPanel
            guessLetterPanel.add(letter);
        }

        // creates a JPanel display for the gallowsLabel and the wrongGuessPanel
        JPanel display = new JPanel();
        display.setLayout(new GridLayout(1, 2));
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

        // creates a JFrame of the rules that automatically pops up in front of the Hangman Game
        JFrame rules = new JFrame("Rules");
        rules.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ImageIcon rulesImage = new ImageIcon(new ImageIcon("images/rules.png").getImage().getScaledInstance(686, 584, Image.SCALE_DEFAULT));
        JLabel rulesLabel = new JLabel();
        rulesLabel.setIcon(rulesImage);
        rules.add(rulesLabel);
        rules.setVisible(true);
        rules.pack();
    }
}

// class CustomButtonListener is used when the custom JButton is selected

/**
 * This class connects to the CustomWords class when the user opts for the Custom Word category.
 */
class CustomButtonListener implements ActionListener{

    /**
     * Creates a new CustomWord Object and displays the frame to enter custom words.
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e){
        CustomWords customWords = new CustomWords();
        customWords.displayCustomFrame();
    }
}


/**
 * This class checks if the word the user inputted matches the target word when they opt to guess a whole word.
 */
class WordGuessListener implements ActionListener{
    private String targetWord;
    private JTextField guess;
    private JPanel guessWord;
    private ArrayList<String> currentGuessWord;

    /**
     * This method sets up the targetWord, guess test field, guessWord, and ArrayList of letters in the currentGuessWord.
     * @param targetWord the word that is meant to be guessed.
     * @param guess the text field where the text was entered.
     * @param guessWord the guess that the user inputted.
     * @param currentGuessWord the ArrayList of letters in the guessed word.
     */
    public WordGuessListener(String targetWord, JTextField guess, JPanel guessWord, ArrayList<String> currentGuessWord){
        this.targetWord = targetWord;
        this.guess = guess;
        this.guessWord = guessWord;
        this.currentGuessWord = currentGuessWord;
    }

    /**
     * This method calculates whether the user entered the correct word and ends the game if they did
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e){
        String guessString = guess.getText();
        Guess thisGuess = new Guess(targetWord, guessString);
        boolean result = thisGuess.wordGuess();

        // if the result is true, the word guess is correct
        if(result) {
            JOptionPane.showMessageDialog(null, "The word guess is correct!");

            guessWord.removeAll();

            // checks if current guess word is the target word
            if(guessString.equals(targetWord)) {
                JOptionPane.showMessageDialog(null, "You win!");
                pause();
            }

            // turns the guess word display into the correctly guessed target word
            for(int a=0;a<targetWord.length();a++) {
                JLabel letter = new JLabel(Character.toString(targetWord.charAt(a)));
                guessWord.add(letter);
            }
            guessWord.revalidate();
            guessWord.repaint();
        } else { // if the result is false, the word guess is incorrect
            JOptionPane.showMessageDialog(null, "The word guess is incorrect!");
        }

    }

    /**
     * This method is used to "pause" the program before the Hangman Game window closes
     */
    public void pause(){
        try{
            Thread.sleep(1000);
            System.exit(0);
        } catch(InterruptedException e) {}
    }
}

/**
 * This class checks if the letter selected by the user is present in the target word
 */
class LetterGuessListener implements ActionListener {
    private String targetWord;
    private String guess;
    private ArrayList<String> wrongBank;
    private JPanel wrongLetterBank;
    private JPanel guessWord;
    private ArrayList<String> currentGuessWord;
    private JLabel gallowsLabel;

    /**
     * This method passes in parameters needed to perform calculations or needed to update
     * @param targetWord the word that is trying to be guessed
     * @param guess the letter that is guessed by the user
     * @param wrong the ArrayList of wrong letters
     * @param wrongLetterBank the GUI panel which displays incorrect letters
     * @param guessWord the GUI panel that displays the asterisks and correct letters
     * @param currentGuessWord the ArrayList which tracks which letters are correct and which are still unknown
     * @param gallows the GUI label which displays the gallows
     */
    public LetterGuessListener(String targetWord, String guess, ArrayList<String> wrong, JPanel wrongLetterBank, JPanel guessWord, ArrayList<String> currentGuessWord, JLabel gallows) {
        this.targetWord = targetWord;
        this.guess = guess;
        this.wrongBank = wrong;
        this.wrongLetterBank = wrongLetterBank;
        this.guessWord= guessWord;
        this.currentGuessWord = currentGuessWord;
        this.gallowsLabel = gallows;
    }

    /**
     * Creates a Guess object to determine if the letter guessed is within the target word and then updates the word or the
     * gallows/wrong letter display as necessary
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        Guess thisGuess = new Guess(targetWord, guess);
        boolean result = thisGuess.letterGuess();
        if (result == true) { // the letter guess is correct
            JOptionPane.showMessageDialog(null, "The letter guess is correct!");
        } else { // the letter guess is incorrect
            JOptionPane.showMessageDialog(null, "The letter guess is incorrect!");
            wrongBank.add(guess);

            // clears the gallows label
            gallowsLabel.removeAll();

            if(wrongBank.size() == 1){ // one letter guessed wrong
                ImageIcon one = new ImageIcon(new ImageIcon("images/First Wrong.png").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                gallowsLabel.setIcon(one);
            } else if(wrongBank.size() == 2) { // two letters guessed wrong
                ImageIcon two = new ImageIcon(new ImageIcon("images/Second Wrong.png").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                gallowsLabel.setIcon(two);
            } else if(wrongBank.size() == 3) {  // three letters guessed wrong
                ImageIcon three = new ImageIcon(new ImageIcon("images/Third Wrong.png").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                gallowsLabel.setIcon(three);
            } else if(wrongBank.size() == 4) { // four letters guessed wrong
                ImageIcon four = new ImageIcon(new ImageIcon("images/Fourth Wrong.png").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                gallowsLabel.setIcon(four);
            } else if(wrongBank.size() == 5) { // five letters guessed wrong
                ImageIcon five = new ImageIcon(new ImageIcon("images/Fifth Wrong.png").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                gallowsLabel.setIcon(five);
            } else if (wrongBank.size() == 6){ // six letters guessed wrong
                ImageIcon six = new ImageIcon(new ImageIcon("images/Game OVer.png").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
                gallowsLabel.setIcon(six);
            }
            gallowsLabel.revalidate();
            gallowsLabel.repaint();

        }

        // clears the display for the wrong letters
        wrongLetterBank.removeAll();

        // re-adds the "Wrong Letters" header
        JLabel wrongAnswer = new JLabel("Wrong Letters");
        wrongLetterBank.add(wrongAnswer);

        // adds all the updated wrong letter guesses to the display
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
            pause();
        } else if(wrongBank.size() == 6){
            // if the user reached the six wrong guess limit, the game ends and closes
            JOptionPane.showMessageDialog(null, "The correct word was: " + targetWord.toUpperCase() + ". Better luck next time!");
            pause();
        }
    }

    /**
     * This method is used to "pause" the program before the Hangman Game window closes
     */
    public void pause(){
        try{
            Thread.sleep(1000);
            System.exit(0);
        } catch(InterruptedException e) {}
    }
}


/**
 * This class instantiates the initial hangman game start menu which lists categories and routes the selected category to the appropriate button listener
 */
public class Main {

    public static void main(String[] args) {
        // creates a JFrame for the start menu
        JFrame startMenu = new JFrame("Start Menu");
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startMenu.getContentPane().setLayout(new BoxLayout(startMenu.getContentPane(), BoxLayout.Y_AXIS));

        // created a header for the start menu welcoming the user to the Hangman game
        JLabel welcome = new JLabel("Welcome to Hangman!");

        JLabel categoriesLabel = new JLabel("Select a category");
        JButton animals = new JButton("Animals");
        animals.addActionListener(new ButtonListener("animals.txt"));
        JButton colors = new JButton("Colors");
        colors.addActionListener(new ButtonListener("colors.txt"));
        JButton food = new JButton("Food");
        food.addActionListener(new ButtonListener("food.txt"));
        JButton countries = new JButton("Countries");
        countries.addActionListener(new ButtonListener("countries.txt"));
        JButton sports = new JButton("Sports");
        sports.addActionListener(new ButtonListener("sports.txt"));
        JButton hobbies = new JButton("Hobbies");
        hobbies.addActionListener(new ButtonListener("hobbies.txt"));
        JButton umw = new JButton("UMW");
        umw.addActionListener(new ButtonListener("umw.txt"));
        JButton challenge = new JButton("Challenge");
        challenge.addActionListener(new ButtonListener("challenge.txt"));
        JButton custom = new JButton("Custom");
        custom.addActionListener(new CustomButtonListener());

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
        categories.add(custom);

        startMenu.getContentPane().add(welcome);
        startMenu.getContentPane().add(categoriesLabel);
        startMenu.getContentPane().add(categories);

        startMenu.pack();
        startMenu.setVisible(true);
    }
}
