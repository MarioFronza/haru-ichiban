package br.udesc.ppr55.hi.model.builder;

import br.udesc.ppr55.hi.model.Piece;
import br.udesc.ppr55.hi.model.abstractfactory.AbstractPieceFactory;

/**
 * Builder game table class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 28/04/2019
 */
public class BuildGameTable extends Builder {

    @Override
    public void buildTable(AbstractPieceFactory factory) {
        tablePiece = new Piece[5][5];

        tablePiece[0][0] = factory.createWaterLily();
        tablePiece[0][1] = factory.createWater();
        tablePiece[0][2] = factory.createWaterLily();
        tablePiece[0][3] = factory.createWater();
        tablePiece[0][4] = factory.createWaterLily();

        tablePiece[1][0] = factory.createWater();
        tablePiece[1][1] = factory.createWaterLily();
        tablePiece[1][2] = factory.createYellowFrog();
        tablePiece[1][3] = factory.createDarkWaterLily(true);
        tablePiece[1][4] = factory.createWater();

        tablePiece[2][0] = factory.createWaterLily();
        tablePiece[2][1] = factory.createWaterLily();
        tablePiece[2][2] = factory.createWater();
        tablePiece[2][3] = factory.createWaterLily();
        tablePiece[2][4] = factory.createWaterLily();

        tablePiece[3][0] = factory.createWater();
        tablePiece[3][1] = factory.createWaterLily();
        tablePiece[3][2] = factory.createWaterLily();
        tablePiece[3][3] = factory.createRedFrog();
        tablePiece[3][4] = factory.createWater();

        tablePiece[4][0] = factory.createWaterLily();
        tablePiece[4][1] = factory.createWater();
        tablePiece[4][2] = factory.createWaterLily();
        tablePiece[4][3] = factory.createWater();
        tablePiece[4][4] = factory.createWaterLily();

        super.table.setGrid(tablePiece);
    }
}
