package br.udesc.ppr55.hi.model;

/**
 * Table class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 * @version 1.0.0
 */
public class Table {

    private Piece[][] grid;

    public Piece[][] getGrid() {
        return grid;
    }

    public void setGrid(Piece[][] table) {
        this.grid = table;
    }
}
