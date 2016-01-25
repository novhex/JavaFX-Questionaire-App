/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baseui;

import com.addquestion.AddQuestionUIController;
import com.mainui.MainUIController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Novi
 */
public class MainBaseController implements Initializable {
@FXML
private AnchorPane parentPane;
@FXML 
private Button btnScores;
@FXML 
 public  Label accesLevel;
@FXML
private Stage stage;
@FXML
public Button btnConfig;
@FXML
public Button btnStart;
@FXML 
public TextField txtUser;



 private static double initX;
 private static double initY;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    
       draggableUI();
      
    }    
    
   public void initialize(String logged) {
      System.out.println("TODO");
    }

    @FXML
    public void Exit() {
    System.exit(0);
    }
      
    @FXML
    public void accountType(String accType) {
    
        if(accType.equals("super_user")){
            btnConfig.setDisable(false);
        }else if(accType.equals("user")){
        btnConfig.setDisable(true);
        }
    
    }
    @FXML 
    
    public void startQuiz(ActionEvent event)throws IOException{
      ((Node)event.getSource()).getScene().getWindow().hide();
      loadQuizUI();
    }
    @FXML
    public void AddQuestionForm(ActionEvent event)throws IOException{
       ((Node)event.getSource()).getScene().getWindow().hide(); 
       loadAddQuestionUI();
    }
    
    @FXML
    public void loadAddQuestionUI()throws IOException{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/addquestion/AddQuestionUI.fxml"));
            loader.load();
            AddQuestionUIController aqc =loader.getController();
            aqc.lblUser.setText(txtUser.getText());
            aqc.lblAccessLevel.setText(accesLevel.getText());
         
    }
    
    @FXML
    
    public void loadQuizUI()throws IOException {
        
         FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/mainui/MainUI.fxml"));
            loader.load();
            MainUIController mainUIController = loader.getController();
            mainUIController.currentExaminee.setText(txtUser.getText());
            mainUIController.examineeAccesLevel.setText(accesLevel.getText());
          
    }
    
    @FXML
    public void draggableUI() {
        stage=new Stage(StageStyle.TRANSPARENT);
        parentPane.setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        initX = me.getScreenX() - stage.getX();
                        initY = me.getScreenY() - stage.getY();
                     
                    }
                });
               
                //when screen is dragged, translate it accordingly
         parentPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent me) {
                        stage.setX(me.getScreenX() - initX);
                        stage.setY(me.getScreenY() - initY);
                    }
                });
    Scene scene = new Scene(parentPane,Color.TRANSPARENT);
    stage.setScene(scene);
      stage.show();
  
    }
  
}
