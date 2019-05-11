package br.udesc.ppr55.hi.model;

/**
 * Gardener class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 11/05/2019
 * @version 1.0.0
 */
public abstract class Gardener extends Piece {

    protected boolean isJunior;
    protected String name;

    public Gardener(String image) {
        super(image);
    }

    public boolean isJunior() {
        return isJunior;
    }

    public void setJunior(boolean junior) {
        isJunior = junior;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
