
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
import javafx.event.ActionEvent;
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
    private GridPane gridPane;
   

    static Student student;

    private static Course currentCourse;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            // initialize fullName label

            
            

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

        //gridPane.getChildren().clear();
        student.getCourses().forEach(a -> {

            try {

                //System.out.println("Student course"+a.getCourseCode());
                
                AnchorPane anchor;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseCardViewFXML.fxml"));
                anchor = (AnchorPane) loader.load();
                CourseCardViewFXMLController con = loader.getController();
                //CourseCardViewFXMLController.setCourse(a);
                con.setCourse(a);
                
                
                
                gridPane.add(anchor,x.get() % count.get(), y.get());
       
                //System.out.println(x.get()+" : "+y.get());
                //gridPane.add(anchor, x.getAndIncrement() / count.get(), y.getAndIncrement() % count.get());

                count.getAndIncrement();
                
                if(x.get() == 1){
                    
                    y.getAndIncrement();
                }
                x.incrementAndGet();
                
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
    
    @FXML private void logout(ActionEvent evt) throws IOException{
        
        ScreenController.changeScreen(FXMLLoader.load(getClass().getResource("LoginFXMLDocument.fxml")));
    }
    
    

}
