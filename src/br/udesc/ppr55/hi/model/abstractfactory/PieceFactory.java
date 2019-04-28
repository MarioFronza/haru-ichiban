package br.udesc.ppr55.hi.model.abstractfactory;

import br.udesc.ppr55.hi.model.*;

public class PieceFactory extends AbstractPieceFactory {

    @Override
    public Piece createWater() {
        return new Water();
    }

    @Override
    public Piece createWaterLily() {
        return new WaterLily();
    }

    @Override
    public Piece createStone(int number) {
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
    public Piece createRedFrog() {
        return new RedFrog();
    }

    @Override
    public Piece createYellowFrog() {
        return new YellowFrog();
    }

    @Override
    public Piece createDarkWaterLily() {
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