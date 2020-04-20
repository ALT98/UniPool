package com.example.unipool;

import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("student_name")
    String student_name;
    @SerializedName("student_id")
    int student_id;
    @SerializedName("dependency")
    String dependency;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("typeOfAccount")
    int typeOfAccount;
    @SerializedName("phone_number")
    String phone_number;

    public Student(String student_name, int student_id, String dependency, String email, String password,
                   int typeOfAccount, String phone_number) {
        this.student_name = student_name;
        this.student_id = student_id;
        this.dependency = dependency;
        this.email = email;
        this.password = password;
        this.typeOfAccount = typeOfAccount;
        this.phone_number = phone_number;
    }

    public Student() {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(int typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "Student{" +
                "student_name='" + student_name + '\'' +
                ", student_id=" + student_id +
                ", dependency='" + dependency + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", typeOfAccount=" + typeOfAccount +
                ", phone_number='" + phone_number + '\'' +
                '}';
    }
}
