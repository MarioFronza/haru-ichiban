package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.view.command.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Control panel
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 13/04/2019
 * @version 1.0.0
 */
public class ControlPanel extends JPanel {

    private IHaruController haruController;
    private CommandInvoker commandInvoker;
    private final int SIZE = 55;

    private JButton up;
    private JButton left;
    private JButton right;
    private JButton down;

    public ControlPanel(IHaruController haruController, CommandInvoker commandInvoker) {
        this.haruController = haruController;
        this.commandInvoker = commandInvoker;
        this.setPreferredSize(new Dimension(100, 100));
        this.setBackground(Color.white);
        this.setLayout(new GridLayout(3, 3));
        initComponents();
    }

    private void initComponents() {
        this.up = new JButton("&#708");
        this.up.setSize(SIZE, SIZE);
        this.up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandInvoker.add(new MoveWaterLilyUp(haruController));
                commandInvoker.execute();
            }
        });
        this.left = new JButton("\uD83E");
        this.left.setSize(SIZE, SIZE);
        this.left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandInvoker.add(new MoveWaterLilyLeft(haruController));
                commandInvoker.execute();
            }
        });
        this.right = new JButton("\uD83E");
        this.right.setSize(SIZE, SIZE);
        this.right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandInvoker.add(new MoveWaterLilyRight(haruController));
                commandInvoker.execute();
            }
        });
        this.down = new JButton("\uD83E");
        this.down.setSize(SIZE, SIZE);
        this.down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandInvoker.add(new MoveWaterLilyDown(haruController));
                commandInvoker.execute();
            }
        });

        this.add(new JLabel(""));
        this.add(up);
        this.add(new JLabel(""));
        this.add(left);
        this.add(new JLabel(""));
        this.add(right);
        this.add(new JLabel(""));
        this.add(down);
        this.add(new JLabel(""));
    }
}
