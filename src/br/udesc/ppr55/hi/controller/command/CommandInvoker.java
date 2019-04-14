package br.udesc.ppr55.hi.controller.command;
import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {


    private List<Command> imediatos = new ArrayList<>();

    private List<Command> todos = new ArrayList<>();

    private List<Command> undo = new ArrayList<>();

    public void add(Command comm) {
        imediatos.add(comm);
    }

    public void execute() {

        for (Command comm : imediatos) {
            comm.execute();
            todos.add(comm);
        }

        imediatos.clear();

    }

    public void undo() {

        Command comm = todos.remove(todos.size() - 1);
        comm.undo();
        undo.add(comm);

    }

    public void redo() {

        Command comm = undo.remove(undo.size() - 1);
        comm.redo();
        todos.add(comm);

    }

    public void resetUndo() {
        undo = new ArrayList<>();
    }

}
