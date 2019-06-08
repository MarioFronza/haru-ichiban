package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.model.abstractfactory.PieceFactory;
import br.udesc.ppr55.hi.view.command.CommandInvoker;
import br.udesc.ppr55.hi.controller.observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

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

    private IHaruController haruController;
    private CommandInvoker commandInvoker;

    public MainFrame() {
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            this.commandInvoker = new CommandInvoker();
            this.haruController = HaruController.getInstance();
            this.haruController.setFactory(new PieceFactory());
            this.haruController.addObserver(this);
            super.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/main-frame.png")))));
            super.setFocusable(true);
            super.setFocusTraversalKeysEnabled(false);
            super.setTitle("Haru Ichiban");
            super.setDefaultCloseOperation(EXIT_ON_CLOSE);
            super.setResizable(false);
            super.setLayout(new BorderLayout());
            super.pack();
            super.setLocationRelativeTo(null);
            super.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    System.exit(0);
                }
            });
            initComponents();
            addComponents();

            super.getContentPane().add(mainPanel);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initComponents() {
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new GridBagLayout());
        this.mainPanel.setOpaque(false);

        this.boardPanel = new BoardPanel(this.haruController, this.commandInvoker);
        this.scorePanel = new ScorePanel();
        this.playerPanel = new PlayerPanel(this.haruController, this.commandInvoker);
        this.flowersPanel = new FlowersPanel(this.haruController, this.commandInvoker);
        this.eyePanel = new EyePanel(this.haruController);
        this.controlPanel = new ControlPanel(this.haruController, this.commandInvoker);
    }

    private void addComponents() {
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 5;
        c.gridy = 0;
        this.mainPanel.add(eyePanel, c);
        
        c.gridx = 5;
        c.gridy = 4;
        this.mainPanel.add(controlPanel, c);

        c.gridx = 5;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 3;
        this.mainPanel.add(playerPanel, c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 5;
        c.insets = new Insets(0, 0, 0, 40);
        this.mainPanel.add(scorePanel, c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 5;
        c.gridheight = 5;
        c.insets = new Insets(0, 0, 0, 40);
        this.mainPanel.add(boardPanel, c);
        
        c.gridx = 6;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 4;
        c.insets = new Insets(0, 80, 0, 0);
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
