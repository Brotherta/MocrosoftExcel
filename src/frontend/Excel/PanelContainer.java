package frontend.Excel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PanelContainer {
    List<JPanel> container;

    public PanelContainer() {
        container = new ArrayList<>();
    }

    public void addJPanel(JPanel jPanel) {
        container.add(jPanel);
    }

    public List<JPanel> getContainer() {
        return container;
    }
}
