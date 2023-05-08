import industry.assignment02.game.Game;
import industry.assignment02.game.GameMode;
import industry.assignment02.game.WordleFileNotFoundException;
import industry.assignment02.role.AILevel;

public class GameManager {
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
     * Prints a welcome message,sets up game mode, starts a new game
     * and prints an exit message when the game ends.
     */
    public void start() {
        try {
            printWelcomeMessage();
            game.setGameMode(getGameMode());
            startNewName();
            printExitMessage();
        } catch (NullPointerException e) {
            System.out.println("Parameters NULL Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Game Manager start() Error: " + e.getMessage());
        }
    }

    /**
     * starts a new game:
     * initialize a game and sets up game parameters
     * prompts player to enter their secret code
     * prompts player to guess the secret code or word, then check result
     * prompts player to save the game result to a txt file
     */
    private void startNewName() {
        try {
            if (GameMode.QUIT == game.getGameMode()) return;
            game.init(getGameLevel());
            processPlayerCodeSetup();
            processPlayerGuess();
            processWriteToTxtFile();
        } catch (WordleFileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            switchToGameLevelMenu();
        }
    }

    /**
     * prints the welcome message of the game
     */
    private void printWelcomeMessage() {
        System.out.println("Welcome to the game!");
    }

    /**
     * when Wodle game can not find the dictionary file,then game stops
     * and return back to game level menu where the player can choose the different difficulty levels of the game
     */
    private void switchToGameLevelMenu() {
        System.out.println("The system will switch to Bulls and Cows automatically.\n");
        game.setGameMode(GameMode.BULLSANDCOWS);
        startNewName();
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

            if (input.isBlank())
                return GameMode.QUIT;

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
        System.out.println("Please make your choice to start a game or just press ENTER to quit:");
        System.out.println("1.Bulls and Cows");
        System.out.println("2.Wordle");
    }

    /**
     * prompt player for Bulls and Cows difficulty level
     * Wordle has no difficulty level. System will set it as "WORDLE"(Enum) automatically.
     *
     * @return String A gameLevel from player's choice
     */
    private AILevel getGameLevel() {
        if (GameMode.WORDLE == game.getGameMode())
            return AILevel.WORDLE;

        printGameLevelMenu();

        while (true) {
            String input = Keyboard.readInput().toLowerCase();

            if (input.isBlank())
                return AILevel.BEGINNER;

            switch (input) {
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
                    return AILevel.EASYAI;
                else if (choice == 2)
                    return AILevel.MEDIUMAI;
                else if (choice == 3)
                    return AILevel.HARDAI;
                System.out.println("Please enter EasyAI, MediumAI, HardAI or a number between 1 and 3");

            } catch (NumberFormatException e) {
                System.out.println("Please enter EasyAI, MediumAI, HardAI or a number between 1 and 3");
            }
        }
    }

    /**
     * prints the game level menu
     */
    private void printGameLevelMenu() {
        System.out.println("If you want to play against AI, please make a choice of your game level" +
                "\nor just press ENTER to start a single player game:");
        System.out.println("1.EasyAI");
        System.out.println("2.MediumAI");
        System.out.println("3.HardAI");
    }

    /**
     * sets up player's Secret code if needed
     */
    private void processPlayerCodeSetup() {
        if (!game.isInteractiveMode()) return;
        String code = getPlayerSecretCode();
        game.setUpPlayerCode(code);
        System.out.println(">>>>>");
    }

    /**
     * prompts player to enter a secret code
     * Bulls and Cows : EasyAI, MediumAI and HardAI need player to enter their secret code
     * Otherwise, only computer can generate a random code at the beginning of the game
     *
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
     * processes player's Guess
     * prompts player to enter a secret code and check if the input is valid
     */
    private void processPlayerGuess() {
        while (!game.isGameEnd()) {
            String guess = getPlayerGuess();
            System.out.println("-----");
            game.guessSecretCodes(guess);
        }
    }

    /**
     * prompts player to enter a valid guess
     * format : non-repetitive 4 digits from 0 to 9
     */
    private String getPlayerGuess() {
        String input = "";
        boolean isInputValid = false;
        while (!isInputValid) {
            System.out.println("Enter your guess:");
            input = Keyboard.readInput();
            switch (game.getGameMode()) {
                case BULLSANDCOWS:
                    isInputValid = isDigitalCodeValid(input);
                    if (!isInputValid)
                        System.out.println("Your guess is invalid! Please enter a number of non-repetitive 4 digits from 0-9!");
                    break;
                case WORDLE:
                    isInputValid = game.isWordleGuessValid(input);
                    if (!isInputValid)
                        System.out.println("Your guess is invalid! Please enter a 5 letter word from A-Z or a-z!");
                    break;
                default:
                    break;
            }
        }
        return input;
    }

    /**
     * checks if the digital code is valid
     *
     * @param code non-repetitive 4-digits code from 0 to 9
     * @return is code valid
     */
    private boolean isDigitalCodeValid(String code) {
        return code.matches("^(?:([0-9])(?!.*\\1)){4}");
    }

    /**
     * processes writing the game result to a txt file
     */
    private void processWriteToTxtFile() {
        System.out.println(">>>");
        System.out.println("If you want to save this game result to a file, please enter a file name: ");
        System.out.print("Hint : Type a file name, or just press ENTER to quit: ");
        String fileName = Keyboard.readInput();
        if (fileName.isBlank()) return;
        game.writeResultToTxtFile(fileName);

    }

    /**
     * Prints the exit message of the game
     */
    private void printExitMessage() {
        System.out.println("Thank you for playing!");
    }
}
