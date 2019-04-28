package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.controller.observer.Observer;
import br.udesc.ppr55.hi.model.*;
import br.udesc.ppr55.hi.model.abstractfactory.AbstractPieceFactory;
import br.udesc.ppr55.hi.model.abstractfactory.PieceFactory;
import br.udesc.ppr55.hi.model.builder.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author João Pedro Schmitz, Mário Fronza
 * @since 07/04/2019
 */
public class HaruController implements IHaruController {

    private static HaruController instance;
    private AbstractPieceFactory factory;
    private Piece[][] scorePanel;
    private Piece[][] playerPanel;

    //private Piece[][] gameBoard;
    //private Piece[][] flowerPanel;

    private Builder builderGameTable;
    private Builder builderRedFlowerTable;
    private Builder builderYellowFlowerTable;
    private Director director;

    private List<Piece> redPlayerPanel;
    private List<Piece> yellowPlayerPanel;

    private List<Observer> observers;

    private Piece currentFlower = null;
    private int currentWaterLilyX = -1;
    private int currentWaterLilyY = -1;
    private boolean isHaruIchiban = false;
    private String currentRotation = "red";


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
        builderGameTable = new BuildGameTable();
        director = new Director(builderGameTable);
        director.build();
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

        redPlayerPanel = new ArrayList<>();
        yellowPlayerPanel = new ArrayList<>();

    }

    @Override
    public void initializeFlowerPanel() {
        builderRedFlowerTable = new BuildRedFlowerTable();
        builderYellowFlowerTable = new BuildYellowFlowerTable();

        director = new Director(builderRedFlowerTable);
        director.build();

        director = new Director(builderYellowFlowerTable);
        director.build();
    }

    @Override
    public void setGardeners(String redName, String yellowName) {
        this.redGardener = (RedGardener) getFlowerType(4, 0);
        this.yellowGardener = (YellowGardener) getFlowerType(4, 1);

        this.redGardener.setName(redName);
        this.yellowGardener.setName(yellowName);

        System.out.println(redGardener.getName());
        System.out.println(yellowGardener.getName());
        notifyHideControlPanel();
        notifyErrorMessage(redGardener.getName() + " escolha três flores");
    }

    @Override
    public void addFlower(int x, int y) {
        if (getFlowerType(x, y).getClass() != RedGardener.class && getFlowerType(x, y).getClass() != YellowGardener.class && getFlowerType(x, y).getClass() != Stone.class) {
            if (redPlayerPanel.get(0) == null) {
                this.playerPanel[0][0] = getFlowerType(x, y);
                setFlowerType(x, y, factory.createStone(0));
                notifyPlayerPanelUpdate();
                notifyFlowersPanelUpdate();
            } else if (playerPanel[0][1] == null) {
                playerPanel[0][1] = getFlowerType(x, y);
                setFlowerType(x, y, factory.createStone(0));
                notifyPlayerPanelUpdate();
                notifyFlowersPanelUpdate();
            } else if (playerPanel[0][2] == null) {
                playerPanel[0][2] = getFlowerType(x, y);
                setFlowerType(x, y, getFlowerType(x, y));
                setFlowerType(x, y, factory.createStone(0));
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
            currentRotation = "yellow";
            notifyFlowersPanelUpdate();
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
        if (isHaruIchiban && builderGameTable.getTable().getGrid()[x][y].getClass() != Water.class) {
            this.currentWaterLilyX = x;
            this.currentWaterLilyY = y;
            this.isHaruIchiban = false;
            notifyShowControlPanel();
        } else {
            if (playerPanel[0][2] == null) {
                this.notifyErrorMessage("Você precisa escolher todas as flores antes");
            } else if (this.currentFlower == null) {
                this.notifyErrorMessage("Você precisa escolher uma de suas flores");
            } else if (builderGameTable.getTable().getGrid()[x][y].getClass() == Water.class || builderGameTable.getTable().getGrid()[x][y].getClass() == DarkWaterLily.class) {
                this.notifyErrorMessage("Posição inválida");
            } else {
                this.playerPanel[0][2] = null;
                this.currentFlower.setImage("images/water-lily-with-pink-petal.png");
                this.builderGameTable.getTable().getGrid()[x][y] = currentFlower;
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
        if (this.currentWaterLilyX != -1 && builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class) {
            if (currentWaterLilyX + 1 != 5) {
                if (builderGameTable.getTable().getGrid()[currentWaterLilyX + 1][currentWaterLilyY].getClass() == Water.class) {
                    Piece auxPosition = builderGameTable.getTable().getGrid()[currentWaterLilyX + 1][currentWaterLilyY];
                    builderGameTable.getTable().getGrid()[currentWaterLilyX + 1][currentWaterLilyY] = builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY];
                    builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    this.notifyBoardPanelUpdate();
                    this.notifyHideControlPanel();
                } else {
                    int x = -1;
                    for (int i = currentWaterLilyX + 1; i <= 4; i++) {
                        if (builderGameTable.getTable().getGrid()[i][currentWaterLilyY].getClass() == Water.class) {
                            x = i;
                        }
                    }
                    if (x != -1) {
                        for (int i = x; i > currentWaterLilyX; i--) {
                            Piece auxPosition = builderGameTable.getTable().getGrid()[i][currentWaterLilyY];
                            builderGameTable.getTable().getGrid()[i][currentWaterLilyY] = builderGameTable.getTable().getGrid()[i - 1][currentWaterLilyY];
                            builderGameTable.getTable().getGrid()[i - 1][currentWaterLilyY] = auxPosition;
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
    public void moveWaterLilyUp() {
        if (this.currentWaterLilyX != -1 && builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class) {
            if (currentWaterLilyX - 1 != -1) {
                if (builderGameTable.getTable().getGrid()[currentWaterLilyX - 1][currentWaterLilyY].getClass() == Water.class) {
                    Piece auxPosition = builderGameTable.getTable().getGrid()[currentWaterLilyX - 1][currentWaterLilyY];
                    builderGameTable.getTable().getGrid()[currentWaterLilyX - 1][currentWaterLilyY] = builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY];
                    builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    this.notifyBoardPanelUpdate();
                    this.notifyHideControlPanel();
                } else {
                    int x = -1;
                    for (int i = currentWaterLilyX - 1; i >= 0; i--) {
                        if (builderGameTable.getTable().getGrid()[i][currentWaterLilyY].getClass() == Water.class) {
                            x = i;
                        }
                    }
                    if (x != -1) {
                        for (int i = x; i < currentWaterLilyX; i++) {
                            Piece auxPosition = builderGameTable.getTable().getGrid()[i][currentWaterLilyY];
                            builderGameTable.getTable().getGrid()[i][currentWaterLilyY] = builderGameTable.getTable().getGrid()[i + 1][currentWaterLilyY];
                            builderGameTable.getTable().getGrid()[i + 1][currentWaterLilyY] = auxPosition;
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
        if (this.currentWaterLilyY != -1 && builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class) {
            if (currentWaterLilyY - 1 != -1) {
                if (builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY - 1].getClass() == Water.class) {
                    Piece auxPosition = builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY - 1];
                    builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY - 1] = builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY];
                    builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    this.notifyBoardPanelUpdate();
                    this.notifyHideControlPanel();
                } else {
                    int x = -1;
                    for (int i = currentWaterLilyY - 1; i >= 0; i--) {
                        if (builderGameTable.getTable().getGrid()[currentWaterLilyX][i].getClass() == Water.class) {
                            x = i;
                        }
                    }
                    if (x != -1) {
                        for (int i = x; i < currentWaterLilyY; i++) {
                            Piece auxPosition = builderGameTable.getTable().getGrid()[currentWaterLilyX][i];
                            builderGameTable.getTable().getGrid()[currentWaterLilyX][i] = builderGameTable.getTable().getGrid()[currentWaterLilyX][i + 1];
                            builderGameTable.getTable().getGrid()[currentWaterLilyX][i + 1] = auxPosition;
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
    public void moveWaterLilyRight() {
        if (this.currentWaterLilyY != -1 && this.builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class) {
            if (currentWaterLilyY + 1 != 5) {
                if (builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY + 1].getClass() == Water.class) {
                    Piece auxPosition = builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY + 1];
                    builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY + 1] = builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY];
                    builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    this.notifyBoardPanelUpdate();
                    this.notifyHideControlPanel();
                } else {
                    int x = -1;
                    for (int i = currentWaterLilyY + 1; i <= 4; i++) {
                        if (builderGameTable.getTable().getGrid()[currentWaterLilyX][i].getClass() == Water.class) {
                            x = i;
                        }
                    }
                    if (x != -1) {
                        for (int i = x; i > currentWaterLilyY; i--) {
                            Piece auxPosition = builderGameTable.getTable().getGrid()[currentWaterLilyX][i];
                            builderGameTable.getTable().getGrid()[currentWaterLilyX][i] = builderGameTable.getTable().getGrid()[currentWaterLilyX][i - 1];
                            builderGameTable.getTable().getGrid()[currentWaterLilyX][i - 1] = auxPosition;
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
    public String getPiece(int col, int row) {
//        return (gameBoard[col][row] == null ? null : gameBoard[col][row].getImage());
        return (builderGameTable.getTable().getGrid()[col][row] == null ? null : builderGameTable.getTable().getGrid()[col][row].getImage());
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
        if (currentRotation == "red") {
            return (builderRedFlowerTable.getTable().getGrid()[col][row] == null ? null : builderRedFlowerTable.getTable().getGrid()[col][row].getImage());
        } else {
            return (builderYellowFlowerTable.getTable().getGrid()[col][row] == null ? null : builderYellowFlowerTable.getTable().getGrid()[col][row].getImage());
        }

    }

    @Override
    public Piece getFlowerType(int col, int row) {
        if (currentRotation == "red") {
            return builderRedFlowerTable.getTable().getGrid()[col][row];
        } else {
            return builderYellowFlowerTable.getTable().getGrid()[col][row];
        }
    }

    @Override
    public void setFlowerType(int col, int row, Piece piece) {
        if (currentRotation == "red") {
            builderRedFlowerTable.getTable().getGrid()[col][row] = piece;
        } else {
            builderYellowFlowerTable.getTable().getGrid()[col][row] = piece;
        }
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
