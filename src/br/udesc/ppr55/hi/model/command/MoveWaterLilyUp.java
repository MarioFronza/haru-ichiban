package br.udesc.ppr55.hi.model.command;

import br.udesc.ppr55.hi.controller.IHaruController;

public class MoveWaterLilyUp extends HaruCommand {

    public MoveWaterLilyUp(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void execute() {
        haruController.moveWaterLilyUp();
    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

}
