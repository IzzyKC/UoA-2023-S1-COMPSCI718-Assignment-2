package industry.assignment02.player;

public abstract class Computer{
    private AILevel aiLevel;
    private String computerCode;

    /**
     * Returns the value of secretCode.
     *
     * @return secretCode.
     */
    public String getComputerCode() {
        return computerCode;
    }

    /**
     * Returns the value of AILevel.
     *
     * @return AILevel.
     */
    public AILevel getAiLevel() {
        return aiLevel;
    }

    /**
     * set the value of AILevel
     *
     * @param aiLevel the value of AiLevel
     */
    public void setAiLevel(AILevel aiLevel) {
        this.aiLevel = aiLevel;
    }

    /**
     * generate random computer secret code automatically.
     * The code is 4 digits from 0 – 9 and non-repetitive
     */
    public void genComputerCode() {
        computerCode = genRandomCode(4, false);
    }

    /**
     * generate random digital code
     *
     * @param length             length of secretCode
     * @param allowRepeatedDigit code can include repeat digit or not
     */
    public String genRandomCode(int length, boolean allowRepeatedDigit) {
        String code = "";
        for (int i = 0; i < length; i++) {
            String digit = String.valueOf(getRandomDigit());
            while ((!allowRepeatedDigit && code.contains(digit))) {
                String newDigit = String.valueOf(getRandomDigit());
                if (!code.contains(newDigit)) {
                    digit = newDigit;
                    break;
                }
            }
            code += digit;
        }
        return code;
    }

    /**
     * return digit number from 0 to 9
     *
     * @return int from 0 to 9
     */
    private int getRandomDigit() {
        return (int) (Math.random() * 10);
    }

    public String genWordleCode(){
        return null;
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

    public abstract String guessPlayerCode();


}
