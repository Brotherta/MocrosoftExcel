package frontend.csv;

import backend.Data;
import backend.program.Program;
import backend.student.Student;

import java.util.List;

public class PV {


    private List<Student> getStudentProgram(Data data, Program program){

        /* a récuperer dans le main */
        List<Student> allStudentList = data.getStudentList();
        /**/

        List<Student> studentList = null;
        for (Student student: allStudentList){
            if (student.getProgramId().equals(program.getId())){
                studentList.add(student);
            }
        }
        return studentList;
    }

    public static void main(String[] args) {
        Data data = new Data();
    }

}
