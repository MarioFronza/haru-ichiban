package br.udesc.ppr55.hi.model.abstractfactory;

import br.udesc.ppr55.hi.model.*;
import br.udesc.ppr55.hi.model.decorator.RedFrogDecorator;
import br.udesc.ppr55.hi.model.decorator.WaterLilyDecorator;
import br.udesc.ppr55.hi.model.decorator.YellowFrogDecorator;

/**
 * Piece factory class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 19/04/2019
 * @version 1.0.0
 */
public class PieceFactory extends AbstractPieceFactory {

    @Override
    public Piece createWater() {
        return new Water();
    }

    @Override
    public Piece createWaterLily() {
        return new WaterLilyComponent();
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
    public Piece createRedFrog() {
        return new RedFrogDecorator(new WaterLilyComponent());
    }

    @Override
    public Piece createYellowFrog() {
        return new YellowFrogDecorator(new WaterLilyComponent());
    }

    @Override
    public Piece createDarkWaterLily() {
        return new DarkWaterLily();
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