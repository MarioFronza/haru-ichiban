package br.udesc.ppr55.hi.model;

/**
 * Dark Water Lily class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 11/04/2019
 */
public class DarkWaterLily extends Piece {

    private boolean isOriginal = false;

    public boolean isOriginal() {
        return isOriginal;
    }


    public DarkWaterLily(boolean isOriginal) {
        super("images/water-lily-dark.png");
        this.isOriginal = isOriginal;
    }

    public DarkWaterLily() {
        super("images/water-lily-dark.png");
    }

}
