package industry.assignment02.player;

import industry.assignment02.game.Result;

import java.util.ArrayList;
import java.util.List;

public class Role {
    private String secretCode;
    private List<Result> guessResults;
    private boolean isWinner;

    /**
     * constructor of Role
     * initialize guessResults
     */
    public Role() {
        guessResults = new ArrayList<>();
    }

    /**
     * Returns the value of  secret code.
     *
     * @return the value of player secret code.
     */
    public String getSecretCode() {
        return secretCode;
    }

    /**
     * sets the value of secretCode
     *
     * @param secretCode the value of secret code
     */
    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    /**
     * Returns the collection of guess results
     *
     * @return the collection of guess results.
     */
    public List<Result> getGuessResults() {
        return guessResults;
    }

    /**
     * sets the collection of guessResults
     *
     * @param guessResults the collection of guessResults
     */
    public void setGuessResults(List<Result> guessResults) {
        this.guessResults = guessResults;
    }

    /**
     * Returns if this role is winner
     *
     * @return if this role is winner
     */
    public boolean isWinner() {
        return isWinner;
    }

    /**
     * sets if this role is winner
     *
     * @param winner if this role is winner
     */
    public void setWinner(boolean winner) {
        this.isWinner = winner;
    }
}
