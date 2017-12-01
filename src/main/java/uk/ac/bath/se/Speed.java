package uk.ac.bath.se;

class Speed {

    private int speedX;
    private int speedY;
    private int speedQuantity;
    public static final int SPEED_OF_CHARACTER = 1;
    private final String SPEED_SYMBOL;
    private int speedBoostRemaining;

    public Speed() {
        speedX = 5;
        speedY = 5;
        speedQuantity = 1;
        SPEED_SYMBOL = "S";
        speedBoostRemaining = 0;
    }

    public int getSpeedBoostRemaining() {
        return speedBoostRemaining;
    }

    public void setSpeedBoostRemaining(int speedBoostRemaining) {
        this.speedBoostRemaining = speedBoostRemaining;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getSpeedQuantity() {
        return speedQuantity;
    }

    public void setSpeedQuantity(int speedQuantity) {
        this.speedQuantity = speedQuantity;
    }

    public String getSPEED_SYMBOL() {
        return SPEED_SYMBOL;
    }
}

