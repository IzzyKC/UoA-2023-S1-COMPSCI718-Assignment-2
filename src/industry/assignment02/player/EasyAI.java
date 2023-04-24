package industry.assignment02.player;

public class EasyAI extends Computer {
    /**
     * constructor of EasyAI
     *
     * @param aiLevel computer AI Level
     */
    public EasyAI(AILevel aiLevel) {
        setAiLevel(aiLevel);
    }

    /**
     * EasyAI guesses player's secret
     * generates random non-repetitive 4 digits
     *
     * @return computer's random guess
     */
    @Override
    public String guessPlayerCode() {
        return genRandomCode(4, false);
    }
}
