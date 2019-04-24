package br.udesc.ppr55.hi.controller.command;

import br.udesc.ppr55.hi.controller.IHaruController;

public class MoveWaterLilyRight extends HaruCommand{

    public MoveWaterLilyRight(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void execute() {
        haruController.moveWaterLilyRight();
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
