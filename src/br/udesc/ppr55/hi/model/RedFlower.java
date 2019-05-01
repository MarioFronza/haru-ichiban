package br.udesc.ppr55.hi.model;

/**
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 */
public class RedFlower extends Piece {

    private int number;

    public RedFlower(int number) {
        super("images/petal-red.png");
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
