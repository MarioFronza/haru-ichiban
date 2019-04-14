package br.udesc.ppr55.hi.view;


import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.controller.observer.Observer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;


public class PlayerPanel extends JPanel implements Observer {

    private static final long serialVersionUID = 1L;

    @Override
    public void notifyItemClicked() {

    }

    @Override
    public void notifyChangeBoard() {

    }

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


    class HaruItemRender extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getTableCellRendererComponent(JTable table,
                                                       Object value, boolean isSelected, boolean hasFocus, int row,

                                                       int column) {
            this.setHorizontalAlignment(SwingConstants.CENTER);
            setIcon((ImageIcon) value);
            return this;
        }
    }

    private JTable playerTable;
    private IHaruController haruController;

    public PlayerPanel() {
        this.haruController = HaruController.getInstance();
        this.haruController.addObserver(this);
        this.setLayout(new GridLayout(1, 1));
        super.setSize(550, 100);
        initComponents();
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
        playerTable.setBackground(new Color(147, 120, 64, 217));
        playerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playerTable.setIntercellSpacing(new Dimension(0, 0));
        playerTable.setDefaultRenderer(Object.class, new HaruItemRender());

        this.add(playerTable);
    }
}
