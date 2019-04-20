package br.udesc.ppr55.hi.model;

/**
 * @since 07/04/2019
 * @author João Pedro Schmitz, Mário Fronza
 */
public class Gardener extends Piece{

    private boolean isJunior;

    public Gardener() {
        super("images/Gardener.png");
    }

    public boolean isJunior() {
        return isJunior;
    }

    public void setJunior(boolean junior) {
        isJunior = junior;
    }
}
