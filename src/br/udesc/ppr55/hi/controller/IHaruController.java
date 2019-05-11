package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.controller.observer.Observed;
import br.udesc.ppr55.hi.model.Flower;
import br.udesc.ppr55.hi.model.Piece;

import java.util.List;

public interface IHaruController extends Observed {

    String getPiece(int col, int row);

    String getScoreStone(int col, int row);

    String getPlayerFlower(int col);

    String getFlower(int col, int row);

    Piece getCurrentFlowerTable(int col, int row);

    void setCurrentFlowerTable(int col, int row, Piece piece);

    void addFlowerPlayerPanel(Flower flower);

    void updateChooseWaterLily();

    List<Flower> getFlowerPlayerPanel();

    Flower getCurrentFlower();

    Piece[][] getGridGameTable();

    String getCurrentRotation();

    String getCurrentNamePlayer();

    boolean getJuniorPlayer();

    void setCurrentFlower(Flower flower);

    void setNextPhase(String previousPhase, String nextPhase);

    void setAppropriateRotation();

    boolean checkFlowerValue();

    int getFlowerNumber(int col, int row);

    void verifyNextPhase();

    void initializeBoard();

    void initializeScorePanel();

    void initializePlayerPanel();

    void initializeFlowerPanel();

    void setGardeners(String redName, String yellowName);

    void addFlower(int x, int y);

    void chooseFlower(int x, int y);

    void eyePressed();

    void eyeReleased();

    void chooseWaterLily(int x, int y);

    void moveWaterLilyDown();

    void moveWaterLilyUp();

    void moveWaterLilyLeft();

    void moveWaterLilyRight();
}
