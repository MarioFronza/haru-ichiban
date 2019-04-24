package br.udesc.ppr55.hi.controller.observer;

public interface Observer {

    void playerPanelUpdate();

    void boardPanelUpdate();

    void flowersPanelUpdate();

    void showFlowerNumber();

    void showFlower();

    void errorMessage(String message);

}
