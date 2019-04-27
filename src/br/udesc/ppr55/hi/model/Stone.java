package br.udesc.ppr55.hi.model;

public class Stone extends Piece{

    private int number;

    public Stone(int number) {
        super("images/rock-"+number+".png");
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
