package br.udesc.ppr55.hi.model;

import br.udesc.ppr55.hi.model.visitor.Visitor;

/**
 * Piece class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 * @version 1.0.0
 */
public abstract class Piece {

    private String image;
    
    public Piece() {
    	
    }

    public Piece(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void accept(Visitor visitor) {
        visitor.visitPiece(this);
    }
}
