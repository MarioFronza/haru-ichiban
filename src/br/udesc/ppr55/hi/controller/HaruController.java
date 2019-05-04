package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.controller.observer.Observer;
import br.udesc.ppr55.hi.model.*;
import br.udesc.ppr55.hi.model.abstractfactory.AbstractPieceFactory;
import br.udesc.ppr55.hi.model.abstractfactory.PieceFactory;
import br.udesc.ppr55.hi.model.builder.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joao Pedro Schmitz, Mario Fronza
 * @since 07/04/2019
 */
public class HaruController implements IHaruController {

    //Fases do jogo
    final private String ADD_FLOWER = "add_flower";
    final private String CHOOSE_FLOWER_VALUE = "choose_flower_value";
    final private String CHOOSE_WATERLILY = "choose_waterlily";
    final private String MOVE_WATERLILY = "move_waterlily";
    final private String CHOOSE_DARK_WATERLILY = "choose_dark_waterlily";


    private static HaruController instance; //Singleton
    private AbstractPieceFactory factory; //Abstract Factory
    private Piece[][] scorePanel;

    private Director director; //Builder
    private Builder builderGameTable;
    private Builder builderRedFlowerTable;
    private Builder builderYellowFlowerTable;

    private List<Piece> redPlayerPanel;
    private List<Piece> yellowPlayerPanel;

    private List<Observer> observers; //Observer

    private Piece currentRedFlower = null; //Flor atual do jogador vermelho
    private Piece currentYellowFlower = null; //Flor atual do jogador amarelo

