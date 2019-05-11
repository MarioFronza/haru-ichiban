package br.udesc.ppr55.hi.model.abstractfactory;

import br.udesc.ppr55.hi.model.*;

public abstract class AbstractPieceFactory {

    public abstract Piece createWater();

    public abstract Piece createWaterLily();

    public abstract Piece createStone(int number);

    public abstract Piece createRedGardener();

    public abstract Piece createYellowGardener();

    public abstract Piece createRedFrog();

    public abstract Piece createYellowFrog();

    public abstract Piece createDarkWaterLily();

    public abstract Piece createRedFlower(int number);

    public abstract Piece createYellowFlower(int number);

}