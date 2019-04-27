package br.udesc.ppr55.hi.model.command;


public interface Command {

    void execute();

    void undo();

    void redo();

}
