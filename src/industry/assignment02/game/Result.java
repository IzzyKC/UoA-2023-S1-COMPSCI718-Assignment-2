package industry.assignment02.game;

/**
 * store check result by guess
 */
public class Result {
    private String guess;
    private int bullCount;
    private int cowCount;

    /**
     * Constructor of result
     *
     * @param guess value of guess
     * @param bullCount value of bulls count
     * @param cowCount  value of cows count
     */
    public Result(String guess, int bullCount, int cowCount) {
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
}
