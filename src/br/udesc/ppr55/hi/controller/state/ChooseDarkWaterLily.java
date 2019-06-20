package br.udesc.ppr55.hi.controller.state;

import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.model.WaterLilyComponent;
import br.udesc.ppr55.hi.model.decorator.RedFrogDecorator;
import br.udesc.ppr55.hi.model.decorator.YellowFrogDecorator;

public class ChooseDarkWaterLily extends HaruState {

    public ChooseDarkWaterLily(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void chooseWaterLily(int x, int y) {
        if (haruController.getGridGameTable()[x][y].getClass() == WaterLilyComponent.class || haruController.getGridGameTable()[x][y].getClass() == YellowFrogDecorator.class || haruController.getGridGameTable()[x][y].getClass() == RedFrogDecorator.class) {
            if (haruController.getGridGameTable()[x][y].getClass() == YellowFrogDecorator.class || haruController.getGridGameTable()[x][y].getClass() == RedFrogDecorator.class) {
                if (haruController.getGridGameTable()[x][y].getClass() == YellowFrogDecorator.class) {
                    haruController.setCurrentFrog("yellow");
                } else {
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
            haruController.updateChooseWaterLily();
        } else {
            haruController.notifyMessage("Invalid position.");
        }
    }

}
