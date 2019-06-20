package br.udesc.ppr55.hi.controller.state;

import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.model.WaterLilyComponent;

public class ChooseFrog extends HaruState {

    public ChooseFrog(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void chooseWaterLily(int x, int y) {
        if (haruController.getGridGameTable()[x][y].getClass() == WaterLilyComponent.class) {
            if (haruController.getCurrentFrog().equals("red")) {
                haruController.getGridGameTable()[x][y] = haruController.getFactory().createRedFrog();
            } else {
                haruController.getGridGameTable()[x][y] = haruController.getFactory().createYellowFrog();
            }
            haruController.updateChooseWaterLily();
            if (!haruController.isMoved()) {
                haruController.setState(new MoveWaterLily(haruController));
                haruController.notifyMessage(haruController.getCurrentNamePlayer() + " should move a water lily.");
            } else {
                if (haruController.getRound() >= 7) {
                    haruController.setState(new ChooseFlowerValue(haruController));
                    haruController.notifyMessage("Each player must choose a flower from your panel.");
                } else {
                    haruController.setState(new AddFlower(haruController));
                    haruController.notifyMessage("Each player must add a new flower in your panel.");
                }
            }
        } else {
            haruController.notifyMessage("Invalid position.");
        }
    }

}
