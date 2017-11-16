public class Player {
    private int playerX;
    private int playerY;
    private int playerScore;
    private int gold;
    private String playerSymbol;

    public Player() {
        playerX = 0;
        playerY = 0;
        playerScore = 0;
        playerSymbol = "*";
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

    private void movePlayer(int x, int y) {

    }

    
}
