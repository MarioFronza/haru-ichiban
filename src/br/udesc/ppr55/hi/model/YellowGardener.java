package br.udesc.ppr55.hi.model;

/**
 * Yellow gardener class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 * @version 1.0.0
 */
public class YellowGardener extends Piece {

    private boolean isJunior;
    private String name;

    public YellowGardener() {
        super("images/gardener-yellow.png");
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
