package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

/**
 * Board panel
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 13/04/2019
 * @version 1.0.0
 */
public class ChoiceFrame extends JFrame {

    private IHaruController haruController;
    private JFrame choiceFrame;
    private JPanel choicePanel;
    private JPanel p1Panel;
    private JPanel p2Panel;
    private JPanel errorPanel;
    private JButton buttonStart;
    private JTextField player1;
    private JTextField player2;
    private JLabel messageError;

    public ChoiceFrame() {
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            this.choiceFrame = new JFrame();
            this.choicePanel = new JPanel();
            this.p1Panel = new JPanel();
            this.p2Panel = new JPanel();
            this.errorPanel = new JPanel();
            this.choiceFrame.setVisible(false);
            this.choiceFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.choiceFrame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/choice-frame.png")))));
            this.choiceFrame.setSize(400, 100);
            this.choiceFrame.setResizable(false);
            this.choiceFrame.setTitle("Haru Ichiban - New Game");
            this.choiceFrame.setLayout(new GridBagLayout());
            this.choiceFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    System.exit(0);
                }
            });
            haruController = HaruController.getInstance();
            initComponents();
            addComponents();
        } catch (IOException ex) {
            Logger.getLogger(ChoiceFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void start() {
        this.choiceFrame.pack();
        this.choiceFrame.setLocationRelativeTo(null);
        this.choiceFrame.setVisible(true);
    }

    private void initComponents() {
        BoxLayout choiceBoxlayout = new BoxLayout(this.choicePanel, BoxLayout.Y_AXIS);
        this.choicePanel.setLayout(choiceBoxlayout);
        this.choicePanel.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200)));
        this.choicePanel.setOpaque(false);
        
        BoxLayout errorBoxlayout = new BoxLayout(this.errorPanel, BoxLayout.Y_AXIS);
        this.errorPanel.setLayout(errorBoxlayout);
        this.errorPanel.setBorder(new EmptyBorder(new Insets(10, 0, 10, 0)));
        this.errorPanel.setOpaque(false);

        BoxLayout p1Boxlayout = new BoxLayout(this.p1Panel, BoxLayout.Y_AXIS);
        this.p1Panel.setLayout(p1Boxlayout);
        this.p1Panel.setBorder(new EmptyBorder(new Insets(10, 0, 10, 0)));
        this.p1Panel.setOpaque(false);

        BoxLayout p2Boxlayout = new BoxLayout(this.p2Panel, BoxLayout.Y_AXIS);
        this.p2Panel.setLayout(p2Boxlayout);
        this.p2Panel.setBorder(new EmptyBorder(new Insets(10, 0, 20, 0)));
        this.p2Panel.setOpaque(false);
    }

    private boolean validateFields() {
        boolean error = false;
        if (player1.getText().equals("") || player2.getText().equals("")) {
            this.messageError.setText("Campos obrigatórios");
            error = true;
        } else {
            if (player2.getText().equals(player1.getText()) || player1.getText().equals(player2.getText())) {
                this.messageError.setText("Nomes iguais");
                error = true;
            }
        }
        return !error;
    }

    private void addComponents() {
        JLabel labelRed = new JLabel("Player 1 (Red): ");
        labelRed.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelRed.setForeground(new Color(52, 52, 52));
        this.p1Panel.add(labelRed);

        this.player1 = new JTextField();
        this.player1.setSize(300, 20);
        this.player1.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.p1Panel.add(player1);

        this.messageError = new JLabel();
        this.messageError.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.messageError.setForeground(Color.RED);

        JLabel labelYellow = new JLabel("Player 2 (Yellow): ");
        labelYellow.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelYellow.setForeground(new Color(52, 52, 52));
        this.p2Panel.add(labelYellow);

        this.player2 = new JTextField();
        this.player2.setSize(300, 20);
        this.player2.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.p2Panel.add(player2);

        this.buttonStart = new JButton();
        this.buttonStart.setText("Novo Jogo");
        this.buttonStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.buttonStart.addActionListener((ActionEvent e) -> {
            if (this.validateFields()) {
                JFrame haruIchiban = new MainFrame();
                haruIchiban.setVisible(true);
                this.choiceFrame.setVisible(false);
                haruController.setGardeners(player1.getText(), player2.getText());
            }
        });
        this.buttonStart.setForeground(new Color(52, 52, 52));
        this.buttonStart.setOpaque(true);
        
        this.messageError = new JLabel();
        this.messageError.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.messageError.setForeground(Color.RED);
        this.errorPanel.add(this.messageError);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        this.choicePanel.add(this.messageError);
        this.choicePanel.add(this.p1Panel);
        this.choicePanel.add(this.p2Panel);
        this.choicePanel.add(this.buttonStart);
        this.choiceFrame.add(this.choicePanel, gbc);
    }

}