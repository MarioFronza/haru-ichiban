package br.udesc.ppr55.hi.model.builder;

import br.udesc.ppr55.hi.model.Piece;

public class BuildRedFlowerTable extends Builder {

    public BuildRedFlowerTable() {
        tablePiece = new Piece[5][2];

        tablePiece[0][0] = factory.createRedFlower(1);
        tablePiece[0][1] = factory.createRedFlower(2);
        tablePiece[1][0] = factory.createRedFlower(3);
        tablePiece[1][1] = factory.createRedFlower(4);
        tablePiece[2][0] = factory.createRedFlower(5);
        tablePiece[2][1] = factory.createRedFlower(6);
        tablePiece[3][0] = factory.createRedFlower(7);
        tablePiece[3][1] = factory.createRedFlower(8);
        tablePiece[4][0] = factory.createRedGardener();
        tablePiece[4][1] = factory.createYellowGardener();
    }

    @Override
    public void buildTable() {
        super.buildTable();
    }
}
