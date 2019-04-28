package br.udesc.ppr55.hi.model.builder;

import br.udesc.ppr55.hi.model.Piece;

public class BuildYellowFlowerTable extends Builder {

    public BuildYellowFlowerTable() {
        tablePiece = new Piece[5][2];

        tablePiece[0][0] = factory.createYellowFlower(1);
        tablePiece[0][1] = factory.createYellowFlower(2);
        tablePiece[1][0] = factory.createYellowFlower(3);
        tablePiece[1][1] = factory.createYellowFlower(4);
        tablePiece[2][0] = factory.createYellowFlower(5);
        tablePiece[2][1] = factory.createYellowFlower(6);
        tablePiece[3][0] = factory.createYellowFlower(7);
        tablePiece[3][1] = factory.createYellowFlower(8);
        tablePiece[4][0] = factory.createRedGardener();
        tablePiece[4][1] = factory.createYellowGardener();
    }

    @Override
    public void buildTable() {
        super.buildTable();
    }
}
