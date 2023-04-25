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
     * return the value of game mode
     *
     * @return gameMode
     */
    public GameMode getGameMode() {
        return this.gameMode;
    }

    /**
     * set the value of game mode
     *
     * @param gameMode gameMode
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
     * initialize a new game: set up max attempts, create a computer object, set up secret code
     */
    public void init(AILevel level) throws WordleFileNotFoundException {
        maxAttempts = (gameMode.equals(GameMode.BULLSANDCOWS)) ? 7 : 6;
        createComputer(level);
        setUpComputerCode();
    }

    /**
     * creates a corresponding computer object
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
     * set up computer's secret code or word
     */
    private void setUpComputerCode() throws WordleFileNotFoundException {
        if (computer == null) return;
        if (this.gameMode.equals(GameMode.BULLSANDCOWS))
            computer.genComputerCode();
        else
            computer.genWordleCode();
    }


    /**
     * set up player's secret code
     *
     * @param playerCode A secret code entered by player
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
     * @param playerGuess A guess from player
     */
    public void guessSecretCodes(String playerGuess) {
        try {
            if (isMaxAttemptsFull()) return;
            System.out.println("Turn " + (attempts + 1) + ":");
            Result playerResult = dispatchScoreGuess("You", computer.getSecretCode(), playerGuess);
            player.getGuessResults().add(playerResult);
            printandCheckGuessResult(playerResult);
            if (!gameEnd && isInterActiveMode()) {
                String computerGuess;
                computerGuess = computer.guessPlayerCode();
                Result computerResult = dispatchScoreGuess("Computer", player.getSecretCode(), computerGuess);
                computer.getGuessResults().add(computerResult);
                printandCheckGuessResult(computerResult);
            }
            addAttempt();
            if (isMaxAttemptsFull() && !gameEnd) {
                setGameEnd(true);
                System.out.println(getGameResultMessage(true));
            }
        } catch (Exception e) {
            System.out.println("[Guess Secret Code] Error: " + e.getMessage());
        }
    }

    /**
     * if no one wins the game, then return the String of the game result
     *
     * @param isPrintSecretCode needs to print secret code or not
     */
    private String getGameResultMessage(boolean isPrintSecretCode) {
        StringBuilder resultMsg = new StringBuilder("Sorry! You ran out of tries! ");
        if (isInterActiveMode())
            resultMsg.append("Result is a draw. ");
        if (isPrintSecretCode)
            resultMsg.append("Secret Code was " + computer.getSecretCode());
        return resultMsg.toString();
    }

    /**
     * check if computer needs to guess player's secret code
     * EASYAI, MEDIUMAI, HARDAI computer need to guess player's secret code
     *
     * @return is a game of multi players
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
     * dispatches the corresponding scoring method by different game mode
     *
     * @param guesser    A name of game roles
     * @param secretCode A secret code/word set up by different game mode
     * @param guess      A player's guess
     * @return A result of guess
     */
    private Result dispatchScoreGuess(String guesser, String secretCode, String guess) {
        switch (gameMode) {
            case BULLSANDCOWS:
                return scoreBullsAndCowsResult(guesser, secretCode, guess);
            case WORDLE:
                return scoreWordleResult(guesser, secretCode, guess);
            default:
                return null;
        }
    }

    /**
     * check guess result
     *
     * @return A result of guess
     */
    private Result scoreBullsAndCowsResult(String guesser, String secretCode, String guess) {
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
        } catch (Exception e) {
            System.out.println("[ScoreBulls&CowsResult] Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * scores the result of player's guess for wordle
     * check bulls count first, and marks the matched char to *
     * then check cows count from secret word after marked
     *
     * @param guesser A name of game role
     * @param target  A secret word
     * @param guess   A player's guess
     * @return A result of player's guess
     */
    private Result scoreWordleResult(String guesser, String target, String guess) {
        try {
            if (target == null || target.isBlank())
                throw new NullPointerException("Wordle secret word is NULL!");
            if (guess == null || guess.isBlank())
                throw new NullPointerException("Player guess is NULL!");

            int bulls = 0, cows = 0;

            Result result = new Result(guesser, guess);

            for (int i = 0; i < target.length(); i++) {
                if (target.toLowerCase().charAt(i) == guess.toLowerCase().charAt(i)) {
                    bulls++;
                    target = target.substring(0, i) + "*" + target.substring(i + 1);
                }
            }
            result.setBullCount(bulls);

            for (int i = 0; i < target.length(); i++) {
                if (target.toLowerCase().contains(String.valueOf(guess.toLowerCase().charAt(i))))
                    cows++;
            }
            result.setCowCount(cows);

            return result;
        } catch (Exception e) {
            System.out.println("[scoreWordleResult] Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * if the maximum of game tries is full, return true
     * otherwise return false
     *
     * @return The maxAttempts is full or not
     */
    private boolean isMaxAttemptsFull() {
        return this.attempts == this.maxAttempts;
    }

    /**
     * saves game result to a text file
     * if the game has only one player(not interactive mode), then skips the step of printing computer result
     * if the result of computer's guess is null, then end while loop(no more result needs to be printed)
     *
     * @param fileName A fileName entered by player
     */
    public void writeResultToTxtFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.printf("%s\n", (GameMode.BULLSANDCOWS.equals(gameMode) ? "Bulls & Cows" : "Wordle") + " game result.");
            if (isInterActiveMode())
                writer.printf("%s\n", "Your code: " + player.getSecretCode());
            writer.printf("%s\n", "Computer's code: " + computer.getSecretCode());
            for (int i = 0; i < player.getGuessResults().size(); i++) {
                writer.print("---\n");
                writer.printf("%s\n", "Turn " + (i + 1) + ":");
                Result playerResult = player.getGuessResults().get(i);
                writer.printf("%s\n", playerResult.toString());
                if (playerResult.isGuessCorrect())
                    writer.printf("%s\n", playerResult.printWinnerMessage());
                if (!isInterActiveMode()) continue;
                if (i >= computer.getGuessResults().size()) break;
                Result computerResult = computer.getGuessResults().get(i);
                writer.printf("%s\n", computerResult.toString());
                if (computerResult.isGuessCorrect())
                    writer.printf("%s\n", computerResult.printWinnerMessage());
            }
            if (isMaxAttemptsFull() && (!player.getGuessResults().get(maxAttempts - 1).isGuessCorrect()
                    || (isInterActiveMode() && computer.getGuessResults().size() == maxAttempts
                    && !computer.getGuessResults().get(maxAttempts - 1).isGuessCorrect()))) {
                writer.print(">>>\n");
                writer.printf("%s", getGameResultMessage(false));
            }
            System.out.println("Game Result saved successfully to " + fileName + "!");
        } catch (IOException e) {
            System.out.println("Error:　" + e.getMessage());
        }
    }

    /**
     * check wordle guess format: five letters and contains only letters A - Z or a - z
     *
     * @param word A player's wordle guess
     * @return A result of format check
     */
    public boolean isWordleGuessValid(String word) {
        return computer.isWordVaild(word);
    }

}
