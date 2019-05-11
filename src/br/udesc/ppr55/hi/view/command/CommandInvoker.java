package br.udesc.ppr55.hi.view.command;

import java.util.ArrayList;
import java.util.List;

public class CommandInvoker {

    private List<Command> imediatos = new ArrayList<>();
    private List<Command> todos = new ArrayList<>();

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


}
