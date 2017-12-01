package uk.ac.bath.se;

public class Chest {

private int chestX;
private int chestY;
private int chestQuantity;
private final String CHEST_SYMBOL;

public Chest () {
    chestX = 6;
    chestY= 6;
    chestQuantity = 0;
    CHEST_SYMBOL = "C";
}

    public int getChestX() {
        return chestX;
    }

    public void setChestX(int chestX) {
        this.chestX = chestX;
    }

    public int getChestY() {
        return chestY;
    }

    public void setChestY(int chestY) {
        this.chestY = chestY;
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
