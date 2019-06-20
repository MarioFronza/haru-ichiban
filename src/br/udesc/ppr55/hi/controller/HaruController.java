package br.udesc.ppr55.hi.controller;

import br.udesc.ppr55.hi.controller.observer.Observer;
import br.udesc.ppr55.hi.controller.state.AddFlower;
import br.udesc.ppr55.hi.controller.state.HaruState;
import br.udesc.ppr55.hi.controller.strategy.ConcretStrategyDown;
import br.udesc.ppr55.hi.controller.strategy.ConcretStrategyLeft;
import br.udesc.ppr55.hi.controller.strategy.ConcretStrategyRight;
import br.udesc.ppr55.hi.controller.strategy.ConcretStrategyUp;
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
    private boolean moved = false;
    private int round = 1;

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
        setState(new AddFlower(this));
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
        this.haruState.addFlower(x, y);
    }

    @Override
    public void chooseFlower(int x, int y) {
        if (getCurrentFlower() != null) {
            this.notifyMessage("You have already chosen a flower.");
        } else {
            this.haruState.chooseFlower(x, y);
            notifyFlowersPanelUpdate();
            notifyPlayerPanelUpdate();
        }
    }

    @Override
    public void eyePressed() {
        if (getFlowerPlayerPanel().size() < 3) {
            //TODO
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
    public int getRound() {
        return round;
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
    public boolean isMoved() {
        return moved;
    }

    @Override
    public void setMoved(boolean moved) {
        this.moved = moved;
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
    public void setRound(int round) {
        this.round = round;
    }

    @Override
    public Gardener getRedGardener() {
        return redGardener;
    }

    @Override
    public Gardener getYellowGardener() {
        return yellowGardener;
    }

    @Override
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
    public List<Flower> getRedPlayerPanel() {
        return redPlayerPanel;
    }

    @Override
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

    @Override
    public void setState(HaruState haruState) {
        this.haruState = haruState;
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
    public boolean visitGameTable() {
        boolean redWinner = false;
        boolean yellowWinner = false;
        boolean redImperial = false;
        boolean yellowImperial = false;
        int redScore = 0;
        int yellowScore = 0;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                redScore = redScore + verifyRedWinner(i, j);

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                yellowScore = yellowScore + verifyYellowWinner(i, j);

        if (redScore > 0) {
            redGardener.setScore(redScore);
            notifyMessage("Red gardener punctuated");
            redWinner = true;
            updateScore();
            if (redGardener.getScore() >= 5) {
                redImperial = true;

            }
        }
        if (yellowScore > 0) {
            yellowGardener.setScore(yellowScore);
            notifyMessage("Yellow gardener punctuated");
            yellowWinner = true;
            updateScore();
            if (yellowGardener.getScore() >= 5) {
                yellowImperial = true;

            }
        }

        if (yellowImperial && redImperial) {
            if (yellowGardener.getScore() > redGardener.getScore()) {
                notifyMessage("Yellow is the imperial gardener");
                notifyEndGame();
                return false;
            } else if (redGardener.getScore() > yellowGardener.getScore()) {
                notifyMessage("Red is the imperial gardener");
                notifyEndGame();
                return false;
            } else {
                newRound();
            }
        } else if (yellowImperial || redImperial) {
            if (yellowImperial) {
                notifyMessage("Yellow is the imperial gardener");
                notifyEndGame();
                return false;
            }
            if (redImperial) {
                notifyMessage("Red is the imperial gardener");
                notifyEndGame();
                return false;
            }
        }

        if (yellowWinner || redWinner || getRound() >= 8) {
            newRound();
            return true;
        }
        return false;
    }

    @Override
    public void newRound() {
        this.builderGameTable.getTable().accept(new ConcreteVisitorPiece());
        currentYellowFlower = null;
        currentRedFlower = null;
        initializePlayerPanel();
        initializeFlowerPanel();
        setRound(1);
        setHaruState(new AddFlower(this));
    }

    @Override
    public void updateScore() {
        Gardener redAuxGardener = (Gardener) factory.createRedGardener();
        redAuxGardener.setImage("images/rock-pink.png");

        Gardener yellowAuxGardener = (Gardener) factory.createYellowGardener();
        yellowAuxGardener.setImage("images/rock-yellow.png");

        initializeScorePanel();
        if (yellowGardener.getScore() != 0)
            builderScorePanel.getTable().getGrid()[9 - yellowGardener.getScore()][0] = yellowAuxGardener;
        if (redGardener.getScore() != 0)
            builderScorePanel.getTable().getGrid()[redGardener.getScore() - 1][0] = redAuxGardener;
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
    public int verifyRedWinner(int x, int y) {
        Piece[][] grid = this.builderGameTable.getTable().getGrid();
        if (x == 0 && y == 0 && grid[x][y].getClass() == RedFlower.class && grid[x + 1][y + 1].getClass() == RedFlower.class && grid[x + 2][y + 2].getClass() == RedFlower.class && grid[x + 3][y + 3].getClass() == RedFlower.class && grid[x + 4][y + 4].getClass() == RedFlower.class) {
            return 5;
        } else if (x == 4 && y == 0 && grid[x][y].getClass() == RedFlower.class && grid[x - 1][y + 1].getClass() == RedFlower.class && grid[x - 2][y + 2].getClass() == RedFlower.class && grid[x - 3][y + 3].getClass() == RedFlower.class && grid[x - 4][y + 4].getClass() == RedFlower.class) {
            return 5;
        } else {
            if (x <= 1 && y <= 1 && grid[x][y].getClass() == RedFlower.class && grid[x + 1][y + 1].getClass() == RedFlower.class && grid[x + 2][y + 2].getClass() == RedFlower.class && grid[x + 3][y + 3].getClass() == RedFlower.class) {
                return 3;
            }
            if (x >= 3 && y <= 1 && grid[x][y].getClass() == RedFlower.class && grid[x - 1][y + 1].getClass() == RedFlower.class && grid[x - 2][y + 2].getClass() == RedFlower.class && grid[x - 3][y + 3].getClass() == RedFlower.class) {
                return 3;
            }
            if (x <= 1 && grid[x][y].getClass() == RedFlower.class && grid[x + 1][y].getClass() == RedFlower.class && grid[x + 2][y].getClass() == RedFlower.class && grid[x + 3][y].getClass() == RedFlower.class) {
                return 2;
            }
            if (y <= 1 && grid[x][y].getClass() == RedFlower.class && grid[x][y + 1].getClass() == RedFlower.class && grid[x][y + 2].getClass() == RedFlower.class && grid[x][y + 3].getClass() == RedFlower.class) {
                return 2;
            }
            if (x <= 3 && y <= 3 && grid[x][y].getClass() == RedFlower.class && grid[x + 1][y].getClass() == RedFlower.class && grid[x][y + 1].getClass() == RedFlower.class && grid[x + 1][y + 1].getClass() == RedFlower.class) {
                return 1;
            }
        }

        return 0;
    }

    @Override
    public int verifyYellowWinner(int x, int y) {
        Piece[][] grid = this.builderGameTable.getTable().getGrid();
        if (x == 0 && y == 0 && grid[x][y].getClass() == YellowFlower.class && grid[x + 1][y + 1].getClass() == YellowFlower.class && grid[x + 2][y + 2].getClass() == YellowFlower.class && grid[x + 3][y + 3].getClass() == YellowFlower.class && grid[x + 4][y + 4].getClass() == YellowFlower.class) {
            return 5;
        } else if (x == 4 && y == 0 && grid[x][y].getClass() == YellowFlower.class && grid[x - 1][y + 1].getClass() == YellowFlower.class && grid[x - 2][y + 2].getClass() == YellowFlower.class && grid[x - 3][y + 3].getClass() == YellowFlower.class && grid[x - 4][y + 4].getClass() == YellowFlower.class) {
            return 5;
        } else {
            if (x <= 1 && y <= 1 && grid[x][y].getClass() == YellowFlower.class && grid[x + 1][y + 1].getClass() == YellowFlower.class && grid[x + 2][y + 2].getClass() == YellowFlower.class && grid[x + 3][y + 3].getClass() == YellowFlower.class) {
                return 3;
            }
            if (x >= 3 && y <= 1 && grid[x][y].getClass() == YellowFlower.class && grid[x - 1][y + 1].getClass() == YellowFlower.class && grid[x - 2][y + 2].getClass() == YellowFlower.class && grid[x - 3][y + 3].getClass() == YellowFlower.class) {
                return 3;
            }
            if (x <= 1 && grid[x][y].getClass() == YellowFlower.class && grid[x + 1][y].getClass() == YellowFlower.class && grid[x + 2][y].getClass() == YellowFlower.class && grid[x + 3][y].getClass() == YellowFlower.class) {
                return 2;
            }
            if (y <= 1 && grid[x][y].getClass() == YellowFlower.class && grid[x][y + 1].getClass() == YellowFlower.class && grid[x][y + 2].getClass() == YellowFlower.class && grid[x][y + 3].getClass() == YellowFlower.class) {
                return 2;
            }
            if (x <= 3 && y <= 3 && grid[x][y].getClass() == YellowFlower.class && grid[x + 1][y].getClass() == YellowFlower.class && grid[x][y + 1].getClass() == YellowFlower.class && grid[x + 1][y + 1].getClass() == YellowFlower.class) {
                return 1;
            }
        }
        return 0;
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
        this.moved = true;
        setAppropriateRotation();
        if (!visitGameTable())
            notifyMessage(getCurrentNamePlayer() + ", choose the next dark water lily.");

        notifyBoardPanelUpdate();
        notifyHideControlPanel();
        setCurrentFlower(null);
        notifyPlayerPanelUpdate();
        notifyFlowersPanelUpdate();
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
    public void notifyEndGame() {
        for (Observer observer : observers) {
            observer.endGame();
        }
    }

    @Override
    public void notifyMessage(String message) {
        for (Observer observer : observers) {
            observer.message(message);
        }
    }
}
