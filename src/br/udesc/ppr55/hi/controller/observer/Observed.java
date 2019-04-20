package br.udesc.ppr55.hi.controller.observer;

public interface Observed {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyItemClicked();

    void notifyPlayerPanelUpdate();

    void notifyBoardPanelUpdate();

    void notifyFlowersPanelUpdate();

    void notifyShowFlowerNumber();

    void notifyShowFlower();

    void notifyErrorMessage(String message);

}
