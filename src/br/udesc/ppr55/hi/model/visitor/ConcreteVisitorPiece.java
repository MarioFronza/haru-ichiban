package br.udesc.ppr55.hi.model.visitor;

import br.udesc.ppr55.hi.model.*;

public class ConcreteVisitorPiece implements Visitor {
//
//    int redCount = 0;
//    int yellowCount = 0;

    private Piece currentPiece;

    @Override
    public void visitPiece(Piece piece) {
        this.currentPiece = piece;
//        if (piece.getClass() == RedFlower.class) {
//            redCount++;
//            if (redCount >= 4)
//                System.out.println("Verificando ganhador vermelho");
//        }
//
//        if (piece.getClass() == YellowFlower.class) {
//            yellowCount++;
//            if (yellowCount >= 4)
//                System.out.println("Verificando ganhador amarelo");
//        }
    }

    @Override
    public Piece getPiece() {
        return currentPiece;
    }


}