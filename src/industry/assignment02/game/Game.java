package industry.assignment02.game;

import industry.assignment02.player.*;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private Player player;
    private Computer computer;
    private GameMode gameMode;
    //private AILevel gameLevel;
    private int maxAttempts;

    private int attempts;

    private boolean gameEnd;

    public Game() {
        player = new Player();
    }

    /**
     * return the value game mode
     *
     * @return gameMode
     */
    public GameMode getGameMode() {
        return this.gameMode;
    }

    /**
     * set the value of game mode
     *
     * @param gameMode game mode
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * after guess, the value of attempts plus 1
     */
    private void addAttempt() {
        this.attempts++;
    }

    public boolean isGameEnd() {
        return this.gameEnd;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    /**
     * initialize a new game: set up max attempts, create game; players
     */
    public void init(AILevel level) {
        this.maxAttempts = (this.gameMode.equals(GameMode.BULLSANDCOWS)) ? 7 : 6;
        createComputer(level);
        setUpComputerCode();

    }

    /**
     * create player list:player and computer by game mode and game level
     * MediumAI and HardAI must create corresponding computer role
     * otherwiese, create EasyAI
     */
    private void createComputer(AILevel level) {
        if (AILevel.MEDIUMAI.equals(level))
            computer = new MediumAI(level);
        else if (AILevel.HARDAI.equals(level))
            computer = new HardAI(level);
        else
            computer = new EasyAI(level);
    }

    /**
     * set up computer secret code
     */
    private void setUpComputerCode() {
        if (computer == null) return;
        if (this.gameMode.equals(GameMode.BULLSANDCOWS))
            computer.genComputerCode();
        else
            computer.genWordleCode();
    }


    /**
     * set up player secret code
     *
     * @param playerCode secret code entered by player
     */
    public void setUpPlayerCode(String playerCode) {
        player.setPlayerCode(playerCode);
    }

    /**
     * guess opponent's secret code
     * if player or computer guess opponent's secret code correctly or max attempts is full, then game stops and return true
     * otherwise game continues, return false
     *
     * @param playerGuess guess from player
     */
    public void guessSecretCodes(String playerGuess) {
        //TODO adjust logic to process computer's guess after player
        try {
            if (isMaxAttemptsFull()) return;
            Map<String, Result> resultMap = new HashMap<>();
            Result result;
            result = scoreGuessResult("You", computer.getComputerCode(), playerGuess);
            resultMap.put(player.getClass().getSimpleName(), result);
            if (isInterActiveMode()) {
                String computerGuess;
                if (computer instanceof EasyAI)
                    computerGuess = ((EasyAI) computer).guessWithAIStrategy();
                else if (computer instanceof MediumAI)
                    computerGuess = ((MediumAI) computer).guessWithAIStrategy();
                else
                    computerGuess = ((HardAI) computer).guessWithAIStrategy();
                result = scoreGuessResult("Computer", player.getPlayerCode(), computerGuess);
                resultMap.put(computer.getClass().getSimpleName(), result);
            }
            addAttempt();
            printGuessResult(resultMap);
            if (!this.gameEnd && isMaxAttemptsFull()) {
                setGameEnd(true);
                System.out.println("You ran out of tries! Secret Code was " + computer.getComputerCode());
            }
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Guess Secret Code] Error: " + e.getMessage());
        }
    }

    /**
     * check if computer needs to guess player's secret code
     * EASYAI, MEDIUMAI, HARDAI computer need to guess player's secret code
     *
     * @return boolean value of computer guess
     */
    public boolean isInterActiveMode() {
        AILevel level = computer.getAiLevel();
        return AILevel.EASYAI.equals(level) || AILevel.MEDIUMAI.equals(level) || AILevel.HARDAI.equals(level);
    }

    /**
     * print guess result
     */
    private void printGuessResult(Map<String, Result> resultMap) {
        if (resultMap.size() == 0) return;
        System.out.println("Turn " + this.attempts+":");
        for(Map.Entry<String, Result> entry : resultMap.entrySet()){
            Result result = entry.getValue();
            System.out.println(result);
            if (result.getBullCount() == 4) {
                setGameEnd(true);
                System.out.println(result.getGuesser() + " win! :)");
            }
        }
    }

    /**
     * check guess result
     *
     * @return result of guess
     */
    private Result scoreGuessResult(String guesser, String secretCode, String guess) {
        try {
            if (secretCode == null || secretCode.isBlank())
                throw new NullPointerException("Input SecretCode is null");
            if (guess == null || guess.isBlank())
                throw new NullPointerException("Input guess is null");
            int bulls = 0, cows = 0;
            for (int i = 0; i < guess.length(); i++) {
                int index = secretCode.indexOf(guess.charAt(i));
                if (index == i)
                    bulls++;
                else if (index >= 0)
                    cows++;
            }
            return new Result(guesser, guess, bulls, cows);
        } catch (NullPointerException e) {
            System.out.println("[ScoreGuessResult] Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * if the maximum of game tries is full, return true
     * otherwise return false
     *
     * @return the status of max attempts
     */
    private boolean isMaxAttemptsFull() {
        return this.attempts == this.maxAttempts;
    }

    public void writeGameResultToFile() {

    }
}
