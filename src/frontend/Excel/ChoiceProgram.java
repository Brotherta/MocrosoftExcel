package frontend.Excel;

import backend.Data;
import backend.program.Program;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChoiceProgram extends JFrame {
    List<String> infos;
    GetInfosPanel getInfosPanel;
    List<Program> resultSearchProgram;
    Program program;
    JPanel inChooseProgramPanel;

    public ChoiceProgram(Data data, Filter programFilter){


        super("Chercher un étudiant à filtrer");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500,500);
        setLayout(new BoxLayout(super.getContentPane(),BoxLayout.Y_AXIS));


        /// Create Elements
        JPanel textPanel = new JPanel();
        JLabel text = new JLabel("Entrez des mots clefs puis sélectionnez l'élève voulu:");
        textPanel.add(text);

        getInfosPanel = new frontend.Excel.ChoiceProgram.GetInfosPanel();
        JPanel searchPanel = new JPanel();
        JButton searchButton = new JButton("Chercher");
        searchPanel.add(searchButton);
        JPanel checkPanel = new JPanel();
        JButton checkButton = new JButton("Valider");
        checkPanel.add(checkButton);

        // Set variables
        resultSearchProgram = data.getProgramList();
        resultSearchProgram.removeAll(programFilter.getProgramsFilter());


        /// RadioButton ChooseProgram
        JPanel chooseProgramPanel = new JPanel();
        chooseProgramPanel.setLayout(new BorderLayout());
        chooseProgramPanel.setBorder(new LineBorder(Color.BLACK));

        buttonChooseProgramPanel();
        chooseProgramPanel.add(new JScrollPane(inChooseProgramPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        searchButton.addActionListener(e -> {
            infos = getInfosPanel.getInfos();
            List<Program> sortedProgram = sortProgram(data,infos.get(0),infos.get(1));
            if (!(sortedProgram.size() == data.getProgramList().size())){
                resultSearchProgram.clear();
                for (Program program: sortedProgram){
                    resultSearchProgram.add(program);
                }
            }
            else {
                resultSearchProgram = data.getProgramList();
                resultSearchProgram.removeAll(programFilter.getProgramsFilter());
            }
            chooseProgramPanel.removeAll();
            buttonChooseProgramPanel();
            chooseProgramPanel.add(new JScrollPane(inChooseProgramPanel));
            chooseProgramPanel.revalidate();

        });

        // Fermer la fenêtre
        checkButton.addActionListener(e -> {
            System.exit(0);
        });



        // Ajout dans la JFrame
        add(textPanel);
        add(getInfosPanel);
        add(searchPanel);
        add(chooseProgramPanel);
        add(checkPanel);
        setVisible(true);
    }

    private void buttonChooseProgramPanel(){
        inChooseProgramPanel = new JPanel();
        inChooseProgramPanel.setLayout(new BoxLayout(inChooseProgramPanel,BoxLayout.Y_AXIS));
        ButtonGroup group = new ButtonGroup();
        for (int i=0; i<resultSearchProgram.size(); i++){
            Program program = resultSearchProgram.get(i);
            JRadioButton programButton = new JRadioButton(program.getName()+" - "+program.getId());
            programButton.setActionCommand(program.getId());
            group.add(programButton);
            inChooseProgramPanel.add(programButton);
            programButton.addActionListener(e -> {
                this.program = getProgramById(this.resultSearchProgram,e.getActionCommand());
            });
        }


    }

    private Program getProgramById(List<Program> programList, String id){
        for (Program program : programList){
            if (program.getId().equals(id)){
                return program;
            }
        }
        return null;
    }

    private List<Program> sortProgram(Data data,String name, String id){
        List<Program> allProgram = data.getProgramList();
        List<Program> sortedProgram = new ArrayList<>();
        for (Program program : allProgram){
            if (program.getName().toLowerCase().contains(name.toLowerCase())
                    && program.getId().toLowerCase().contains(id.toLowerCase()))
            {
                sortedProgram.add(program);
            }
        }
        return sortedProgram;
    }



    private class GetInfosPanel extends JPanel{
        JTextField name;
        JTextField id;

        public GetInfosPanel(){
            name = new JTextField();
            id = new JTextField();

            name.setBorder(new TitledBorder("Nom"));
            id.setBorder(new TitledBorder("Identifiant"));
            name.setMaximumSize(new Dimension(2000,50));
            id.setMaximumSize(new Dimension(2000,50));
            setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
            add(name);
            add(id);
        }

        public List<String> getInfos(){
            List<String> infos = new ArrayList<String>();
            infos.add(name.getText());
            infos.add(id.getText());
            return infos;
        }

    }

    public static void main(String[] args) {
        Data data = new Data();
        Filter programFilter = new Filter();
        frontend.Excel.ChoiceProgram choiceProgram = new frontend.Excel.ChoiceProgram(data,programFilter);
    }


}