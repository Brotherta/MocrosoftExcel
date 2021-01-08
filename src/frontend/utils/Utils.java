package frontend.utils;

import backend.Data;
import backend.course.Course;
import backend.program.Program;
import backend.student.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    public static List<Student> getStudentListByProgramId(Data data, Program program){
        List<Student> allStudentList = data.getStudentList();
        List<Student> studentList = new ArrayList<>();
        for (Student student: allStudentList){
            if (student.getProgramId().equals(program.getId())){
                studentList.add(student);
            }
        }
        return studentList;
    }  // List des student de program


    public static Student getStudentById(Data data, String studentId) {
        List<Student> students = data.getStudentList();
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    // On ouvre le fichier dataPath.txt pour récupérer le path de la dernière exécution
    public static String getDataPath() {
        String dataPath = "";
        try {
            File pathFile = new File("src/resources/dataPath.txt");
            Scanner scanner = new Scanner(pathFile);
            while(scanner.hasNextLine()) {
                dataPath = scanner.nextLine();
            }
            scanner.close();
            System.out.println("path: " + pathFile);
        } catch (FileNotFoundException e) {
            System.out.println("fichier introuvable");
            e.printStackTrace();
        }
        return dataPath;
    }

    public static Course getCourseById(String id, List<Course> courseList) {
        Course res = null;
        for (Course course : courseList) {
            if (course.getId().equals(id)) {
                res = course;
            }
        }
        return res;
    }
}
