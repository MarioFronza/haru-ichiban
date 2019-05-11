package br.udesc.ppr55.hi.view.command;

import br.udesc.ppr55.hi.controller.IHaruController;

public class ChooseWaterLily extends HaruCommand{

    private int x;
    private int y;

    public ChooseWaterLily(IHaruController haruController) {
        super(haruController);
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        haruController.chooseWaterLily(x, y);
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
