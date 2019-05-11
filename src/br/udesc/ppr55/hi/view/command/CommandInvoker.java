package br.udesc.ppr55.hi.view.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Command Invoker class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 14/04/2019
 * @version 1.0.0
 */
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
