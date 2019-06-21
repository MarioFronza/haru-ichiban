package br.udesc.ppr55.hi.model;

/**
 * Water lily class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 07/04/2019
 */
public class WaterLily extends Piece {

    private boolean contaisnEgg;

    public WaterLily(boolean contaisnEgg) {
        super("");
        this.contaisnEgg = contaisnEgg;
    }

    public boolean isContaisnEgg() {
        return contaisnEgg;
    }
}
