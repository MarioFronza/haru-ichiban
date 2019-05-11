package br.udesc.ppr55.hi.model.builder;

import br.udesc.ppr55.hi.model.Piece;

public class BuildScorePanel extends Builder {

    public BuildScorePanel() {
        tablePiece = new Piece[10][1];

        tablePiece[0][0] = factory.createStone(1);
        tablePiece[1][0] = factory.createStone(2);
        tablePiece[2][0] = factory.createStone(3);
        tablePiece[3][0] = factory.createStone(4);
        tablePiece[4][0] = factory.createStone(5);
        tablePiece[5][0] = factory.createStone(4);
        tablePiece[6][0] = factory.createStone(3);
        tablePiece[7][0] = factory.createStone(2);
        tablePiece[8][0] = factory.createStone(1);
        tablePiece[9][0] = factory.createStone(0);
    }

    @Override
    public void buildTable() {
        super.buildTable();
    }
}
