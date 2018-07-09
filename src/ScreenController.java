


import java.io.IOException;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USER
 */
public class ScreenController {

    private static Scene scene;

    public ScreenController(Scene _main) throws IOException {
        scene = _main;
        
    }
    
    public static void changeScreen(Pane pane){
    
        scene.setRoot(pane);
    }

 

}
