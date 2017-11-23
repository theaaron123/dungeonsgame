public class Gold {
    private int goldX;
    private int goldY;
    private int goldQuantity;
    private final String GOLD_SYMBOL;

    public Gold() {
        goldX = 0;
        goldY = 0;
        goldQuantity = 5;
        GOLD_SYMBOL = "G";
    }

    public String getGoldSymbol() {
        return GOLD_SYMBOL;
    }

    public int getGoldX()
    {
        return goldX;
    }
    public int getGoldY()
    {
        return goldY;
    }
    public int getGoldQuantity()
    {
        return goldQuantity;
    }

    public void setGoldQuantity(int goldQuantity) {
        this.goldQuantity = goldQuantity;
    }

    public void setGoldX(int goldX) {
        this.goldX = goldX;
    }

    public void setGoldY(int goldY) {
        this.goldY = goldY;
    }
}
