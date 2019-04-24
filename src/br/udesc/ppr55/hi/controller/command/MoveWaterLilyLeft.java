package br.udesc.ppr55.hi.controller.command;

import br.udesc.ppr55.hi.controller.IHaruController;

public class MoveWaterLilyLeft extends HaruCommand{

    public MoveWaterLilyLeft(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void execute() {
        haruController.moveWaterLilyLeft();
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

}
