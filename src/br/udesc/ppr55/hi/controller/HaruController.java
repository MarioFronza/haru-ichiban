package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.controller.observer.Observer;
import br.udesc.ppr55.hi.model.*;
import br.udesc.ppr55.hi.model.AbstractFactory.AbstractPieceFactory;
import br.udesc.ppr55.hi.model.AbstractFactory.PieceFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 */
public class HaruController implements IHaruController {

    private static HaruController instance;
    private AbstractPieceFactory factory;
    private Piece[][] gameBoard;
    private Piece[][] scorePanel;
    private Piece[][] playerPanel;
    private Piece[][] flowerPanel;

    private List<Observer> observers;

    private Piece currentFlower = null;
    private int currentWaterLilyX = -1;
    private int currentWaterLilyY = -1;
    private boolean isHaruIchiban = false;


    private RedGardener redGardener;
    private YellowGardener yellowGardener;

    public static HaruController getInstance() {
        if (instance == null)
            instance = new HaruController();

        return instance;
    }

    private HaruController() {
        this.factory = new PieceFactory();
        this.observers = new ArrayList<>();

        this.initializeBoard();
        this.initializeScorePanel();
        this.initializePlayerPanel();
        this.initializeFlowerPanel();
    }

    @Override
    public void initializeBoard() {
        gameBoard = new Piece[5][5];

        gameBoard[0][0] = factory.createWaterLily();
        gameBoard[0][1] = factory.createWater();
        gameBoard[0][2] = factory.createWaterLily();
        gameBoard[0][3] = factory.createWater();
        gameBoard[0][4] = factory.createWaterLily();

        gameBoard[1][0] = factory.createWater();
        gameBoard[1][1] = factory.createWaterLily();
        gameBoard[1][2] = factory.createYellowFrog();
        gameBoard[1][3] = factory.createDarkWaterLily();
        gameBoard[1][4] = factory.createWater();

        gameBoard[2][0] = factory.createWaterLily();
        gameBoard[2][1] = factory.createWaterLily();
        gameBoard[2][2] = factory.createWater();
        gameBoard[2][3] = factory.createWaterLily();
        gameBoard[2][4] = factory.createWaterLily();

        gameBoard[3][0] = factory.createWater();
        gameBoard[3][1] = factory.createWaterLily();
        gameBoard[3][2] = factory.createWaterLily();
        gameBoard[3][3] = factory.createRedFrog();
        gameBoard[3][4] = factory.createWater();

        gameBoard[4][0] = factory.createWaterLily();
        gameBoard[4][1] = factory.createWater();
        gameBoard[4][2] = factory.createWaterLily();
        gameBoard[4][3] = factory.createWater();
        gameBoard[4][4] = factory.createWaterLily();


    }

    @Override
    public void initializeScorePanel() {

        scorePanel = new Piece[10][1];

        scorePanel[0][0] = factory.createStone(1);
        scorePanel[1][0] = factory.createStone(2);
        scorePanel[2][0] = factory.createStone(3);
        scorePanel[3][0] = factory.createStone(4);
        scorePanel[4][0] = factory.createStone(5);
        scorePanel[5][0] = factory.createStone(4);
        scorePanel[6][0] = factory.createStone(3);
        scorePanel[7][0] = factory.createStone(2);
        scorePanel[8][0] = factory.createStone(1);
        scorePanel[9][0] = factory.createStone(0);
    }

    @Override
    public void initializePlayerPanel() {
        playerPanel = new Piece[1][3];

        playerPanel[0][0] = null;
        playerPanel[0][1] = null;
        playerPanel[0][2] = null;

    }

