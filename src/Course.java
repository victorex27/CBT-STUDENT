

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class Course {
    
    private int id;
    private int regId;
    private String courseCode;
    private String courseTitle;

    public Course(int regId, int id, String courseCode, String courseTitle) {
        this.regId = regId;
        this.id = id;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
    }

    public int getRegId() {
        return regId;
    }

    public int getId() {
        return id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }
    
}
