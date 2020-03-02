package com.example.unipool;

public class Student {
    String student_name;
    int student_id;
    String dependency;
    String email;
    String password;
    int typeOfAccount;

    public Student(){

    }

    public Student(int student_id, String student_name, String dependency, String email, String password, int typeOfAccount) {
        this.student_name = student_name;
        this.student_id = student_id;
        this.dependency = dependency;
        this.email = email;
        this.password = password;
        this.typeOfAccount = typeOfAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(int typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }
}
