package br.udesc.ppr55.hi.view.command;

import br.udesc.ppr55.hi.controller.IHaruController;

public class AddFlower extends HaruCommand {


    public AddFlower(int x, int y, IHaruController haruController) {
        super(haruController);
        super.x = x;
        super.y = y;
    }

    @Override
    public void execute() {
        haruController.addFlower(x, y);
    }

}
