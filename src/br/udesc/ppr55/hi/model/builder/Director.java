package br.udesc.ppr55.hi.model.builder;

/**
 * Director class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 28/04/2019
 * @version 1.0.0
 */
public class Director {

    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void build() {
        builder.reset();
        builder.buildTable();
    }
}
