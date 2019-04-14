package br.udesc.ppr55.hi.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

class HaruItemRender extends DefaultTableCellRenderer {


    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row,
                                                   int column) {

        this.setHorizontalAlignment(SwingConstants.CENTER);
        setIcon((ImageIcon) value);

        return this;
    }

}
