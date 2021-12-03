package com.sysirohub.academicapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sysirohub.academicapp.AllSlot;

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

    //    get all days
    @SerializedName("ActiveWeekdays")
    @Expose
    private List<ActiveWeekday> activeWeekdays = null;

    public List<ActiveWeekday> getActiveWeekdays() {
        return activeWeekdays;
    }

    public void setActiveWeekdays(List<ActiveWeekday> activeWeekdays) {
        this.activeWeekdays = activeWeekdays;
    }


//    TimeTable start

    @SerializedName("Monday")
    @Expose
    private List<Monday> monday = null;

    @SerializedName("Tuesday")
    @Expose
    private List<Tuesday> tuesday = null;

    @SerializedName("Wednesday")
    @Expose
    private List<Wednesday> wednesday = null;

    @SerializedName("Thursday")
    @Expose
    private List<Thursday> thursday = null;

    @SerializedName("Friday")
    @Expose
    private List<Friday> friday = null;

    @SerializedName("Saturday")
    @Expose
    private List<Saturday> saturday = null;

    public List<Monday> getMonday() {
        return monday;
    }

    public void setMonday(List<Monday> monday) {
        this.monday = monday;
    }

    public List<Tuesday> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<Tuesday> tuesday) {
        this.tuesday = tuesday;
    }

    public List<Wednesday> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<Wednesday> wednesday) {
        this.wednesday = wednesday;
    }

    public List<Thursday> getThursday() {
        return thursday;
    }

    public void setThursday(List<Thursday> thursday) {
        this.thursday = thursday;
    }

    public List<Friday> getFriday() {
        return friday;
    }

    public void setFriday(List<Friday> friday) {
        this.friday = friday;
    }

    public List<Saturday> getSaturday() {
        return saturday;
    }

    public void setSaturday(List<Saturday> saturday) {
        this.saturday = saturday;
    }

//    TimeTable end

    //    session times
    @SerializedName("AllSlots")
    @Expose
    private List<AllSlot> allSlots = null;

    public List<AllSlot> getAllSlots() {
        return allSlots;
    }

    public void setAllSlots(List<AllSlot> allSlots) {
        this.allSlots = allSlots;
    }

    // attendanceview
    @SerializedName("AttendanceView")
    @Expose
    private List<AttendanceView> attendanceView = null;

    public List<AttendanceView> getAttendanceView() {
        return attendanceView;
    }

    public void setAttendanceView(List<AttendanceView> attendanceView) {
        this.attendanceView = attendanceView;
    }


}