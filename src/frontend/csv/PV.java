package frontend.csv;

import backend.Data;
import backend.course.CompositeCourse;
import backend.course.Course;
import backend.course.OptionCourse;
import backend.course.SimpleCourse;
import backend.program.Program;
import backend.student.Grade;
import backend.student.Student;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PV {

    /// Variables
    private static final char SEPARATOR = ';';
    private final Program program;
    private final List<Course> coursesList;
    private final List<Student> studentList;
    private final double[] averageYearList;
    private final double[][] gradeList;


    //////// Constructeur
    public PV(Program program, Data data){
        this.program = program;
        List<Student> list = getStudent(data,program);
        Collections.sort(list,(a, b) -> a.getName().compareToIgnoreCase(b.getName()));
        this.studentList = list;
        this.coursesList = getCourseList(program);
        this.averageYearList = new double[studentList.size()];
        this.gradeList = new double[studentList.size()][coursesList.size()];
        for (int s=0; s<studentList.size(); s++){
            for (int c=0; c<coursesList.size(); c++){
                this.gradeList[s][c] = getGrade(studentList.get(s), coursesList.get(c));
            }
            averageYearList[s] = averageYearStudent(studentList.get(s));
        }
    }



    //////// Faire le PV
    public void makePV() {
        List<String[]> toWrite = new ArrayList<String[]>();
        toWrite.add(getHeader());
        for (Student student : studentList){
            toWrite.add(getStudentCsv(student));
        }
        toWrite.add(getMinGrade());
        toWrite.add(getMaxGrade());
        toWrite.add(getAverage());
        toWrite.add(getStandartDeviation());

        String filename = program.getName()+".csv";
        csvBuilder(toWrite,filename);
    }

    private void csvBuilder(List<String[]> toWrite, String filename) {
        try (FileWriter writer = new FileWriter(filename)){
            for (String[] strings : toWrite){
                for ( int i =0; i < strings.length; i++){
                    writer.append(strings[i]);
                    if (i < strings.length-1){
                        writer.append(SEPARATOR);
                    }
                }
                writer.append(System.lineSeparator());
            }
            writer.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    //////// Pour construire le csv

    private String[] getHeader(){
        int headerSize = this.coursesList.size() + 4;
        String[] res = new String[headerSize] ;
        res[0] = "N° Étudiant";
        res[1] = "Nom" ;
        res[2] = "Prénom" ;
        res[3] = program.getId()+" - "+program.getName();
        int i = 4;
        for (Course course : coursesList){
            res[i] = course.getId()+" - "+course.getName();
            i+=1;
        }
        return res;
    }  //  header
    private String[] getStudentCsv(Student student){
        int indexStudent = studentList.indexOf(student);
        String[] res = new String[coursesList.size()+4];
        res[0] = student.getStudentId();
        res[1] = student.getName();
        res[2] = student.getSurname();
        res[3] = String.valueOf(averageYearStudent(student));
        for (int i=0; i<coursesList.size(); i++){
            double grade = gradeList[indexStudent][i];
            if (grade == -1){ res[i+4] ="ABI"; }
            else if (grade == -2){ res[i+4] = "";}
            else { res[i+4] = String.valueOf(gradeList[indexStudent][i]); }
        }
        return res;
    }  // un élève
    private String[] getMinGrade(){

        return gradeTreatment(0);
    }   // note min
    private String[] getMaxGrade(){
        return gradeTreatment(1);
    }    // note max
    private String[] getAverage(){
        return gradeTreatment(2);
    }    // moyenne des notes
    private String[] getStandartDeviation(){
        return gradeTreatment(3);

    }    // ecart type des notes




    ////////// Fonctions utilitaires de calcul


    private String[] gradeTreatment(int choice){
        String[] name = {"Note min","Note max","Moyenne","Écart-type"};
        Double[] calculYear = {Calculate.min(averageYearList),Calculate.max(averageYearList),Calculate.average(averageYearList),Calculate.standartDeviation(averageYearList)};

        String[] res = new String[coursesList.size()+4];
        res[0] = name[choice];
        res[1] = "";
        res[2] = "";
        res[3] = String.valueOf(calculYear[choice]);
        for (int c=0; c<coursesList.size();c++){
            double[] grades = getGrades(coursesList.get(c));
            grades = cleanGradeList(grades);
            switch (choice){
                case 0: res[c+4] = String.valueOf(Calculate.min(grades));
                case 1: res[c+4] = String.valueOf(Calculate.max(grades));
                case 2: res[c+4] = String.valueOf(Calculate.average(grades));
                case 3: res[c+4] = String.valueOf(Calculate.standartDeviation(grades));
                default: res[c+4] = "";
            }
        }
        return res;
    }  // Calcul de min/max/average/sd des notes

    private double[] cleanGradeList(double[] gradeList){
        double[] newList = new double[gradeList.length];
        int indexNewList = 0;

        for (int indexGradeList=0; indexGradeList<gradeList.length; indexGradeList++){
            if (!(gradeList[indexGradeList]== -2 || gradeList[indexGradeList] == -1)) {
                newList[indexNewList] = gradeList[indexGradeList];
                indexNewList += 1;
            }
        }
        return newList;
    }




    private double averageYearStudent(Student student){
        double average = 0;
        int sumCredits = 0;

        for (int i=0; i< coursesList.size();i++){
            Course course = coursesList.get(i);
            double grade = gradeList[studentList.indexOf(student)][i];
            if (grade == -1){ grade = 0;}
            if (course instanceof SimpleCourse && grade != -2 && grade != -1 ){
                average += coursesList.get(i).getCredits()* gradeList[studentList.indexOf(student)][i];
                sumCredits += coursesList.get(i).getCredits();
            }
        }
        return Calculate.roundDouble(average/sumCredits,3);
    }   // Moyen d'un étudiant






    // Recuperer les données
    private List<Student> getStudent(Data data, Program program){
        List<Student> allStudentList = data.getStudentList();
    List<Student> studentList = new ArrayList<>();
        for (Student student: allStudentList){
            if (student.getProgramId().equals(program.getId())){
                studentList.add(student);
            }
        }
        return studentList;
    }  // List des student de program

    private List<Course> getCourseList( Program program){

        List<Course> courseList = new ArrayList<>();

        courseList.addAll(program.getSimpleCourseList());

        for (OptionCourse optionCourse : program.getOptionCourseList()){
            courseList.add(optionCourse);
            courseList.addAll(optionCourse.getOptionList());
        }
        for (CompositeCourse compositeCourse : program.getCompositeCoursesList()){
            courseList.add(compositeCourse);
            courseList.addAll(compositeCourse.getCompositeList());
        }
        return courseList;
    }   // Liste des cours de program

    private double getGrade(Student student, Course course){
        double res = -2;
        if (course instanceof SimpleCourse) {
            for (Grade grade : student.getGradeList()) {
                if (grade.getCourse().equals(course)) {
                    res = grade.getGrade();
                }
            }
        }
        else if (course instanceof OptionCourse){
            for (Grade grade : student.getGradeList()){
                for (SimpleCourse option : ((OptionCourse) course).getOptionList()){
                    if (grade.getCourse().equals(option)){
                        res = Math.max(res,grade.getGrade());
                    }
                }
            }
        }
        else if (course instanceof  CompositeCourse){
            int nbCredits = 0;
            int nbABI = 0;

            res = 0;
            for (Grade grade : student.getGradeList()){
                for (SimpleCourse composite : ((CompositeCourse) course).getCompositeList()){
                    if (grade.getCourse().equals(composite)){
                        if (grade.getGrade() == -1){
                            nbABI +=1;
                        }
                        else {
                            res += grade.getGrade()*composite.getCredits();
                            nbCredits += composite.getCredits();
                        }
                    }
                }
            }
            if (nbABI == 3){
                res = -1;
            }
            else {
                res = res / nbCredits;
            }
        }
        return Calculate.roundDouble(res,3);
    }    // Note de student à course


    private double[] getGrades(Course course){
        int courseIndex = coursesList.indexOf(course);
        double[] grades = new double[studentList.size()];
        for (int s=0; s<studentList.size(); s++){
            grades[s] = gradeList[s][courseIndex];
        }
        return grades;
    }   // Liste des notes du cours course









//    public static void main(String[] args) throws Exception {
//        Data data = new Data("data/data.xml");
//        Program program = data.getProgramList().get(0);
//        PV pv = new PV(program,data);
//        pv.makePV();
//    }

}
