package industry.assignment02.player;

public class EasyAI extends Computer implements ComputerAI{
    public EasyAI(AILevel aiLevel) {
        setAiLevel(aiLevel);
    }

    @Override
    public String guessPlayerCode() {
        return null;
    }

    @Override
    public String guessWithAIStrategy() {
        return null;
    }
}