    @Override
    public void initializeFlowerPanel() {
        flowerPanel = new Piece[5][2];

        flowerPanel[0][0] = factory.createFlower(1);
        flowerPanel[0][1] = factory.createFlower(2);
        flowerPanel[1][0] = factory.createFlower(3);
        flowerPanel[1][1] = factory.createFlower(4);
        flowerPanel[2][0] = factory.createFlower(5);
        flowerPanel[2][1] = factory.createFlower(6);
        flowerPanel[3][0] = factory.createFlower(7);
        flowerPanel[3][1] = factory.createFlower(8);
        flowerPanel[4][0] = factory.createRedGardener();
        flowerPanel[4][1] = factory.createYellowGardener();
    }

    @Override
    public void setGardeners(String redName, String yellowName) {
        this.redGardener = (RedGardener) flowerPanel[4][0];
        this.yellowGardener = (YellowGardener) flowerPanel[4][1];

        this.redGardener.setName(redName);
        this.yellowGardener.setName(yellowName);

        System.out.println(redGardener.getName());
        System.out.println(yellowGardener.getName());
        notifyHideControlPanel();
        notifyErrorMessage(redGardener.getName() + " escolha três flores");
    }

    @Override
    public void addFlower(int x, int y) {
        if (flowerPanel[x][y].getClass() != RedGardener.class && flowerPanel[x][y].getClass() != YellowGardener.class && flowerPanel[x][y].getClass() != Stone.class) {
            if (playerPanel[0][0] == null) {
                this.playerPanel[0][0] = flowerPanel[x][y];
                this.flowerPanel[x][y] = factory.createStone(0);
                notifyPlayerPanelUpdate();
                notifyFlowersPanelUpdate();
            } else if (playerPanel[0][1] == null) {
                playerPanel[0][1] = flowerPanel[x][y];
                this.flowerPanel[x][y] = factory.createStone(0);
                notifyPlayerPanelUpdate();
                notifyFlowersPanelUpdate();
            } else if (playerPanel[0][2] == null) {
                playerPanel[0][2] = flowerPanel[x][y];
                this.flowerPanel[x][y] = flowerPanel[x][y];
                this.flowerPanel[x][y] = factory.createStone(0);
                notifyPlayerPanelUpdate();
                notifyFlowersPanelUpdate();
            } else {
                notifyErrorMessage("Não é possível escolher mais flores");
            }
        }
    }

    @Override
    public void chooseFlower(int x, int y) {
        if (this.playerPanel[0][2] == null) {
            this.notifyErrorMessage("Você precisa escolher todas as flores antes");
        } else if (this.currentFlower != null) {
            this.notifyErrorMessage("Você já escolheu uma flor");
        } else {
            this.currentFlower = this.playerPanel[x][y];
            this.playerPanel[x][y] = playerPanel[0][2];
        }
    }

    @Override
    public void eyePressed() {
        if (this.playerPanel[0][2] == null) {
            this.notifyErrorMessage("Você precisa escolher todas as flores antes");
        } else {
            this.notifyShowFlowerNumber();
        }

    }

    @Override
    public void eyeReleased() {
        this.notifyShowFlower();
    }

    @Override
    public void chooseWaterLily(int x, int y) {
        if (isHaruIchiban && gameBoard[x][y].getClass() != Water.class) {
            this.currentWaterLilyX = x;
            this.currentWaterLilyY = y;
            this.notifyShowControlPanel();
            isHaruIchiban = false;
        } else {
            if (this.playerPanel[0][2] == null) {
                this.notifyErrorMessage("Você precisa escolher todas as flores antes");
            } else if (this.currentFlower == null) {
                this.notifyErrorMessage("Você precisa escolher uma de suas flores");
            } else if (gameBoard[x][y].getClass() == Water.class || gameBoard[x][y].getClass() == DarkWaterLily.class) {
                this.notifyErrorMessage("Posição inválida");
            } else {
                this.playerPanel[0][2] = null;
                this.currentFlower.setImage("images/water-lily-with-pink-petal.png");
                this.gameBoard[x][y] = currentFlower;
                this.currentFlower = null;
                this.notifyBoardPanelUpdate();
                this.notifyPlayerPanelUpdate();
                isHaruIchiban = true;
            }
        }


    }

    @Override
    public void addFlowerInWaterLily(int x, int y) {

    }

