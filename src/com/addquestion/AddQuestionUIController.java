/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.addquestion;

import com.DB.DBInfo;
import com.DB.Queries;
import com.baseui.MainBaseController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Novi
 */
public class AddQuestionUIController implements Initializable {
@FXML Button btnAdd;
@FXML Button btnSet;
@FXML TextField txtA;
@FXML TextField txtB;
@FXML TextField txtC;
@FXML TextField txtD;
@FXML TextField txtCorrectAns;
@FXML TextField txtQuestion;
@FXML TextField txtGamePlayTimer;
@FXML TableView <QuestionList>table_questions;
@FXML TableColumn col_questions;
@FXML TableColumn col_answers;
@FXML TableColumn tA;
@FXML TableColumn tB;
@FXML TableColumn tC;
@FXML TableColumn tD;
@FXML public Label lblAccessLevel;
@FXML public Label lblUser;
@FXML AnchorPane addquestionPane;
Connection conn=null;
Stage stage;
public static double  initY;
public static double initX;
ResultSet rs=null;
PreparedStatement pst=null;
ObservableList<QuestionList> ol=FXCollections.observableArrayList();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
           conn=DBInfo.ConnectDb();
            showQuestionsToTable();
           col_questions.setCellValueFactory(new PropertyValueFactory("question"));
           col_answers.setCellValueFactory(new PropertyValueFactory("letter"));
           tA.setCellValueFactory(new PropertyValueFactory("chA"));
           tB.setCellValueFactory(new PropertyValueFactory("chB"));
           tC.setCellValueFactory(new PropertyValueFactory("chC"));
           tD.setCellValueFactory(new PropertyValueFactory("chD"));
           
           draggableUI();
           getPlayTimeLength();        

    
    
}
      

    /**
     * close this pane
     */
    public void closeUI(ActionEvent event)throws IOException{
           ((Node)event.getSource()).getScene().getWindow().hide();
            returnToMainUI();
    }
    public void returnToMainUI() throws IOException{
    FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/baseui/mainBase.fxml"));
            loader.load();
            MainBaseController mainBaseController = loader.getController();
            mainBaseController.txtUser.setText(lblUser.getText());
            mainBaseController.accesLevel.setText(lblAccessLevel.getText());
            

}
    /**
     *validate if those mandatory text fields are blank
     * @return
     */
    public boolean if_blank_textfields() {
  if((txtA.getText().length()<=0)||(txtB.getText().length()<=0) || (txtC.getText().length()<=0) || (txtD.getText().length()<=0) || (txtCorrectAns.getText().length()<=0 || txtQuestion.getText().length()<=0))
    {  
    return true;
    }else {
        return false;
  }
 }
 
    /**
     *
     * @param state
     * @return
     */
    public boolean isSaved(int state){
        if(state==1) {
        
            txtCorrectAns.setText("");
            txtQuestion.setText("");
            txtA.setText("");
            txtB.setText("");
            txtC.setText("");
            txtD.setText("");
            return true;
        }
        return false;
    }
 
    /**
     * Save questions to database
     */
    public void saveQuestion() {
        
        if(if_blank_textfields()==false){
            //do something good :)
            try {
                  pst=conn.prepareStatement("INSERT INTO question (id, que, ch_a, ch_b, ch_c, ch_d, answer) VALUES (NULL, '"+txtQuestion.getText().toUpperCase()+"', '"+txtA.getText().toUpperCase()+"', '"+txtB.getText().toUpperCase()+"', '"+txtC.getText().toUpperCase()+"', '"+txtD.getText().toUpperCase()+"', '"+txtCorrectAns.getText().toLowerCase()+"');");
                   pst.execute("INSERT INTO question (id, que, ch_a, ch_b, ch_c, ch_d, answer) VALUES (NULL, '"+txtQuestion.getText().toUpperCase()+"', '"+txtA.getText().toUpperCase()+"', '"+txtB.getText().toUpperCase()+"', '"+txtC.getText().toUpperCase()+"', '"+txtD.getText().toUpperCase()+"', '"+txtCorrectAns.getText().toLowerCase()+"');");
                   isSaved(1);
                  showQuestionsToTable();
            }catch(Exception e) { 
            e.printStackTrace();
            }
            
        }else{
            //it will not be added on database
            JOptionPane.showMessageDialog(null,"Hey");
        }
               
    }
    
    public void setPlayTimeInterval() {
    
        int convertedVal=convertGamePlayLength(txtGamePlayTimer.getText());
    //UPDATE `qs`.`cfg` SET `cfg_value` = '60' WHERE `cfg`.`cfg_id` =1;
        try{
        pst=conn.prepareStatement("UPDATE `cfg` SET `cfg_value` = '"+convertedVal+"' WHERE `cfg`.`cfg_id` ='1';");
        pst.execute("UPDATE `cfg` SET `cfg_value` = '"+convertedVal+"' WHERE `cfg`.`cfg_id` ='1';");
        getPlayTimeLength();
        }catch(Exception e){
        
        }
    }
    
    private int convertGamePlayLength(String str){
    
       int convertedValue=0;
       
       
       convertedValue=Integer.parseInt(str)*60;
       
      return convertedValue;
       
    }
    /**
     *show questions to the table view
     */
    public void showQuestionsToTable()
    {
            table_questions.getItems().clear();
            try{
                String query=Queries.SELECT_ALL_FROM_QUESTIONS();
                pst=conn.prepareStatement(query);
                
                rs=pst.executeQuery();
                while(rs.next())
                {
 ol.addAll(new QuestionList(rs.getString("que"),rs.getString("answer"),rs.getString("ch_a"),rs.getString("ch_b"),rs.getString("ch_c"),rs.getString("ch_d")));
                table_questions.setItems(ol);
                 }
            
            }catch(Exception e)
            {
                e.printStackTrace();
            }
             
    }
    
    public void getPlayTimeLength() {
    try{
                String query=Queries.SETTINGS_GAMETIME_LIMIT();
                pst=conn.prepareStatement(query);
                rs=pst.executeQuery();
                if(rs.next())
                {
                int time=Integer.parseInt(rs.getString("cfg_value"));
                int min=time/60;
                
                String timeEnd=String.format("%02d",min);
                txtGamePlayTimer.setText(timeEnd);
               
                }
    }catch(Exception e) { e.printStackTrace();}
    }
    
    
@FXML
    public void draggableUI() {
        stage=new Stage(StageStyle.TRANSPARENT);
 

       
            addquestionPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        initX = me.getScreenX() - stage.getX();
                        initY = me.getScreenY() - stage.getY();
                     
                    }
                });
               
                //when screen is dragged, translate it accordingly
         addquestionPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        stage.setX(me.getScreenX() - initX);
                        stage.setY(me.getScreenY() - initY);
                    }
                });
    Scene scene = new Scene(addquestionPane,Color.TRANSPARENT);
    stage.setScene(scene);
      stage.show();
  
    }
    
}
