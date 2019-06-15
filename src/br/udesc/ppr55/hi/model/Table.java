package br.udesc.ppr55.hi.model;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.model.visitor.Visitor;

/**
 * Table class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 07/04/2019
 */
public class Table {

    private Piece[][] grid;

    public Piece[][] getGrid() {
        return grid;
    }

    public void setGrid(Piece[][] table) {
        this.grid = table;
    }

    public void accept(Visitor visitor) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j].accept(visitor);
                if (visitor.getPiece().getClass() == RedFlower.class || visitor.getPiece().getClass() == YellowFlower.class) {
                    Flower flower = (Flower) grid[i][j];
                    if (flower.isOriginalDarkWaterLily()) {
                        grid[i][j] = HaruController.getInstance().getFactory().createDarkWaterLily(true);
                    } else {
                        grid[i][j] = HaruController.getInstance().getFactory().createWaterLily();
                    }
                }
            }
        }
    }

}