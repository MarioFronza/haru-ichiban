package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.view.command.CommandInvoker;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FrogFrame extends JFrame {

    class HaruTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                if (columnIndex == 0) {
                    return new ImageIcon("images/water-lily-with-pink-frog.png");
                } else {
                    return new ImageIcon("images/water-lily-with-yellow-frog.png");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
                return null;
            }
        }

    }

    private JTable frogTable;
    private IHaruController haruController;

    public FrogFrame(IHaruController haruController) {
        this.haruController = haruController;
        super.setTitle("Coachar!!!");
        super.setLocationRelativeTo(null);
        super.setSize(200, 130);
        super.setResizable(false);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        super.setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        frogTable = new JTable();
        frogTable.setModel(new HaruTableModel());
        for (int x = 0; x < frogTable.getColumnModel().getColumnCount(); x++) {
            frogTable.getColumnModel().getColumn(x).setWidth(100);
            frogTable.getColumnModel().getColumn(x).setMinWidth(100);
        }
        frogTable.setRowHeight(100);
        frogTable.setShowGrid(false);
        frogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        frogTable.setIntercellSpacing(new Dimension(0, 0));
        frogTable.setDefaultRenderer(Object.class, new HaruItemRender());
        frogTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                haruController.setValueFrog(frogTable.getSelectedColumn());
                FrogFrame.super.dispose();
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

        super.add(frogTable, BorderLayout.CENTER);
    }


}
