package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.view.command.ChooseFlower;
import br.udesc.ppr55.hi.view.command.CommandInvoker;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Player panel
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 13/04/2019
 * @version 1.0.0
 */
public class PlayerPanel extends JPanel {

    class HaruTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        private boolean showFlower;

        public HaruTableModel(boolean showFlower) {
            this.showFlower = showFlower;
        }

        @Override
        public int getRowCount() {
            return 3;
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                if (showFlower) {
                    return new ImageIcon(haruController.getPlayerFlower(rowIndex));
                } else {
                    return new ImageIcon("images/petal-" + haruController.getCurrentRotation() + "-" + haruController.getFlowerNumber(rowIndex, columnIndex) + ".png");
                }
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

    public void update() {
        this.playerTable.updateUI();
    }

    public void showNumber() {
        playerTable.setModel(new HaruTableModel(false));

    }

    public void showFlower() {
        playerTable.setModel(new HaruTableModel(true));
    }

    private void initComponents() {
        playerTable = new JTable();
        playerTable.setModel(new HaruTableModel(true));
        for (int x = 0; x < playerTable.getColumnModel().getColumnCount(); x++) {
            playerTable.getColumnModel().getColumn(x).setWidth(100);
            playerTable.getColumnModel().getColumn(x).setMinWidth(100);
        }
        playerTable.setRowHeight(100);
        playerTable.setShowGrid(false);
        playerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playerTable.setIntercellSpacing(new Dimension(0, 0));
        playerTable.setDefaultRenderer(Object.class, new HaruItemRender());
        playerTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                commandInvoker.add(new ChooseFlower(playerTable.getSelectedRow(), playerTable.getSelectedColumn(), haruController));
                commandInvoker.execute();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.add(playerTable);
    }

}
