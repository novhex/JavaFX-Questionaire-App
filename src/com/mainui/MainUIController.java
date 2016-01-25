/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mainui;


import java.net.URL;
import java.sql.*;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.*;
import javafx.scene.control.*;
import com.DB.*;
import com.baseui.MainBaseController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Novi
 */



public class MainUIController implements Initializable {
 @FXML TableView<TestClass> questionsLog; 
@FXML TableColumn questions;
@FXML  TextField txtTimer;
 @FXML TextField txtAnswer;
 @FXML TextArea txt_question;
 @FXML TextField txtMistakes;
 @FXML Label lbl_choices;
 @FXML Button btn_proceed;
 @FXML Label lbl_a;
 @FXML Label lbl_b;
 @FXML Label lbl_c;
 @FXML Label lbl_d;
 @FXML TextField txtQuestID;
 @FXML TextField txtCorrectAnswer;
 @FXML TextField txtScore;
 @FXML AnchorPane MainPane;
 @FXML Button btnClose;
 @FXML Label timerLabel;
 @FXML public Label currentExaminee;
 @FXML public Label examineeAccesLevel;
 @FXML Stage stage;
  private IntegerProperty timeSeconds;
  ObservableList<TestClass> ol=FXCollections.observableArrayList();
 Connection conn=null;
ResultSet rs=null;
PreparedStatement pst=null;
  private Timeline timeline;
   private int i=10;
static int interval;
static Timer timer;
private static double initX;
private static double initY;
 Alert alert = new Alert(AlertType.WARNING);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        conn=DBInfo.ConnectDb();
        check_if_db_hasContent();
        txtScore.setText(0+"");
        getQuestion(1+"");
        getPlayLength();
        draggableUI();
     btnClose.setDisable(true);
     timerLabel.textProperty().bind(timeSeconds.asString());
     timerLabel.setTextFill(Color.BLACK);
     timerLabel.setStyle("-fx-font-size: 3em;");
    
 
     // questions.setCellValueFactory(new PropertyValueFactory("que"));
    }
    @FXML
    private void getQuestion(String questionID){
   String sql =Queries.SELECT_SPECIFIC_RECORD();
       
    try{
       pst=conn.prepareStatement(sql);
      pst.setString(1, questionID);
       rs=pst.executeQuery();
       if (rs.next()){
         
           txtQuestID.setText(rs.getString("id"));
           txt_question.setText(rs.getString("que"));
           lbl_a.setText(""+rs.getString("ch_a"));
           lbl_b.setText(""+rs.getString("ch_b"));
           lbl_c.setText(""+rs.getString("ch_c"));
           lbl_d.setText(""+rs.getString("ch_d"));
           txtCorrectAnswer.setText(rs.getString("answer"));
          }
       else{ alert.setTitle("Info");
        alert.setHeaderText("Application Info");
        alert.setContentText("Please try again!");
        alert.showAndWait();}
       }
       catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
                          }
    }                
    @FXML
    public void nextQuestion(){
        
        if(txtAnswer.getText().equals("")){
        alert.setTitle("Info");
        alert.setHeaderText("Application Info");
        alert.setContentText("Enter your answer before you proceed!");
        alert.showAndWait();
        }else{
        checkAnswer();
        String currentQuestionID=txtQuestID.getText();
        int nextQuestionID=Integer.parseInt(currentQuestionID)+1;
  
       if(checkIfValidChoice(txtAnswer.getText()))
       {
        if(nextQuestionID>getNumber_of_questions())
        {
            timer.cancel();
            timeline.stop();
            btn_proceed.setDisable(true);
            btnClose.setDisable(false);
            
        alert.setTitle("Info");
        alert.setHeaderText("Application Info");
        alert.setContentText("Done . no more questions!");
        alert.showAndWait();
            
        }else{
        
        getQuestion(nextQuestionID+"");
        //randomQuestionNumber();
        txtAnswer.setText("");
        }
        }
       }
    }
    @FXML
    public boolean checkIfValidChoice(String input){
    
        if(input.toLowerCase().equals("a")){return true;}
        if(input.toLowerCase().equals("b")) {return true;}
        if(input.toLowerCase().equals("c")){return true;}
        if(input.toLowerCase().equals("d")){return true;}
        
        return false;
    }
    @FXML
    
    private void randomQuestionNumber(){
    
     int size = getNumber_of_questions();

        ArrayList<Integer> list = new ArrayList<Integer>(size);
        for(int i = 1; i <= size; i++) {
            list.add(i);
        }

        Random rand = new Random();
        while(list.size() > 0) {
            int index = rand.nextInt(list.size());
             System.out.println("gen @: "+index);
           
        }
    }
    
   @FXML
    public void checkAnswer()
    {
       String txtCorrect=txtCorrectAnswer.getText();
       String user_answer=txtAnswer.getText().toLowerCase();
       
        if(user_answer.equals(txtCorrect))
        {
        int currentScore=Integer.parseInt(txtScore.getText());
        currentScore+=1;
        txtScore.setText(currentScore+"");
        }else if((user_answer!=txtCorrect)&&(checkIfValidChoice(user_answer)==true))
        {
            int currentMistake=Integer.parseInt(txtMistakes.getText());
            currentMistake+=1;
            txtMistakes.setText(currentMistake+"");
        }
        else if(checkIfValidChoice(user_answer)==false) {
        alert.setTitle("Info");
        alert.setHeaderText("Application Info");
        alert.setContentText("Thats not a valid choice");
        alert.showAndWait();
        }
       
    }
    
    @FXML
    public int getNumber_of_questions()
    {
      int count=0;  
        try{
        String query=Queries.SELECT_ALL_FROM_QUESTIONS();
        System.out.println(Queries.SELECT_ALL_FROM_QUESTIONS());
        pst=conn.prepareStatement(query);
        rs=pst.executeQuery();
        
        while(rs.next())
        {
           count++; 
        }
        
        }catch(Exception e){
        
        }
        return count;
    }
   @FXML
    public void closeMainUI(ActionEvent event) throws IOException{
        ((Node)event.getSource()).getScene().getWindow().hide();
        returnToMainUI();
    
    
    }
    
    
    @FXML
    public void check_if_db_hasContent() {
    if(getNumber_of_questions()>=1)
    {
        MainPane.setVisible(true);
    }else
    {
        MainPane.setVisible(false);
       
        alert.setTitle("Info");
        alert.setHeaderText("Application Info");
        alert.setContentText("There are no questions found in DB!");
        alert.showAndWait();
        
    }   
    }
    @FXML
    public void getPlayLength() {
       
        try{
                String query=Queries.SETTINGS_GAMETIME_LIMIT();
                pst=conn.prepareStatement(query);
                rs=pst.executeQuery();
                if(rs.next())
                {
                    int time=Integer.parseInt(rs.getString("cfg_value"));
                    countDown(time);
                    listenToLabelTimer(time+"");
                    System.err.println(time);
               
                }
    }catch(Exception e) { e.printStackTrace();}
    }
    
