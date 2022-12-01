// used to check the letter guess or word guess
public class Guess {
    private String targetWord;
    private String guess;

    // takes the targetWord and the guess as parameters
    public Guess(String targetWord, String guess) {
        this.targetWord = targetWord;
        this.guess = guess;
    }

    // returns whether the targetWord contains the letter guess (true if it does; false if it does not)
    public boolean letterGuess() {
        if(targetWord.contains(guess)) {
            return true;
        } else {
            return false;
        }
    }

    // returns whether the targetWord is the word guess (true if it is; false if it is not)
    public boolean wordGuess() {
        if(targetWord.equals(guess)) {
            return true;
        } else {
            return false;
        }
    }
}
