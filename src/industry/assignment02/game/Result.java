package industry.assignment02.game;

/**
 * store check result by guess
 */
public class Result {
    private String guesser;
    private String guess;
    private int bullCount;
    private int cowCount;

    /**
     * Constructor of result
     *
     * @param guesser   name of guesser
     * @param guess     value of guess
     * @param bullCount value of bulls count
     * @param cowCount  value of cows count
     */
    public Result(String guesser, String guess, int bullCount, int cowCount) {
        this.guesser = guesser;
        this.guess = guess;
        this.bullCount = bullCount;
        this.cowCount = cowCount;
    }

    /**
     * return the value of guess
     *
     * @return value of guess
     */
    public String getGuess() {
        return this.guess;
    }

    /**
     * set the value of guess
     *
     * @param guess the value of guess
     */
    public void setGuess(String guess) {
        this.guess = guess;
    }

    /**
     * return the number of bulls count
     *
     * @return bullCount the number of bulls
     */
    public int getBullCount() {
        return this.bullCount;
    }

    /**
     * set the number of bulls
     *
     * @param bullCount tne number of bulls
     */
    public void setBullCount(int bullCount) {
        this.bullCount = bullCount;
    }

    /**
     * return the number of cows
     *
     * @return cowCount the number of cows
     */
    public int getCowCount() {
        return this.cowCount;
    }

    /**
     * set the number of cows
     *
     * @param cowCount the number of cows
     */
    public void setCowCount(int cowCount) {
        this.cowCount = cowCount;
    }

    /**
     * return the value of guesser
     *
     * @return value of guesser
     */

    public String getGuesser() {
        return guesser;
    }

    /**
     * set the value of guesser
     *
     * @param guesser value of guesser
     */
    public void setGuesser(String guesser) {
        this.guesser = guesser;
    }

    /**
     * return the String of guess result
     *
     * @return String
     */
    public String toString() {
        return guesser + " guessed " + guess + ", scoring " + bullCount + " bulls and " + cowCount + " cows";
    }

    /**
     * prints winner message
     *
     * @return winner message
     */
    public String printWinnerMessage() {
        return guesser.equals("You") ? "Congratulations! You win! :)" : "Sorry! Computer Win :(";
    }

    /**
     * check if this guess is correct when bullsCount == 4
     *
     * @return is guess match secret code correctly
     */
    public boolean isGuessCorrect() {
        return (bullCount == 4);
    }
}
