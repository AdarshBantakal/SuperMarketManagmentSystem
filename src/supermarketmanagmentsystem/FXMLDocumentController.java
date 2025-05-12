  /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package supermarketmanagmentsystem;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController implements Initializable {

    @FXML private AnchorPane admin_form, employee_form;
    @FXML private TextField admin_username, employee_id;
    @FXML private PasswordField admin_password, employee_password;
    @FXML private Button admin_login, employee_login;
    @FXML private Hyperlink employee_hyperlink, admin_hyperlink;
    
    private Connection connect;
    private ResultSet result;
    private PreparedStatement prepare;
    
    private double x=0;
    private double y=0;
    public void employeeLogin(){
        String employeeData = "SELECT * FROM employee WHERE employee_id=? and password = ?  ";
        connect = database.connectDb();
        try{
            Alert alert;
            if(employee_id.getText().isEmpty()||employee_password.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                 alert.setHeaderText(null); 
                alert.setContentText("Please fill all blank fields"); 
                alert.showAndWait(); 
            }else{ 
                prepare = connect.prepareStatement (employeeData); 
                prepare.setString(1,employee_id.getText()); 
                prepare.setString (2, employee_password.getText()); 
                result = prepare.executeQuery(); 
                if (result.next()) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null); 
                    alert.setContentText("Sucessfully Login!"); 
                    alert.showAndWait(); 
                     
                    employee_login.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("employeeDashboard.fxml"));
                     Stage stage = new Stage();
                      Scene scene = new Scene(root);
                      stage.setScene(scene);
        
        
        
                        stage.show();
                    //THEN LETS PROCEED TO DASHBOARD FORM 
                }else{ 
                    alert = new Alert (AlertType.ERROR); 
                    alert.setTitle("Error Message"); 
                    alert.setHeaderText(null); 
                    alert.setContentText("Wrong Employee Id or Passward"); 
                    alert.showAndWait(); 
                    //IF WRONG DATA YOU GAVE THEN ERROR MESSAGE
                }   
            }
       

       

    } catch (Exception e) {
        e.printStackTrace();
    }
        
    }
    public void adminLogin(){
        String username = admin_username.getText();
        String password = admin_password.getText();
        String adminData = "SELECT * FROM admin WHERE username=? and password = ?  ";
        connect = database.connectDb();
        try{
            Alert alert;
            if(username.isEmpty()||password.isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                 alert.setHeaderText(null); 
                alert.setContentText("Please fill all blank fields"); 
                alert.showAndWait(); 
            }else{ 
                prepare = connect.prepareStatement (adminData); 
                prepare.setString(1, username); 
                prepare.setString (2, password); 
                result = prepare.executeQuery(); 
                if (result.next()) {
                    getData.username=admin_username.getText();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null); 
                    alert.setContentText("Sucessfully Login!"); 
                    alert.showAndWait(); 
                     
                    admin_login.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
                     Stage stage = new Stage();
                     
                     root.setOnMousePressed((MouseEvent event)->{
                     x=event.getSceneX();
                     y=event.getSceneY();
                     });
                     
                     root.setOnMouseDragged((MouseEvent event)->{
                      stage.setX(event.getScreenX() - x);
                      stage.setY(event.getScreenY() - y);
                    });
                     
                     stage.initStyle(StageStyle.TRANSPARENT);
                      Scene scene = new Scene(root);
                      stage.setScene(scene);
                        stage.show();
                    //THEN LETS PROCEED TO DASHBOARD FORM 
                }else{ 
                    alert = new Alert (AlertType.ERROR); 
                    alert.setTitle("Error Message"); 
                    alert.setHeaderText(null); 
                    alert.setContentText("Wrong Ussername or Passward"); 
                    alert.showAndWait(); 
                    //IF WRONG DATA YOU GAVE THEN ERROR MESSAGE
                }   
            }
       

       

    } catch (Exception e) {
        e.printStackTrace();
    }}
    
public void switchForm(ActionEvent event){
        if(event.getSource()==admin_hyperlink){
            admin_form.setVisible(false);
            employee_form.setVisible(true);
        }
        else if(event.getSource()==employee_hyperlink){
             admin_form.setVisible(true);
            employee_form.setVisible(false);
        }
    }
public void close(){
    System.exit(0);
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {      
    }
}

   
    