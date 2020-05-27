package com.example.unipool;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefinitiveStudentsClass extends AccountType{

    @SerializedName("studentName")
    @Expose
    private String studentName;

    @SerializedName("studentId")
    @Expose
    private int studentId;

    @SerializedName("dependency")
    @Expose
    private String dependency;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    public DefinitiveStudentsClass(String name, int value, String studentName, int studentId, String dependency, String email, String phoneNumber) {
        super(name, value);
        this.studentName = studentName;
        this.studentId = studentId;
        this.dependency = dependency;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public DefinitiveStudentsClass() {
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "DefinitiveStudentsClass{" +
                "studentName='" + studentName + '\'' +
                ", studentId=" + studentId +
                ", dependency='" + dependency + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
