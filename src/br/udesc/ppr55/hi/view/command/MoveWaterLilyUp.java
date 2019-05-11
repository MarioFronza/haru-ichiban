package br.udesc.ppr55.hi.view.command;

import br.udesc.ppr55.hi.controller.IHaruController;

public class MoveWaterLilyUp extends HaruCommand {

    public MoveWaterLilyUp(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void execute() {
        haruController.moveWaterLilyUp();
    }

}
