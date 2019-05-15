package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.controller.observer.Observer;
import br.udesc.ppr55.hi.model.*;
import br.udesc.ppr55.hi.model.abstractfactory.AbstractPieceFactory;
import br.udesc.ppr55.hi.model.abstractfactory.PieceFactory;
import br.udesc.ppr55.hi.model.builder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller class that has the game logic
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 07/04/2019
 */
public class HaruController implements IHaruController {

    final private String ADD_FLOWER = "add_flower";
    final private String CHOOSE_FLOWER_VALUE = "choose_flower_value";
    final private String CHOOSE_WATERLILY = "choose_waterlily";
    final private String MOVE_WATERLILY = "move_waterlily";
    final private String CHOOSE_DARK_WATERLILY = "choose_dark_waterlily";
    final private String CHOOSE_FROG = "choose_frog";

    private static HaruController instance;
    private AbstractPieceFactory factory;

    private Director director; //Builder
    private Builder builderGameTable;
    private Builder builderRedFlowerTable;
    private Builder builderYellowFlowerTable;
    private Builder builderScorePanel;

    private List<Flower> redPlayerPanel;
    private List<Flower> yellowPlayerPanel;

    private List<Observer> observers;

    private Flower currentRedFlower = null;
    private Flower currentYellowFlower = null;
    private Flower redFlowerRemoved = null;
    private Flower yellowFlowerRemoved = null;
    private String currentFrog;

    private int currentWaterLilyX = -1;
    private int currentWaterLilyY = -1;
    private String currentRotation = "red";
    private String currentPhase;
    private String previousPhase;

    private Gardener redGardener;
    private Gardener yellowGardener;

    public static HaruController getInstance() {
        if (instance == null) {
            instance = new HaruController();
        }

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
        this.builderGameTable = new BuildGameTable();
        this.director = new Director(builderGameTable);
        this.director.build(factory);
    }

    @Override
    public void initializeScorePanel() {
        this.builderScorePanel = new BuildScorePanel();
        this.director = new Director(builderScorePanel);
        this.director.build(factory);
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
        this.director.build(factory);

        this.director = new Director(builderYellowFlowerTable);
        this.director.build(factory);
    }

    @Override
    public void setGardeners(String redName, String yellowName) {
        this.redGardener = (RedGardener) getCurrentFlowerTable(4, 0);
        this.yellowGardener = (YellowGardener) getCurrentFlowerTable(4, 1);

        this.redGardener.setName(redName);
        this.yellowGardener.setName(yellowName);

        notifyHideControlPanel();
        this.currentPhase = ADD_FLOWER;
        notifyMessage("Each player must add three flowers.");
    }

    @Override
    public void addFlower(int x, int y) {
        if (currentPhase.equals(ADD_FLOWER) && (getCurrentFlowerTable(x, y).getClass() != RedGardener.class) && (getCurrentFlowerTable(x, y).getClass() != YellowGardener.class) && (getCurrentFlowerTable(x, y).getClass() != Stone.class) && (getFlowerPlayerPanel().size() < 3)) {
            addFlowerPlayerPanel((Flower) getCurrentFlowerTable(x, y));
            setCurrentFlowerTable(x, y, factory.createStoneWithoutNumber());
            if (getFlowerPlayerPanel().size() == 3) {
                setAppropriateRotation();
            }
            notifyPlayerPanelUpdate();
            notifyFlowersPanelUpdate();
            if (redPlayerPanel.size() == 3 && yellowPlayerPanel.size() == 3) {
                setNextPhase(ADD_FLOWER, CHOOSE_FLOWER_VALUE);
                this.notifyMessage("Each player must choose a flower from your panel.");
            }
        }
    }

    @Override
    public void chooseFlower(int x, int y) {
        if (getCurrentFlower() != null) {
            this.notifyMessage("You have already chosen a flower.");
        } else {
            if (currentPhase.equals(CHOOSE_FLOWER_VALUE)) {
                setCurrentFlower(getFlowerPlayerPanel().get(x));
                setRemovedFlower(getFlowerPlayerPanel().remove(x));
                setAppropriateRotation();
                if (checkFlowerValue()) {
                    if (redGardener.isJunior()) {
                        this.currentRotation = "red";
                        setNextPhase(CHOOSE_FLOWER_VALUE, CHOOSE_WATERLILY);
                        notifyMessage("Each player must choose a water lily.");
                    } else if (yellowGardener.isJunior()) {
                        this.currentRotation = "yellow";
                        setNextPhase(CHOOSE_FLOWER_VALUE, CHOOSE_WATERLILY);
                        notifyMessage("Each player must choose a water lily.");
                    } else {
                        notifyMessage("Cada jogador deve escolher novamente uma flor");
                    }

                }
            }
            notifyFlowersPanelUpdate();
            notifyPlayerPanelUpdate();
        }
    }

