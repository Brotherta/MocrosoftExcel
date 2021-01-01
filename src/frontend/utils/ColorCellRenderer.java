package frontend.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ColorCellRenderer extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent
            (JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column){
        Component cell = super.getTableCellRendererComponent
                (table,value,isSelected,hasFocus,row,column);
        if(value.equals(" ")) {
            cell.setBackground(new Color(168, 168, 168));
        }
        else cell.setBackground(Color.WHITE);
        return cell;
    }
}