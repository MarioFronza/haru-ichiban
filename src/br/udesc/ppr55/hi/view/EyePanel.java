package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.IHaruController;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Eye panel
 *
 * @author João Pedro Schmitz, Mário Fronza
 * @since 13/04/2019
 * @version 1.0.0
 */
public class EyePanel extends JPanel {

    class HaruTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            try {
                return new ImageIcon("images/eye.png");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString());
                return null;
            }
        }

    }

    private JTable eyeTable;
    private IHaruController haruController;

    public EyePanel(IHaruController haruController) {

        this.haruController = haruController;

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        initComponents();
    }


    private void initComponents() {
        eyeTable = new JTable();
        eyeTable.setModel(new HaruTableModel());

        for (int x = 0; x < eyeTable.getColumnModel().getColumnCount(); x++) {
            eyeTable.getColumnModel().getColumn(x).setWidth(100);
            eyeTable.getColumnModel().getColumn(x).setMinWidth(100);
        }
        eyeTable.setRowHeight(100);
        eyeTable.setShowGrid(false);
        eyeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        eyeTable.setIntercellSpacing(new Dimension(0, 0));
        eyeTable.setDefaultRenderer(Object.class, new HaruItemRender());
        eyeTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                haruController.eyePressed();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                haruController.eyeReleased();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                eyeTable.setBackground(new Color(34, 34, 34, 125));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                eyeTable.setBackground(new Color(249, 249, 255, 255));
            }
        });

        this.add(eyeTable);
    }
}
