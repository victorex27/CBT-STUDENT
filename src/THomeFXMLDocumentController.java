
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class THomeFXMLDocumentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label fullName;
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox vBox;

    static Student student;

    private static Course currentCourse;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            // initialize fullName label

            fullName.setText(student.getFullName());

            /**
             * Clean this up*
             */
            student.setDepartment();
            student.retrieveCourses();

            showStudentView(student);

        } catch (Exception ex) {
            Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This displays the view only for students * *
     */
    private void showStudentView(Student student) throws ClassNotFoundException, Exception {

        if (student.getCourses().size() < 1) {
            throw new Exception("No Registered Course for student ");
        }

        AtomicInteger count = new AtomicInteger(1);
        AtomicInteger x = new AtomicInteger(0);
        AtomicInteger y = new AtomicInteger(0);

        student.getCourses().forEach(a -> {

            try {

                AnchorPane anchor;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseCardViewFXML.fxml"));
                anchor = (AnchorPane) loader.load();
                CourseCardViewFXMLController con = loader.getController();
                //CourseCardViewFXMLController.setCourse(a);
                con.setCourse(a);
                

                gridPane.add(anchor, x.getAndIncrement() / count.get(), y.getAndIncrement() % count.get());

                count.getAndIncrement();
                //vBox.getChildren().add( anchor );
                //rightPane.getChildren().add(anchor);

                /*
                link.setText(a.getCourseCode());
                link.setOnAction(e -> {
                
                try {
                
                AnchorPane anchor;
                
                setCurrentCourse(a);
                //dragPane.setVisible(false);
                vBox.setVisible(false);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseCardViewFXML.fxml"));
                try {
                anchor = (AnchorPane) loader.load();
                CourseCardViewFXMLController con = loader.getController();
                
                //con.setDocumentId(1);
                //StudentQuestionFormatFXMLController controller = loader.getController();
                //controller.setQuestions(student.getId(), student.getAllQuestions(a.getCourseCode()));
                
                //controller.setRegId(a.getRegId());
                //controller.play(student, a.getCourseCode());
                
                //rightPane.getChildren().clear();
                rightPane.getChildren().add(anchor);
                
                } catch (IOException ex) {
                Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                } catch (Exception ex) {
                Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                });
                
                 */
                //vBox.getChildren().add();
            } catch (IOException ex) {
                Logger.getLogger(THomeFXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    public static void setPerson(Student _person) {

        student = _person;
    }

    public static void setCurrentCourse(Course _course) {

        currentCourse = _course;
    }

}
