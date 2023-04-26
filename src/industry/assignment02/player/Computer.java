package industry.assignment02.player;

import industry.assignment02.game.WordleFileNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Computer extends Role {
    public final String WORDLE_FILENAME = "dictionary1.txt";
    private AILevel aiLevel;

    /**
     * Returns the value of AILevel.
     *
     * @return the value of AILevel.
     */
    public AILevel getAiLevel() {
        return aiLevel;
    }

    /**
     * sets the value of AILevel
     *
     * @param aiLevel the value of AiLevel
     */
    public void setAiLevel(AILevel aiLevel) {
        this.aiLevel = aiLevel;
    }

    /**
     * generates random computer secret code automatically.
     * The code is non-repetitive 4 digits from 0 – 9
     */
    public void genComputerCode() {
        super.setSecretCode(genRandomCode(4, false));
    }

    /**
     * generates a 4 digits randomly
     *
     * @param length             length of secret Code
     * @param allowRepeatedDigit code can include repeat digit or not
     */
    public String genRandomCode(int length, boolean allowRepeatedDigit) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String digit = String.valueOf(getRandomDigit(0, 9));
            while (!allowRepeatedDigit && code.indexOf(digit) >= 0) {
                String newDigit = String.valueOf(getRandomDigit(0, 9));
                if (code.indexOf(newDigit) < 0) {
                    digit = newDigit;
                    break;
                }
            }
            code.append(digit);
        }
        return code.toString();
    }

    /**
     * returns a  digit number from 0 to 9
     *
     * @return A random integer number
     */
    public int getRandomDigit(int min, int max) {
        return (int) (Math.random() * (max - min + 1));
    }

    /**
     * generates Wordle five-letter word from dictionary.txt
     * format: five letters and contains only letters A - Z or a - z
     */
    public void genWordleCode() throws WordleFileNotFoundException {
        List<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(WORDLE_FILENAME))) {

            scanner.useDelimiter(",|\\r\\n");

            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWordleWordVaild(word)) {
                    words.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            throw new WordleFileNotFoundException("Something went wrong! Wordle can't start to play! " +
                    "The system cannot find Wordle File: " + WORDLE_FILENAME);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        setSecretCode(words.size() > 0 ? words.get(getRandomDigit(0, words.size() - 1)) : null);
    }

    /**
     * check Wordle word format: five letters and contains only letters A - Z or a - z
     *
     * @param word A Wordle word
     * @return A result of format check
     */
    public boolean isWordleWordVaild(String word) {
        return word.matches("[a-zA-z]{5}$");
    }

    /**
     * An abstract method: guess the player's secret code
     *
     * @return A possible secret code
     */
    public abstract String guessPlayerCode();


}
