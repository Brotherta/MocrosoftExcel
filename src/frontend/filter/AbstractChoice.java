package frontend.filter;

import backend.Data;
import backend.Item;
import backend.course.Course;
import backend.program.Program;
import backend.student.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class AbstractChoice extends JDialog {
    List<String> infos;
    GetInfosPanel getInfosPanel;
    JPanel inChoosePanel;
    List<Item> resultSearch;
    List<Item> items;
    List<Item> filterList;
    Item item;

    public AbstractChoice(Filter filter,Data data, int typeItem,JFrame main, boolean bool){
        super(main,bool);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(500,500);
        setLayout(new BoxLayout(super.getContentPane(),BoxLayout.Y_AXIS));


        switch (typeItem){
            case 0: {
                this.filterList = studentToItem(filter.getStudentsFilter());
                this.items = studentToItem(data.getStudentList());
                break;
            }
            case 1: {
                this.filterList = courseToItem(filter.getCoursesFilter());
                this.items = courseToItem(data.getCourseList());
                break;
            }
            case 2: {
                this.filterList = programToItem(filter.getProgramsFilter());
                this.items = programToItem(data.getProgramList());
                break;
            }
        }


        /// Create Elements
        JPanel textPanel = new JPanel();
        JLabel text = new JLabel("Entrez des mots clefs puis sélectionnez ");
        textPanel.add(text);


        getInfosPanel = new GetInfosPanel(typeItem == 0);
        JPanel searchPanel = new JPanel();
        JButton searchButton = new JButton("Chercher");
        searchPanel.add(searchButton);
        JPanel checkPanel = new JPanel();
        JButton checkButton = new JButton("Valider");
        checkPanel.add(checkButton);

        // Set variables
        this.resultSearch = new ArrayList<>(items);

        // Enlever les filtres !!
        resultSearch.removeAll(filterList);

        /// RadioButton ChooseStudent
        JPanel choosePanel = new JPanel();
        choosePanel.setLayout(new BorderLayout());
        choosePanel.setBorder(new LineBorder(Color.BLACK));

        buttonChoosePanel();
        choosePanel.add(new JScrollPane(inChoosePanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        searchButton.addActionListener(e -> {
            infos = getInfosPanel.getInfos(typeItem == 0);
            List<Item> filtredItem;
            if (typeItem == 0){
                filtredItem = filtreItem(infos.get(0),infos.get(1),infos.get(2));
            }
            else {
                filtredItem = filtreItem(infos.get(0),null,infos.get(1));
            }
//            List<XmlItem> filtredItem = filtreItem(infos.get(0),infos.get(1),infos.get(2));
            if (filtredItem.size() != items.size()){
                resultSearch.clear();
                for (Item item: filtredItem){
                    resultSearch.add(item);
                }
            }
            else {
                resultSearch = new ArrayList<>(items);
                // Filtrer !!
                resultSearch.removeAll(filterList);
            }
            choosePanel.removeAll();
            buttonChoosePanel();
            choosePanel.add(new JScrollPane(inChoosePanel));
            choosePanel.revalidate();

        });


        // Fermer la fenêtre
        checkButton.addActionListener(e -> {
            dispose();
        });


        // Ajout dans la JFrame
        add(textPanel);
        add(getInfosPanel);
        add(searchPanel);
        add(choosePanel);
        add(checkPanel);
        setVisible(true);
    }

    protected Item getItem(){ return item;}

    private void buttonChoosePanel(){
        inChoosePanel = new JPanel();
        inChoosePanel.setLayout(new BoxLayout(inChoosePanel,BoxLayout.Y_AXIS));
        ButtonGroup group = new ButtonGroup();
        for (int i=0; i<resultSearch.size(); i++){
            Item item = resultSearch.get(i);
            JRadioButton itemButton;
            if (item.getSurname() == null){
                itemButton = new JRadioButton(item.getName()+" - "+item.getId());
            }
            else {
                itemButton = new JRadioButton(item.getName()+" - "+item.getSurname()+" - "+item.getId());
            }
            itemButton.setActionCommand(item.getId());
            group.add(itemButton);
            inChoosePanel.add(itemButton);
            itemButton.addActionListener(e -> {
                this.item = getItemById(this.resultSearch,e.getActionCommand());
            });
        }


    }

    protected Item getItemById(List<Item> itemList, String id){
        for (Item item : itemList){
            if (item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }

    protected List<Item> filtreItem(String name, String surname, String id) {

        List<Item> filtredItem = new ArrayList<>();
        for (Item xmlItem : items) {
            if (xmlItem.getId().toLowerCase().contains(id.toLowerCase())
                    && xmlItem.getName().toLowerCase().contains(name.toLowerCase())) {
                if (xmlItem.getSurname() != null) {
                    if (xmlItem.getSurname().toLowerCase().contains(surname.toLowerCase())) {
                        filtredItem.add(xmlItem);
                    }
                } else {
                    filtredItem.add(xmlItem);
                }

            }
        }
        return filtredItem;
    }

    public static List<Item> studentToItem(List<Student> studentList){
        List<Item> itemList = new ArrayList<>();
        for (Student student : studentList){
            itemList.add((Item) student);
        }
        return itemList;
    }
    public static List<Item> courseToItem(List<Course> courseList){
        List<Item> itemList = new ArrayList<>();
        for (Course course : courseList){
            itemList.add((Item) course);
        }
        return itemList;
    }
    public static List<Item> programToItem(List<Program> programList){
        List<Item> itemList = new ArrayList<>();
        for (Program program : programList){
            itemList.add((Item) program);
        }
        return itemList;
    }

    protected class GetInfosPanel extends JPanel{
        JTextField name;
        JTextField surname;
        JTextField id;

        public GetInfosPanel(boolean isStudent){
            Dimension boxDim = new Dimension(2000,50);
            setLayout(new BoxLayout(this,BoxLayout.X_AXIS));

            name = new JTextField();
            name.setBorder(new TitledBorder("Nom"));
            name.setMaximumSize(boxDim);
            add(name);

            if (isStudent){
                surname = new JTextField();
                surname.setBorder(new TitledBorder("Prénom"));
                surname.setMaximumSize(boxDim);
                add(surname);
            }

            id = new JTextField();
            id.setBorder(new TitledBorder("Identifiant"));
            id.setMaximumSize(boxDim);
            add(id);
        }

        public List<String> getInfos(boolean isStudent){
            List<String> infos = new ArrayList<>();
            infos.add(name.getText());
            if (isStudent){ infos.add(surname.getText()); }
            infos.add(id.getText());
            return infos;
        }
    }




}

