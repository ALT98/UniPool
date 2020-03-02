package com.example.unipool;

public class Utilities {

    public static final String USER_TABLE = "users";
    public static final String STUDENT_ID = "student_id";
    public static final String STUDENT_NAME = "student_name";
    public static final String DEPENDENCY = "dependency";
    public static final String TYPE_OF_ACCOUNT = "typeOfAccount";
    public static final String EMAIL = "email";

    public static final String CREATE_TABLE_USER = "CREATE TABLE "+ USER_TABLE +" ("+ STUDENT_ID +" INTEGER, "+ STUDENT_NAME + " TEXT, " +
            DEPENDENCY + " TEXT, " + EMAIL + " TEXT, " + TYPE_OF_ACCOUNT + " INTEGER)";

}
