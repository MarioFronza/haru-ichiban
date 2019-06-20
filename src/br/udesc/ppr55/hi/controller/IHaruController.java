package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.controller.observer.Observed;
import br.udesc.ppr55.hi.controller.state.HaruState;
import br.udesc.ppr55.hi.model.Flower;
import br.udesc.ppr55.hi.model.Gardener;
import br.udesc.ppr55.hi.model.Piece;
import br.udesc.ppr55.hi.model.abstractfactory.AbstractPieceFactory;
import br.udesc.ppr55.hi.model.abstractfactory.PieceFactory;
import br.udesc.ppr55.hi.model.builder.Builder;

import java.util.List;

/**
 * Controller interface
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 07/04/2019
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

    int verifyRedWinner(int x, int y);

    int verifyYellowWinner(int x, int y);

    void verifyNextPhase();

    boolean visitGameTable();

    void updateScore();

    boolean checkFlowerValue();

    void newRound();

    List<Flower> getFlowerPlayerPanel();

    Flower getCurrentFlower();

    Piece[][] getGridGameTable();

    String getCurrentRotation();

    String getCurrentNamePlayer();

    boolean isMoved();

    boolean getJuniorPlayer();

    int getFlowerNumber(int col, int row);

    String getCurrentFrog();

    List<Flower> getRedPlayerPanel();

    List<Flower> getYellowPlayerPanel();

    Gardener getRedGardener();

    Gardener getYellowGardener();

    int getRound();

    AbstractPieceFactory getFactory();

    void setState(HaruState haruState);

    void setRound(int round);

    void setFactory(PieceFactory pieceFactory);

    void setMoved(boolean moved);

    void setCurrentFlower(Flower flower);

    void setAppropriateRotation();

    void setCurrentFrog(String currentFrog);

    void setCurrentWaterLilyX(int currentWaterLilyX);

    void setCurrentWaterLilyY(int currentWaterLilyY);

    void setHaruState(HaruState haruState);

    void setCurrentRotation(String currentRotation);

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
