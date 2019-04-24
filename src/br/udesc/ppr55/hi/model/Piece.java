package br.udesc.ppr55.hi.model;

/**
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 */
public abstract class Piece {

    private String image;

    public Piece(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
