package br.udesc.ppr55.hi.model.abstractfactory;

import br.udesc.ppr55.hi.model.*;
import br.udesc.ppr55.hi.model.decorator.RedFrog;
import br.udesc.ppr55.hi.model.decorator.YellowFrog;

/**
 * Piece factory class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 19/04/2019
 */
public class PieceFactory extends AbstractPieceFactory {

    @Override
    public Piece createWater() {
        return new Water();
    }

    @Override
    public Piece createWaterLily() {
        return new WaterLilyComponent(false);
    }

    @Override
    public Piece createStone(int number) {
        return new Stone(number);
    }

    @Override
    public Piece createStoneWithoutNumber() {
        return new Stone();
    }

    @Override
    public Piece createRedGardener() {
        return new RedGardener();
    }

    @Override
    public Piece createYellowGardener() {
        return new YellowGardener();
    }

    @Override
    public Piece createRedFrog(boolean contaisnEgg) {
        return new RedFrog(new WaterLilyComponent(contaisnEgg));
    }

    @Override
    public Piece createYellowFrog(boolean contaisnEgg) {
        return new YellowFrog(new WaterLilyComponent(contaisnEgg));
    }

    @Override
    public Piece createDarkWaterLily(boolean isOriginal) {
        return new DarkWaterLily(isOriginal);
    }

    @Override
    public Piece createRedFlower(int number) {
        return new RedFlower(number);
    }

    @Override
    public Piece createYellowFlower(int number) {
        return new YellowFlower(number);
    }


}