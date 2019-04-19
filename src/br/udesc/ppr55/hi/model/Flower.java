package br.udesc.ppr55.hi.model;

/**
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 */
public class Flower extends Piece {

    private int number;

    public Flower(int number) {
        super("images/Flower.png");
        this.number = number;
    }

}
