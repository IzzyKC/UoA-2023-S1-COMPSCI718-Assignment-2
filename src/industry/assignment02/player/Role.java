package industry.assignment02.player;

import industry.assignment02.game.Result;

import java.util.ArrayList;
import java.util.List;

public abstract class Role {
    private String secretCode;
    private List<Result> guessResults;

    /**
     * constructor of Role
     * initialize guessResults
     */
    public Role(){
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
     * set the value of secretCode
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
     * set the collection of guessResults
     *
     * @param guessResults the collection of guessResults
     */
    public void setGuessResults(List<Result> guessResults) {
        this.guessResults = guessResults;
    }
}
