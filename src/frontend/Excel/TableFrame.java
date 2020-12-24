package frontend.Excel;

import javax.swing.*;
import java.awt.*;

public class TableFrame extends JTable {
    private Object[][] data;
    private String[] headers;
    private JTable table;

    public TableFrame(Object[][] data, String[] headers) {
        super(data, headers);
        this.data = data;
        this.headers = headers;
        this.table = this;

        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        setRowSelectionAllowed(false);
        setFont(new Font("Arial", Font.PLAIN, 15));
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        autoResizeColumn();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column < 4) {
            return false;
        }
        else {
            return true;
        }
    }

    private void autoResizeColumn() {
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        for (int i = 4; i < headers.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(100);
        }
    }

}
