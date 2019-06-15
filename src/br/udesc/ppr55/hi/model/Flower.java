package br.udesc.ppr55.hi.model;

/**
 * Flower class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 11/05/2019
 * @version 1.0.0
 */
public abstract class Flower extends Piece {

    protected int number;
    private boolean isOriginalDarkWaterLily = false;

    public boolean isOriginalDarkWaterLily() {
        return isOriginalDarkWaterLily;
    }

    public void setOriginalDarkWaterLily(boolean originalDarkWaterLily) {
        isOriginalDarkWaterLily = originalDarkWaterLily;
    }

    public Flower(String image) {
        super(image);
    }

    public int getNumber() {
        return number;
    }
    
}
