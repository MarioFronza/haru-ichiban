package br.udesc.ppr55.hi.view.command;

import java.util.ArrayList;
import java.util.List;

/**
 * Command Invoker class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 14/04/2019
 */
public class CommandInvoker {

    private List<Command> commands = new ArrayList<>();

    public void add(Command comm) {
        commands.add(comm);
    }

    public void execute() {
        for (Command comm : commands) {
            comm.execute();
        }
        commands.clear();
    }

}
