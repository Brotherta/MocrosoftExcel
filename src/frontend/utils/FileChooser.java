package frontend.utils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileChooser extends JDialog {
    private String path;
    private File fileToSave;

    public FileChooser(JFrame main, boolean bool, String extension, String title) {
        super(main, bool);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setSize(800,800);
        fileChooser.setSelectedFile(new File(extension));
        int userSelection = fileChooser.showSaveDialog(main);


        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();

            path = fileToSave.getAbsolutePath();
            dispose();
        }
    }

    public File getFileToSave() {
        return fileToSave;
    }

    public String getFilePath() {
        return path;
    }
}
