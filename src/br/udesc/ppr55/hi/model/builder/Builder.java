package br.udesc.ppr55.hi.model.builder;

import br.udesc.ppr55.hi.model.Piece;
import br.udesc.ppr55.hi.model.Table;
import br.udesc.ppr55.hi.model.abstractfactory.AbstractPieceFactory;
import br.udesc.ppr55.hi.model.abstractfactory.PieceFactory;

public abstract class Builder {

    private Table table;
    protected AbstractPieceFactory factory;
    protected Piece[][] tablePiece;

    public Builder() {
        this.factory = new PieceFactory();
    }

    public Table getTable(){
        return table;
    }

    public void reset(){
        this.table = new Table();
    }

    public void buildTable(){
        this.table.setGrid(tablePiece);
    }

}
