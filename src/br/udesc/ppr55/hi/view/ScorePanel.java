package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 * Score panel
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 13/04/2019
 * @version 1.0.0
 */
public class ScorePanel extends JPanel {

    private static final long serialVersionUID = 1L;

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

    private JTable scoreTable;
    private IHaruController haruController;

    public ScorePanel() {
        this.haruController = HaruController.getInstance();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
    }

    private void initComponents() {
        scoreTable = new JTable();
        scoreTable.setModel(new HaruTableModel());
        for (int x = 0; x < scoreTable.getColumnModel().getColumnCount(); x++) {
            scoreTable.getColumnModel().getColumn(x).setWidth(100);
            scoreTable.getColumnModel().getColumn(x).setMinWidth(100);
            scoreTable.getColumnModel().getColumn(x).setMaxWidth(100);
        }
        scoreTable.setBackground(MainFrame.BG_COLOR);
        scoreTable.setOpaque(false);
        scoreTable.setRowHeight(50);
        scoreTable.setShowGrid(true);
        scoreTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scoreTable.setIntercellSpacing(new Dimension(0, 0));
        scoreTable.setDefaultRenderer(Object.class, new HaruItemRender());

        this.add(scoreTable);
    }

}
