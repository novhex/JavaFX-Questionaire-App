/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package questionerapp;

import com.DB.DBInfo;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Novi
 */
public class QuestionerApp extends Application {
    @FXML public static AnchorPane MainPane;
    @FXML Button exit;
    @FXML Stage stage;
    
    private static double initX;
    private static double initY;
    @Override
    
    public void start(Stage primaryStage)throws IOException {
     
      MainPane=(AnchorPane)FXMLLoader.load(QuestionerApp.class.getResource("/com/login/Login_Form.fxml"));

       primaryStage=new Stage(StageStyle.TRANSPARENT);
       this.stage=primaryStage;

       
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
        primaryStage.setResizable(false);
       
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
       
        
       
    }
    
}