    private int currentWaterLilyX = -1;
    private int currentWaterLilyY = -1;
    private String currentRotation = "red"; //No momento o jogador vermelho sempre começa
    private String currentPhase; //Fase atual


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
        this.redPlayerPanel = new ArrayList<>();
        this.yellowPlayerPanel = new ArrayList<>();
    }

    @Override
    public void initializeFlowerPanel() {
        this.builderRedFlowerTable = new BuildRedFlowerTable();
        this.builderYellowFlowerTable = new BuildYellowFlowerTable();

        this.director = new Director(builderRedFlowerTable);
        this.director.build();

        this.director = new Director(builderYellowFlowerTable);
        this.director.build();
    }

    @Override
    public void setGardeners(String redName, String yellowName) {
        this.redGardener = (RedGardener) getCurrentFlowerTable(4, 0);
        this.yellowGardener = (YellowGardener) getCurrentFlowerTable(4, 1);

        this.redGardener.setName(redName);
        this.yellowGardener.setName(yellowName);

        notifyHideControlPanel();
        this.currentPhase = ADD_FLOWER;
        notifyMessage("Cada jogador deve adicionar três flores");
    }

    @Override
    public void addFlower(int x, int y) {
        if (currentPhase.equals(ADD_FLOWER) && (getCurrentFlowerTable(x, y).getClass() != RedGardener.class) && (getCurrentFlowerTable(x, y).getClass() != YellowGardener.class) && (getCurrentFlowerTable(x, y).getClass() != Stone.class) && (getFlowerPlayerPanel().size() < 3)) {
            addFlowerPlayerPanel(getCurrentFlowerTable(x, y));
            setCurrentFlowerTable(x, y, factory.createStone(0));
            if (getFlowerPlayerPanel().size() == 3) setAppropriateRotation();
            notifyPlayerPanelUpdate();
            notifyFlowersPanelUpdate();
            if (redPlayerPanel.size() == 3 && yellowPlayerPanel.size() == 3) {
                this.currentPhase = CHOOSE_FLOWER_VALUE;
                this.notifyMessage("Cada jogardor deve escolher uma flor");
            }
        }
    }

    @Override
    public void chooseFlower(int x, int y) {
        if (getCurrentFlower() != null) {
            this.notifyMessage("Você já escolheu uma flor");
        } else {
            if (currentPhase.equals(CHOOSE_FLOWER_VALUE)) {
                setCurrentFlower(getFlowerPlayerPanel().get(y));
                getFlowerPlayerPanel().remove(y);
                setAppropriateRotation();
                if (checkFlowerValue()) {
                    if (redGardener.isJunior()) {
                        this.currentRotation = "red";
                    } else {
                        this.currentRotation = "yellow";
                    }
                    this.currentPhase = CHOOSE_WATERLILY;
                    notifyMessage("Cada jogador deve escolher uma vitória régia");
                }
            }
            notifyFlowersPanelUpdate();
            notifyPlayerPanelUpdate();
        }
    }

    @Override
    public void eyePressed() {
        if (getFlowerPlayerPanel().size() < 3) {
            notifyMessage("Você precisa escolher todas as flores antes");
        } else {
            notifyShowFlowerNumber();
        }
    }

    @Override
    public void eyeReleased() {
        notifyShowFlower();
    }

    @Override
    public void chooseWaterLily(int x, int y) {
        if (currentPhase.equals(CHOOSE_DARK_WATERLILY)) {
            this.currentWaterLilyX = x;
            this.currentWaterLilyY = y;
            if (builderGameTable.getTable().getGrid()[x][y].getClass() == WaterLily.class) {
                this.builderGameTable.getTable().getGrid()[x][y] = factory.createDarkWaterLily();
                setAppropriateRotation();
                notifyBoardPanelUpdate();
                notifyFlowersPanelUpdate();
                notifyPlayerPanelUpdate();
                setCurrentFlower(null);
                currentPhase = ADD_FLOWER;
                notifyMessage("Cada jogador deve escolher uma flor");
            } else notifyMessage("Posição inválida");
        } else if (currentPhase.equals(CHOOSE_WATERLILY)) {
            this.currentWaterLilyX = x;
            this.currentWaterLilyY = y;
            if (getJuniorPlayer()) {
                if (builderGameTable.getTable().getGrid()[x][y].getClass() == DarkWaterLily.class) {
                    getCurrentFlower().setImage("images/water-lily-dark-with-" + getCurrentRotation() + "-petal.png");
                    this.builderGameTable.getTable().getGrid()[x][y] = getCurrentFlower();
                    setCurrentFlower(null);
                    setAppropriateRotation();
                } else {
                    notifyMessage("Posição inválida");
                }
            } else {
                getCurrentFlower().setImage("images/water-lily-with-" + getCurrentRotation() + "-petal.png");
                this.builderGameTable.getTable().getGrid()[x][y] = getCurrentFlower();
                setCurrentFlower(null);
                setAppropriateRotation();
                this.currentPhase = MOVE_WATERLILY;
                notifyMessage("Cada jogador deve mover uma vitória régia");
            }
            notifyBoardPanelUpdate();
            notifyFlowersPanelUpdate();
            notifyPlayerPanelUpdate();
        } else if (builderGameTable.getTable().getGrid()[x][y].getClass() != Water.class && currentPhase == MOVE_WATERLILY) {
            setCurrentFlower(null);
            this.currentWaterLilyX = x;
            this.currentWaterLilyY = y;
            notifyShowControlPanel();
        }
    }

    @Override
    public void moveWaterLilyDown() {
        if (this.currentWaterLilyX != -1 && builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class && currentPhase == MOVE_WATERLILY) {
            if (currentWaterLilyX + 1 != 5) {
                if (builderGameTable.getTable().getGrid()[currentWaterLilyX + 1][currentWaterLilyY].getClass() == Water.class) {
                    Piece auxPosition = builderGameTable.getTable().getGrid()[currentWaterLilyX + 1][currentWaterLilyY];
                    this.builderGameTable.getTable().getGrid()[currentWaterLilyX + 1][currentWaterLilyY] = builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY];
                    this.builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    verifyNextPhase();
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
                            this.builderGameTable.getTable().getGrid()[i][currentWaterLilyY] = builderGameTable.getTable().getGrid()[i - 1][currentWaterLilyY];
                            this.builderGameTable.getTable().getGrid()[i - 1][currentWaterLilyY] = auxPosition;
                        }
                        verifyNextPhase();
                    } else this.notifyMessage("Não é possível realizar este movimento!");
                }
            } else this.notifyMessage("Não é possível realizar este movimento!");
        } else this.notifyMessage("Escolha uma vitória régia");
    }

    @Override
    public void moveWaterLilyUp() {
        if (this.currentWaterLilyX != -1 && builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class && currentPhase == MOVE_WATERLILY) {
            if (currentWaterLilyX - 1 != -1) {
                if (builderGameTable.getTable().getGrid()[currentWaterLilyX - 1][currentWaterLilyY].getClass() == Water.class) {
                    Piece auxPosition = builderGameTable.getTable().getGrid()[currentWaterLilyX - 1][currentWaterLilyY];
                    this.builderGameTable.getTable().getGrid()[currentWaterLilyX - 1][currentWaterLilyY] = builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY];
                    this.builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    verifyNextPhase();
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
                            this.builderGameTable.getTable().getGrid()[i][currentWaterLilyY] = builderGameTable.getTable().getGrid()[i + 1][currentWaterLilyY];
                            this.builderGameTable.getTable().getGrid()[i + 1][currentWaterLilyY] = auxPosition;
                        }
                        verifyNextPhase();
                    } else this.notifyMessage("Não é possível realizar este movimento!");
                }
            } else this.notifyMessage("Não é possível realizar este movimento!");
        } else this.notifyMessage("Escolha uma vitória régia");
    }

    @Override
    public void moveWaterLilyLeft() {
        if (this.currentWaterLilyY != -1 && builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class && currentPhase == MOVE_WATERLILY) {
            if (currentWaterLilyY - 1 != -1) {
                if (builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY - 1].getClass() == Water.class) {
                    Piece auxPosition = builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY - 1];
                    this.builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY - 1] = builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY];
                    this.builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    verifyNextPhase();
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
                            this.builderGameTable.getTable().getGrid()[currentWaterLilyX][i] = builderGameTable.getTable().getGrid()[currentWaterLilyX][i + 1];
                            this.builderGameTable.getTable().getGrid()[currentWaterLilyX][i + 1] = auxPosition;
                        }
                        verifyNextPhase();
                    } else this.notifyMessage("Não é possível realizar este movimento!");
                }
            } else this.notifyMessage("Não é possível realizar este movimento!");
        } else this.notifyMessage("Escolha uma vitória régia");
    }

    @Override
    public void moveWaterLilyRight() {
        if (this.currentWaterLilyY != -1 && this.builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class && currentPhase == MOVE_WATERLILY) {
            if (currentWaterLilyY + 1 != 5) {
                if (builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY + 1].getClass() == Water.class) {
                    Piece auxPosition = builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY + 1];
                    this.builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY + 1] = builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY];
                    this.builderGameTable.getTable().getGrid()[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    verifyNextPhase();
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
                            this.builderGameTable.getTable().getGrid()[currentWaterLilyX][i] = builderGameTable.getTable().getGrid()[currentWaterLilyX][i - 1];
                            this.builderGameTable.getTable().getGrid()[currentWaterLilyX][i - 1] = auxPosition;
                        }
                        verifyNextPhase();
                    } else this.notifyMessage("Não é possível realizar este movimento!");
                }
            } else this.notifyMessage("Não é possível realizar este movimento!");
        } else this.notifyMessage("Escolha uma vitória régia");

    }

    @Override
    public String getCurrentRotation() {
        return currentRotation;
    }

    @Override
    public String getPiece(int col, int row) {
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
        return getCurrentFlowerTable(col, row) == null ? null : getCurrentFlowerTable(col, row).getImage();
    }

    @Override
    public Piece getCurrentFlowerTable(int col, int row) {
        if (currentRotation.equals("red")) return builderRedFlowerTable.getTable().getGrid()[col][row];
        else return builderYellowFlowerTable.getTable().getGrid()[col][row];
    }

    @Override
    public void setCurrentFlowerTable(int col, int row, Piece piece) {
        if (currentRotation.equals("red")) builderRedFlowerTable.getTable().getGrid()[col][row] = piece;
        else builderYellowFlowerTable.getTable().getGrid()[col][row] = piece;
    }

    @Override
    public void addFlowerPlayerPanel(Piece piece) {
        if (currentRotation.equals("red")) this.redPlayerPanel.add(piece);
        else this.yellowPlayerPanel.add(piece);
    }

    @Override
    public List<Piece> getFlowerPlayerPanel() {
        if (currentRotation.equals("red")) return redPlayerPanel;
        else return yellowPlayerPanel;
    }

    @Override
    public Piece getCurrentFlower() {
        if (currentRotation.equals("red")) return currentRedFlower;
        else return currentYellowFlower;
    }

    @Override
    public boolean getJuniorPlayer() {
        if (currentRotation.equals("red")) return redGardener.isJunior();
        else return yellowGardener.isJunior();
    }

    @Override
    public String getCurrentNamePlayer() {
        if (currentRotation.equals("red")) return redGardener.getName();
        else return yellowGardener.getName();
    }

    @Override
    public void setCurrentFlower(Piece piece) {
        if (currentRotation.equals("red")) this.currentRedFlower = piece;
        else this.currentYellowFlower = piece;
    }

    @Override
    public void setAppropriateRotation() {
        if (currentRotation.equals("red")) this.currentRotation = "yellow";
        else this.currentRotation = "red";
    }

    @Override
    public boolean checkFlowerValue() {
        if (currentRedFlower != null && currentYellowFlower != null) {
            RedFlower redFlower = (RedFlower) currentRedFlower;
            YellowFlower yellowFlower = (YellowFlower) currentYellowFlower;
            this.yellowGardener.setJunior(false);
            this.redGardener.setJunior(false);
            if (redFlower.getNumber() > yellowFlower.getNumber()) {
                this.yellowGardener.setJunior(true);
                notifyMessage(yellowGardener.getName() + " é o jardineiro junior");
                return true;
            } else if (redFlower.getNumber() < yellowFlower.getNumber()) {
                this.redGardener.setJunior(true);
                notifyMessage(redGardener.getName() + " é o jardineiro junior");
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
        if (currentRotation.equals("red")) {
            RedFlower redFlower = (RedFlower) getFlowerPlayerPanel().get(row);
            return redFlower.getNumber();
        } else {
            YellowFlower yellowFlower = (YellowFlower) getFlowerPlayerPanel().get(row);
            return yellowFlower.getNumber();
        }
    }

    @Override
    public void verifyNextPhase() {
        notifyBoardPanelUpdate();
        notifyHideControlPanel();
        if (this.getJuniorPlayer()) {
            this.currentPhase = MOVE_WATERLILY;
            setAppropriateRotation();
        } else {
            this.currentPhase = CHOOSE_DARK_WATERLILY;
            notifyMessage(getCurrentNamePlayer() + " escolha a próxima vitória régia escura");
        }
        notifyPlayerPanelUpdate();
        notifyFlowersPanelUpdate();
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
    public void notifyMessage(String message) {
        for (Observer observer : observers) {
            observer.message(message);
        }
    }
}
