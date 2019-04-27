package br.udesc.ppr55.hi.controller.observer;

public interface Observed {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyPlayerPanelUpdate();

    void notifyBoardPanelUpdate();

    void notifyFlowersPanelUpdate();

    void notifyShowFlowerNumber();

    void notifyShowFlower();

    void notifyShowControlPanel();
    
    void notifyHideControlPanel();

    void notifyErrorMessage(String message);

}
