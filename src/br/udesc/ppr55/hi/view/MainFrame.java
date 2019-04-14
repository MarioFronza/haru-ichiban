package br.udesc.ppr55.hi.view;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;


public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel mainPanel;
    private BoardPanel boardPanel;
    private ScorePanel scorePanel;
    private PlayerPanel playerPanel;
    private FlowersPanel flowersPanel;

    private Dimension dimension;
    private LayoutManager layout;


    private GridBagConstraints c = new GridBagConstraints();

    public MainFrame() {
        this.dimension = new Dimension(750, 600);

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

    }

    private void addComponents() {
        this.setContentPane(mainPanel);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        this.mainPanel.add(scorePanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        this.mainPanel.add(boardPanel, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = -1;
        c.gridwidth = 1;
        this.mainPanel.add(playerPanel, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 2;
        c.gridy = 0;
        this.mainPanel.add(flowersPanel, c);
    }


}


