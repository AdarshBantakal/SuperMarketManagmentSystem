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

        try {
            // Test database connection first
            connect = database.connectDb();
            if (connect == null) {
                showError("Database Connection Error", "Could not connect to the database");
                return;
            }

            if (username.isEmpty() || password.isEmpty()) {
                showError("Input Error", "Please fill all blank fields");
                return;
            }

            // Try admin login
            try {
                String adminQuery = "SELECT * FROM admin WHERE username = ? AND password = ?";
                prepare = connect.prepareStatement(adminQuery);
                prepare.setString(1, username);
                prepare.setString(2, password);
                result = prepare.executeQuery();

                if (result.next()) {
                    getData.username = username;
                    showInfo("Success", "Successfully Login as Admin!");
                    
                    try {
                        // Load admin dashboard
                        login_btn.getScene().getWindow().hide();
                        
                        // Get the resource URL
                        URL location = getClass().getResource("/supermarketmanagmentsystem/adminDashboard.fxml");
                        if (location == null) {
                            showError("FXML Error", "Could not find adminDashboard.fxml");
                            return;
                        }
                        
                        FXMLLoader loader = new FXMLLoader(location);
                        Parent root = loader.load();
                        
                        if (root == null) {
                            showError("FXML Error", "Could not load adminDashboard.fxml");
                            return;
                        }

                        Stage stage = new Stage();
                        setupStage(root, stage);
                        stage.show();
                        return;
                    } catch (Exception e) {
                        showError("Navigation Error", "Failed to open admin dashboard: " + e.getMessage());
                        e.printStackTrace();
                        return;
                    }
                }
            } catch (Exception e) {
                showError("Database Error", "Error checking admin credentials: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            // Try employee login
            try {
                String employeeQuery = "SELECT * FROM employee WHERE employee_id = ? AND password = ?";
                prepare = connect.prepareStatement(employeeQuery);
                prepare.setString(1, username);
                prepare.setString(2, password);
                result = prepare.executeQuery();

                if (result.next()) {
                    getData.username = username;
                    showInfo("Success", "Successfully Login as Employee!");
                    
                    try {
                        // Load employee dashboard
                        login_btn.getScene().getWindow().hide();
                        
                        // Get the resource URL
                        URL location = getClass().getResource("/supermarketmanagmentsystem/employeeDashboard.fxml");
                        if (location == null) {
                            showError("FXML Error", "Could not find employeeDashboard.fxml");
                            return;
                        }
                        
                        FXMLLoader loader = new FXMLLoader(location);
                        Parent root = loader.load();
                        
                        if (root == null) {
                            showError("FXML Error", "Could not load employeeDashboard.fxml");
                            return;
                        }

                        Stage stage = new Stage();
                        setupStage(root, stage);
                        stage.show();
                        return;
                    } catch (Exception e) {
                        showError("Navigation Error", "Failed to open employee dashboard: " + e.getMessage());
                        e.printStackTrace();
                        return;
                    }
                }
            } catch (Exception e) {
                showError("Database Error", "Error checking employee credentials: " + e.getMessage());
                e.printStackTrace();
                return;
            }

            showError("Authentication Error", "Invalid Username/ID or Password");

        } catch (Exception e) {
            showError("System Error", "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupStage(Parent root, Stage stage) {
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
