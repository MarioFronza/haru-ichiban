package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.controller.observer.Observer;
import br.udesc.ppr55.hi.model.Flower;
import br.udesc.ppr55.hi.model.Piece;
import br.udesc.ppr55.hi.model.WaterLily;

import java.util.ArrayList;
import java.util.List;

/**
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 */
public class HaruController implements IHaruController {

    private static HaruController instance;
    private Piece[][] gameBoard;
    private Piece[][] scorePanel;
    private Piece[][] playerPanel;
    private Piece[][] flowerPanel;

    private List<Observer> observers = new ArrayList<>();

    public static HaruController getInstance() {
        if (instance == null)
            instance = new HaruController();

        return instance;
    }

    private HaruController() {
        this.initializeBoard();
        this.initializeScorePanel();
        this.initializePlayerPanel();
        this.initializeFlowerPanel();
    }

    @Override
    public void initializeBoard() {
        gameBoard = new Piece[5][5];

        gameBoard[0][0] = new WaterLily();
        gameBoard[0][1] = null;
        gameBoard[0][2] = new WaterLily();
        gameBoard[0][3] = null;
        gameBoard[0][4] = new WaterLily();

        gameBoard[1][0] = null;
        gameBoard[1][1] = new WaterLily();
        gameBoard[1][2] = new WaterLily();
        gameBoard[1][3] = new WaterLily();
        gameBoard[1][4] = null;

        gameBoard[2][0] = new WaterLily();
        gameBoard[2][1] = new WaterLily();
        gameBoard[2][2] = null;
        gameBoard[2][3] = new WaterLily();
        gameBoard[2][4] = new WaterLily();

        gameBoard[3][0] = null;
        gameBoard[3][1] = new WaterLily();
        gameBoard[3][2] = new WaterLily();
        gameBoard[3][3] = new WaterLily();
        gameBoard[3][4] = null;

        gameBoard[4][0] = new WaterLily();
        gameBoard[4][1] = null;
        gameBoard[4][2] = new WaterLily();
        gameBoard[4][3] = null;
        gameBoard[4][4] = new WaterLily();

    }

    @Override
    public void initializeScorePanel() {

        scorePanel = new Piece[5][1];

        scorePanel[0][0] = null;
        scorePanel[1][0] = null;
        scorePanel[2][0] = null;
        scorePanel[3][0] = null;
        scorePanel[4][0] = null;

    }

    @Override
    public void initializePlayerPanel() {
        playerPanel = new Piece[1][3];

        playerPanel[0][0] = new Flower();
        playerPanel[0][1] = new Flower();
        playerPanel[0][2] = new Flower();

    }

    @Override
    public void initializeFlowerPanel() {
        flowerPanel = new Piece[4][2];

        flowerPanel[0][0] = new Flower();
        flowerPanel[0][1] = new Flower();
        flowerPanel[1][0] = new Flower();
        flowerPanel[1][1] = new Flower();
        flowerPanel[2][0] = new Flower();
        flowerPanel[2][1] = null;
        flowerPanel[3][0] = null;
        flowerPanel[3][1] = null;
    }

    @Override
    public String getPiece(int col, int row) {
        return (gameBoard[col][row] == null ? null : gameBoard[col][row].getImage());
    }

    @Override
    public String getScoreStone(int col, int row) {
        return null;
    }

    @Override
    public String getPlayerFlower(int col, int row) {
        return (playerPanel[col][row] == null ? null : playerPanel[col][row].getImage());
    }

    @Override
    public String getFlower(int col, int row) {
        return (flowerPanel[col][row] == null ? null : flowerPanel[col][row].getImage());
    }

    @Override
    public void itemClicked(int x, int y) {

        this.notifyItemClicked();
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyItemClicked() {
        for (Observer observer : observers) {
            observer.notifyItemClicked();
        }
    }
}
