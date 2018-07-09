

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
    private String courseCode;
    private String courseTitle;

    public Course(int id, String courseCode, String courseTitle) {
        this.id = id;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
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
