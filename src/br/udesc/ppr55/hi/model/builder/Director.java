package br.udesc.ppr55.hi.model.builder;

import br.udesc.ppr55.hi.model.abstractfactory.AbstractPieceFactory;

/**
 * Director class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 28/04/2019
 */
public class Director {

    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void build(AbstractPieceFactory abstractPieceFactory) {
        builder.reset();
        builder.buildTable(abstractPieceFactory);
    }
}
