package br.udesc.ppr55.hi.model.AbstractFactory;

import br.udesc.ppr55.hi.model.Piece;

public abstract class AbstractPieceFactory {

    public abstract Piece createWater();

    public abstract Piece createWaterLily();

    public abstract Piece createStone(int number);

    public abstract Piece createGardener();

    public abstract Piece createFrog();

    public abstract Piece createFlower(int number);

}
