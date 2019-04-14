package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {


    private IHaruController haruController;

    public ControlPanel() {
        this.haruController = HaruController.getInstance();
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(200, 100));
        initComponents();
    }

    private void initComponents() {
        super.setBackground(new Color(205, 186, 35, 183));
    }
}
