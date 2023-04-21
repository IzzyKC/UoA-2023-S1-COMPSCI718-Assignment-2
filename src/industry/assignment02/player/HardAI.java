package industry.assignment02.player;

public class HardAI extends Computer implements ComputerAI{
    public HardAI(AILevel aiLevel) {
        setAiLevel(aiLevel);
    }
    @Override
    public String guessWithAIStrategy() {
        return null;
    }

    @Override
    public String guessPlayerCode() {
        return null;
    }

}
