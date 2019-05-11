package br.udesc.ppr55.hi.view.command;


public interface Command {

    void execute();

    void undo();

    void redo();

}
