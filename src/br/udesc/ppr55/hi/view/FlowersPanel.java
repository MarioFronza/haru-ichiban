package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.controller.command.AddFlower;
import br.udesc.ppr55.hi.controller.command.CommandInvoker;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FlowersPanel extends JPanel {

    private static final long serialVersionUID = 1L;


    class HaruTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getRowCount() {
            return 5;
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
    private CommandInvoker commandInvoker;


    public FlowersPanel(IHaruController haruController, CommandInvoker commandInvoker) {
        this.haruController = haruController;
        this.commandInvoker = commandInvoker;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initComponents();
    }

    public void update() {
        this.flowersPanel.updateUI();
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
        flowersPanel.setBackground(new Color(53, 178, 72, 162));
        flowersPanel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        flowersPanel.setIntercellSpacing(new Dimension(0, 0));
        flowersPanel.setDefaultRenderer(Object.class, new HaruItemRender());
        flowersPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                commandInvoker.add(new AddFlower(flowersPanel.getSelectedRow(), flowersPanel.getSelectedColumn(), haruController));
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

        this.add(flowersPanel);
    }
}
