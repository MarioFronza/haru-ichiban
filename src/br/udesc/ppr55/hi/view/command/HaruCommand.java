package br.udesc.ppr55.hi.view.command;


import br.udesc.ppr55.hi.controller.IHaruController;

public abstract class HaruCommand implements Command {

    protected IHaruController haruController;
    protected int x;
    protected int y;

    public HaruCommand(IHaruController haruController) {
        this.haruController = haruController;
    }
}
