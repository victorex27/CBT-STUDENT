/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AssignmentUploadFXMLController implements Initializable {

    private Student student = THomeFXMLDocumentController.student;
    private Course course;
    private ArrayList<Question> questions;
    private ObservableList<Pane> data;
    @FXML
    private ListView listView;
    @FXML private Label noResultLabel;
    
    private static int regId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BackButtonController.setPrevious("CourseDefaultHomeFXML.fxml");
        questions = new ArrayList<>();
        data = FXCollections.observableArrayList();
        
    }

    public void setCourse(int regId ) {
        try {
            
            questions = student.getAllAssignmentQuestions(regId);
            showQuestionPane();
            //System.out.println("the amount of assignments for this course is "+questions.size());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AssignmentUploadFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AssignmentUploadFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void showQuestionPane() throws IOException{
        
        if(questions.size() > 0){
            noResultLabel.setVisible(false);
            listView.setVisible(true);
            questions.forEach(e->{
                
                try {
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("AssignmentPaneFXML.fxml"));
                    Pane pane = (Pane) loader.load();
                    AssignmentPaneFXMLController con = loader.getController();
                    con.setLabel(e.getQuestion());
                    con.setCourseRegId(regId);
                    
                    con.setQuestionId(e.getId());
                    con.setData(data);
                    con.setPane(pane);
                    data.add(pane);
                } catch (IOException ex) {
                    Logger.getLogger(AssignmentUploadFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            
            });
            
            
            
        
        }else {
        
           noResultLabel.setVisible(true);
            
        
        }
    
        listView.setItems(data);
    }

}
