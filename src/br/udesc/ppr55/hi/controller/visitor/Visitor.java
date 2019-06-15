package br.udesc.ppr55.hi.controller.visitor;

import br.udesc.ppr55.hi.model.Piece;

public interface Visitor {

    void visitPiece(Piece piece);

    Piece getPiece();

}