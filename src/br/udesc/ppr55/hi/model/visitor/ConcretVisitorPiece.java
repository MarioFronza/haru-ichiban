package br.udesc.ppr55.hi.model.visitor;

import br.udesc.ppr55.hi.model.Flower;
import br.udesc.ppr55.hi.model.Piece;
import br.udesc.ppr55.hi.model.RedFlower;
import br.udesc.ppr55.hi.model.YellowFlower;

public class ConcretVisitorPiece implements Visitor {

    int redCount = 0;
    int yellowCount = 0;

    @Override
    public void visitPiece(Piece piece) {
        if (piece.getClass() == RedFlower.class) {
            redCount++;
            if (redCount >= 4)
                System.out.println("Verificando ganhador vermelho");
        }

        if (piece.getClass() == YellowFlower.class) {
            yellowCount++;
            if (yellowCount >= 4)
                System.out.println("Verificando ganhador amarelo");
        }
    }

}