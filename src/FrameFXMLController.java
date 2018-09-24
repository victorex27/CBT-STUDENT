/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class FrameFXMLController implements Initializable {

    
    /**
     * Initializes the controller class.
     */
    @FXML
    private Button closeButton;
    
    @FXML private Label fullName;

    private static String studentName;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        fullName.setText(studentName);
                 
    }
    
    public static void setVariables(String _studentName){
    
        studentName = _studentName;
    
    }

    @FXML
    public void shutDownApp(ActionEvent actionEvent) {

        Logger.getLogger(FrameFXMLController.class.getName()).log(Level.SEVERE, "Closing App", this);

        try {
            // TODO

            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
            System.exit(0);
            //this.stage.initStyle(StageStyle.UNDECORATED);
            //primaryStage.setScene(scene);
            //primaryStage.initStyle(StageStyle.UNDECORATED);
            //primaryStage.show();
            //Stage stage = (Stage) imageView.getScene().getWindow();
            //Parent root = FXMLLoader.load(getClass().getResource("UserDashboardFXML.fxml"));
            //Scene scene = new Scene(root);
        } catch (Exception ex) {
            Logger.getLogger(FrameFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
