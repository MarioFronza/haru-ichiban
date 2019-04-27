package br.udesc.ppr55.hi.model;

/**
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 */
public class YellowFlower extends Piece {

    private int number;

    public YellowFlower(int number) {
        super("images/petal-yellow.png");
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
