package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.controller.observer.Observed;
import br.udesc.ppr55.hi.controller.state.HaruState;
import br.udesc.ppr55.hi.model.Flower;
import br.udesc.ppr55.hi.model.Gardener;
import br.udesc.ppr55.hi.model.Piece;
import br.udesc.ppr55.hi.model.abstractfactory.AbstractPieceFactory;
import br.udesc.ppr55.hi.model.abstractfactory.PieceFactory;

import java.util.List;

/**
 * Controller interface
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 * @version 1.0.0
 */
public interface IHaruController extends Observed {

    String getPiece(int col, int row);

    String getScoreStone(int col, int row);

    String getPlayerFlower(int col);

    String getFlower(int col, int row);

    Piece getCurrentFlowerTable(int col, int row);

    void setCurrentFlowerTable(int col, int row, Piece piece);

    void addFlowerPlayerPanel(Flower flower);

    void setRemovedFlower(Flower flower);

    void updateChooseWaterLily();

    List<Flower> getFlowerPlayerPanel();

    Flower getCurrentFlower();

    Piece[][] getGridGameTable();

    String getCurrentRotation();

    String getCurrentNamePlayer();

    AbstractPieceFactory getFactory();

    void setFactory(PieceFactory pieceFactory);

    boolean verifyRedWinner(int x, int y);

    boolean verifyYellowWinner(int x, int y);

    boolean getJuniorPlayer();

    void visitGameTable();

    void setCurrentFlower(Flower flower);

    void setAppropriateRotation();

    boolean checkFlowerValue();

    int getFlowerNumber(int col, int row);

    void setCurrentFrog(String currentFrog);

    void setCurrentWaterLilyX(int currentWaterLilyX);

    void setCurrentWaterLilyY(int currentWaterLilyY);

    String getCurrentFrog();

    HaruState getHaruState();

    void setHaruState(HaruState haruState);

    List<Flower> getRedPlayerPanel();

    List<Flower> getYellowPlayerPanel();

    Gardener getRedGardener();

    Gardener getYellowGardener();

    String getPreviousPhase();

    void setCurrentRotation(String currentRotation);

    void verifyNextPhase();

    void initializeBoard();

    void initializeScorePanel();

    void setPreviousPhase(String previousPhase);

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
