/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;


/**
 * FXML Controller class
 *
 * @author USER
 */
public class AssignmentPaneFXMLController implements Initializable {

    private String filePath;
    @FXML
    private Button fileChooserButton;
    @FXML
    private Button submitButton;
    @FXML private Label label;
    private Student student = THomeFXMLDocumentController.student;
    private int courseRegId;
    private int questionId;
    private ObservableList<Pane> data;
    private Pane pane;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt", "*.pdf", "*.docx", "*.doc");
        fileChooser.getExtensionFilters().add(extFilter);

        fileChooserButton.setOnAction((ActionEvent e) -> {

            File file = fileChooser.showOpenDialog((Stage) fileChooserButton.getScene().getWindow());

            if (file.isDirectory() || (file == null)) {

                try {
                    throw new Exception("This is a directory");
                } catch (Exception ex) {
                    //errorLabel.setText(ex.getMessage());
                }
            }
            filePath = file.getAbsolutePath();
            if(!filePath.equals("")){
                
                submitButton.setDisable(false);
            
            }

        });

    }    

    public void setLabel(String text) {
        label.setText(text);
    }

    public void setCourseRegId(int courseRegId) {
        this.courseRegId = courseRegId;
    }
    
    @FXML
    public void onSubmitClicked(ActionEvent evt){
    
            try {
                
                Connection connection = SimpleConnection.getConnection();
                
                String sqlQuery = " INSERT INTO assignment_answer(teacher_id, name, file, type,student_id, question_id) VALUES (?,?,?,?,?,?) ";
                
                
                PreparedStatement pStatement = connection.prepareStatement(sqlQuery);
                
                
                InputStream stream = null;
                try {
                    
                    
                    File file = new File(filePath);
                    stream = new FileInputStream(file);
                    String ext = FilenameUtils.getExtension(file.getAbsolutePath());
                    pStatement.setInt(1, courseRegId);
                    pStatement.setString(2, student.getFullName());
                    pStatement.setBinaryStream(3, stream, (int) (file.length()));
                    pStatement.setString(4, ext);
                    pStatement.setString(5, student.getId());
                    pStatement.setInt(6, questionId);
                } catch (FileNotFoundException | SQLException ex) {
                    Logger.getLogger(AssignmentPaneFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
                pStatement.execute();
                pStatement.close();
                
                connection.close();
                
                data.remove(pane);
                
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(AssignmentPaneFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

        
    
    }

    public void setData(ObservableList<Pane> data) {
        this.data = data;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }
    
    
}
