package com.lymno.cabinet;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Rhinrei on 20.06.2015.
 */
public class Day {
    private String name;
    ArrayList<Subject> subjects = new ArrayList<Subject>();

    public Day(String name, ArrayList<Subject> subjects) {
        this.name = name;
        this.subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public String getTodayTable(){
        String res = "";

        for (int i = 0; i < subjects.size(); i++) {
            res = res + Integer.toString(subjects.get(i).getNumber()) + ". " + subjects.get(i).getSubjectName() + "\n";
        }
        return res;
    }
}
