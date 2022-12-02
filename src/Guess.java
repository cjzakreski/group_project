/**
 * This guess is used to check whether guesses are correct
 */
public class Guess {
    private String targetWord;
    private String guess;

    /**
     * this constructor sets the targetWord and the guess and the ones that the user is currently dealing with
     * @param targetWord the word the user is trying to guess
     * @param guess The letter or word that the user most recently guessed
     */
    public Guess(String targetWord, String guess) {
        this.targetWord = targetWord;
        this.guess = guess;
    }

    /**
     * This method checks whether the guessed letter is in the word
     * @return true if the letter was found, false if it was not
     */
    public boolean letterGuess() {
        if(targetWord.contains(guess)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method checks whether the guessed word matches the target word
     * @return true if the words are the same, false otherwise
     */
    public boolean wordGuess() {
        if(targetWord.equals(guess.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }
}
