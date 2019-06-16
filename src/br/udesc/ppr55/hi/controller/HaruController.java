package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.controller.observer.Observer;
import br.udesc.ppr55.hi.controller.state.AddFlower;
import br.udesc.ppr55.hi.controller.state.ChooseDarkWaterLily;
import br.udesc.ppr55.hi.controller.state.HaruState;
import br.udesc.ppr55.hi.model.strategy.ConcretStrategyDown;
import br.udesc.ppr55.hi.model.strategy.ConcretStrategyLeft;
import br.udesc.ppr55.hi.model.strategy.ConcretStrategyRight;
import br.udesc.ppr55.hi.model.strategy.ConcretStrategyUp;
import br.udesc.ppr55.hi.model.*;
import br.udesc.ppr55.hi.model.abstractfactory.AbstractPieceFactory;
import br.udesc.ppr55.hi.model.abstractfactory.PieceFactory;
import br.udesc.ppr55.hi.model.builder.*;
import br.udesc.ppr55.hi.controller.visitor.ConcreteVisitorPiece;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class that has the game logic
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 07/04/2019
 */
public class HaruController implements IHaruController {

    private static HaruController instance;
    private AbstractPieceFactory factory;

    private HaruState haruState;

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
        this.observers = new ArrayList<>();
        this.haruState = new AddFlower(this);
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
        notifyMessage("Each player must add three flowers.");
    }

    @Override
    public void addFlower(int x, int y) {
//        if (currentPhase.equals(ADD_FLOWER) && (getCurrentFlowerTable(x, y).getClass() != RedGardener.class) && (getCurrentFlowerTable(x, y).getClass() != YellowGardener.class) && (getCurrentFlowerTable(x, y).getClass() != Stone.class) && (getFlowerPlayerPanel().size() < 3)) {
//            addFlowerPlayerPanel((Flower) getCurrentFlowerTable(x, y));
//            setCurrentFlowerTable(x, y, factory.createStoneWithoutNumber());
//            if (getFlowerPlayerPanel().size() == 3) {
//                setAppropriateRotation();
//            }
//            notifyPlayerPanelUpdate();
//            notifyFlowersPanelUpdate();
//            if (redPlayerPanel.size() == 3 && yellowPlayerPanel.size() == 3) {
//                setPreviousPhase(ADD_FLOWER, CHOOSE_FLOWER_VALUE);
//                this.notifyMessage("Each player must choose a flower from your panel.");
//            }
//        }
        this.haruState.addFlower(x, y);
    }

    @Override
    public void chooseFlower(int x, int y) {
        if (getCurrentFlower() != null) {
            this.notifyMessage("You have already chosen a flower.");
        } else {
            this.haruState.chooseFlower(x, y);
//            if (currentPhase.equals(CHOOSE_FLOWER_VALUE)) {
//                setCurrentFlower(getFlowerPlayerPanel().get(x));
//                setRemovedFlower(getFlowerPlayerPanel().remove(x));
//                setAppropriateRotation();
//                if (checkFlowerValue()) {
//                    if (redGardener.isJunior()) {
//                        this.currentRotation = "red";
//                        setPreviousPhase(CHOOSE_FLOWER_VALUE, CHOOSE_WATERLILY);
//                        notifyMessage("Each player must choose a water lily.");
//                    } else if (yellowGardener.isJunior()) {
//                        this.currentRotation = "yellow";
//                        setPreviousPhase(CHOOSE_FLOWER_VALUE, CHOOSE_WATERLILY);
//                        notifyMessage("Each player must choose a water lily.");
//                    } else {
//                        notifyMessage("Each player must choose again a flower in the panel.");
//                    }
//                }
//            }
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
        haruState.chooseWaterLily(x, y);
//        if (currentPhase.equals(CHOOSE_FROG)) {
//            if (getGridGameTable()[x][y].getClass() == WaterLily.class) {
//                if (currentFrog.equals("red")) {
//                    getGridGameTable()[x][y] = factory.createRedFrog();
//                } else {
//                    getGridGameTable()[x][y] = factory.createYellowFrog();
//                }
//                updateChooseWaterLily();
//                if (previousPhase.equals(CHOOSE_WATERLILY)) {
//                    setPreviousPhase(CHOOSE_FROG, MOVE_WATERLILY);
//                    notifyMessage(getCurrentNamePlayer() + " should move a water lily.");
//                } else {
//                    setPreviousPhase(CHOOSE_FROG, ADD_FLOWER);
//                    notifyMessage("Each player must add a new flower in your panel.");
//                }
//            } else {
//                notifyMessage("Invalid position.");
//            }
//        } else if (currentPhase.equals(CHOOSE_DARK_WATERLILY)) {
//            if (getGridGameTable()[x][y].getClass() == WaterLily.class || getGridGameTable()[x][y].getClass() == YellowFrog.class || getGridGameTable()[x][y].getClass() == RedFrog.class) {
//                if (getGridGameTable()[x][y].getClass() == YellowFrog.class || getGridGameTable()[x][y].getClass() == RedFrog.class) {
//                    if (getGridGameTable()[x][y].getClass() == YellowFrog.class) {
//                        this.currentFrog = "yellow";
//                    } else {
//                        currentFrog = "red";
//                    }
//                    setPreviousPhase(CHOOSE_DARK_WATERLILY, CHOOSE_FROG);
//                    notifyMessage("Choose a new place for your frog.");
//                } else {
//                    setPreviousPhase(CHOOSE_DARK_WATERLILY, ADD_FLOWER);
//                    notifyMessage("Each player must add a new flower in your panel.");
//                }
//                getGridGameTable()[x][y] = factory.createDarkWaterLily(false);
//                updateChooseWaterLily();
//            } else {
//                notifyMessage("Invalid position.");
//            }
//        } else if (currentPhase.equals(CHOOSE_WATERLILY)) {
//            this.currentWaterLilyX = x;
//            this.currentWaterLilyY = y;
//            if (getJuniorPlayer()) {
//                if (getGridGameTable()[x][y].getClass() == DarkWaterLily.class) {
//                    DarkWaterLily darkWaterLily = (DarkWaterLily) getGridGameTable()[x][y];
//                    getCurrentFlower().setImage("images/water-lily-dark-with-" + getCurrentRotation() + "-petal.png");
//                    if (darkWaterLily.isOriginal())
//                        getCurrentFlower().setOriginalDarkWaterLily(true);
//                    getGridGameTable()[x][y] = getCurrentFlower();
//                    setCurrentFlower(null);
//                    setAppropriateRotation();
//                } else {
//                    notifyMessage("Invalid position.");
//                }
//            } else if (getGridGameTable()[x][y].getClass() == WaterLily.class || getGridGameTable()[x][y].getClass() == YellowFrog.class || getGridGameTable()[x][y].getClass() == RedFrog.class) {
//                getCurrentFlower().setImage("images/water-lily-with-" + getCurrentRotation() + "-petal.png");
//                if (getGridGameTable()[x][y].getClass() == YellowFrog.class || getGridGameTable()[x][y].getClass() == RedFrog.class) {
//                    if (getGridGameTable()[x][y].getClass() == YellowFrog.class) {
//                        this.currentFrog = "yellow";
//                    } else {
//                        currentFrog = "red";
//                    }
//                    getGridGameTable()[x][y] = getCurrentFlower();
//                    setPreviousPhase(CHOOSE_WATERLILY, CHOOSE_FROG);
//                    notifyMessage("Choose a new place for your frog.");
//                } else {
//                    getGridGameTable()[x][y] = getCurrentFlower();
//                    setAppropriateRotation();
//                    setCurrentFlower(null);
//                    notifyMessage(getCurrentNamePlayer() + " should move a water lily.");
//                    setPreviousPhase(CHOOSE_WATERLILY, MOVE_WATERLILY);
//                }
//            } else {
//                notifyMessage("Invalid position.");
//            }
//            visitGameTable();
//            notifyBoardPanelUpdate();
//            notifyFlowersPanelUpdate();
//            notifyPlayerPanelUpdate();
//        } else if (getGridGameTable()[x][y].getClass() != Water.class && currentPhase == MOVE_WATERLILY) {
//            setCurrentFlower(null);
//            this.currentWaterLilyX = x;
//            this.currentWaterLilyY = y;
//            notifyShowControlPanel();
//        }
    }

    @Override
    public void moveWaterLilyDown() {
        if (this.currentWaterLilyX != -1 && getGridGameTable()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class) {
            Table table = builderGameTable.getTable();
            table.setMoveStrategyWaterLily(new ConcretStrategyDown());
            table.setCurrentX(currentWaterLilyX);
            table.setCurrentY(currentWaterLilyY);
            if (table.move()) {
                verifyNextPhase();
            } else {
                this.notifyMessage("This move is not possible.");
            }
        } else {
            this.notifyMessage("Choose a water lily.");
        }
    }

    @Override
    public void moveWaterLilyUp() {
        if (this.currentWaterLilyX != -1 && getGridGameTable()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class) {
            Table table = builderGameTable.getTable();
            table.setMoveStrategyWaterLily(new ConcretStrategyUp());
            table.setCurrentX(currentWaterLilyX);
            table.setCurrentY(currentWaterLilyY);
            if (table.move()) {
                verifyNextPhase();
            } else {
                this.notifyMessage("This move is not possible.");
            }
        } else {
            this.notifyMessage("Choose a water lily.");
        }
    }

    @Override
    public void moveWaterLilyLeft() {
        if (this.currentWaterLilyY != -1 && getGridGameTable()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class) {
            Table table = builderGameTable.getTable();
            table.setMoveStrategyWaterLily(new ConcretStrategyLeft());
            table.setCurrentX(currentWaterLilyX);
            table.setCurrentY(currentWaterLilyY);
            if (table.move()) {
                verifyNextPhase();
            } else {
                this.notifyMessage("This move is not possible.");
            }
        } else {
            this.notifyMessage("Choose a water lily.");
        }
    }

    @Override
    public void moveWaterLilyRight() {
        if (this.currentWaterLilyY != -1 && getGridGameTable()[currentWaterLilyX][currentWaterLilyY].getClass() != Water.class) {
            Table table = builderGameTable.getTable();
            table.setMoveStrategyWaterLily(new ConcretStrategyRight());
            table.setCurrentX(currentWaterLilyX);
            table.setCurrentY(currentWaterLilyY);
            if (table.move()) {
                verifyNextPhase();
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
    public void setCurrentFrog(String currentFrog) {
        this.currentFrog = currentFrog;
    }

    @Override
    public void setCurrentWaterLilyX(int currentWaterLilyX) {
        this.currentWaterLilyX = currentWaterLilyX;
    }

    @Override
    public void setCurrentWaterLilyY(int currentWaterLilyY) {
        this.currentWaterLilyY = currentWaterLilyY;
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
    public String getCurrentFrog() {
        return currentFrog;
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

    public Gardener getRedGardener() {
        return redGardener;
    }

    public Gardener getYellowGardener() {
        return yellowGardener;
    }

    public void setCurrentRotation(String currentRotation) {
        this.currentRotation = currentRotation;
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
    public HaruState getHaruState() {
        return haruState;
    }

    public List<Flower> getRedPlayerPanel() {
        return redPlayerPanel;
    }

    public List<Flower> getYellowPlayerPanel() {
        return yellowPlayerPanel;
    }

    @Override
    public void setHaruState(HaruState haruState) {
        this.haruState = haruState;
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
    public AbstractPieceFactory getFactory() {
        return factory;
    }

    public String getPreviousPhase() {
        return previousPhase;
    }

    @Override
    public void setFactory(PieceFactory pieceFactory) {
        this.factory = pieceFactory;
        this.initializeBoard();
        this.initializeScorePanel();
        this.initializePlayerPanel();
        this.initializeFlowerPanel();
    }

    @Override
    public void visitGameTable() {
        boolean redWinner;
        boolean yellowWinner;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                redWinner = verifyRedWinner(i, j);
                yellowWinner = verifyYellowWinner(i, j);

                if (redWinner)
                    notifyMessage("Vermelho pontuou");

                if (yellowWinner)
                    notifyMessage("Amarelo pontuou");

                if (yellowWinner || redWinner) {
                    this.builderGameTable.getTable().accept(new ConcreteVisitorPiece());
                    initializePlayerPanel();
                    initializeFlowerPanel();
                    setHaruState(new AddFlower(this));
                    notifyMessage("Each player must add three flowers.");
                    break;
                }
            }
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
    public boolean verifyRedWinner(int x, int y) {
        Piece[][] grid = this.builderGameTable.getTable().getGrid();
        if (x == 0 && y == 0 && grid[x][y].getClass() == RedFlower.class && grid[x + 1][y + 1].getClass() == RedFlower.class && grid[x + 2][y + 2].getClass() == RedFlower.class && grid[x + 3][y + 3].getClass() == RedFlower.class && grid[x + 4][y + 4].getClass() == RedFlower.class)
            return true;
        if (x == 4 && y == 0 && grid[x][y].getClass() == RedFlower.class && grid[x - 1][y + 1].getClass() == RedFlower.class && grid[x - 2][y + 2].getClass() == RedFlower.class && grid[x - 3][y + 3].getClass() == RedFlower.class && grid[x - 4][y + 4].getClass() == RedFlower.class)
            return true;
        if (x <= 1 && y <= 1 && grid[x][y].getClass() == RedFlower.class && grid[x + 1][y + 1].getClass() == RedFlower.class && grid[x + 2][y + 2].getClass() == RedFlower.class && grid[x + 3][y + 3].getClass() == RedFlower.class)
            return true;
        if (x >= 3 && y <= 1 && grid[x][y].getClass() == RedFlower.class && grid[x - 1][y + 1].getClass() == RedFlower.class && grid[x - 2][y + 2].getClass() == RedFlower.class && grid[x - 3][y + 3].getClass() == RedFlower.class)
            return true;
        if (x <= 1 && grid[x][y].getClass() == RedFlower.class && grid[x + 1][y].getClass() == RedFlower.class && grid[x + 2][y].getClass() == RedFlower.class && grid[x + 3][y].getClass() == RedFlower.class)
            return true;
        if (y <= 1 && grid[x][y].getClass() == RedFlower.class && grid[x][y + 1].getClass() == RedFlower.class && grid[x][y + 2].getClass() == RedFlower.class && grid[x][y + 3].getClass() == RedFlower.class)
            return true;
        if (x <= 3 && y <= 3 && grid[x][y].getClass() == RedFlower.class && grid[x + 1][y].getClass() == RedFlower.class && grid[x][y + 1].getClass() == RedFlower.class && grid[x + 1][y + 1].getClass() == RedFlower.class)
            return true;
        return false;
    }

    @Override
    public boolean verifyYellowWinner(int x, int y) {
        Piece[][] grid = this.builderGameTable.getTable().getGrid();
        if (x == 0 && y == 0 && grid[x][y].getClass() == YellowFlower.class && grid[x + 1][y + 1].getClass() == YellowFlower.class && grid[x + 2][y + 2].getClass() == YellowFlower.class && grid[x + 3][y + 3].getClass() == YellowFlower.class && grid[x + 4][y + 4].getClass() == YellowFlower.class)
            return true;
        if (x == 4 && y == 0 && grid[x][y].getClass() == YellowFlower.class && grid[x - 1][y + 1].getClass() == YellowFlower.class && grid[x - 2][y + 2].getClass() == YellowFlower.class && grid[x - 3][y + 3].getClass() == YellowFlower.class && grid[x - 4][y + 4].getClass() == YellowFlower.class)
            return true;
        if (x <= 1 && y <= 1 && grid[x][y].getClass() == YellowFlower.class && grid[x + 1][y + 1].getClass() == YellowFlower.class && grid[x + 2][y + 2].getClass() == YellowFlower.class && grid[x + 3][y + 3].getClass() == YellowFlower.class)
            return true;
        if (x >= 3 && y <= 1 && grid[x][y].getClass() == YellowFlower.class && grid[x - 1][y + 1].getClass() == YellowFlower.class && grid[x - 2][y + 2].getClass() == YellowFlower.class && grid[x - 3][y + 3].getClass() == YellowFlower.class)
            return true;
        if (x <= 1 && grid[x][y].getClass() == YellowFlower.class && grid[x + 1][y].getClass() == YellowFlower.class && grid[x + 2][y].getClass() == YellowFlower.class && grid[x + 3][y].getClass() == YellowFlower.class)
            return true;
        if (y <= 1 && grid[x][y].getClass() == YellowFlower.class && grid[x][y + 1].getClass() == YellowFlower.class && grid[x][y + 2].getClass() == YellowFlower.class && grid[x][y + 3].getClass() == YellowFlower.class)
            return true;
        if (x <= 3 && y <= 3 && grid[x][y].getClass() == YellowFlower.class && grid[x + 1][y].getClass() == YellowFlower.class && grid[x][y + 1].getClass() == YellowFlower.class && grid[x + 1][y + 1].getClass() == YellowFlower.class)
            return true;
        return false;
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
    public void setPreviousPhase(String previousPhase) {
        this.previousPhase = previousPhase;
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
        visitGameTable();
        notifyBoardPanelUpdate();
        notifyHideControlPanel();
        setAppropriateRotation();
        setCurrentFlower(null);
        notifyPlayerPanelUpdate();
        notifyFlowersPanelUpdate();
        notifyMessage(getCurrentNamePlayer() + ", choose the next dark water lily.");
        setPreviousPhase("move_waterlily");
        setHaruState(new ChooseDarkWaterLily(this));
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
