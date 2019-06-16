package br.udesc.ppr55.hi.controller.state;

import br.udesc.ppr55.hi.controller.IHaruController;

public abstract class HaruState {

    protected IHaruController haruController;

    public HaruState(IHaruController haruController) {
        this.haruController = haruController;
    }

    public void addFlower(int x, int y) {
        haruController.notifyMessage("Invalid Action!");
    }

    public void chooseFlower(int x, int y) {
        haruController.notifyMessage("Invalid Action!");
    }

    public void chooseWaterLily(int x, int y) {
        haruController.notifyMessage("Invalid Action!");
    }

    public abstract void nextState(HaruState haruState);
}
