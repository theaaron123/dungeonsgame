package uk.ac.bath.se;

class Chest {

private int xCoord;
private int yCoord;
    private static int chestQuantity = 1;
    public static final String CHEST_SYMBOL = "C";

    public Chest() {
        xCoord = 6;
        yCoord = 6;
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

    public static int getChestQuantity() {
        return chestQuantity;
    }

    public static void setChestQuantity(int chestNumber) {
        chestQuantity = chestNumber;
    }

}
