package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.view.command.CommandInvoker;
import br.udesc.ppr55.hi.controller.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main frame
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 13/04/2019
 * @version 1.0.0
 */
public class MainFrame extends JFrame implements Observer {

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

    public static final Color BG_COLOR = new Color(178, 190, 195, 168);

    private GridBagConstraints c = new GridBagConstraints();

    public MainFrame() {
        this.dimension = new Dimension(1000, 700);
        this.commandInvoker = new CommandInvoker();
        this.haruController = HaruController.getInstance();
        this.haruController.addObserver(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        super.setTitle("Haru Ichiban");
        super.setSize(dimension);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        super.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
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
    public void showControlPanel() {
        this.controlPanel.setVisible(true);
    }

    @Override
    public void hideControlPanel() {
        this.controlPanel.setVisible(false);
    }

    @Override
    public void message(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

}
