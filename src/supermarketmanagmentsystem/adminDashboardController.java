/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarketmanagmentsystem;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author DELL
 */
public class adminDashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;
    /*  //dashboard
    @FXML private AnchorPane dashbord_form;
    @FXML private Label dashboard_totalIncome;
    @FXML private Label dashboard_activeEmployees;
    @FXML private Label dashboard_IncomeToday;
    @FXML private AreaChart<?, ?> dashboard_chart;
    @FXML private Button dashboard_btn;

    //Employee
    @FXML private AnchorPane employees_form;
    @FXML private TableView<?> employees_tableView;
    @FXML private TableColumn<?, ?> employees_col_employeeID;
    @FXML private TableColumn<?, ?> employees_col_firstName;
    @FXML private TableColumn<?, ?> employees_col_lastName;
    @FXML private TableColumn<?, ?> employees_col_gender;
    @FXML private TableColumn<?, ?> employees_col_password;
    @FXML private TableColumn<?, ?> employees_col_date;

    @FXML private TextField employees_employeeID;
    @FXML private TextField employees_firstName;
    @FXML private TextField employees_lastName;
    @FXML private TextField employees_password;
    @FXML private ComboBox<?> employees_gender;

    @FXML private Button employees_btn;
    @FXML private Button employees_saveBtn;
    @FXML private Button employees_updateBtn;
    @FXML private Button employees_deletebtn;

    //product
    @FXML private AnchorPane addProducts_form;
    @FXML private TableView<?> addProducts_tableView;

    @FXML private TableColumn<?, ?> addProducts_col_productID;
    @FXML private TableColumn<?, ?> addProducts__col_productName;
    @FXML private TableColumn<?, ?> addProducts_col_brandName;
    @FXML private TableColumn<?, ?> addProducts_col_price;
    @FXML private TableColumn<?, ?> addProducts__col_status;

    @FXML private TextField addProducts_productID;
    @FXML private TextField addProducts_productName;
    @FXML private TextField addProducts_brandName;
    @FXML private TextField addProducts_price;
    @FXML private ComboBox<?> addProducts_status;
    @FXML private TextField addProducts_search;

    @FXML private Button addProducts_btn;
    @FXML private Button addProducts_addBtn;
    @FXML private Button addProducts_UpdateBtn;
    @FXML private Button addProducts_deleteBtn;
    @FXML private Button addProducts_clearBtn;
    
    //General controls
    @FXML private Button close;
    @FXML private Button minimize;
    @FXML private Button logout;
    @FXML private Label username;
    
     */

    @FXML
    private AnchorPane addProducts_form;

    @FXML
    private TableColumn<?, ?> employees_col_employeeID;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private TableColumn<?, ?> addProducts__col_status;

    @FXML
    private ComboBox<?> employees_gender;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button addProducts_clearBtn;

    @FXML
    private TableColumn<?, ?> addProducts_col_productID;

    @FXML
    private TableColumn<?, ?> addProducts__col_productName;

    @FXML
    private Button addProducts_btn;

    @FXML
    private Button employees_btn;

    @FXML
    private TextField employees_password;

    @FXML
    private TextField addProducts_productID;

    @FXML
    private Button logout;

    @FXML
    private TableView<?> addProducts_tableView;

    @FXML
    private TableColumn<?, ?> addProducts_col_price;

    @FXML
    private TextField addProducts_brandName;

    @FXML
    private Button addProducts_deleteBtn;

    @FXML
    private Button employees_deletebtn;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private TableColumn<?, ?> employees_col_gender;

    @FXML
    private TableColumn<?, ?> employees_col_lastName;

    @FXML
    private TableColumn<?, ?> addProducts_col_brandName;

    @FXML
    private TextField addProducts_price;

    @FXML
    private TextField addProducts_search;

    @FXML
    private Button employees_updateBtn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button addProducts_UpdateBtn;

    @FXML
    private TextField employees_lastName;

    @FXML
    private TableColumn<?, ?> employees_col_date;

    @FXML
    private TableColumn<?, ?> employees_col_firstName;

    @FXML
    private Label dashboard_activeEmployees;

    @FXML
    private Button addProducts_addBtn;

    @FXML
    private TextField employees_employeeID;

    @FXML
    private ComboBox<?> addProducts_status;

    @FXML
    private Label dashboard_IncomeToday;

    @FXML
    private TextField addProducts_productName;

    @FXML
    private TextField employees_firstName;

    @FXML
    private TableColumn<?, ?> employees_col_password;

    @FXML
    private AnchorPane employees_form;

    @FXML
    private Button employees_saveBtn;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private TableView<?> employees_tableView;

    @FXML
    private Label username;
    private double x = 0;
    private double y = 0;

    public void logout() {
        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();
                //back to login form
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.8);
                });
                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });
                Scene scene = new Scene(root);
                stage.setScene(scene);
                    stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            addProducts_form.setVisible(false);
            employees_form.setVisible(false);
            dashboard_btn.setStyle("-fx-background-color:linear-gradient(to top right, #896b34, #b8a536);   ");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            employees_btn.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == addProducts_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(true);
            employees_form.setVisible(false);
            addProducts_btn.setStyle("-fx-background-color:linear-gradient(to top right, #896b34, #b8a536);   ");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            employees_btn.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == employees_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(false);
            employees_form.setVisible(true);
            employees_btn.setStyle("-fx-background-color:linear-gradient(to top right, #896b34, #b8a536);   ");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            dashboard_btn.setStyle("-fx-background-color:transparent");
        }
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
