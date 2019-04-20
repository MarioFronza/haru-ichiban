package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.controller.observer.Observed;

public interface IHaruController extends Observed {

    String getPiece(int col, int row);
    String getScoreStone(int col, int row);
    String getPlayerFlower(int col, int row);
    String getFlower(int col, int row);
    int getFlowerNumber(int col, int row);

    void itemClicked(int x, int y);
    void initializeBoard();
    void initializeScorePanel();
    void initializePlayerPanel();
    void initializeFlowerPanel();

    void addFlower(int x, int y);
    void chooseFlower(int x, int y);

    void eyePressed();
    void eyeReleased();
}
