package br.udesc.ppr55.hi.view.command;

import br.udesc.ppr55.hi.controller.IHaruController;

/**
 * Command abstract class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 14/04/2019
 * @version 1.0.0
 */
public abstract class HaruCommand implements Command {

    protected IHaruController haruController;
    protected int x;
    protected int y;

    public HaruCommand(IHaruController haruController) {
        this.haruController = haruController;
    }
}
