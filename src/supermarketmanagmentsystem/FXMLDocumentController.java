/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package supermarketmanagmentsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane login_form;
    @FXML
    private TextField login_username;
    @FXML
    private PasswordField login_password;
    @FXML
    private Button login_btn;

    private Connection connect;
    private ResultSet result;
    private PreparedStatement prepare;

    private double x = 0;
    private double y = 0;

    public void handleLogin() {
        String username = login_username.getText();
        String password = login_password.getText();

        connect = database.connectDb();

        try {
            Alert alert;

            if (username.isEmpty() || password.isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
                return;
            }

            String adminQuery = "SELECT * FROM admin WHERE username = ? AND password = ?";
            prepare = connect.prepareStatement(adminQuery);
            prepare.setString(1, username);
            prepare.setString(2, password);
            result = prepare.executeQuery();

            if (result.next()) {

                getData.username = username;
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Login as Admin!");
                alert.showAndWait();

                login_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("adminDashboard.fxml"));
                Stage stage = new Stage();

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                });

                stage.initStyle(StageStyle.TRANSPARENT);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                return;
            }

            String employeeQuery = "SELECT * FROM employee WHERE employee_id = ? AND password = ?";
            prepare = connect.prepareStatement(employeeQuery);
            prepare.setString(1, username);
            prepare.setString(2, password);
            result = prepare.executeQuery();

            if (result.next()) {

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Login as Employee!");
                alert.showAndWait();

                login_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("employeeDashboard.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                return;
            }

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Username/ID or Password");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
