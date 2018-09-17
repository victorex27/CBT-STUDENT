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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class CourseDefaultHomeFXMLController implements Initializable {
    
    
    @FXML Label result;
    
    @FXML private Label courseTitle;
    @FXML private Label courseCode;

    private static Course course;
    static String courseCodeString;
    static String courseTitleString;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        BackButtonController.setPrevious("THomeFXMLDocument.fxml");
        courseCode.setText( course.getCourseCode());
        courseTitle.setText( course.getCourseTitle());
    }    

    public static void setCourse(Course c){
    
        course = c;
    }
    @FXML
    public void onAssessmentClick(ActionEvent evt){
    
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AssessmentFXML.fxml"));
            AnchorPane anchor = (AnchorPane) loader.load();
            AssessmentFXMLController con = loader.getController();
            con.setCourse(course);
           
            
            ScreenController.changeScreen(anchor);
        } catch (IOException ex) {
            Logger.getLogger(CourseDefaultHomeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    public void onLibraryClick(ActionEvent evt){
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReadingListFXML.fxml"));
            AnchorPane anchor = (AnchorPane) loader.load();
            ReadingListFXMLController con = loader.getController();
            con.setDocumentId(course.getRegId());
            //con.setCourse(course);
           
            
            ScreenController.changeScreen(anchor);
        } catch (IOException ex) {
            Logger.getLogger(CourseDefaultHomeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    @FXML
    public void onAssinmentClick(ActionEvent event){
    
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignmentUploadFXML.fxml"));
            AnchorPane anchor = (AnchorPane) loader.load();
            AssignmentUploadFXMLController con = loader.getController();
            con.setCourse(course.getRegId());
            
           
            ScreenController.changeScreen(anchor);
        } catch (IOException ex) {
            Logger.getLogger(CourseDefaultHomeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Course getCourse() {
        return course;
    }

   
    
}
