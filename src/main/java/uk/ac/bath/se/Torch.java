package uk.ac.bath.se;

public class Torch {

    private int torchX;
    private int torchY;
    private int torchQuantity;
    private final String TORCH_SYMBOL;

    public Torch() {
        torchX = 5;
        torchY = 5;
        torchQuantity = 1;
        TORCH_SYMBOL = "T";
    }

    public int getTorchX() {
        return torchX;
    }

    public void setTorchX(int torchX) {
        this.torchX = torchX;
    }

    public int getTorchY() {
        return torchY;
    }

    public void setTorchY(int torchY) {
        this.torchY = torchY;
    }

    public int getTorchQuantity() {
        return torchQuantity;
    }

    public void setTorchQuantity(int torchQuantity) {
        this.torchQuantity = torchQuantity;
    }

    public String getTORCH_SYMBOL() {
        return TORCH_SYMBOL;
    }
}

