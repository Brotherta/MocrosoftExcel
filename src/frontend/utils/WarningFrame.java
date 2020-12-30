package frontend.utils;

import javax.swing.*;
import java.awt.*;

public class WarningFrame extends JDialog {
    private boolean status;

    public WarningFrame(JFrame main, boolean bool, String warning) {
        super(main, bool);

        status = false;
        setSize(new Dimension(400,100));
        JPanel option = new JPanel();
        option.setLayout(new BoxLayout(option, BoxLayout.X_AXIS));

        JButton accept = new JButton("Continuer");
        JButton decline = new JButton("Annuler");

        option.add(accept, BorderLayout.CENTER);
        option.add(decline, BorderLayout.CENTER);

        accept.addActionListener(e1 -> {
            status = true;
            dispose();
        });
        decline.addActionListener(e1 -> {
            dispose();
        });

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/3-this.getSize().width/3, dim.height/3-this.getSize().height/3);
        setResizable(false);
        setLayout(new FlowLayout());
        add(option);
        setTitle(warning);
        setVisible(true);
    }

    public boolean getStatus() {
        return status;
    }
}
