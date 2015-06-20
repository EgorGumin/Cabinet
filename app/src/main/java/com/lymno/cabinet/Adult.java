package com.lymno.cabinet;

public class Adult {
    private int id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;

    public Adult(int id, String firstName, String lastName, String middleName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getEmail() {
        return email;
    }
}
