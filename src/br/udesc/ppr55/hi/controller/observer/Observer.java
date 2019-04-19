package br.udesc.ppr55.hi.controller.observer;

public interface Observer {

    void itemClicked();

    void playerPanelUpdate();

    void boardPanelUpdate();

    void flowersPanelUpdate();

    void errorMessage();

}
