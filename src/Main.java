import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class ButtonListener implements ActionListener{
    private String filename = null;
    private ArrayList<String> wrongLetterBank;
    private int guessCount;
    private int wrongGuessCount;
    public ButtonListener(String fileName){
        this.filename = fileName;
        this.wrongLetterBank = new ArrayList<>();
        this.guessCount = 0;
        this.wrongGuessCount = 0;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        Categories c = new Categories(filename);
        String targetWord = c.getWord();

        JOptionPane.showMessageDialog(null, "Now we will start the Hangman Game!");
        JFrame game = new JFrame("Hangman Game");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.getContentPane().setLayout(new BoxLayout(game.getContentPane(), BoxLayout.Y_AXIS));

        ImageIcon image= new ImageIcon(new ImageIcon("Start.png").getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
        JLabel label = new JLabel();
        label.setIcon(image);

        JPanel wrongGuessPanel = new JPanel();
        wrongGuessPanel.setLayout(new GridLayout(6,5));
        wrongGuessPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel wrongAnswer = new JLabel("Wrong Letters");
        wrongGuessPanel.add(wrongAnswer);

        //testing purposes - displays the targetword
        JLabel target = new JLabel(targetWord);
        game.getContentPane().add(target);

        JPanel guessLetterPanel = new JPanel();
        guessLetterPanel.setLayout(new GridLayout(6,5));
        guessLetterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel alphabetHeader = new JLabel("Guess a letter:");
        guessLetterPanel.add(alphabetHeader);

        JPanel guessWordPanel = new JPanel();
        guessWordPanel.setLayout(new GridLayout(1,5));
        guessWordPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 40, 10));
        JLabel wordGuessHeader = new JLabel("Guess a word:");
        JTextField enteredGuess = new JTextField();
        String guessString = enteredGuess.getText();;

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

        // ArrayList to store the current state of the guessed words - can update ArrayList to store the correct letters guessed; accounts for white spaces in the word
        ArrayList<String> currentGuessWord = new ArrayList<>();

        for(int i=0; i< targetWord.length();i++) {
            String targetLetter = Character.toString(targetWord.charAt(i));
            if(targetLetter.equals(" ")) {
                currentGuessWord.add(" ");
            } else {
                currentGuessWord.add("*");
            }
        }


        JButton wordGuess = new JButton("Guess Word");
        wordGuess.addActionListener(new WordGuessListener(targetWord, enteredGuess, displayWordGuess, currentGuessWord, wrongGuessCount));
        guessWordPanel.add(wordGuessHeader);
        guessWordPanel.add(enteredGuess);
        guessWordPanel.add(wordGuess);

        //alphabet button option
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for(int i=0; i<alphabet.length(); i++) {
            String alphletter = String.valueOf(alphabet.charAt(i));
            JButton letter = new JButton(alphletter);
            letter.addActionListener(new LetterGuessListener(targetWord, alphletter, wrongLetterBank, wrongGuessPanel, displayWordGuess, guessCount, currentGuessWord,wrongGuessCount));
            guessLetterPanel.add(letter);
        }

        JPanel display = new JPanel();
        display.setLayout(new GridLayout(1,2));
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        display.add(label);
        display.add(wrongGuessPanel);

        game.getContentPane().add(display);
        game.getContentPane().add(displayWordGuess);
        game.getContentPane().add(guessLetterPanel);
        game.getContentPane().add(guessWordPanel);

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
        String guessString = guess.getText();;
        Guess thisGuess = new Guess(targetWord, guessString);
        boolean result = thisGuess.wordGuess();

        // creates high score JFrame
        JFrame highScore = new JFrame("High Scores");
        highScore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        highScore.getContentPane().setLayout(new BoxLayout(highScore.getContentPane(), BoxLayout.Y_AXIS));

        JLabel hslabel = new JLabel("High Scores");
        highScore.add(hslabel);
        highScore.pack();

        if(result == true) {
            JOptionPane.showMessageDialog(null, "The word guess is correct!");

            guessWord.removeAll();
            // checks if current guess word is the target word
            if(guessString.equals(targetWord)) {
                // if true, it opens a High Score Class
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Guess thisGuess = new Guess(targetWord, guess);
        boolean result = thisGuess.letterGuess();
        if (result == true) {
            JOptionPane.showMessageDialog(null, "The letter guess is correct!");
        } else {
            JOptionPane.showMessageDialog(null, "The letter guess is incorrect!");
            guessCount += 1;
            JOptionPane.showMessageDialog(null, "Guess Count:" + guessCount);
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
        food.addActionListener(new ButtonListener("sports.txt"));
        JButton hobbies = new JButton("Hobbies");
        food.addActionListener(new ButtonListener("hobbies.txt"));
        JButton umw = new JButton("UMW");
        umw.addActionListener(new ButtonListener("umw.txt"));
        JButton challenge = new JButton("Challenge");
        challenge.addActionListener(new ButtonListener("challenge.txt"));

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
