package br.udesc.ppr55.hi.model.abstractfactory;

import br.udesc.ppr55.hi.model.*;

/**
 * Abstract piece factory class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 19/04/2019
 * @version 1.0.0
 */
public abstract class AbstractPieceFactory {

    public abstract Piece createWater();

    public abstract Piece createWaterLily();

    public abstract Piece createStone(int number);

    public abstract Piece createStoneWithoutNumber();

    public abstract Piece createRedGardener();

    public abstract Piece createYellowGardener();

    public abstract Piece createRedFrog(boolean contaisnEgg);

    public abstract Piece createYellowFrog(boolean contaisnEgg);

    public abstract Piece createDarkWaterLily(boolean isOriginal);

    public abstract Piece createRedFlower(int number);

    public abstract Piece createYellowFlower(int number);

}