package industry.assignment02.player;

public class Computer extends Role {
    private AILevel level;

    public Computer(AILevel level) {
        this.level = level;
    }

    public AILevel getLevel() {
        return level;
    }

    public void setLevel(AILevel level) {
        this.level = level;
    }

    /**
     * Returns the type of the computer.
     *
     * @return type of computer.
     */
    @Override
    public RoleType getType() {
        return RoleType.COMPUTER;
    }

    /**
     * set up computer secret code.
     * if the input of secretCode is null, then generate a random code
     * The code is 4 digits from 0 – 9 and non-repetitive
     */
    @Override
    public void processSecretCodeSetting(String computerSecretCode) {
        if (computerSecretCode == null || computerSecretCode.isEmpty()) {
            computerSecretCode = genRandomCode(4, false);
        }
        setSecretCode(computerSecretCode);
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

    public String guessPlayerSecretCode() {
        return null;
    }

    private String guessWithEasyAI() {
        return null;
    }

    private String guessWithMediumAI() {
        return null;
    }

    private String guessWithHardAI() {
        return null;
    }
}
