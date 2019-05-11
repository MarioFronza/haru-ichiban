package br.udesc.ppr55.hi.view.command;

import br.udesc.ppr55.hi.controller.IHaruController;

public class MoveWaterLilyLeft extends HaruCommand {

    public MoveWaterLilyLeft(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void execute() {
        haruController.moveWaterLilyLeft();
    }

}
