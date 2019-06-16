package br.udesc.ppr55.hi.controller.state;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.model.RedFrog;
import br.udesc.ppr55.hi.model.WaterLily;
import br.udesc.ppr55.hi.model.YellowFrog;

public class ChooseDarkWaterLily extends HaruState {

    public ChooseDarkWaterLily(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void chooseWaterLily(int x, int y) {
        if (haruController.getGridGameTable()[x][y].getClass() == WaterLily.class || haruController.getGridGameTable()[x][y].getClass() == YellowFrog.class || haruController.getGridGameTable()[x][y].getClass() == RedFrog.class) {
            if (haruController.getGridGameTable()[x][y].getClass() == YellowFrog.class || haruController.getGridGameTable()[x][y].getClass() == RedFrog.class) {
                if (haruController.getGridGameTable()[x][y].getClass() == YellowFrog.class) {
                    haruController.setCurrentFrog("yellow");
                } else {
                    haruController.setCurrentFrog("red");
                }
                haruController.setPreviousPhase("choose_dark_waterlily");
                nextState(new ChooseFrog(haruController));
                haruController.notifyMessage("Choose a new place for your frog.");
            } else {
                haruController.setPreviousPhase("choose_dark_waterlily");
                nextState(new AddFlower(haruController));
                haruController.notifyMessage("Each player must add a new flower in your panel.");
            }
            haruController.getGridGameTable()[x][y] = haruController.getFactory().createDarkWaterLily(false);
            haruController.updateChooseWaterLily();
        } else {
            haruController.notifyMessage("Invalid position.");
        }
    }

    @Override
    public void nextState(HaruState haruState) {
        haruController.setHaruState(haruState);
    }
}
