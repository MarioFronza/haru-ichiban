package br.udesc.ppr55.hi.view.command;

import br.udesc.ppr55.hi.controller.IHaruController;

/**
 * Add flower command class
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 14/04/2019
 * @version 1.0.0
 */
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
