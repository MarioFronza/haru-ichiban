package br.udesc.ppr55.hi.controller.state;

import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.model.DarkWaterLily;
import br.udesc.ppr55.hi.model.WaterLilyComponent;
import br.udesc.ppr55.hi.model.decorator.RedFrog;
import br.udesc.ppr55.hi.model.decorator.YellowFrog;

public class ChooseDarkWaterLily extends HaruState {

    public ChooseDarkWaterLily(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void chooseWaterLily(int x, int y) {
        boolean containsFrog = false;
        if (haruController.getGridGameTable()[x][y].getClass() == WaterLilyComponent.class || haruController.getGridGameTable()[x][y].getClass() == YellowFrog.class || haruController.getGridGameTable()[x][y].getClass() == RedFrog.class) {
            if (haruController.getGridGameTable()[x][y].getClass() == YellowFrog.class || haruController.getGridGameTable()[x][y].getClass() == RedFrog.class) {
                if (haruController.getGridGameTable()[x][y].getClass() == YellowFrog.class) {
                    YellowFrog yellowFrog = (YellowFrog) haruController.getGridGameTable()[x][y];
                    if (yellowFrog.getWaterLily().isContaisnEgg())
                        containsFrog = true;
                    haruController.setCurrentFrog("yellow");
                } else {
                    RedFrog redFrog = (RedFrog) haruController.getGridGameTable()[x][y];
                    if (redFrog.getWaterLily().isContaisnEgg())
                        containsFrog = true;
                    haruController.setCurrentFrog("red");
                }
                haruController.setState(new ChooseFrog(haruController));
                haruController.notifyMessage("Choose a new place for your frog.");
            } else {
                if (haruController.getRound() >= 7) {
                    haruController.setState(new ChooseFlowerValue(haruController));
                    haruController.notifyMessage("Each player must choose a flower from your panel.");
                } else {
                    haruController.setState(new AddFlower(haruController));
                    haruController.notifyMessage("Each player must add a new flower in your panel.");
                }
            }
            haruController.getGridGameTable()[x][y] = haruController.getFactory().createDarkWaterLily(false);
            DarkWaterLily darkWaterLily = (DarkWaterLily) haruController.getGridGameTable()[x][y];
            if (haruController.getCurrentFrog() != null) {
                if (haruController.getCurrentFrog().equals("red") && containsFrog) {
                    darkWaterLily.setOriginalRedEggWaterLily(true);
                } else if (haruController.getCurrentFrog().equals("yellow") && containsFrog) {
                    darkWaterLily.setOriginalYellowEggWaterLily(true);
                }
            }

            haruController.updateChooseWaterLily();
        } else {
            haruController.notifyMessage("Invalid position.");
        }
    }

}
