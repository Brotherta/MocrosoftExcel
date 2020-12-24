package frontend.Excel;

import javax.swing.*;

public class PanelTest extends JPanel {
    JButton button;

    public PanelTest() {
        button = new JButton("salut");
        add(button);
    }

    public void update(String s) {
        remove(button);
        revalidate();
        button = new JButton(s);
        add(button);
        revalidate();
    }
}
