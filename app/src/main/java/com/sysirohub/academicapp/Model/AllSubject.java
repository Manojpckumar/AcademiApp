package com.sysirohub.academicapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllSubject {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("class_id")
    @Expose
    private String classId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

}