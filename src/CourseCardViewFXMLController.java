/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class CourseCardViewFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML private Label courseCode;
    @FXML private Label courseTitle;
    
    private String courseCodeString;
    private String courseTitleString;
    
    private Student student;
    
    private Course course;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    
    @FXML
    public void onClick(ActionEvent evt){
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseDefaultHomeFXML.fxml"));
            AnchorPane anchor = (AnchorPane) loader.load();
            CourseDefaultHomeFXMLController con = loader.getController();
            
            con.setCourse(course);
            //con.setCourseCode(course.getCourseCode());
            //con.setCourseTitle(course.getCourseTitle());
            ScreenController.changeScreen(anchor);
        } catch (IOException ex) {
            Logger.getLogger(CourseCardViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
        courseCode.setText(course.getCourseCode());
        courseTitle.setText(course.getCourseTitle());
    }

    
}
