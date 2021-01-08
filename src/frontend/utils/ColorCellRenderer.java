package frontend.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ColorCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent (JTable table,
                                                    Object value,
                                                    boolean isSelected,
                                                    boolean hasFocus,
                                                    int row,
                                                    int column){

        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        cell.setBackground(Color.WHITE);
        if (isSelected) {
            cell.setBackground(new Color(140, 180, 229, 144));
        }
        if(value.equals(" ")) {
            cell.setBackground(new Color(168, 168, 168));
        }

        return cell;
    }
}