package br.udesc.ppr55.hi.view;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel mainPanel;

    private BoardPanel boardPanel;
    private ScorePanel scorePanel;
    private PlayerPanel playerPanel;
    private FlowersPanel flowersPanel;
    private ControlPanel controlPanel;

    private JPanel testPanel = new JPanel();
    private JPanel testPanel2 = new JPanel();

    private Dimension dimension;


    private GridBagConstraints c = new GridBagConstraints();

    public MainFrame() {
        this.dimension = new Dimension(800, 600);

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

        this.boardPanel = new BoardPanel();
        this.scorePanel = new ScorePanel();
        this.playerPanel = new PlayerPanel();
        this.flowersPanel = new FlowersPanel();
        this.controlPanel = new ControlPanel();

        this.testPanel.setPreferredSize(new Dimension(100, 150));
        this.testPanel.setBackground(new Color(243, 0, 1, 218));
        this.testPanel2.setSize(200, 200);
        this.testPanel2.setBackground(new Color(243, 204, 7, 218));

    }

    private void addComponents() {
        this.setContentPane(mainPanel);

//        c.fill = GridBagConstraints.VERTICAL;
//        c.gridx = 0;
//        c.gridy = 1;
//        this.mainPanel.add(testPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        this.mainPanel.add(playerPanel, c);

//        c.fill = GridBagConstraints.VERTICAL;
//        c.gridx = 2;
//        c.gridy = 1;
//        this.mainPanel.add(testPanel2, c);

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


//        c.fill = GridBagConstraints.VERTICAL;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.gridheight = 2;
//        this.mainPanel.add(scorePanel, c);
//
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 1;
//        c.gridy = 0;
//        this.mainPanel.add(boardPanel, c);
//
//        c.fill = GridBagConstraints.VERTICAL;
//        c.gridx = 2;
//        c.gridy = 0;
//        this.mainPanel.add(flowersPanel, c);
//
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridx = 1;
//        c.gridy = 2;
//        this.mainPanel.add(playerPanel, c);
//
//
//        c.fill = GridBagConstraints.VERTICAL;
//        c.gridx = 2;
//        c.gridy = 2;

//        this.mainPanel.add(controlPanel, c);
    }


}


