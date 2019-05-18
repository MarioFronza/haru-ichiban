package br.udesc.ppr55.hi.controller.observer;

/**
 * Observed interface
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 11/04/2019
 * @version 1.0.0
 */
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

    void notifyMessage(String message);

}
