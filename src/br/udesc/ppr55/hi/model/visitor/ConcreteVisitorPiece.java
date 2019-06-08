package br.udesc.ppr55.hi.model.visitor;

import br.udesc.ppr55.hi.model.*;

public class ConcreteVisitorPiece implements Visitor {

    private Piece currentPiece;

    @Override
    public void visitPiece(Piece piece) {
        this.currentPiece = piece;
    }

    @Override
    public Piece getPiece() {
        return currentPiece;
    }


}