package br.udesc.ppr55.hi.controller.state;

import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.model.Flower;
import br.udesc.ppr55.hi.model.RedGardener;
import br.udesc.ppr55.hi.model.Stone;
import br.udesc.ppr55.hi.model.YellowGardener;

public class AddFlower extends HaruState {

    public AddFlower(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void addFlower(int x, int y) {
        if ((haruController.getCurrentFlowerTable(x, y).getClass() != RedGardener.class) && (haruController.getCurrentFlowerTable(x, y).getClass() != YellowGardener.class) && (haruController.getCurrentFlowerTable(x, y).getClass() != Stone.class) && (haruController.getFlowerPlayerPanel().size() < 3)) {
            haruController.setMoved(false);
            haruController.addFlowerPlayerPanel((Flower) haruController.getCurrentFlowerTable(x, y));
            haruController.setCurrentFlowerTable(x, y, haruController.getFactory().createStoneWithoutNumber());
            if (haruController.getFlowerPlayerPanel().size() == 3) {
                haruController.setAppropriateRotation();
            }
            haruController.notifyPlayerPanelUpdate();
            haruController.notifyFlowersPanelUpdate();
            if (haruController.getRedPlayerPanel().size() == 3 && haruController.getYellowPlayerPanel().size() == 3) {
                haruController.setState(new ChooseFlowerValue(haruController));
                haruController.notifyMessage("Each player must choose a flower from your panel.");
            }
        }

    }


}
