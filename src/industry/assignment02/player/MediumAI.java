package industry.assignment02.player;

public class MediumAI extends Computer implements ComputerAI{
    public MediumAI(AILevel aiLevel) {
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
