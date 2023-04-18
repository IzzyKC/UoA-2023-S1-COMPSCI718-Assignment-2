package industry.assignment02.player;

public abstract class Role {
    private String secretCode;

    /**
     * Returns the value of secretCode.
     *
     * @return secretCode.
     */
    public String getSecretCode() {
        return secretCode;
    }

    /**
     * set the value of secretCode
     *
     * @param secretCode the value of secret code
     */
    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    /**
     * Returns the type of the role.
     *
     * @return type of role.
     */
    public abstract RoleType getType();

    /**
     * set secret code.
     */
    public abstract void processSecretCodeSetting(String secretCode);


}
