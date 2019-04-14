package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.controller.observer.Observer;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class ScorePanel extends JPanel implements Observer {

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
            return 10;
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                return new ImageIcon(haruController.getScoreStone(rowIndex, columnIndex));
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

            setIcon((ImageIcon) value);

            return this;
        }

    }

    private JTable scoreTable;
    private IHaruController haruController;

    public ScorePanel() {
        this.haruController = HaruController.getInstance();
        this.haruController.addObserver(this);
        this.setLayout(new GridLayout(1, 1));
        super.setSize(50, 500);
        initComponents();
    }

    private void initComponents() {
        scoreTable = new JTable();
        scoreTable.setModel(new HaruTableModel());

        for (int x = 0; x < scoreTable.getColumnModel().getColumnCount(); x++) {
            scoreTable.getColumnModel().getColumn(x).setWidth(50);
            scoreTable.getColumnModel().getColumn(x).setMinWidth(50);
            scoreTable.getColumnModel().getColumn(x).setMaxWidth(50);
        }
        scoreTable.setRowHeight(50);
        scoreTable.setShowGrid(true);
        scoreTable.setBackground(new Color(116, 105, 97, 217));
        scoreTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scoreTable.setIntercellSpacing(new Dimension(0, 0));
        scoreTable.setDefaultRenderer(Object.class, new HaruItemRender());

        this.add(scoreTable);
    }


}
