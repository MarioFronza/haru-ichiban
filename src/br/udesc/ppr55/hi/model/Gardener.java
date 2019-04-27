package br.udesc.ppr55.hi.model;

/**
 * @since 07/04/2019
 * @author João Pedro Schmitz, Mário Fronza
 */
public class Gardener extends Piece{

    private boolean isJunior;
    private String name;

    public Gardener() {
        super("images/Stone.png");
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