    @Override
    public void eyePressed() {
        if (getFlowerPlayerPanel().size() < 3) {
            notifyMessage("You have to choose all the flowers before.");
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
        if (currentPhase.equals(CHOOSE_FROG)) {
            if (getGridGameTable()[x][y].getClass() == WaterLily.class) {
                if (currentFrog.equals("red")) {
                    getGridGameTable()[x][y] = factory.createRedFrog();
                } else {
                    getGridGameTable()[x][y] = factory.createYellowFrog();
                }
                updateChooseWaterLily();
                if (previousPhase.equals(CHOOSE_WATERLILY)) {
                    setNextPhase(CHOOSE_FROG, MOVE_WATERLILY);
                    notifyMessage(getCurrentNamePlayer() + " should move a water lily.");
                } else {
                    setNextPhase(CHOOSE_FROG, ADD_FLOWER);
                    notifyMessage("Each player must add a new flower in your panel.");
                }
            } else {
                notifyMessage("Invalid position.");
            }
        } else if (currentPhase.equals(CHOOSE_DARK_WATERLILY)) {
            if (getGridGameTable()[x][y].getClass() == WaterLily.class || getGridGameTable()[x][y].getClass() == YellowFrog.class || getGridGameTable()[x][y].getClass() == RedFrog.class) {
                if (getGridGameTable()[x][y].getClass() == YellowFrog.class || getGridGameTable()[x][y].getClass() == RedFrog.class) {
                    if (getGridGameTable()[x][y].getClass() == YellowFrog.class) {
                        this.currentFrog = "yellow";
                    } else {
                        currentFrog = "red";
                    }
                    setNextPhase(CHOOSE_DARK_WATERLILY, CHOOSE_FROG);
                    notifyMessage("Choose a new place for your frog.");
                } else {
                    setNextPhase(CHOOSE_DARK_WATERLILY, ADD_FLOWER);
                    notifyMessage("Each player must add a new flower in your panel.");
                }
                getGridGameTable()[x][y] = factory.createDarkWaterLily();
                updateChooseWaterLily();
            } else {
                notifyMessage("Invalid position.");
            }
        } else if (currentPhase.equals(CHOOSE_WATERLILY)) {
            this.currentWaterLilyX = x;
            this.currentWaterLilyY = y;
            if (getJuniorPlayer()) {
                if (getGridGameTable()[x][y].getClass() == DarkWaterLily.class) {
                    getCurrentFlower().setImage("images/water-lily-dark-with-" + getCurrentRotation() + "-petal.png");
                    getGridGameTable()[x][y] = getCurrentFlower();
                    setCurrentFlower(null);
                    setAppropriateRotation();
                } else {
                    notifyMessage("Invalid position.");
                }
            } else if (getGridGameTable()[x][y].getClass() == WaterLily.class || getGridGameTable()[x][y].getClass() == YellowFrog.class || getGridGameTable()[x][y].getClass() == RedFrog.class) {
                getCurrentFlower().setImage("images/water-lily-with-" + getCurrentRotation() + "-petal.png");
                if (getGridGameTable()[x][y].getClass() == YellowFrog.class || getGridGameTable()[x][y].getClass() == RedFrog.class) {
                    if (getGridGameTable()[x][y].getClass() == YellowFrog.class) {
                        this.currentFrog = "yellow";
                    } else {
                        currentFrog = "red";
                    }
                    getGridGameTable()[x][y] = getCurrentFlower();
                    setNextPhase(CHOOSE_WATERLILY, CHOOSE_FROG);
                    notifyMessage("Choose a new place for your frog.");
                } else {
                    getGridGameTable()[x][y] = getCurrentFlower();
                    setAppropriateRotation();
                    setCurrentFlower(null);
                    notifyMessage(getCurrentNamePlayer() + " should move a water lily.");
                    setNextPhase(CHOOSE_WATERLILY, MOVE_WATERLILY);
                }
            } else {
                notifyMessage("Invalid position.");
            }
            notifyBoardPanelUpdate();
            notifyFlowersPanelUpdate();
            notifyPlayerPanelUpdate();
        } else if (getGridGameTable()[x][y].getClass() != Water.class && currentPhase == MOVE_WATERLILY) {
            setCurrentFlower(null);
            this.currentWaterLilyX = x;
            this.currentWaterLilyY = y;
            notifyShowControlPanel();
        }
    }

    @Override
    public void moveWaterLilyDown() {
        if (this.currentWaterLilyX != -1 && getGridGameTable()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class && currentPhase == MOVE_WATERLILY) {
            if (currentWaterLilyX + 1 != 5) {
                if (getGridGameTable()[currentWaterLilyX + 1][currentWaterLilyY].getClass() == Water.class) {
                    Piece auxPosition = getGridGameTable()[currentWaterLilyX + 1][currentWaterLilyY];
                    getGridGameTable()[currentWaterLilyX + 1][currentWaterLilyY] = getGridGameTable()[currentWaterLilyX][currentWaterLilyY];
                    getGridGameTable()[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    verifyNextPhase();
                } else {
                    int x = -1;
                    for (int i = currentWaterLilyX + 1; i <= 4; i++) {
                        if (getGridGameTable()[i][currentWaterLilyY].getClass() == Water.class) {
                            x = i;
                        }
                    }
                    if (x != -1) {
                        for (int i = x; i > currentWaterLilyX; i--) {
                            Piece auxPosition = getGridGameTable()[i][currentWaterLilyY];
                            getGridGameTable()[i][currentWaterLilyY] = getGridGameTable()[i - 1][currentWaterLilyY];
                            getGridGameTable()[i - 1][currentWaterLilyY] = auxPosition;
                        }
                        verifyNextPhase();
                    } else {
                        this.notifyMessage("This move is not possible.");
                    }
                }
            } else {
                this.notifyMessage("This move is not possible.");
            }
        } else {
            this.notifyMessage("Choose a water lily.");
        }
    }

    @Override
    public void moveWaterLilyUp() {
        if (this.currentWaterLilyX != -1 && getGridGameTable()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class && currentPhase == MOVE_WATERLILY) {
            if (currentWaterLilyX - 1 != -1) {
                if (getGridGameTable()[currentWaterLilyX - 1][currentWaterLilyY].getClass() == Water.class) {
                    Piece auxPosition = getGridGameTable()[currentWaterLilyX - 1][currentWaterLilyY];
                    getGridGameTable()[currentWaterLilyX - 1][currentWaterLilyY] = getGridGameTable()[currentWaterLilyX][currentWaterLilyY];
                    getGridGameTable()[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    verifyNextPhase();
                } else {
                    int x = -1;
                    for (int i = currentWaterLilyX - 1; i >= 0; i--) {
                        if (getGridGameTable()[i][currentWaterLilyY].getClass() == Water.class) {
                            x = i;
                        }
                    }
                    if (x != -1) {
                        for (int i = x; i < currentWaterLilyX; i++) {
                            Piece auxPosition = getGridGameTable()[i][currentWaterLilyY];
                            getGridGameTable()[i][currentWaterLilyY] = getGridGameTable()[i + 1][currentWaterLilyY];
                            getGridGameTable()[i + 1][currentWaterLilyY] = auxPosition;
                        }
                        verifyNextPhase();
                    } else {
                        this.notifyMessage("This move is not possible.");
                    }
                }
            } else {
                this.notifyMessage("This move is not possible.");
            }
        } else {
            this.notifyMessage("Choose a water lily.");
        }
    }

    @Override
    public void moveWaterLilyLeft() {
        if (this.currentWaterLilyY != -1 && getGridGameTable()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class && currentPhase == MOVE_WATERLILY) {
            if (currentWaterLilyY - 1 != -1) {
                if (getGridGameTable()[currentWaterLilyX][currentWaterLilyY - 1].getClass() == Water.class) {
                    Piece auxPosition = getGridGameTable()[currentWaterLilyX][currentWaterLilyY - 1];
                    getGridGameTable()[currentWaterLilyX][currentWaterLilyY - 1] = getGridGameTable()[currentWaterLilyX][currentWaterLilyY];
                    getGridGameTable()[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    verifyNextPhase();
                } else {
                    int x = -1;
                    for (int i = currentWaterLilyY - 1; i >= 0; i--) {
                        if (getGridGameTable()[currentWaterLilyX][i].getClass() == Water.class) {
                            x = i;
                        }
                    }
                    if (x != -1) {
                        for (int i = x; i < currentWaterLilyY; i++) {
                            Piece auxPosition = getGridGameTable()[currentWaterLilyX][i];
                            getGridGameTable()[currentWaterLilyX][i] = getGridGameTable()[currentWaterLilyX][i + 1];
                            getGridGameTable()[currentWaterLilyX][i + 1] = auxPosition;
                        }
                        verifyNextPhase();
                    } else {
                        this.notifyMessage("This move is not possible.");
                    }
                }
            } else {
                this.notifyMessage("This move is not possible.");
            }
        } else {
            this.notifyMessage("Choose a water lily.");
        }
    }

    @Override
    public void moveWaterLilyRight() {
        if (this.currentWaterLilyY != -1 && getGridGameTable()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class && currentPhase == MOVE_WATERLILY) {
            if (currentWaterLilyY + 1 != 5) {
                if (getGridGameTable()[currentWaterLilyX][currentWaterLilyY + 1].getClass() == Water.class) {
                    Piece auxPosition = getGridGameTable()[currentWaterLilyX][currentWaterLilyY + 1];
                    getGridGameTable()[currentWaterLilyX][currentWaterLilyY + 1] = getGridGameTable()[currentWaterLilyX][currentWaterLilyY];
                    getGridGameTable()[currentWaterLilyX][currentWaterLilyY] = auxPosition;
                    verifyNextPhase();
                } else {
                    int x = -1;
                    for (int i = currentWaterLilyY + 1; i <= 4; i++) {
                        if (getGridGameTable()[currentWaterLilyX][i].getClass() == Water.class) {
                            x = i;
                        }
                    }
                    if (x != -1) {
                        for (int i = x; i > currentWaterLilyY; i--) {
                            Piece auxPosition = getGridGameTable()[currentWaterLilyX][i];
                            getGridGameTable()[currentWaterLilyX][i] = getGridGameTable()[currentWaterLilyX][i - 1];
                            getGridGameTable()[currentWaterLilyX][i - 1] = auxPosition;
                        }
                        verifyNextPhase();
                    } else {
                        this.notifyMessage("This move is not possible.");
                    }
                }
            } else {
                this.notifyMessage("This move is not possible.");
            }
        } else {
            this.notifyMessage("Choose a water lily.");
        }

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
        return (builderScorePanel.getTable().getGrid()[col][row] == null ? null : builderScorePanel.getTable().getGrid()[col][row].getImage());
    }

    @Override
    public String getPlayerFlower(int row) {
        return (getFlowerPlayerPanel().size() <= row || getFlowerPlayerPanel().isEmpty()) ? null : getFlowerPlayerPanel().get(row).getImage();
    }

    @Override
    public String getFlower(int col, int row) {
        return getCurrentFlowerTable(col, row) == null ? null : getCurrentFlowerTable(col, row).getImage();
    }

    @Override
    public Piece getCurrentFlowerTable(int col, int row) {
        if (currentRotation.equals("red")) {
            return builderRedFlowerTable.getTable().getGrid()[col][row];
        } else {
            return builderYellowFlowerTable.getTable().getGrid()[col][row];
        }
    }

    @Override
    public void setCurrentFlowerTable(int col, int row, Piece piece) {
        if (currentRotation.equals("red")) {
            builderRedFlowerTable.getTable().getGrid()[col][row] = piece;
        } else {
            builderYellowFlowerTable.getTable().getGrid()[col][row] = piece;
        }
    }

    @Override
    public void addFlowerPlayerPanel(Flower flower) {
        if (currentRotation.equals("red")) {
            this.redPlayerPanel.add(flower);
        } else {
            this.yellowPlayerPanel.add(flower);
        }
    }

    @Override
    public void setRemovedFlower(Flower flower) {
        if (currentRotation.equals("red")) {
            this.redFlowerRemoved = flower;
        } else {
            this.yellowFlowerRemoved = flower;
        }
    }

    @Override
    public List<Flower> getFlowerPlayerPanel() {
        if (currentRotation.equals("red")) {
            return redPlayerPanel;
        } else {
            return yellowPlayerPanel;
        }
    }

    @Override
    public Flower getCurrentFlower() {
        if (currentRotation.equals("red")) {
            return currentRedFlower;
        } else {
            return currentYellowFlower;
        }
    }

    @Override
    public Piece[][] getGridGameTable() {
        return builderGameTable.getTable().getGrid();
    }

    @Override
    public boolean getJuniorPlayer() {
        if (currentRotation.equals("red")) {
            return redGardener.isJunior();
        } else {
            return yellowGardener.isJunior();
        }
    }

    @Override
    public String getCurrentNamePlayer() {
        if (currentRotation.equals("red")) {
            return redGardener.getName();
        } else {
            return yellowGardener.getName();
        }
    }

    @Override
    public void setCurrentFlower(Flower flower) {
        if (currentRotation.equals("red")) {
            this.currentRedFlower = flower;
        } else {
            this.currentYellowFlower = flower;
        }
    }

    @Override
    public void setNextPhase(String previousPhase, String nextPhase) {
        this.previousPhase = previousPhase;
        this.currentPhase = nextPhase;
    }

    @Override
    public void setAppropriateRotation() {
        if (currentRotation.equals("red")) {
            this.currentRotation = "yellow";
        } else {
            this.currentRotation = "red";
        }
    }

    @Override
    public void updateChooseWaterLily() {
        setAppropriateRotation();
        notifyBoardPanelUpdate();
        notifyFlowersPanelUpdate();
        notifyPlayerPanelUpdate();
        setCurrentFlower(null);
    }

    @Override
    public boolean checkFlowerValue() {
        if (currentRedFlower != null && currentYellowFlower != null) {
            Flower redFlower = currentRedFlower;
            Flower yellowFlower = currentYellowFlower;
            System.out.println(redFlower.getNumber());
            System.out.println(yellowFlower.getNumber());
            this.yellowGardener.setJunior(false);
            this.redGardener.setJunior(false);
            if (redFlower.getNumber() > yellowFlower.getNumber()) {
                this.yellowGardener.setJunior(true);
                notifyMessage(yellowGardener.getName() + " is the junior gardener.");
                return true;
            } else if (redFlower.getNumber() < yellowFlower.getNumber()) {
                this.redGardener.setJunior(true);
                notifyMessage(redGardener.getName() + " is the junior gardener.");
                return true;
            } else {
//                Random random = new Random();
//                int number = random.nextInt(1);
//                if (number == 0) {
//                    this.redGardener.setJunior(true);
//                    notifyMessage(redGardener.getName() + " is the junior gardener.");
//                } else {
//                    this.yellowGardener.setJunior(true);
//                    notifyMessage(yellowGardener.getName() + " is the junior gardener.");
//                }
                this.currentRedFlower = null;
                this.currentYellowFlower = null;
                this.yellowGardener.setJunior(false);
                this.redGardener.setJunior(false);
                this.redPlayerPanel.add(redFlowerRemoved);
                this.yellowPlayerPanel.add(yellowFlowerRemoved);


                return true;
            }
        }
        return false;
    }

    @Override
    public void verifyNextPhase() {
        notifyBoardPanelUpdate();
        notifyHideControlPanel();
        setAppropriateRotation();
        setCurrentFlower(null);
        notifyPlayerPanelUpdate();
        notifyFlowersPanelUpdate();
        notifyMessage(getCurrentNamePlayer() + ", choose the next dark water lily.");
        setNextPhase(MOVE_WATERLILY, CHOOSE_DARK_WATERLILY);
    }

    @Override
    public int getFlowerNumber(int col, int row) {
        if (currentRotation.equals("red")) {
            Flower redFlower = getFlowerPlayerPanel().get(col);
            return redFlower.getNumber();
        } else {
            Flower yellowFlower = getFlowerPlayerPanel().get(col);
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
    public void notifyMessage(String message) {
        for (Observer observer : observers) {
            observer.message(message);
        }
    }
}
