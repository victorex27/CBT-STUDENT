/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Make a modification to check if file exists
 * then ask the user if he wants to replace or rename the file.
 * (Modification not yet made) initialize method
 */
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ReadingListFXMLController implements Initializable {

    @FXML
    private ListView listView;
    @FXML
    private Label noResultLabel;
    private ObservableList<String> list;
    Map<String, File> fileMap;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BackButtonController.setPrevious("CourseDefaultHomeFXML.fxml");
        fileMap = new HashMap<>();

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (Desktop.isDesktopSupported()) {
                    File file = fileMap.get(newValue);
                    //Desktop.getDesktop().open(file);

                    /**
                     * Make a modification to check if file exists then ask the
                     * user if he wants to replace or rename the file.
                     * (Modification not yet made)
                     */
                    fileMap.get(newValue).renameTo(new File(System.getProperty("user.home") + "//Documents//" + file.getName()));
                    //System.out.println(new File(System.getProperty("user.home")+"//Documents//"+file.getName()).getAbsolutePath());

                }

            }
        });
    }

    public void setDocumentId(int id) throws FileNotFoundException, IOException {

        list = FXCollections.observableArrayList();
        try {
            Connection connection = SimpleConnection.getConnection();

            String sql = "Select name,file from document where teacher_id = ? ";
            PreparedStatement pStatement = connection.prepareStatement(sql);

            pStatement.setInt(1, id);

            ResultSet result = pStatement.executeQuery();

            if (result.next()) {

                noResultLabel.setVisible(false);
                listView.setVisible(true);
                result.beforeFirst();
                while (result.next()) {

                    String name = result.getString("name");

                    File file = new File(name);

                    InputStream is = result.getBinaryStream("file");
                    OutputStream os = new FileOutputStream(file);

                    byte[] content = new byte[1024];
                    int size = 0;
                    while ((size = is.read(content)) != -1) {
                        os.write(content, 0, size);
                    }

                    os.close();
                    is.close();

                    fileMap.put(name, file);

                    list.add(name);
                }
            }else{
            
                noResultLabel.setVisible(true);
            }

            result.close();
            pStatement.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ReadingListFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        listView.setItems(list);

    }

}
