package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoiceFrame extends JFrame {


    private JTextField redNameField;
    private JTextField yellowNameField;
    private JLabel redLabel;
    private JLabel yellowLabel;
    private JButton button;

    private GridBagConstraints c = new GridBagConstraints();

    private IHaruController haruController;

    public ChoiceFrame() {
        super.setLayout(new GridBagLayout());
        super.setSize(new Dimension(400, 100));
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        initComponents();
    }


    private void initComponents() {
        this.haruController = HaruController.getInstance();
        this.redNameField = new JTextField();
        this.redNameField.setPreferredSize(new Dimension(300, 20));
        this.yellowNameField = new JTextField();
        this.yellowNameField.setPreferredSize(new Dimension(300, 20));
        this.redLabel = new JLabel("Jogador vermelho");
        this.yellowLabel = new JLabel("Jogador amarelo");
        this.button = new JButton("Jogar");
        this.button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame haruIchiban = new MainFrame();
                haruIchiban.setVisible(true);
                setVisible(false);
                haruController.setGardeners(redNameField.getText(), yellowNameField.getText());
            }
        });

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        this.add(redLabel, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 1;
        this.add(redNameField, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 2;
        this.add(yellowLabel, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 3;
        this.add(yellowNameField, c);

        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 4;
        this.add(button, c);

    }
}
