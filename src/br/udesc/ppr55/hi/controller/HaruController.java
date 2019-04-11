package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.model.Piece;
import br.udesc.ppr55.hi.model.Water;
import br.udesc.ppr55.hi.model.WaterLily;

/**
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 */
public class HaruController {

    private static HaruController instance;
    private Piece[][] gameBoard;

    public static HaruController getInstance() {
        if (instance == null)
            instance = new HaruController();

        return instance;
    }

    private HaruController() {
        this.initializeBoard();
    }

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

    public String getPiece(int col, int row) {
        return (gameBoard[col][row] == null ? null : gameBoard[col][row].getImage());
    }

    public void itemClicked(int x, int y){

    }

}
