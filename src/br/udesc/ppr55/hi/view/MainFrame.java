package br.udesc.ppr55.hi.view;

import br.udesc.ppr55.hi.controller.HaruController;
import br.udesc.ppr55.hi.controller.listeners.ClickItemListener;
import static java.awt.BorderLayout.CENTER;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

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
    
    
    //transformar em classe separada
    class HaruItemRender extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {

            setIcon((ImageIcon) value);

            return this;
        }

    }

    private HaruController haruController;
    private JTable gameBoard;

    public MainFrame() {
        this.haruController = new HaruController();
        this.haruController.initializeBoard();

        super.setTitle("Haru Ichiban");
        super.setSize(500, 500);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setLocationRelativeTo(null);
        super.setResizable(false);

        initComponents();
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
        gameBoard.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gameBoard.setIntercellSpacing(new Dimension(0, 0));
        gameBoard.setDefaultRenderer(Object.class, new HaruItemRender());
        gameBoard.addMouseListener(new ClickItemListener());
        this.add(gameBoard, CENTER);
    }

}

//    private ScorePanel panelScore;
//
//    public MainFrame() {
//        super("Haru Ichiban");
//        SwingUtilities.invokeLater(() -> {
//            this.intializeFrame();
//            this.initializeGame();
//        });
//    }
//    
//    private void initializeGame() {
//        this.getContentPane().setBackground(Color.white);
//        this.setLayout(new BorderLayout());
//        
//        this.panelScore = new ScorePanel();
//        this.add(panelScore, BorderLayout.WEST);
//    }
//    
//    private void intializeFrame() {
//        this.setSize(new Dimension(1024, 768));
//        this.setResizable(false);
//        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//        this.setVisible(true);
//    }
