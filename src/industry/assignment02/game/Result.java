package industry.assignment02.game;

/**
 * A result for each guess
 */
public class Result {
    private final String guesser;
    private String guess;
    private int bulls;
    private int cows;

    /**
     * Constructor of result
     *
     * @param guesser A name of guesser
     * @param guess   value of guess
     * @param bulls   value of bulls count
     * @param cows    value of cows count
     */
    public Result(String guesser, String guess, int bulls, int cows) {
        this.guesser = guesser;
        this.guess = guess;
        this.bulls = bulls;
        this.cows = cows;
    }

    /**
     * Constructor of result
     *
     * @param guesser A name of guesser
     * @param guess   value of guess
     */
    public Result(String guesser, String guess) {
        this.guesser = guesser;
        this.guess = guess;
    }


    /**
     * returns the value of guess
     *
     * @return value of guess
     */
    public String getGuess() {
        return this.guess;
    }

    /**
     * sets the value of guess
     *
     * @param guess value of guess
     */
    public void setGuess(String guess) {
        this.guess = guess;
    }

    /**
     * returns the number of bulls
     *
     * @return bullCount the number of bulls
     */
    public int getBulls() {
        return this.bulls;
    }

    /**
     * sets the number of bulls
     *
     * @param bulls tne number of bulls
     */
    public void setBulls(int bulls) {
        this.bulls = bulls;
    }

    /**
     * returns the number of cows
     *
     * @return cowCount the number of cows
     */
    public int getCows() {
        return this.cows;
    }

    /**
     * sets the number of cows
     *
     * @param cows the number of cows
     */
    public void setCows(int cows) {
        this.cows = cows;
    }

    /**
     * returns the String of guess result
     *
     * @return A string
     */
    public String toString() {
        return guesser + " guess: " + guess + "\nResult: " + bulls + " bulls and " + cows + " cows";
    }

    /**
     * prints winner's message
     *
     * @return winner's message
     */
    public String getWinnerMessage() {
        return guesser.equals("You") ? "Congratulations! You win! :)" : "Sorry! Computer Win :(";
    }

    /**
     * check if this guess is correct when bulls Count equals to 4
     *
     * @return is the guess matches secret code correctly
     */
    public boolean isGuessCorrect() {
        return (bulls == 4);
    }

}
