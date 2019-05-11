package br.udesc.ppr55.hi.view.command;

import br.udesc.ppr55.hi.controller.IHaruController;

public class ChooseFlower extends HaruCommand{

    public ChooseFlower(int x, int y, IHaruController haruController) {
        super(haruController);
        super.x = x;
        super.y = y;
    }

    @Override
    public void execute() {
        haruController.chooseFlower(x, y);
    }

}
