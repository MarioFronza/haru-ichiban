package br.udesc.ppr55.hi.model.abstractfactory;

import br.udesc.ppr55.hi.model.*;

/**
 * Piece factory class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 19/04/2019
 * @version 1.0.0
 */
public class PieceFactory extends AbstractPieceFactory {

    @Override
    public Water createWater() {
        return new Water();
    }

    @Override
    public WaterLily createWaterLily() {
        return new WaterLily();
    }

    @Override
    public Stone createStone(int number) {
        return new Stone(number);
    }

    @Override
    public RedGardener createRedGardener() {
        return new RedGardener();
    }

    @Override
    public YellowGardener createYellowGardener() {
        return new YellowGardener();
    }

    @Override
    public RedFrog createRedFrog() {
        return new RedFrog();
    }

    @Override
    public YellowFrog createYellowFrog() {
        return new YellowFrog();
    }

    @Override
    public DarkWaterLily createDarkWaterLily() {
        return new DarkWaterLily();
    }

    @Override
    public RedFlower createRedFlower(int number) {
        return new RedFlower(number);
    }

    @Override
    public YellowFlower createYellowFlower(int number) {
        return new YellowFlower(number);
    }


}