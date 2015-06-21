package com.lymno.cabinet;

import java.util.ArrayList;

public class DayMark {
    private String name;
    private String date;
    ArrayList<SubjectMarks> marks = new ArrayList<SubjectMarks>();

    public DayMark(String name, String date, ArrayList<SubjectMarks> marks) {
        this.name = name;
        this.date = date;
        this.marks = marks;
    }

    public String getName() {
        return name;
    }

    public ArrayList<SubjectMarks> getMarks() {
        return marks;
    }

    public String getDate() {
        return date;
    }

    public String getTodayMarks(){
        String res = "";

        for (int i = 0; i < marks.size(); i++) {
            String text = "";
            if(marks.get(i).getMark() == 0){
                text = "Пропущено.";
            }
            else{
                text = Integer.toString(marks.get(i).getMark());
            }

            res = res + marks.get(i).getSubjectName() + ". " + text + "\n";
        }
        return res;
    }
}
