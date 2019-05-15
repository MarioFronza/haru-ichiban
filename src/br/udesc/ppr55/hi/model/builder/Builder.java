package br.udesc.ppr55.hi.model.builder;

import br.udesc.ppr55.hi.model.Piece;
import br.udesc.ppr55.hi.model.Table;
import br.udesc.ppr55.hi.model.abstractfactory.AbstractPieceFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 28/04/2019
 */
public abstract class Builder {

    protected Table table;
    protected List<Integer> numbers;
    protected Piece[][] tablePiece;

    public Builder() {
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

    public void buildTable(AbstractPieceFactory factory) {
        this.table.setGrid(tablePiece);
    }

}
