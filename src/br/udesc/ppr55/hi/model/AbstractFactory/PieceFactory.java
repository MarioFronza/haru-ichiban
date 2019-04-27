package br.udesc.ppr55.hi.model.AbstractFactory;

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
    public Gardener createGardener() {
        return new Gardener();
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
    public Piece createFlower(int number) {
        return new RedFlower(number);
    }
}