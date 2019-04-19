package br.udesc.ppr55.hi.view;


import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.controller.command.CommandInvoker;
import br.udesc.ppr55.hi.controller.observer.Observer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class PlayerPanel extends JPanel{


    class HaruTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                return new ImageIcon(haruController.getPlayerFlower(rowIndex, columnIndex));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
                return null;
            }
        }

    }

    private JTable playerTable;
    private IHaruController haruController;
    private CommandInvoker commandInvoker;

    public PlayerPanel(IHaruController haruController, CommandInvoker commandInvoker) {
        this.haruController = haruController;
        this.commandInvoker = commandInvoker;
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        initComponents();
    }

    public void update(){
        this.playerTable.updateUI();
    }

    private void initComponents() {
        playerTable = new JTable();
        playerTable.setModel(new HaruTableModel());

        for (int x = 0; x < playerTable.getColumnModel().getColumnCount(); x++) {
            playerTable.getColumnModel().getColumn(x).setWidth(100);
            playerTable.getColumnModel().getColumn(x).setMinWidth(100);
        }
        playerTable.setRowHeight(100);
        playerTable.setShowGrid(true);
        playerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playerTable.setIntercellSpacing(new Dimension(0, 0));
        playerTable.setDefaultRenderer(Object.class, new HaruItemRender());

        this.add(playerTable);
    }
}
