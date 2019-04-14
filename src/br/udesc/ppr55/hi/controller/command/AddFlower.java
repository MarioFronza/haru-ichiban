package br.udesc.ppr55.hi.controller.command;

import br.udesc.ppr55.hi.controller.IHaruController;

public class AddFlower extends HaruCommand {


    public AddFlower(int x, int y, IHaruController haruController) {
        super(x, y, haruController);
    }

    @Override
    public void execute() {
        haruController.addFlower(x, y);
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
