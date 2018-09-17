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
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class BackButtonController implements Initializable {

    @FXML
    private Button returnButton;
    
    @FXML
    private static String cursor;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //System.out.println("Teacher name is "+THomeFXMLDocumentController.getTeacher().getFullName());
    }    
    
    static void setPrevious(String previousPage){
    
        cursor = previousPage;
        
    
    }
    
    @FXML
    private void onClick(ActionEvent evt){
        try {
            System.out.println("Current cursor: "+cursor);
            ScreenController.changeScreen(FXMLLoader.load(getClass().getResource(cursor)));
        } catch (IOException ex) {
            Logger.getLogger(BackButtonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
