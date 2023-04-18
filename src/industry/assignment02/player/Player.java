package industry.assignment02.player;

public class Player extends Role {
    /**
     * Returns the type of the player.
     *
     * @return type of player.
     */
    @Override
    public RoleType getType() {
        return RoleType.PLAYER;
    }

    /**
     * set up player secret code.
     */
    @Override
    public void processSecretCodeSetting(String playerSecretCode) {
        setSecretCode(playerSecretCode);
    }

}
