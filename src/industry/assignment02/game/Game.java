package industry.assignment02.game;

import industry.assignment02.player.*;

import java.util.ArrayList;

public class Game {
    private ArrayList<Role> roles;
    private GameMode gameMode;
    //private AILevel gameLevel;
    private int maxAttempts;

    private int attempts;

    private boolean gameEnd;

    public Game() {
        this.roles = new ArrayList<>();
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

    /**
     * return the value of attempt
     *
     * @return the value of attempts
     */

    public int getAttempts() {
        return this.attempts;
    }

    /**
     * return the value of max attempts for each game
     *
     * @return the value of maximum attempts
     */
    public int getMaxAttempts() {
        return this.maxAttempts;
    }

    /**
     * set the value of max attempts for each game
     *
     * @param maxAttempts maximum of attempts
     */
    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
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
        createGameRoles(level);
        setUpComputerSecretCode();

    }

    /**
     * create player list:player and computer or AY by game mode and game level
     */
    private void createGameRoles(AILevel level) {
        roles.add(new Player());
        roles.add(new Computer(level));
    }

    private void setUpComputerSecretCode() {
        if (this.gameMode.equals(GameMode.BULLSANDCOWS))
            setSecretCodeByRole(RoleType.COMPUTER, null);
        else
            SetWordleSecretWord();
    }


    /**
     * set up player secret code
     *
     * @param roleType   player role type
     * @param secretCode if needed
     */
    public void setSecretCodeByRole(RoleType roleType, String secretCode) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i) != null && roleType.equals(roles.get(i).getType())) {
                roles.get(i).processSecretCodeSetting(secretCode);
            }
        }
    }

    /**
     * set five-letter word of wordle
     */
    private void SetWordleSecretWord() {
        getWordleSecretWordFromFile();
    }

    /**
     * set up wordle five-letter word from dictionary.txt
     * format: five letters and contains only letters A - Z or a - z
     *
     * @return wordle secret word
     */
    private String getWordleSecretWordFromFile() {
        return null;
    }

    /**
     * guess opponent's secret code
     * if player or computer guess opponent's secret code correctly or max attempts is full, then game stops and return true
     * otherwise game continues, return false
     *
     * @param playerGuess guess from player
     */
    public void guessSecretCode(String playerGuess) {
        //TODO adjust logic to process computer's guess after player
        try {
            if (isMaxAttemptsFull()) return;
            boolean isComputerGuess = isComputerGuess();
            for (Role role : roles) {
                if (role == null) continue;
                String opponentSecretCode = getOpponentSecretCode(role.getType());
                Result scoreResult = null;
                if (role instanceof Player)
                    scoreResult = scoreGuessResult(opponentSecretCode, playerGuess);
                else if (role instanceof Computer && isComputerGuess) {
                    Computer computer = (Computer) role;
                    scoreResult = scoreGuessResult(opponentSecretCode, computer.guessPlayerSecretCode());
                }
                printGuessResult(scoreResult, role.getType());
            }
            addAttempt();
            if (!this.gameEnd && isMaxAttemptsFull()) {
                setGameEnd(true);
                System.out.println("You ran out of tries! Secret Code was " + getOpponentSecretCode(RoleType.PLAYER));
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
    private boolean isComputerGuess() {
        AILevel level = getComputerAILevel();
        return !GameMode.WORDLE.equals(this.getGameMode()) && !AILevel.BEGINNER.equals(level);
    }

    /**
     * print guess result
     */
    private void printGuessResult(Result result, RoleType roleType) {
        String subject;
        if (roleType.equals(RoleType.PLAYER))
            subject = "You";
        else
            subject = "Computer";

        if (result != null) {
            StringBuilder resultMsg = new StringBuilder();
            resultMsg.append("Turn " + (this.getAttempts() + 1) + ":\n");
            resultMsg.append(subject + " guessed " + result.getGuess() + ", ");
            resultMsg.append("scoring " + result.getBullCount() + " bulls and");
            resultMsg.append(result.getCowCount() + " cows");
            System.out.println(resultMsg);
            if (result.getBullCount() == 4) {
                setGameEnd(true);
                System.out.println(subject + " win! :)");
            }
        }
    }

    /**
     * return opponent's role type
     *
     * @param roleType role Type of current guesser
     * @return opponent's role type
     */
    private RoleType getOpponentRoleType(RoleType roleType) {
        switch (roleType) {
            case COMPUTER:
                return RoleType.PLAYER;
            case PLAYER:
                return RoleType.COMPUTER;
            default:
                return null;
        }
    }

    /**
     * get opponent's secret code
     *
     * @param roleType role type of opponent
     * @return opponent's secret code
     */
    private String getOpponentSecretCode(RoleType roleType) {
        RoleType opponentType = null;
        if (RoleType.COMPUTER.equals(roleType))
            opponentType = RoleType.PLAYER;
        else
            opponentType = RoleType.COMPUTER;

        for (Role role : roles) {
            if (role != null && opponentType.equals(role.getType()))
                return role.getSecretCode();
        }
        return null;
    }

    /**
     * check guess result
     *
     * @return result of guess
     */
    private Result scoreGuessResult(String secretCode, String guess) {
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
            return new Result(guess, bulls, cows);
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
     * return the value of AI level from Computer
     *
     * @return AI level
     */
    public AILevel getComputerAILevel() {
        try {
            for (Role role : roles) {
                if (role instanceof Computer) {
                    Computer computer = (Computer) role;
                    return computer.getLevel();
                }
            }
        } catch (Exception e) {
            System.out.println("[GetComputerAILevel] Error: " + e.getMessage());
        }
        return null;
    }

    public void writeGameResultToFile() {

    }
}