@FXML
private void  countDown(int startTime) {
timeSeconds = new SimpleIntegerProperty(startTime);
  if (timeline != null) {
                    timeline.stop();
                    
                }
                timeSeconds.set(startTime);
                timeline = new Timeline();
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(startTime+1),
                             
                        new KeyValue(timeSeconds, 0)));
                timeline.playFromStart();
              
}

/*
*
*/
@FXML
private void listenToLabelTimer(String secs) {

  
    int delay = 1000;
    int period = 1000;
    timer = new Timer();
    interval = Integer.parseInt(secs);
 
    
    timer.scheduleAtFixedRate(new TimerTask() {
        @Override
        public void run(){
            Platform.runLater(()->{
            setListenerInterval();
        	   
                    if(timerLabel.getText().equals("0")) {
                       setButtonProccedState(true,timerLabel.getText());
                    }
                    
                    if(timerLabel.getText().equals("10")){
                        timerLabel.setTextFill(Color.RED);
                    }
        });
        }
    },delay,period);

}
@FXML
private void setButtonProccedState(boolean state,String count){
    
    if(count.equals("0"))
    {
     btn_proceed.setDisable(state);
     btnClose.setDisable(false);
    }

}

@FXML
public void returnToMainUI() throws IOException{
    FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/baseui/mainBase.fxml"));
            loader.load();
            MainBaseController mainBaseController = loader.getController();
            mainBaseController.txtUser.setText(currentExaminee.getText());
            mainBaseController.accesLevel.setText(examineeAccesLevel.getText());
            mainBaseController.accountType(examineeAccesLevel.getText());
}

@FXML
private static final int setListenerInterval() {
	
    int min=interval/60;
    int second=interval%60;
    
    if (interval == 0)
        timer.cancel();
    return --interval;
}
 @FXML
    public void draggableUI() {
        stage=new Stage(StageStyle.TRANSPARENT);
        MainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        initX = me.getScreenX() - stage.getX();
                        initY = me.getScreenY() - stage.getY();
                     
                    }
                });
               
                //when screen is dragged, translate it accordingly
         MainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        stage.setX(me.getScreenX() - initX);
                        stage.setY(me.getScreenY() - initY);
                    }
                });
    Scene scene = new Scene(MainPane,Color.TRANSPARENT);
    stage.setScene(scene);
    stage.show();
  
    }

}