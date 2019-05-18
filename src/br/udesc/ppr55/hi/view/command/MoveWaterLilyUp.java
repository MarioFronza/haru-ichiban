package br.udesc.ppr55.hi.view.command;

import br.udesc.ppr55.hi.controller.IHaruController;

/**
 * Move Water Lily up command class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 14/04/2019
 * @version 1.0.0
 */
public class MoveWaterLilyUp extends HaruCommand {

    public MoveWaterLilyUp(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void execute() {
        haruController.moveWaterLilyUp();
    }

}
