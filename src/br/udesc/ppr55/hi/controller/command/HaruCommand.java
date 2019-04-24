package br.udesc.ppr55.hi.controller.command;


import br.udesc.ppr55.hi.controller.IHaruController;

public abstract class HaruCommand implements Command{

//    protected int x;
//    protected int y;
    protected IHaruController haruController;

    public HaruCommand( IHaruController haruController) {
        this.haruController = haruController;
    }
}
