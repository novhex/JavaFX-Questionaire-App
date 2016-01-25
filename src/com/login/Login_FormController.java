/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.login;

import com.DB.DBInfo;
import com.DB.Queries;
import com.baseui.MainBaseController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Novi
 */
public class Login_FormController implements Initializable {
@FXML TextField txtUsername;
@FXML PasswordField txtPassword;
@FXML Button btnLogin;
@FXML Button btn_close;
Connection conn=null;
ResultSet rs=null;
PreparedStatement pst=null;
@FXML AnchorPane loginPane;
@FXML Stage stage;    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       conn=DBInfo.ConnectDb();
       
    }    
    @FXML
    public void login(ActionEvent event)
    {
             try{
                
                String query=Queries.LOGIN_QUERY();
                pst=conn.prepareStatement(query);
                
                   pst.setString(1, txtUsername.getText());
                   pst.setString(2, txtPassword.getText());
                
                   rs=pst.executeQuery();
                   
                   if(rs.next()) {  
                        ((Node)event.getSource()).getScene().getWindow().hide();
                         showMainUI(rs.getString("username"),rs.getString("acc_type"));
                   }else {
                   System.err.println("Invalid Credentials!");
                   
                   }
             }catch(Exception e){
             e.printStackTrace();
             }
    }
    
    public void closeUI(){System.exit(0); }
    
    public void showMainUI(String loggedUser,String access_level) throws IOException
    {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/baseui/mainBase.fxml"));
            loader.load();
            MainBaseController mbController = loader.getController();
            
             mbController.txtUser.setText(loggedUser);
             mbController.accesLevel.setText(access_level);
             mbController.accountType(access_level);
             
             System.err.println("correct ");
             
             
          
             
    }
    
   
   
}
