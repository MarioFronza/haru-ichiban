package br.udesc.ppr55.hi.model.builder;

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
