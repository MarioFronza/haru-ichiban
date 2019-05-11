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

    public abstract Water createWater();

    public abstract WaterLily createWaterLily();

    public abstract Stone createStone(int number);

    public abstract RedGardener createRedGardener();

    public abstract YellowGardener createYellowGardener();

    public abstract RedFrog createRedFrog();

    public abstract YellowFrog createYellowFrog();

    public abstract DarkWaterLily createDarkWaterLily();

    public abstract RedFlower createRedFlower(int number);

    public abstract YellowFlower createYellowFlower(int number);

}