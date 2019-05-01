package br.udesc.ppr55.hi.model.abstractfactory;

import br.udesc.ppr55.hi.model.*;

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