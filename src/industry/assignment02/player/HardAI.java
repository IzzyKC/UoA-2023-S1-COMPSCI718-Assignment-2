package industry.assignment02.player;

import industry.assignment02.game.Result;

import java.util.ArrayList;
import java.util.List;

public class HardAI extends Computer {
    private static List<String> possibleGuesses = new ArrayList<>();

    /**
     * constructor of HardAI
     *
     * @param aiLevel computer AI Level
     */
    public HardAI(AILevel aiLevel) {
        setAiLevel(aiLevel);
        genAllPossibleGuesses();
    }

    /**
     * HardAI guesses player's secret with HardAI guess strategy
     * First : generates a random non-repetitive 4 digits
     * According to the latest guess result, fine next guess from possible candidates
     * randomly picks a possible guess match up same bulls number and same cows number with the latest guess
     * and remove other unmatched candidates
     *
     * @return computer's random guess
     */
    @Override
    public String guessPlayerCode() {
        if (getGuessResults().size() == 0)
            return genRandomCode(4, false);
        Result latestResult = getGuessResults().get(getGuessResults().size() - 1);
        for (int j = 0; j < possibleGuesses.size(); j++) {
            if (!isGuessMatchUp(possibleGuesses.get(j), latestResult.getGuess(),
                    latestResult.getBullCount(), latestResult.getCowCount())) {
                possibleGuesses.remove(j);
                j--;
            }
        }
        return (possibleGuesses.size() > 0) ? possibleGuesses.get(getRandomDigit(0, possibleGuesses.size() - 1)) : null;
    }

    /**
     * generates all possible combitnations of 4- digits from 0 to 9
     * The digits must be all different.
     */
    private void genAllPossibleGuesses() {
        for (int d1 = 0; d1 <= 9; d1++) {
            for (int d2 = 0; d2 <= 9; d2++) {
                if (d1 == d2) continue;
                for (int d3 = 0; d3 <= 9; d3++) {
                    if (d3 == d1 || d3 == d2) continue;
                    for (int d4 = 1; d4 <= 9; d4++) {
                        if (d4 == d1 || d4 == d2 || d4 == d3) continue;
                        StringBuilder candidate = new StringBuilder().append(d1).append(d2).append(d3).append(d4);
                        possibleGuesses.add(candidate.toString());
                    }
                }
            }
        }
    }

    /**
     * check if possible guess match up same bulls and cows with target
     *
     * @param target target guess
     * @param guess  possible guess
     * @param bulls  target bulls number
     * @param cows   target cows number
     * @return is candidate match up same bulls and cows with target
     */
    private boolean isGuessMatchUp(String target, String guess, int bulls, int cows) {
        int i = 0;
        while (i < guess.length()) {
            int index = target.indexOf(guess.charAt(i));
            if (index == i) bulls--;
            else if (index >= 0) cows--;
            i++;
        }
        return (bulls == 0) && (cows == 0);
    }

}
