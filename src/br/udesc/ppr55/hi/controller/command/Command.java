package br.udesc.ppr55.hi.controller.command;


public interface Command {

    void execute();

    void undo();

    void redo();

}
