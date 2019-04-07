package br.udesc.ppr55.hi.model;

/**
 * @since 07/04/2019
 * @author João Pedro Schmitz, Mário Fronza
 */
public abstract class Piece {

    private String image;

    public Piece(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

}
