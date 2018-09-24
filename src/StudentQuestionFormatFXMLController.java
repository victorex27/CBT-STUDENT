/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


/**
 * FXML Controller class
 *
 * @author USER
 */
public class StudentQuestionFormatFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label serialNumberLabel;
    @FXML
    private Label questionLabel;
    @FXML
    private Label answerALabel;
    @FXML
    private Label answerBLabel;
    @FXML
    private Label answerCLabel;
    @FXML
    private Label answerDLabel;
    @FXML
    private Label answerELabel;
    @FXML
    private RadioButton radioButtonA;
    @FXML
    private RadioButton radioButtonB;
    @FXML
    private RadioButton radioButtonC;
    @FXML
    RadioButton radioButtonD;
    @FXML
    private RadioButton radioButtonE;
    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;
    @FXML
    private Button submitButton;

    @FXML
    private Label timerHour;

    @FXML
    private Label timerMinute;

    @FXML
    private Label timerSecond;

    @FXML
    private VBox vBox;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane textAreaPane;

    @FXML
    private TextArea textArea;
    
    @FXML private ImageView imageViewOfQuestion;

    private String courseCode;
    private Student student;
    private ArrayList<Question> questions;

    private String id;
    private int currentSN;
    private Question currentQuestion;
    private Map<Integer, Boolean> scoreSheet;
    //hold the index of question with their answer;
    private Map<Integer, String> answerQuestions;
    private int totalNumberOfQuestions;
    private int regId;

    private String selectedAnswer;
    private String correctAnswer;

    private Connection connection;
    private PreparedStatement pStatement;

    private ToggleGroup toggleGroup;

    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        questions = new ArrayList<>();
        scoreSheet = new HashMap<>();
        answerQuestions = new HashMap<>();

        try {
            connection = SimpleConnection.getConnection();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(StudentQuestionFormatFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

       
        currentSN = 0;

    }

    public void play(Student student, String courseCode) throws Exception {

        this.courseCode = courseCode;
        this.student = student;

        setQuestions(this.student.getId(), student.getAllQuestions(courseCode));

        toggleGroup = new ToggleGroup();

        radioButtonA.setToggleGroup(toggleGroup);
        radioButtonB.setToggleGroup(toggleGroup);
        radioButtonC.setToggleGroup(toggleGroup);
        radioButtonD.setToggleGroup(toggleGroup);
        radioButtonE.setToggleGroup(toggleGroup);

        String sqlQuery = "UPDATE course_registration SET status = 'close' where id = ? ";

        pStatement = connection.prepareStatement(sqlQuery);

       
        pStatement.setInt(1, regId);

        pStatement.executeUpdate();

        //connection.close();
        toggleGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) -> {

            if (newToggle != null) {

                selectedAnswer = (String) newToggle.getUserData();
                answerQuestions.put(currentSN, selectedAnswer);
            }

        });

        // this will not work
        showQuestion(currentSN);
        
        System.out.println(""+getDuration(regId));
       String[] array = getDuration(regId).split(":");
        int hour = Integer.parseInt(array[0]);
        int min = Integer.parseInt(array[1]);
        int sec = Integer.parseInt(array[2]);
        

        new CountDownTimer(hour, min, sec);

    }

    private String getDuration(int id) throws SQLException, ClassNotFoundException{
    
        String time= null;
        Connection conn = SimpleConnection.getConnection();
        
        PreparedStatement pStmt = conn.prepareStatement("SELECT duration from teacher where id = (SELECT teacher_id FROM course_registration WHERE id = ?) LIMIT 1");
        pStmt.setInt(1, id);
        ResultSet res = pStmt.executeQuery();
        
        
        while(res.next()){
        
            time = res.getString(1);
        }
          
        conn.close();
        return time;
    
    }
    
    
    @FXML
    private void showNext() {

        updateScore();
        currentSN++;

        showQuestion(currentSN);
    }

    private void updateScore() {

        boolean t;

        if (currentQuestion.isTheory()) {
            
            selectedAnswer = textArea.getText();
            
            if(answerQuestions.containsKey(currentSN)){
                try {

                String sqlQuery = " UPDATE answer SET text = ? WHERE course_registration_id = ? and exam_question_id = ?";

                pStatement = connection.prepareStatement(sqlQuery);
                
                pStatement.setString(1, selectedAnswer );
                pStatement.setInt(2, regId);
                pStatement.setInt(3, currentQuestion.getId());
                

                pStatement.executeUpdate();
              
                //id = PreparedStatement.RETURN_GENERATED_KEYS;
            } catch (SQLException ex) {
                Logger.getLogger(StudentQuestionFormatFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            }else{
                
                 try {

                String sqlQuery = " INSERT INTO answer (course_registration_id, exam_question_id, text) VALUES (?,?,?)";

                pStatement = connection.prepareStatement(sqlQuery);
                
                pStatement.setInt(1, regId);
                pStatement.setInt(2, currentQuestion.getId());
                pStatement.setString(3, selectedAnswer );

                pStatement.executeUpdate();
              
                //id = PreparedStatement.RETURN_GENERATED_KEYS;
            } catch (SQLException ex) {
                Logger.getLogger(StudentQuestionFormatFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            
            }
            
            answerQuestions.put(currentSN, selectedAnswer);
            //t = true;
        } else {
            t = selectedAnswer == null ? correctAnswer == null : selectedAnswer.equals(correctAnswer);

            scoreSheet.put(currentSN, t);

            int total = (int) scoreSheet.values().stream().filter(e -> e == true).count();

            try {

                String sqlQuery = "UPDATE course_registration SET result = ? where id = ? ";

                pStatement = connection.prepareStatement(sqlQuery);

                pStatement.setInt(1, total);
            
                pStatement.setInt(2, regId );

                pStatement.executeUpdate();
                
            } catch (SQLException ex) {
                Logger.getLogger(StudentQuestionFormatFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @FXML
    private void onSubmit(ActionEvent evt) throws IOException {

        updateScore();
        anchorPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader( getClass().getResource("EndOfPaperMessageFXML.fxml"));
        Pane pane = (Pane)loader.load();
        anchorPane.getChildren().add(pane);
        //ScreenController.changeScreen(FXMLLoader.load(getClass().getResource("THomeFXMLDocument.fxml")));

        timeline.stop();
    }

    @FXML
    private void showPrev() {

        updateScore();
        currentSN--;

        showQuestion(currentSN);
    }

    @FXML
    private void showSelected(int i) {

        currentSN = i;

        showQuestion(i);
    }

    private void showQuestion(int i) {

        /**
         * Check the next and previous button
         */
        
        
        if (currentSN == totalNumberOfQuestions - 1) {

            nextButton.setDisable(true);
        } else {

            nextButton.setDisable(false);
        }
        if (currentSN == 0) {
            prevButton.setDisable(true);

        } else {

            prevButton.setDisable(false);
        }

        selectedAnswer = "";
        
        toggleGroup.selectToggle(null);

        Question q = this.questions.get(i);

        // Set Current Question
        setCurrentQuestion(q);
        
        if(q.getImage() != null){
        
            imageViewOfQuestion.setImage(q.getImage());
        }else{
        
            imageViewOfQuestion.setImage(null);
        }

        correctAnswer = q.getA();

        serialNumberLabel.setText(String.valueOf(i + 1));

        String qu = q.getQuestion();

        questionLabel.setText(qu);
        
        if (q.isTheory()) {

            
            //remove vBox
            vBox.setVisible(false);

            //show textAreaPane
            //textAreaPane.setLayoutY(112);
            textArea.setText( answerQuestions.get(currentSN));
            textAreaPane.setVisible(true);

        } else {

            //initialize radio button
            radioButtonA.setVisible(true);
            radioButtonB.setVisible(true);
            radioButtonC.setVisible(true);
            radioButtonD.setVisible(true);
            radioButtonE.setVisible(true);

            //remove textAreaPane
            textAreaPane.setVisible(false);

            //show vBox
            vBox.setVisible(true);

            //You can scramble the options here.
            String a = q.getA();
            String b = q.getB();
            String c = q.getC();
            String d = q.getD();
            String e = q.getE();

            
            answerALabel.setText(a);
            answerBLabel.setText(b);
            answerCLabel.setText(c);
            answerDLabel.setText(d);
            answerELabel.setText(e);

            radioButtonA.setUserData(a);
            radioButtonB.setUserData(b);
            radioButtonC.setUserData(c);
            radioButtonD.setUserData(d);
            radioButtonE.setUserData(e);

            if (answerQuestions.containsKey(currentSN)) {

                String savedAnswer = answerQuestions.get(currentSN);
                if (radioButtonA.getUserData().equals(savedAnswer)) {

                    radioButtonA.setSelected(true);
                } else if (radioButtonB.getUserData().equals(savedAnswer)) {

                    radioButtonB.setSelected(true);
                } else if (radioButtonC.getUserData().equals(savedAnswer)) {

                    radioButtonC.setSelected(true);
                } else if (radioButtonD.getUserData().equals(savedAnswer)) {

                    radioButtonD.setSelected(true);
                } else if (radioButtonE.getUserData().equals(savedAnswer)) {

                    radioButtonE.setSelected(true);
                }

            }

            if (a == null) {

                radioButtonA.setVisible(false);

            }

            if (b == null) {

                radioButtonB.setVisible(false);

            }
            if (c == null) {

                radioButtonC.setVisible(false);

            }
            if (d == null) {

                radioButtonD.setVisible(false);
            }
            if (e == null) {

                radioButtonE.setVisible(false);
            }

        }

    }

    public void setQuestions(String id, ArrayList<Question> questions) {

        this.questions = questions;
        this.id = id;
        totalNumberOfQuestions = questions.size();
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public void setExamid(int Examid) {
        this.regId = Examid;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    class CountDownTimer {

        int totalTime;
        int remainingTime;
        int rate = 1000;
        int hour;
        int min;
        int sec;

        public CountDownTimer(int hour, int min, int sec) {

            //totalTime = sec * 1000 + min * 60 *1000 + hour * 60* 60 * 1000;
            this.hour = hour;
            this.min = min;
            this.sec = sec;

            totalTime = 1000 * (sec + 60 * (min + 60 * hour));
            remainingTime = totalTime;

            AtomicInteger i = new AtomicInteger(0);
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                remainingTime = totalTime - i.getAndIncrement() * rate;

                timerHour.setText(String.valueOf(getHour()));
                timerMinute.setText(String.format("%02d", getMinute()));
                timerSecond.setText(String.format("%02d", getSecond()));

            }));

            timeline.setCycleCount(totalTime / 1000);
            timeline.play();

            timeline.setOnFinished(e -> {

                submitButton.fire();

            });

        }

        public int getHour() {
            hour = (int) TimeUnit.MILLISECONDS.toHours(remainingTime);

            return hour;

        }

        public int getMinute() {

            min = (int) (TimeUnit.MILLISECONDS.toMinutes(remainingTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(remainingTime)));
            return min;
        }

        public int getSecond() {

            sec = (int) (TimeUnit.MILLISECONDS.toSeconds(remainingTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)));
            return sec;

        }

    }

}
