package com.sysirohub.academicapp.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static String teacherID = "0";
    public static String adminID = "0";
    public static String studentID = "0";
    public static String parentID = "0";

    public static String teacherClassId = "0";
    public static String teacherSubjectId = "0";

    public static String userRole = "nomatch";

    public static void setDefault() {
        teacherID = "0";
        adminID = "0";
        studentID = "0";
        parentID = "0";
        userRole = "nomatch";
    }

    public static String getSystemDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        return dayOfTheWeek;
    }

    public static String getSystemTime() {

//  24 hour return      HH:mm:ss
//  12 hour return      hh:mm a
        SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm ");
        String currentTime = sdf.format(new Date());

        return currentTime;
    }

    public static Date getSystem24Time() throws ParseException {

//  24 hour return      HH:mm:ss
//  12 hour return      hh:mm a
        SimpleDateFormat sdf = new SimpleDateFormat(" HH:mm:ss ");
        String currentTime = sdf.format(new Date());

        SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");
        Date dates = parser.parse(currentTime);

        return dates;
    }

    public static String getSystemTimeandDate() {
//        yyyy-MM-dd HH:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;

    }

    public static String getSystemDate() {
//        yyyy-MM-dd HH:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;

    }

    public static String getDayNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        String dayNumber;

        switch (dayOfTheWeek) {

            case "Monday":
                dayNumber = "1";
                break;

            case "Tuesday":
                dayNumber = "2";
                break;

            case "Wednesday":
                dayNumber = "3";
                break;

            case "Thursday":
                dayNumber = "4";
                break;

            case "Friday":
                dayNumber = "5";
                break;

            case "Saturday":
                dayNumber = "6";
                break;

            default:
                dayNumber = "0";
                break;
        }

        return dayNumber;
    }


}
