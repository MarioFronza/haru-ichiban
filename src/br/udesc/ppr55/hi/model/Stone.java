package br.udesc.ppr55.hi.model;

/**
 * Stone class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 * @version 1.0.0
 */
public class Stone extends Piece {

    private int number;
    
    public Stone() {
        super("images/rock.png");
    }

    public Stone(int number) {
        super("images/rock-" + number + ".png");
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
