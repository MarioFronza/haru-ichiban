package br.udesc.ppr55.hi.controller.state;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.model.*;
import br.udesc.ppr55.hi.model.decorator.RedFrogDecorator;
import br.udesc.ppr55.hi.model.decorator.YellowFrogDecorator;

public class ChooseWaterLily extends HaruState {

    public ChooseWaterLily(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void chooseWaterLily(int x, int y) {
        haruController.setCurrentWaterLilyX(x);
        haruController.setCurrentWaterLilyY(y);
        if (haruController.getJuniorPlayer()) {
            if (haruController.getGridGameTable()[x][y].getClass() == DarkWaterLily.class) {
                DarkWaterLily darkWaterLily = (DarkWaterLily) haruController.getGridGameTable()[x][y];
                haruController.getCurrentFlower().setImage("images/water-lily-dark-with-" + haruController.getCurrentRotation() + "-petal.png");
                if (darkWaterLily.isOriginal())
                    haruController.getCurrentFlower().setOriginalDarkWaterLily(true);
                haruController.getGridGameTable()[x][y] = haruController.getCurrentFlower();
                haruController.setCurrentFlower(null);
                haruController.setAppropriateRotation();
            } else {
                haruController.notifyMessage("Invalid position.");
            }
        } else if (haruController.getGridGameTable()[x][y].getClass() == WaterLilyComponent.class || haruController.getGridGameTable()[x][y].getClass() == YellowFrogDecorator.class || haruController.getGridGameTable()[x][y].getClass() == RedFrogDecorator.class) {
            haruController.getCurrentFlower().setImage("images/water-lily-with-" + haruController.getCurrentRotation() + "-petal.png");
            if (haruController.getGridGameTable()[x][y].getClass() == YellowFrogDecorator.class || haruController.getGridGameTable()[x][y].getClass() == RedFrogDecorator.class) {
                if (haruController.getGridGameTable()[x][y].getClass() == YellowFrogDecorator.class) {
                    haruController.setCurrentFrog("yellow");
                } else {
                    haruController.setCurrentFrog("red");
                }
                haruController.getGridGameTable()[x][y] = haruController.getCurrentFlower();
                haruController.setState(new ChooseFrog(haruController));
                haruController.notifyMessage("Choose a new place for your frog.");
            } else {
                haruController.getGridGameTable()[x][y] = haruController.getCurrentFlower();
                haruController.setAppropriateRotation();
                haruController.setCurrentFlower(null);
                haruController.notifyMessage(haruController.getCurrentNamePlayer() + " should move a water lily.");
                haruController.setState(new MoveWaterLily(haruController));
            }
        } else {
            haruController.notifyMessage("Invalid position.");
        }
        haruController.visitGameTable();
        haruController.notifyBoardPanelUpdate();
        haruController.notifyFlowersPanelUpdate();
        haruController.notifyPlayerPanelUpdate();
    }

}
