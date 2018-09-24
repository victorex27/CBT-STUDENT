
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 *
 * @author USER
 */
public class LoginFXMLDocumentController implements Initializable {

    //why cant the fxml fields be static
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button button;
    @FXML
    private ImageView warningImage;
    @FXML
    private Label warningText;

    @FXML
    private void handleLoginAction(ActionEvent event) {

        warningImage.setVisible(false);
        warningText.setVisible(false);

        try {
            String uName = username.getText().trim();
            String pWord = password.getText().trim();

            if (uName == null || "".equals(uName) || pWord == null || "".equals(pWord)) {

                throw new Exception("Fields Cannot be empty");

            }

            Student student = new Student();
            /* Remember to change*/
            if (!student.login(uName, pWord)) {
                throw new Exception("Incorrect Username and Password Combination");
            }

            FrameFXMLController.setVariables(student.getFullName());
            THomeFXMLDocumentController.setPerson(student);
            ScreenController.changeScreen(FXMLLoader.load(getClass().getResource("THomeFXMLDocument.fxml")));

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
            warningText.setText(ex.getMessage());
            warningImage.setVisible(true);
            warningText.setVisible(true);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    

}
