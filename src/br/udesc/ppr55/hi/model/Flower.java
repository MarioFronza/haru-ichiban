package br.udesc.ppr55.hi.model;

/**
 * Flower class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 11/05/2019
 */
public abstract class Flower extends Piece {

    protected int number;
    private boolean isOriginalDarkWaterLily = false;
    private boolean isOriginalRedEggWaterLily = false;
    private boolean isOriginalYellowEggWaterLily = false;

    public boolean isOriginalDarkWaterLily() {
        return isOriginalDarkWaterLily;
    }

    public boolean isOriginalRedEggWaterLily() {
        return isOriginalRedEggWaterLily;
    }

    public boolean isOriginalYellowEggWaterLily() {
        return isOriginalYellowEggWaterLily;
    }

    public void setOriginalDarkWaterLily(boolean originalDarkWaterLily) {
        isOriginalDarkWaterLily = originalDarkWaterLily;
    }

    public void setOriginalRedEggWaterLily(boolean originalRedEggWaterLily) {
        isOriginalRedEggWaterLily = originalRedEggWaterLily;
    }

    public void setOriginalYellowEggWaterLily(boolean originalYellowEggWaterLily) {
        isOriginalYellowEggWaterLily = originalYellowEggWaterLily;
    }

    public Flower(String image) {
        super(image);
    }

    public int getNumber() {
        return number;
    }

}
