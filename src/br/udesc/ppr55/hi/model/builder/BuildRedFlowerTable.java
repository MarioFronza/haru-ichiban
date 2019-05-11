package br.udesc.ppr55.hi.model.builder;

import br.udesc.ppr55.hi.model.Piece;

import java.util.Collections;

/**
 * Builder red flower table class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 28/04/2019
 * @version 1.0.0
 */
public class BuildRedFlowerTable extends Builder {

    public BuildRedFlowerTable() {
        tablePiece = new Piece[5][2];
        Collections.shuffle(numbers);

        tablePiece[0][0] = factory.createRedFlower(numbers.get(0));
        tablePiece[0][1] = factory.createRedFlower(numbers.get(1));
        tablePiece[1][0] = factory.createRedFlower(numbers.get(2));
        tablePiece[1][1] = factory.createRedFlower(numbers.get(3));
        tablePiece[2][0] = factory.createRedFlower(numbers.get(4));
        tablePiece[2][1] = factory.createRedFlower(numbers.get(5));
        tablePiece[3][0] = factory.createRedFlower(numbers.get(6));
        tablePiece[3][1] = factory.createRedFlower(numbers.get(7));
        tablePiece[4][0] = factory.createRedGardener();
        tablePiece[4][1] = factory.createYellowGardener();

    }

    @Override
    public void buildTable() {
        super.buildTable();
    }
}
