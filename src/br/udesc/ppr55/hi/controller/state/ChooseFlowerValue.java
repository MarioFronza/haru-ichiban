package br.udesc.ppr55.hi.controller.state;

import br.udesc.ppr55.hi.controller.IHaruController;

public class ChooseFlowerValue extends HaruState {

    public ChooseFlowerValue(IHaruController haruController) {
        super(haruController);
    }

    @Override
    public void chooseFlower(int x, int y) {
        haruController.setCurrentFlower(haruController.getFlowerPlayerPanel().get(x));
        haruController.setRemovedFlower(haruController.getFlowerPlayerPanel().remove(x));
        haruController.setAppropriateRotation();
        if (haruController.checkFlowerValue()) {
            if (haruController.getRedGardener().isJunior()) {
                haruController.setCurrentRotation("red");
                haruController.setState(new ChooseWaterLily(haruController));
                haruController.notifyMessage("Each player must choose a water lily.");
            } else if (haruController.getYellowGardener().isJunior()) {
                haruController.setCurrentRotation("yellow");
                haruController.setState(new ChooseWaterLily(haruController));
                haruController.notifyMessage("Each player must choose a water lily.");
            } else {
                haruController.notifyMessage("Each player must choose again a flower in the panel.");
            }
        }
    }

}
