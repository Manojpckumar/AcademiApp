package com.sysirohub.academicapp.Model;

public class Common {

    public static String teacherID = "0";
    public static String adminID = "0";
    public static String studentID = "0";
    public static String parentID = "0";

    public static String teacherClassId = "0";
    public static String teacherSubjectId = "0";

    public static String userRole = "nomatch";

    public static void setDefault()
    {
        teacherID = "0";
        adminID = "0";
        studentID = "0";
        parentID = "0";
        userRole = "nomatch";
    }

}
