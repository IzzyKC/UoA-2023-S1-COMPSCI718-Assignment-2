import industry.assignment02.game.Game;
import industry.assignment02.game.GameMode;
import industry.assignment02.player.AILevel;
import industry.assignment02.player.RoleType;

public class GameManager {
    enum Command {SET_PLAYER_SECRET_CODE, PROCESS_PLAYER_GUESS, WRITE_RESULT_TO_FILE}

    private Game game;

    /**
     * Constructor of GameManager
     */
    public GameManager() {
        this.game = new Game();
    }

    /**
     * Construct a GameManager for Starting a game
     *
     * @param args The array of string parsed from command-line
     */
    public static void main(String[] args) {
        GameManager gm = new GameManager();
        gm.start();
    }

    /**
     * Prints a welcome message, prompt player for game mode and difficulty, initialize game setting
     * guess the secrect code, check result and
     * prints an exit message when the game ends.
     */
    public void start() {
        try {
            printWelcomeMessage();
            game.setGameMode(getGameMode());
            AILevel gameLevel = getGameLevel();
            game.init(gameLevel);
            passCommand(Command.SET_PLAYER_SECRET_CODE);
            passCommand(Command.PROCESS_PLAYER_GUESS);
            //TODO ask player if he wants to  save game result to file or not
            passCommand(Command.WRITE_RESULT_TO_FILE);
            printExitMessage();
        } catch (Exception e) {
            System.out.println("[Game Manager] Error Message: " + e.getMessage());
        }
    }

    /**
     * print the welcome message of the game
     */
    private void printWelcomeMessage() {
        System.out.println("Welcome to the game!");
    }

    /**
     * prompt player for game mode
     *
     * @return gameMode by player's choice
     */
    private GameMode getGameMode() {
        printGameModeMenu();
        while (true) {
            String input = Keyboard.readInput().toLowerCase();

            //when user enter the actual name , return corresponding value
            switch (input) {
                case "bulls and cows":
                    return GameMode.BULLSANDCOWS;
                case "wordle":
                    return GameMode.WORDLE;
            }
            try {
                int choice = Integer.parseInt(input);
                if (choice == 1)
                    return GameMode.BULLSANDCOWS;
                else if (choice == 2)
                    return GameMode.WORDLE;
                System.out.println("Please enter a number 1 or 2 or full game name: Bulls and Cows, Wordle");

            } catch (NumberFormatException e) {
                System.out.println("Please enter a number 1 or 2 or full game name: Bulls and Cows, Wordle");
            }
        }


    }

    /**
     * prints the game mode menu
     */
    private void printGameModeMenu() {
        System.out.println("Please make your choice to start a game:");
        System.out.println("1.Bulls and Cows");
        System.out.println("2.Wordle");
    }

    /**
     * prompt player for Bulls and Cows difficulty
     * WORDLE has no difficulty level.
     *
     * @return String gameLevel by player's choice
     */
    private AILevel getGameLevel() {
        if (game.getGameMode().equals(GameMode.WORDLE))
            return AILevel.WORDLE;

        printGameLevelMenu();

        while (true) {
            String input = Keyboard.readInput().toLowerCase();

            //when user enter the actual name , return corresponding value
            switch (input) {
                case "singleplayer":
                    return AILevel.BEGINNER;
                case "easyai":
                    return AILevel.EASYAI;
                case "mediumai":
                    return AILevel.MEDIUMAI;
                case "hardai":
                    return AILevel.HARDAI;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice == 1)
                    return AILevel.BEGINNER;
                else if (choice == 2)
                    return AILevel.EASYAI;
                else if (choice == 3)
                    return AILevel.MEDIUMAI;
                else if (choice == 4)
                    return AILevel.HARDAI;
                System.out.println("Please enter SinglePlayer, EasyAI, MediumAI, HardAI or a number between 1 and 4");

            } catch (NumberFormatException e) {
                System.out.println("Please enter SinglePlayer, EasyAI, MediumAI, HardAI or a number between 1 and 4");
            }
        }
    }

    /**
     * prints the game level menu
     */
    private void printGameLevelMenu() {
        System.out.println("Please make a choice of your game level:");
        System.out.println("1.SinglePlayer");
        System.out.println("2.EasyAI");
        System.out.println("3.MediumAI");
        System.out.println("4.HardAI");
    }

    /**
     * set up player Secret code if needed
     */
    private void setPlayerSecretCode() {
        if((game.getGameMode().equals(GameMode.BULLSANDCOWS) && game.getComputerAILevel().equals(AILevel.BEGINNER)) ||
                game.getGameMode().equals(GameMode.WORDLE))
            return ;
        String code = getPlayerSecretCode();
        game.setSecretCodeByRole(RoleType.PLAYER, code);
        System.out.println(">>>>>");
    }

    /**
     * prompt player to enter secret code
     * Bulls and Cows : EasyAI, MediumAI and HARDAI need player to enter their secret code
     * Otherwise, computer will generate random code at the beginning of the game
     * @return secretCode from player
     */
    private String getPlayerSecretCode() {
        String code = null;
        boolean isCodeValid = false;
        while (!isCodeValid) {
            System.out.println("Please Enter your secret code:");
            code = Keyboard.readInput();
            if (isDigitalCodeValid(code)) {
                isCodeValid = true;
            } else {
                System.out.println("Your secret code is invalid! Please enter a number of non-repetitive 4 digits from 0-9!");
            }
        }
        return code;
    }

    /**
     * process Use Guess
     */
    private void processPlayerGuess() {
        while (!game.isGameEnd()) {
            String guess = getUserGuess();
            game.guessSecretCode(RoleType.PLAYER, guess);
            System.out.println("-----");
        }
    }

    /**
     * get user guess
     * format : non-repetitive 4 digit from 0 to 9
     */
    private String getUserGuess() {
        String input = "";
        boolean isInputValid = false;
        while (!isInputValid) {
            System.out.println("Enter your guess:");
            input = Keyboard.readInput();
            if (isDigitalCodeValid(input)) {
                isInputValid = true;
            } else {
                System.out.println("Your guess is invalid! Please enter a number of non-repetitive 4 digits from 0-9!");
            }
        }
        return input;
    }

    /**
     * check if format of the code is valid
     *
     * @param code non-repetitive 4-digits code from 0 to 9
     * @return is code valid
     */
    private boolean isDigitalCodeValid(String code){
        return code.matches("^(?:([0-9])(?!.*\\1)){4}");
    }

    /**
     * Prints the exit message of the game
     */
    private void printExitMessage() {
        System.out.println("Thank you for playing!");
    }

    /**
     * Parses a string into a command plus arguments and executes them.
     *
     * @param cmd The string to be parsed
     */
    private void passCommand(Command cmd) {
        switch (cmd) {
            case SET_PLAYER_SECRET_CODE:
                setPlayerSecretCode();
            case PROCESS_PLAYER_GUESS:
                processPlayerGuess();
            case WRITE_RESULT_TO_FILE:
                game.writeGameResultToFile();
        }

    }

}
