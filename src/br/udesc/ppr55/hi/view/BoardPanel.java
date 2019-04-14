package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.IHaruController;
import br.udesc.ppr55.hi.controller.observer.Observer;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class BoardPanel extends JPanel {

    //Transformar em classe separada
    class HaruTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getRowCount() {
            return 5;
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                return new ImageIcon(haruController.getPiece(rowIndex, columnIndex));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
                return null;
            }
        }

    }

    private IHaruController haruController;
    private JTable gameBoard;

    public BoardPanel(IHaruController haruController) {
        this.haruController = haruController;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.initComponents();
        this.addComponents();
    }

    private void addComponents() {
        this.add(gameBoard);
    }

    private void initComponents() {
        gameBoard = new JTable();
        gameBoard.setModel(new HaruTableModel());

        for (int x = 0; x < gameBoard.getColumnModel().getColumnCount(); x++) {
            gameBoard.getColumnModel().getColumn(x).setWidth(100);
            gameBoard.getColumnModel().getColumn(x).setMinWidth(100);
            gameBoard.getColumnModel().getColumn(x).setMaxWidth(100);
        }
        gameBoard.setRowHeight(100);
        gameBoard.setShowGrid(true);
        gameBoard.setBackground(new Color(45, 68, 255, 168));
        gameBoard.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameBoard.setIntercellSpacing(new Dimension(0, 0));
        gameBoard.setDefaultRenderer(Object.class, new HaruItemRender());
        gameBoard.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                haruController.itemClicked(gameBoard.getSelectedRow(), gameBoard.getSelectedColumn());
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

    }

}
