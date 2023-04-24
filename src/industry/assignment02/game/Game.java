package industry.assignment02.game;

import industry.assignment02.player.*;

import java.io.*;

public class Game {
    private Player player;
    private Computer computer;
    private GameMode gameMode;
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
     * initialize a new game: set up max attempts, create computer, set up secret code
     */
    public void init(AILevel level) throws IOException {
        maxAttempts = (gameMode.equals(GameMode.BULLSANDCOWS)) ? 7 : 6;
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
    private void setUpComputerCode() throws FileNotFoundException {
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
        player.setSecretCode(playerCode);
    }

    /**
     * guess opponent's secret code
     * if player or computer guess opponent's secret code correctly or max attempts is full, then game stops and return true
     * otherwise game continues, return false
     * after all guesses execute successfully, attempt number plus 1
     *
     * @param playerGuess guess from player
     */
    public void guessSecretCodes(String playerGuess) {
        try {
            if (isMaxAttemptsFull()) return;
            System.out.println("Turn " + (attempts + 1) + ":");
            Result playerResult = scoreGuessResult("You", computer.getSecretCode(), playerGuess);
            player.getGuessResults().add(playerResult);
            printandCheckGuessResult(playerResult);
            if (!gameEnd && isInterActiveMode()) {
                String computerGuess;
                computerGuess = computer.guessPlayerCode();
                Result computerResult = scoreGuessResult("Computer", player.getSecretCode(), computerGuess);
                computer.getGuessResults().add(computerResult);
                printandCheckGuessResult(computerResult);
            }
            addAttempt();
            if (!gameEnd && isMaxAttemptsFull()) {
                setGameEnd(true);
                if (isInterActiveMode())
                    System.out.println("Sorry, " + maxAttempts + " tries is full!\nResult is a draw. Secret Code was "
                            + computer.getSecretCode());
                else
                    System.out.println("You ran out of tries! Secret Code was " + computer.getSecretCode());
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
     * print and check guess result
     * prints game result and check if bulls count number is 4, then game ends and prints winner message
     */
    private void printandCheckGuessResult(Result result) {
        if (result == null)
            throw new NullPointerException("There is no guess Result to be print!");
        System.out.println(result);
        if (result.isGuessCorrect()) {
            setGameEnd(true);
            System.out.println(result.printWinnerMessage());
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
                throw new NullPointerException(guesser + " SecretCode is NULL!");
            if (guess == null || guess.isBlank())
                throw new NullPointerException(guesser + " Guess is NULL!");
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

    /**
     * saves game result to a text file
     *
     * @param fileName fileName entered by player
     */
    public void writeResultToTxtFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.printf("%s\n", (GameMode.BULLSANDCOWS.equals(gameMode) ? "Bulls & Cows" : "Wordle") + " game result.");
            if (isInterActiveMode())
                writer.printf("%s\n", "Your code: " + player.getSecretCode());
            writer.printf("%s\n", "Computer's code: " + computer.getSecretCode());
            int i = 0;
            while (i < player.getGuessResults().size()) {
                writer.print("---\n");
                writer.printf("%s\n", "Turn " + (i + 1) + ":");
                Result playerResult = player.getGuessResults().get(i);
                writer.printf("%s\n", playerResult.toString());
                if (playerResult.isGuessCorrect())
                    writer.printf("%s\n", playerResult.printWinnerMessage());
                if (i >= computer.getGuessResults().size()) break;
                Result computerResult = computer.getGuessResults().get(i);
                writer.printf("%s\n", computerResult.toString());
                if (computerResult.isGuessCorrect())
                    writer.printf("%s\n", computerResult.printWinnerMessage());
                if (i == (player.getGuessResults().size() - 1) &&
                        playerResult.getBullCount() != 4 && computerResult.getBullCount() != 4) {
                    writer.print(">>>\n");
                    writer.printf("%s", maxAttempts + " tries is full. Result is a draw.");
                }
                i++;
            }
            System.out.println("Game Result saved successfully to " + fileName + "!");
        } catch (IOException e) {
            System.out.println("Error:　" + e.getMessage());
        }
    }

}
