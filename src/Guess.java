
public class Guess {
    private String targetWord;
    private String guess;

    public Guess(String targetWord, String guess) {
        this.targetWord = targetWord;
        this.guess = guess;
    }

    public boolean letterGuess() {
        if(targetWord.contains(guess)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean wordGuess() {
        if(targetWord.equals(guess)) {
            return true;
        } else {
            return false;
        }
    }
}
