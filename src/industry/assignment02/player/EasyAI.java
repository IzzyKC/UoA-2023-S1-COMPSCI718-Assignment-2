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
     EasyAI guesses player's secret by different AI Level
     *
     * @return computer's random guess
     */
    @Override
    public String guessPlayerCode() {
        switch(getAiLevel()) {
            case EASYAI:
                return genRandomCode(4, false);
            case WORDLE:
                return genWordleGuess();
            default:
                return null;
        }
    }
}
