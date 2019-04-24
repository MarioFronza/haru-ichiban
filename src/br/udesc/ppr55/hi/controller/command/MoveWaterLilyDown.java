package br.udesc.ppr55.hi.controller.command;

import br.udesc.ppr55.hi.controller.IHaruController;

public class MoveWaterLilyDown extends HaruCommand {

    public MoveWaterLilyDown(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void execute() {
        haruController.moveWaterLilyDown();
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
