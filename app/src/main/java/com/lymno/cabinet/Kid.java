package com.lymno.cabinet;

public class Kid {
    private  String firstName;
    private  String secondName;
    private  String middleName;
    private  String school;
    private  String classOfSchool;
    private  String dateOfBirth;
    private  int parentId;
    private  String bornCertificate;

    public Kid(String firstName, String secondName, String middleName, String school, String classOfSchool, String dateOfBirth, int parentId, String bornCertificate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.school = school;
        this.classOfSchool = classOfSchool;
        this.dateOfBirth = dateOfBirth;
        this.parentId = parentId;
        this.bornCertificate = bornCertificate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getSchool() {
        return school;
    }

    public String getClassOfSchool() {
        return classOfSchool;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public int getParentId() {
        return parentId;
    }

    public String getBornCertificate() {
        return bornCertificate;
    }
}
