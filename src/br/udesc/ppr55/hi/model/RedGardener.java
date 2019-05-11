package br.udesc.ppr55.hi.model;

/**
 * Red gardener class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 * @version 1.0.0
 */
public class RedGardener extends Piece{

    private boolean isJunior;
    private String name;

    public RedGardener() {
        super("images/gardener-pink.png");
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

