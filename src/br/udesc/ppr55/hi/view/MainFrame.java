package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.controller.command.CommandInvoker;
import br.udesc.ppr55.hi.controller.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MainFrame extends JFrame implements Observer, KeyListener {

    private static final long serialVersionUID = 1L;

    private JPanel mainPanel;

    private BoardPanel boardPanel;
    private ScorePanel scorePanel;
    private PlayerPanel playerPanel;
    private FlowersPanel flowersPanel;
    private ControlPanel controlPanel;
    private EyePanel eyePanel;

    private JPanel testPanel = new JPanel();
    private JPanel testPanel2 = new JPanel();

    private Dimension dimension;

    private IHaruController haruController;
    private CommandInvoker commandInvoker;


    private GridBagConstraints c = new GridBagConstraints();

    public MainFrame() {
        this.dimension = new Dimension(800, 600);
        this.commandInvoker = new CommandInvoker();
        this.haruController = HaruController.getInstance();
        this.haruController.addObserver(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        super.setTitle("Haru Ichiban");
        super.setSize(dimension);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        this.initComponents();
        this.addComponents();
    }

    private void initComponents() {

        this.mainPanel = new JPanel();

        this.mainPanel.setLayout(new GridBagLayout());

        this.boardPanel = new BoardPanel(this.haruController, this.commandInvoker);
        this.scorePanel = new ScorePanel();
        this.playerPanel = new PlayerPanel(this.haruController, this.commandInvoker);
        this.flowersPanel = new FlowersPanel(this.haruController, this.commandInvoker);
        this.eyePanel = new EyePanel(this.haruController);
        this.controlPanel = new ControlPanel(this.haruController, this.commandInvoker);


        this.testPanel2.setSize(200, 200);
        this.testPanel2.setBackground(new Color(243, 204, 7, 218));

    }


    private void addComponents() {
        this.setContentPane(mainPanel);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 1;
        this.mainPanel.add(eyePanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        this.mainPanel.add(playerPanel, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 2;
        c.gridy = 1;
        this.mainPanel.add(controlPanel, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;

        this.mainPanel.add(scorePanel, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 1;
        c.gridy = 0;
        this.mainPanel.add(boardPanel, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 2;
        c.gridy = 0;
        this.mainPanel.add(flowersPanel, c);

    }


    @Override
    public void playerPanelUpdate() {
        this.playerPanel.update();
    }

    @Override
    public void boardPanelUpdate() {
        this.boardPanel.update();
    }

    @Override
    public void flowersPanelUpdate() {
        this.flowersPanel.update();
    }

    @Override
    public void showFlowerNumber() {
        this.playerPanel.showNumber();
    }

    @Override
    public void showFlower() {
        this.playerPanel.showFlower();
    }

    @Override
    public void errorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //Vamos deixar isso pro final
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Z) {
            this.commandInvoker.undo();
        } else if (e.getKeyCode() == KeyEvent.VK_Y) {
            this.commandInvoker.redo();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}


