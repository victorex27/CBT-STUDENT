


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class Student extends Person{
    
   
    private String department;
    private ArrayList<Course> listOfCourses;
    private Connection connection;
    
    public Student() throws ClassNotFoundException, Exception{
    
        //super();
        listOfCourses = new ArrayList<>();
        
    }
    
    //this is called when the student wants to take an examination
    private void takeExam(String subject){
    
    }
    
    public void retrieveCourses() throws SQLException, ClassNotFoundException, Exception{
        
        
        connection = SimpleConnection.getConnection();

        String sqlQuery = "SELECT course_registration.id, "
                + "course_code, status, result,course_title, course.id as courseid "
                + "FROM course_registration INNER JOIN course "
                + "ON course.id = course_registration.course_id "
                + "WHERE reg_number = ? AND status = 'open'";

        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        pStatement.setString(1, this.getId());

        ResultSet resultSet = pStatement.executeQuery();

        if (!resultSet.next()) {

            connection.close();
            //throw new Exception("No record Found 1");

        } else {
            System.out.println("Record found");
            resultSet.beforeFirst();
            while (resultSet.next()) {

                //System.out.printf("%s    ",resultSet);
                listOfCourses.add(new Course(resultSet.getInt("courseid"), resultSet.getString("course_code"), resultSet.getString("course_title")));

            }
        }

        connection.close();

    
    }
    
    public ArrayList<Course> getCourses() throws SQLException, ClassNotFoundException, Exception {

        
        return this.listOfCourses;

    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment() throws SQLException, Exception {
    
        connection = SimpleConnection.getConnection();

        
        String sqlQuery = "SELECT department FROM student WHERE person_id = ? ";

        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        pStatement.setString(1, this.getId());

        ResultSet resultSet = pStatement.executeQuery();

        System.out.println(this.getId());
        if (!resultSet.next()) {

            connection.close();
            throw new Exception("No record Found 2");

        } else {
            resultSet.beforeFirst();
            while (resultSet.next()) {

                department = resultSet.getString("department");
               
            }
        }

        connection.close();

    
        
        
    }

    public ArrayList<Question> getAllQuestions(String course_code) throws SQLException, ClassNotFoundException, Exception {
        
        ArrayList<Question> allQuestions = new ArrayList<>();
        connection = SimpleConnection.getConnection();

        // change this
        String sqlQuery = "SELECT question,a,b,c,d,e FROM exam_question WHERE teacher_id = ( SELECT id FROM Teacher WHERE course_id = (SELECT id FROM  course WHERE course_code = ? ) ) ";

        PreparedStatement pStatement = connection.prepareStatement(sqlQuery);

        pStatement.setString(1, course_code);

        ResultSet resultSet = pStatement.executeQuery();

      
        if (!resultSet.next()) {

            connection.close();
            throw new Exception("No record Found 2");

        } else {
            resultSet.beforeFirst();
            while (resultSet.next()) {

                Question q = new Question.Builder(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)).addC(resultSet.getString(4)).addD(resultSet.getString(5)).addE(resultSet.getString(6)).build();
                allQuestions.add(q);
               
            }
        }

        connection.close();

    
        
        
        
        return allQuestions;
    }
    
}
