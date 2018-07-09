/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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
    Label serialNumberLabel;
    @FXML
    Label questionLabel;
    @FXML
    Label answerALabel;
    @FXML
    Label answerBLabel;
    @FXML
    Label answerCLabel;
    @FXML
    Label answerDLabel;
    @FXML
    Label answerELabel;
    @FXML
    RadioButton radioButtonA;
    @FXML
    RadioButton radioButtonB;
    @FXML
    RadioButton radioButtonC;
    @FXML
    RadioButton radioButtonD;
    @FXML
    RadioButton radioButtonE;
    @FXML Button nextButton;
    @FXML Button prevButton;

    @FXML
    Label timerHour;

    @FXML
    Label timerMinute;

    @FXML
    Label timerSecond;
    
    @FXML
    GridPane gridPane;
    
    @FXML
    private AnchorPane anchorPane;

    
    private String courseCode;
    private Student student;
    private ArrayList<Question> questions;

    private String id;
    private int currentSN;
    private Map<Integer, Boolean> scoreSheet;
    //hold the index of question with their answer;
    private Map<Integer, String> answerQuestions;
    private int totalNumberOfQuestions;
    private int numberOfCorrectAnswers;

    private ArrayList<Integer> answeredQuestionIndex;
    private String selectedAnswer;
    private String correctAnswer;

    private Connection connection;
    private PreparedStatement pStatement;

    private ToggleGroup toggleGroup;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        questions = new ArrayList<>();
        scoreSheet = new HashMap<>();
        answerQuestions = new HashMap<>(); 
        answeredQuestionIndex = new ArrayList<>();
        
        try {
            connection = SimpleConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(StudentQuestionFormatFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentQuestionFormatFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        currentSN = 0;
        
        
    }
    
    public void play(Student student, String courseCode) throws Exception{
    
        this.courseCode = courseCode;
        this.student = student;
        
        
        setQuestions(this.student.getId(), student.getAllQuestions(courseCode));
        
        toggleGroup = new ToggleGroup();

        radioButtonA.setToggleGroup(toggleGroup);
        radioButtonB.setToggleGroup(toggleGroup);
        radioButtonC.setToggleGroup(toggleGroup);
        radioButtonD.setToggleGroup(toggleGroup);
        radioButtonE.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle oldToggle, Toggle newToggle) -> {

            if( newToggle != null ){
            
                selectedAnswer = (String) newToggle.getUserData();
                answerQuestions.put(currentSN, selectedAnswer);
            }
            

        });

        // this will not work
        showQuestion(currentSN);

       CountDownTimer timer = new CountDownTimer(1, 0, 0);
    
    }
    
    
    
        
    

    @FXML
    private void showNext() {

        updateScore();
        currentSN++;

        showQuestion(currentSN);
    }

    private void updateScore() {

        
        boolean t;

        if (selectedAnswer == null ? correctAnswer == null : selectedAnswer.equals(correctAnswer)) {
            t = true;

        } else {

            t = false;
        }

        scoreSheet.put(currentSN, t);

        int total = (int) scoreSheet.values().stream().filter(e -> e == true).count();

       

        try {

            String sqlQuery = "UPDATE course_registration SET result = ? where reg_number = ? AND course_code = ?";

            pStatement = connection.prepareStatement(sqlQuery);

            pStatement.setInt(1, total);
            pStatement.setString(2, id);
            pStatement.setString(3, courseCode);

            pStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentQuestionFormatFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     

        
    }

    @FXML
    private void onSubmit() {

        updateScore();
        try {

            String sqlQuery = "UPDATE course_registration SET status = 'close' where reg_number = ? and course_code =? ";

            pStatement = connection.prepareStatement(sqlQuery);

           
            pStatement.setString(1, id);
            pStatement.setString(2, courseCode);

            pStatement.executeUpdate();
            anchorPane.getChildren().clear();
            
            Label label = new Label("Submitted");        
            label.setStyle("-fx-color:white;");
            AnchorPane.setTopAnchor(label, 10.0); 
            anchorPane.getChildren().add(label);
            //ScreenController.changeScreen(FXMLLoader.load(getClass().getResource("THomeFXMLDocument.fxml")));
        
        } catch (SQLException ex ) {
            Logger.getLogger(StudentQuestionFormatFXMLController.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        
            
        
        
        
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

        
        if( currentSN == totalNumberOfQuestions -1 ){
        
            nextButton.setDisable(true);
        }else{
        
            nextButton.setDisable(false);
        }
        if(currentSN == 0){
            prevButton.setDisable(true);
        
        }else{
        
            prevButton.setDisable(false);
        }
        
        selectedAnswer = "";
        toggleGroup.selectToggle(null);
        
        
        
        Question q = this.questions.get(i);
       

        correctAnswer = q.getA();

        serialNumberLabel.setText(String.valueOf(i + 1));

        
        //You can scramble the options here.
        String qu = q.getQuestion();
        String a = q.getA();
        String b = q.getB();
        String c = q.getC();
        String d = q.getD();
        String e = q.getE();
        
        questionLabel.setText(qu);
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


        if(answerQuestions.containsKey(currentSN)){
            
            String savedAnswer =  answerQuestions.get(currentSN);
            if( radioButtonA.getUserData().equals(savedAnswer)){
            
                radioButtonA.setSelected(true);
            }else if( radioButtonB.getUserData().equals(savedAnswer)){
            
                radioButtonB.setSelected(true);
            }else if( radioButtonC.getUserData().equals(savedAnswer)){
            
                radioButtonC.setSelected(true);
            }else if( radioButtonD.getUserData().equals(savedAnswer)){
            
                radioButtonD.setSelected(true);
            }else if( radioButtonE.getUserData().equals(savedAnswer)){
            
                radioButtonE.setSelected(true);
            }
            
            
        
        }
        
        
        if( q.getC() == null ){
        
            radioButtonC.setVisible(false);
            
        }else if( q.getD() == null ){
        
            radioButtonD.setVisible(false);
        }else if( q.getE() == null ){
        
            radioButtonE.setVisible(false);
        }

    }

    public void setQuestions(String id, ArrayList<Question> questions) {

       
        this.questions = questions;
        this.id = id;
        totalNumberOfQuestions = questions.size();
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

            Timer timer = new Timer();

            TimerTask task = new TimerTask() {
                int i = 0;

                @Override
                public void run() {

                    Platform.runLater(() -> {
                        timerHour.setText(String.valueOf(getHour()));
                        timerMinute.setText(String.format("%02d",getMinute()));
                        timerSecond.setText(String.format("%02d",getSecond()));
                    });

                    remainingTime = totalTime - i * rate;

                    if (remainingTime == 0) {

                        this.cancel();
                        
                        onSubmit();
                    }

                    i++;

                }
            };

            timer.scheduleAtFixedRate(task, 0, rate);

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
