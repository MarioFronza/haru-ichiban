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

    //Fases
    final String ADD_FLOWER = "add_flower";
    final String CHOOSE_FLOWER_VALUE = "choose_flower_value";
    final String CHOOSE_FLOWER = "choose_flower";
    final String CHOOSE_WATERLILY = "choose_waterlily";
    final String MOVE_WATERLILY = "move_waterlily";


    private static HaruController instance;
    private AbstractPieceFactory factory;
    private Piece[][] scorePanel;

    private Builder builderGameTable;
    private Builder builderRedFlowerTable;
    private Builder builderYellowFlowerTable;
    private Director director;

    private List<Piece> redPlayerPanel;
    private List<Piece> yellowPlayerPanel;

    private List<Observer> observers;

    private Piece currentRedFlower = null;
    private Piece currentYellowFlower = null;

    private int currentWaterLilyX = -1;
    private int currentWaterLilyY = -1;
    private String currentRotation = "red";
    private String currentPhase;


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

        notifyHideControlPanel();
        notifyErrorMessage(redGardener.getName() + " escolha três flores");
        this.currentPhase = ADD_FLOWER;
        System.out.println("Fase atual: adicionar flor");
    }

    @Override
    public void addFlower(int x, int y) {
        System.out.println("chegou aqui");
        if (currentPhase == ADD_FLOWER) {
            if (getFlowerType(x, y).getClass() != RedGardener.class && getFlowerType(x, y).getClass() != YellowGardener.class && getFlowerType(x, y).getClass() != Stone.class && getFlowerPlayerPanel().size() < 3) {
                addFlowerPlayerPanel(getFlowerType(x, y));
                setFlowerType(x, y, factory.createStone(0));
                notifyPlayerPanelUpdate();
                notifyFlowersPanelUpdate();
                if (getFlowerPlayerPanel().size() == 3) {
                    setAppropriateRotation();
                }
                if (redPlayerPanel.size() == 3 && yellowPlayerPanel.size() == 3) {
                    System.out.println("Fase atual: escolher valor da flor");
                    this.currentPhase = CHOOSE_FLOWER_VALUE;
                }
            }
        }
    }

    @Override
    public void chooseFlower(int x, int y) {
        if (getFlowerPlayerPanel().size() < 3) {
            this.notifyErrorMessage("Você precisa escolher todas as flores antes");
        } else if (getCurrentFlower() != null) {
            this.notifyErrorMessage("Você já escolheu uma flor");
        } else {
            if (currentPhase == CHOOSE_FLOWER_VALUE) {
                setCurrentFlower(getFlowerPlayerPanel().get(y));
                setAppropriateRotation();
                if (checkFlowerValue()) {
                    if (redGardener.isJunior()) {
                        currentRotation = "red";
                    } else {
                        currentRotation = "yellow";
                    }
                    setCurrentFlower(null);
                    currentPhase = CHOOSE_FLOWER;
                    System.out.println("Fase atual: escolher flor");
                }
            } else if (currentPhase == CHOOSE_FLOWER) {
                setCurrentFlower(getFlowerPlayerPanel().get(y));
                getFlowerPlayerPanel().remove(y);
                currentPhase = CHOOSE_WATERLILY;
                System.out.println("Fase atual: escolher vitória régia");
            }
            notifyFlowersPanelUpdate();
            notifyPlayerPanelUpdate();
        }
    }

    @Override
    public void eyePressed() {
        if (getFlowerPlayerPanel().size() < 3) {
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

        if (currentPhase == CHOOSE_WATERLILY) {
            currentWaterLilyX = x;
            currentWaterLilyY = y;

            if (getCurrentPlayer()) {
                if (builderGameTable.getTable().getGrid()[x][y].getClass() == DarkWaterLily.class) {
                    getCurrentFlower().setImage("images/water-lily-dark-with-" + getCurrentRotation() + "-petal.png");
                    builderGameTable.getTable().getGrid()[x][y] = getCurrentFlower();
                    setAppropriateRotation();
                    currentPhase = CHOOSE_FLOWER;
                    System.out.println("Escolher flor");
                } else {
                    notifyErrorMessage("Posição inválida");
                }
            } else {
                getCurrentFlower().setImage("images/water-lily-with-" + getCurrentRotation() + "-petal.png");
                builderGameTable.getTable().getGrid()[x][y] = getCurrentFlower();
                setAppropriateRotation();
                currentPhase = MOVE_WATERLILY;
                System.out.println("Fase atual: mover vitória régia");
            }
            setCurrentFlower(null);
            notifyBoardPanelUpdate();
            notifyFlowersPanelUpdate();
            notifyPlayerPanelUpdate();
        } else if (builderGameTable.getTable().getGrid()[x][y].getClass() != Water.class) {
            this.currentWaterLilyX = x;
            this.currentWaterLilyY = y;
            notifyShowControlPanel();
        }
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
    public String getCurrentRotation() {
        return currentRotation;
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
    public String getPlayerFlower(int col) {
        return (getFlowerPlayerPanel().size() <= col || getFlowerPlayerPanel().isEmpty()) ? null : getFlowerPlayerPanel().get(col).getImage();
    }

    @Override
    public String getFlower(int col, int row) {
        return getFlowerType(col, row) == null ? null : getFlowerType(col, row).getImage();
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
    public void addFlowerPlayerPanel(Piece piece) {
        if (currentRotation == "red") {
            redPlayerPanel.add(piece);
        } else {
            yellowPlayerPanel.add(piece);
        }
    }

    @Override
    public List<Piece> getFlowerPlayerPanel() {
        if (currentRotation == "red") {
            return redPlayerPanel;
        } else {
            return yellowPlayerPanel;
        }
    }

    @Override
    public Piece getCurrentFlower() {
        if (currentRotation == "red") {
            return currentRedFlower;
        } else {
            return currentYellowFlower;
        }
    }

    @Override
    public boolean getCurrentPlayer() {
        if (currentRotation == "red") {
            return redGardener.isJunior();
        } else {
            return yellowGardener.isJunior();
        }
    }

    @Override
    public void setCurrentFlower(Piece piece) {
        if (currentRotation == "red") {
            currentRedFlower = piece;
        } else {
            currentYellowFlower = piece;
        }
    }

    @Override
    public void setAppropriateRotation() {
        if (currentRotation == "red") {
            currentRotation = "yellow";
        } else {
            currentRotation = "red";
        }
    }

    //rever isso aqui
    @Override
    public boolean checkFlowerValue() {
        if (currentRedFlower != null && currentYellowFlower != null) {
            RedFlower redFlower = (RedFlower) currentRedFlower;
            YellowFlower yellowFlower = (YellowFlower) currentYellowFlower;
            if (redFlower.getNumber() > yellowFlower.getNumber()) {
                yellowGardener.setJunior(true);
                notifyErrorMessage(yellowGardener.getName() + " é o jardineiro junior");
                return true;
            } else if (redFlower.getNumber() < yellowFlower.getNumber()) {
                System.out.println(yellowFlower.getNumber());
                redGardener.setJunior(true);
                notifyErrorMessage(redGardener.getName() + " é o jardineiro junior");
                return true;
            } else {
                //Número igual
                return true;
            }
        }
        return false;
    }

    @Override
    public int getFlowerNumber(int col, int row) {
        if (currentRotation == "red") {
            RedFlower redFlower = (RedFlower) getFlowerPlayerPanel().get(row);
            return redFlower.getNumber();
        } else {
            YellowFlower yellowFlower = (YellowFlower) getFlowerPlayerPanel().get(row);
            return yellowFlower.getNumber();
        }
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
