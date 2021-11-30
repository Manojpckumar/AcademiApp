package com.sysirohub.academicapp.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @POST(APIClient.APPEND_URL + "register")
    Call<JsonObject> registerUser(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getStatusbyemail")
    Call<JsonObject> getStatusByEmail(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "login")
    Call<JsonObject> userLogin(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "addClass")
    Call<JsonObject> addClasses(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getAllClasses")
    Call<JsonObject> getAllClasses(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "AllStudentsByClass")
    Call<JsonObject> AllStudentsByClass(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "addSubjects")
    Call<JsonObject> addSubjecttoAPI(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getSubjectsbyClass")
    Call<JsonObject> getAllSubjects(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "AllTeachers")
    Call<JsonObject> getAllTeachers(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "AssignTeacher")
    Call<JsonObject> assignSubToTeacher(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getAssignedClass")
    Call<JsonObject> getAssignedClasses(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getAssignedClassbysub")
    Call<JsonObject> getAssignedSubjectsByClass(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getActiveWeekdays")
    Call<JsonObject> getWeekDays(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "AddTimetable")
    Call<JsonObject> AddTimetable(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "TeacherApproval")
    Call<JsonObject> updateTeacherStatus(@Body JsonObject object);

    @POST(APIClient.APPEND_URL + "getAttendanceModule")
    Call<JsonObject> getTimeTable(@Body JsonObject object);



}
