package br.udesc.ppr55.hi.controller.state;

import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.model.Water;

public class MoveWaterLily extends HaruState {

    public MoveWaterLily(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void chooseWaterLily(int x, int y) {
        if (haruController.getGridGameTable()[x][y].getClass() != Water.class) {
            haruController.setCurrentFlower(null);
            haruController.setCurrentWaterLilyX(x);
            haruController.setCurrentWaterLilyY(y);
            haruController.setCurrentWaterLilyX(x);
            haruController.notifyShowControlPanel();
            haruController.setMoved(true);
            haruController.setState(new ChooseDarkWaterLily(haruController));
        }
    }

}
