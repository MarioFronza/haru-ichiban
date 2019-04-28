package br.udesc.ppr55.hi.model.abstractfactory;

import br.udesc.ppr55.hi.model.*;

public abstract class AbstractPieceFactory {

    public abstract Piece createWater();

    public abstract Piece createWaterLily();

    public abstract Piece createStone(int number);

    public abstract RedGardener createRedGardener();

    public abstract YellowGardener createYellowGardener();

    public abstract Piece createRedFrog();

    public abstract Piece createYellowFrog();

    public abstract Piece createDarkWaterLily();

    public abstract RedFlower createRedFlower(int number);

    public abstract YellowFlower createYellowFlower(int number);

}