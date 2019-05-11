package br.udesc.ppr55.hi.model.builder;

import br.udesc.ppr55.hi.model.Piece;
import br.udesc.ppr55.hi.model.Table;
import br.udesc.ppr55.hi.model.abstractfactory.AbstractPieceFactory;
import br.udesc.ppr55.hi.model.abstractfactory.PieceFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 28/04/2019
 * @version 1.0.0
 */
public abstract class Builder {

    private Table table;
    protected List<Integer> numbers;
    protected AbstractPieceFactory factory;
    protected Piece[][] tablePiece;

    public Builder() {
        this.factory = new PieceFactory();
        this.numbers = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            numbers.add(i);
        }
    }

    public Table getTable() {
        return table;
    }

    public void reset() {
        this.table = new Table();
    }

    public void buildTable() {
        this.table.setGrid(tablePiece);
    }

}
