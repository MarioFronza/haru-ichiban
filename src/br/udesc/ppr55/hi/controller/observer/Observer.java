package br.udesc.ppr55.hi.controller.observer;

/**
 * Observer interface
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 11/04/2019
 * @version 1.0.0
 */
public interface Observer {

    void playerPanelUpdate();

    void boardPanelUpdate();

    void flowersPanelUpdate();

    void showFlowerNumber();

    void showFlower();

    void showControlPanel();

    void hideControlPanel();

    void endGame();

    void message(String message);

}
