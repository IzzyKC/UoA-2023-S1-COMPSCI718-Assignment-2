package industry.assignment02.player;

public class HardAI extends Computer {
    /**
     * constructor of HardAI
     *
     * @param aiLevel computer AI Level
     */
    public HardAI(AILevel aiLevel) {
        setAiLevel(aiLevel);
    }

    /**
     * HardAI guesses player's secret under HardAI guess strategy
     *
     * @return computer's random guess
     */
    @Override
    public String guessPlayerCode() {
        return null;
    }

}
