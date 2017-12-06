package uk.ac.bath.se;

class Gold {
    private int xCoord;
    private int yCoord;
    private int quantity;
    private final String GOLD_SYMBOL;
    public static final int LOCATION = 2;

    public Gold() {
        xCoord = 0;
        yCoord = 0;
        quantity = 5;
        GOLD_SYMBOL = "G";
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getGoldSymbol() {
        return GOLD_SYMBOL;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

}
