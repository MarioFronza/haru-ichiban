package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.controller.listeners.ClickFlowerListener;
import br.udesc.ppr55.hi.controller.observer.Observer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class FlowersPanel extends JPanel{

    private static final long serialVersionUID = 1L;


    class HaruTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getRowCount() {
            return 4;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                return new ImageIcon(haruController.getFlower(rowIndex, columnIndex));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
                return null;
            }
        }

    }


    private JTable flowersPanel;
    private IHaruController haruController;

    public FlowersPanel() {
        this.haruController = HaruController.getInstance();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
    }

    private void initComponents() {
        flowersPanel = new JTable();
        flowersPanel.setModel(new HaruTableModel());

        for (int x = 0; x < flowersPanel.getColumnModel().getColumnCount(); x++) {
            flowersPanel.getColumnModel().getColumn(x).setWidth(100);
            flowersPanel.getColumnModel().getColumn(x).setMinWidth(100);
        }
        flowersPanel.setRowHeight(100);
        flowersPanel.setShowGrid(true);
        flowersPanel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flowersPanel.setIntercellSpacing(new Dimension(0, 0));
        flowersPanel.setDefaultRenderer(Object.class, new HaruItemRender());
        flowersPanel.addMouseListener(new ClickFlowerListener(flowersPanel.getSelectedRow(), flowersPanel.getSelectedColumn(), this.haruController));

        this.add(flowersPanel);
    }
}
