package com.lymno.cabinet;


public class Subject {
    private int number;
    private String subjectName;

    public Subject(int number, String subjectName) {
        this.number = number;
        this.subjectName = subjectName;
    }

    public int getNumber() {
        return number;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
