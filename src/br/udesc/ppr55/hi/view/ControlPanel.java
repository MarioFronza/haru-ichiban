package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.view.command.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Control panel
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @version 1.0.0
 * @since 13/04/2019
 */
public class ControlPanel extends JPanel {

    private IHaruController haruController;
    private CommandInvoker commandInvoker;

    private JButton up;
    private JButton left;
    private JButton right;
    private JButton down;

    public ControlPanel(IHaruController haruController, CommandInvoker commandInvoker) {
        this.haruController = haruController;
        this.commandInvoker = commandInvoker;
        this.setPreferredSize(new Dimension(100, 100));
        this.setLayout(new GridLayout(3, 3));
        initComponents();
    }

    private void initComponents() {
        this.up = new BasicArrowButton(BasicArrowButton.NORTH);
        this.up.setSize(33, 33);
        this.up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandInvoker.add(new MoveWaterLilyUp(haruController));
                commandInvoker.execute();
            }
        });
        this.left = new BasicArrowButton(BasicArrowButton.WEST);
        this.left.setSize(33, 33);
        this.left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandInvoker.add(new MoveWaterLilyLeft(haruController));
                commandInvoker.execute();
            }
        });
        this.right = new BasicArrowButton(BasicArrowButton.EAST);
        this.right.setSize(33, 33);
        this.right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandInvoker.add(new MoveWaterLilyRight(haruController));
                commandInvoker.execute();
            }
        });
        this.down = new BasicArrowButton(BasicArrowButton.SOUTH);
        this.down.setSize(33, 33);
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
