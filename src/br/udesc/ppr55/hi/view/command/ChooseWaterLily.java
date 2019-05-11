package br.udesc.ppr55.hi.view.command;

import br.udesc.ppr55.hi.controller.IHaruController;

public class ChooseWaterLily extends HaruCommand {

    public ChooseWaterLily(int x, int y, IHaruController haruController) {
        super(haruController);
        super.x = x;
        super.y = y;
    }

    @Override
    public void execute() {
        haruController.chooseWaterLily(x, y);
    }

}
