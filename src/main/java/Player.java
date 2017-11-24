public class Player {
    private int playerX;
    private int playerY;
    private int[] position;
    private int playerScore;
    private int gold;
    private final String PLAYER_SYMBOL = "@";



    public Player() {
        playerX = 0;
        playerY = 0;
        gold = 0;
        playerScore = 0;
        position = new int[2];
    }

    public String getPlayerSymbol() {
        return PLAYER_SYMBOL;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getPlayerX() {
        return playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int playerY, int playerX) {
       position[0] = playerY;
       position[1] = playerX;
    }
}
