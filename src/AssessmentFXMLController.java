/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AssessmentFXMLController implements Initializable {

    @FXML
    private Pane rightPane;
    private Course course;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BackButtonController.setPrevious("CourseDefaultHomeFXML.fxml");
    }

    public void setCourse(Course course) {
        this.course = course;
        System.out.println("Course set "+course.getCourseCode());
        try {
            Student student = THomeFXMLDocumentController.student;
            
            // TODO
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentQuestionFormatFXML.fxml"));
            AnchorPane anchorPane = loader.load();
            StudentQuestionFormatFXMLController controller = loader.getController();
            controller.setQuestions(student.getId(), student.getAllQuestions(course.getCourseCode()));

            controller.setRegId(course.getRegId());
            controller.play(student, course.getCourseCode());

            //rightPane.getChildren().clear();
            rightPane.getChildren().add(anchorPane);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AssessmentFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AssessmentFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
