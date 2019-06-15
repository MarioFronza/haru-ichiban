package br.udesc.ppr55.hi.model;

/**
 * Dark Water Lily class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 11/04/2019
 * @version 1.0.0
 */
public class DarkWaterLily extends Piece {

    private boolean isOriginal = false;

    public boolean isOriginal() {
        return isOriginal;
    }

    public void setOriginal(boolean original) {
        isOriginal = original;
    }

    public DarkWaterLily() {
        super("images/water-lily-dark.png");
    }

}
