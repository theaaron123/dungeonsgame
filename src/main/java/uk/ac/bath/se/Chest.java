package uk.ac.bath.se;

class Chest {

private int xCoord;
private int yCoord;
private int chestQuantity;
private final String CHEST_SYMBOL;

public Chest () {
    xCoord = 6;
    yCoord = 6;
    chestQuantity = 0;
    CHEST_SYMBOL = "C";
}

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getChestQuantity() {
        return chestQuantity;
    }

    public void setChestQuantity(int chestQuantity) {
        this.chestQuantity = chestQuantity;
    }

    public String getCHEST_SYMBOL() {
        return CHEST_SYMBOL;
    }
}
