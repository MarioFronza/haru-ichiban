package br.udesc.ppr55.hi.controller.command;

import br.udesc.ppr55.hi.controller.IHaruController;

public class ChooseFlower extends HaruCommand{

    public ChooseFlower(int x, int y, IHaruController haruController) {
        super(x, y, haruController);
    }

    @Override
    public void execute() {
        haruController.chooseFlower(x, y);
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
