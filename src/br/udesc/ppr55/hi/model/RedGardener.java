package br.udesc.ppr55.hi.model;

/**
 * @since 07/04/2019
 * @author João Pedro Schmitz, Mário Fronza
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

