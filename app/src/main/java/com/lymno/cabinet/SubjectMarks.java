package com.lymno.cabinet;


public class SubjectMarks {
    private int number;
    private String subjectName;
    private int mark;

    public SubjectMarks(int number, String subjectName, int mark) {
        this.number = number;
        this.subjectName = subjectName;
        this.mark = mark;
    }

    public int getNumber() {
        return number;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getMark() {
        return mark;
    }
}
