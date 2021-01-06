package frontend.filter;

import backend.Data;
import backend.program.Program;
import javax.swing.*;

public class ChoiceProgram extends AbstractChoice{
    Program program;

    public ChoiceProgram(Data data, Filter programFilter, JFrame main, boolean bool){
        super(programFilter,data,2,main,bool);
        program = (Program) getItem();
    }

    public Program getProgram(){
        return program;
    }
}