    @Override
    public void moveWaterLilyDown() {
        if (this.currentWaterLilyX != -1) {

        } else {
            this.notifyErrorMessage("Escolha uma vitória régia");
        }
    }

    @Override
    public void moveWaterLilyUp() {
        if (this.currentWaterLilyX != -1 && this.gameBoard[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class) {
            if (currentWaterLilyX - 1 != -1) {
                System.out.println(gameBoard[currentWaterLilyX - 1][currentWaterLilyY].getClass() == Water.class);
                if (gameBoard[currentWaterLilyX - 1][currentWaterLilyY].getClass() == Water.class) {
                    Piece auxPosition = gameBoard[currentWaterLilyX - 1][currentWaterLilyY];
                    gameBoard[currentWaterLilyX - 1][currentWaterLilyY] = gameBoard[currentWaterLilyX][currentWaterLilyY];
                    gameBoard[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    this.notifyBoardPanelUpdate();
                    this.notifyHideControlPanel();
                } else {
                    int x = -1;
                    for (int i = currentWaterLilyX - 1; i >= 0; i--) {
                        if (gameBoard[i][currentWaterLilyY].getClass() == Water.class) {
                            x = i;
                        }
                    }
                    if (x != -1) {
                        for (int i = x; i < currentWaterLilyX; i++) {
                            Piece auxPosition = gameBoard[i][currentWaterLilyY];
                            gameBoard[i][currentWaterLilyY] = gameBoard[i + 1][currentWaterLilyY];
                            gameBoard[i + 1][currentWaterLilyY] = auxPosition;
                            this.notifyBoardPanelUpdate();
                            this.notifyHideControlPanel();
                        }
                    } else {
                        this.notifyErrorMessage("Não é possível realizar este movimento!");
                    }
                }
            } else {
                this.notifyErrorMessage("Não é possível realizar este movimento!");
            }
        } else {
            this.notifyErrorMessage("Escolha uma vitória régia");
        }
    }

    @Override
    public void moveWaterLilyLeft() {
        if (this.currentWaterLilyX != -1) {

        } else {
            this.notifyErrorMessage("Escolha uma vitória régia");
        }
    }

    @Override
    public void moveWaterLilyRight() {
        if (this.currentWaterLilyX != -1) {

        } else {
            this.notifyErrorMessage("Escolha uma vitória régia");
        }
    }

    @Override
    public String getPiece(int col, int row) {
        return (gameBoard[col][row] == null ? null : gameBoard[col][row].getImage());
    }

    @Override
    public String getScoreStone(int col, int row) {
        return scorePanel[col][row].getImage();
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
    public int getFlowerNumber(int col, int row) {
        RedFlower redFlower = (RedFlower) playerPanel[col][row];
        return (playerPanel[col][row] == null ? null : redFlower.getNumber());
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
    public void notifyPlayerPanelUpdate() {
        for (Observer observer : observers) {
            observer.playerPanelUpdate();
        }
    }

    @Override
    public void notifyBoardPanelUpdate() {
        for (Observer observer : observers) {
            observer.boardPanelUpdate();
        }
    }

    @Override
    public void notifyFlowersPanelUpdate() {
        for (Observer observer : observers) {
            observer.flowersPanelUpdate();
        }
    }

    @Override
    public void notifyShowFlowerNumber() {
        for (Observer observer : observers) {
            observer.showFlowerNumber();
        }
    }

    @Override
    public void notifyShowFlower() {
        for (Observer observer : observers) {
            observer.showFlower();
        }
    }

    @Override
    public void notifyShowControlPanel() {
        for (Observer observer : observers) {
            observer.showControlPanel();
        }
    }

    @Override
    public void notifyHideControlPanel() {
        for (Observer observer : observers) {
            observer.hideControlPanel();
        }
    }

    @Override
    public void notifyErrorMessage(String message) {
        for (Observer observer : observers) {
            observer.errorMessage(message);
        }
    }
}
