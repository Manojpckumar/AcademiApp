package com.sysirohub.academicapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultData {

    @SerializedName("User_details")
    @Expose
    private UserDetails userDetails;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    //    all classes
    @SerializedName("All_Classes")
    @Expose
    private List<AllClass> allClasses = null;

    public List<AllClass> getAllClasses() {
        return allClasses;
    }

    public void setAllClasses(List<AllClass> allClasses) {
        this.allClasses = allClasses;
    }

//    all students

    @SerializedName("Students")
    @Expose
    private List<Student> students = null;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    //    all subjects
    @SerializedName("AllSubjects")
    @Expose
    private List<AllSubject> allSubjects = null;

    public List<AllSubject> getAllSubjects() {
        return allSubjects;
    }

    public void setAllSubjects(List<AllSubject> allSubjects) {
        this.allSubjects = allSubjects;
    }

    //    all teachers
    @SerializedName("Teachers")
    @Expose
    private List<Teacher> teachers = null;

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    //    all classes teacher
    @SerializedName("Allassigned")
    @Expose
    private List<Allassigned> allassigned = null;

    public List<Allassigned> getAllassigned() {
        return allassigned;
    }

    public void setAllassigned(List<Allassigned> allassigned) {
        this.allassigned = allassigned;
    }

//    all subject by class teacher

    @SerializedName("AllSubyclass")
    @Expose
    private List<AllSubyclas> allSubyclass = null;

    public List<AllSubyclas> getAllSubyclass() {
        return allSubyclass;
    }

    public void setAllSubyclass(List<AllSubyclas> allSubyclass) {
        this.allSubyclass = allSubyclass;
    }

}