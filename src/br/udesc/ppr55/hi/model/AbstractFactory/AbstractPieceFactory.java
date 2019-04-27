package br.udesc.ppr55.hi.model.AbstractFactory;

import br.udesc.ppr55.hi.model.Gardener;
import br.udesc.ppr55.hi.model.Piece;

public abstract class AbstractPieceFactory {

    public abstract Piece createWater();

    public abstract Piece createWaterLily();

    public abstract Piece createStone(int number);

    public abstract Gardener createGardener();

    public abstract Piece createRedFrog();

    public abstract Piece createYellowFrog();

    public abstract Piece createDarkWaterLily();

    public abstract Piece createFlower(int number);

}