/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarketmanagmentsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private TableColumn<productData, String> addProducts__col_status;

    @FXML
    private ComboBox<?> employees_gender;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button addProducts_clearBtn;

    @FXML
    private TableColumn<productData, String> addProducts_col_productID;

    @FXML
    private TableColumn<productData, String> addProducts__col_productName;

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
    private TableView<productData> addProducts_tableView;

    @FXML
    private TableColumn<productData, Double> addProducts_col_price;

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
    private TableColumn<productData, String> addProducts_col_brandName;

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

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    //table for products
    /* public void addProductsAdd() {
        String insertProduct = "INSERT INTO product "
                + " (product_id, brand, product_name, status, price) "
                + "VALUES (?, ?, ?, ?, ?) ";
        connect = database.connectDb();
        try {
            Alert alert;
            //CHECK IF EMPTY /validate emptyfields first
            if (addProducts_productID.getText().isEmpty() || addProducts_brandName.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_price.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                String check = "SELECT product_id FROM  product WHERE product_id = ?";
                statement = connect.createStatement();
                result = statement.executeQuery(check);
                //if if product id is same ,block
                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID" + addProducts_productID.getText() + "was already exist!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertProduct);
                    prepare.setString(1, addProducts_productID.getText());
                    prepare.setString(2, addProducts_brandName.getText());
                    prepare.setString(3, addProducts_productName.getText());
                    prepare.setString(4, (String) addProducts_status.getSelectionModel().getSelectedItem());

                    try {
                        prepare.setDouble(5, Double.parseDouble(addProducts_price.getText()));
                    } catch (NumberFormatException e) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setContentText("Invalid price format");
                        alert.showAndWait();
                        return;
                    }
                    result = prepare.executeQuery();

                    prepare.executeUpdate();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Sucessfully Added!");
                    alert.showAndWait();
                    //to update the table view once user inserts the data
                    addProductsShowData();
                    //clears textfield once user incerts
                    addProductsClear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public void addProductsAdd() {
        String insertProduct = "INSERT INTO product (product_id, brand, product_name, status, price) VALUES (?, ?, ?, ?, ?)";
        connect = database.connectDb();

        try {
            Alert alert;

            // Validate fields
            if (addProducts_productID.getText().isEmpty()
                    || addProducts_brandName.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_price.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
                return;
            }

            // Check existing product 
            String checkSql = "SELECT product_id FROM product WHERE product_id = ?";
            try (PreparedStatement checkStmt = connect.prepareStatement(checkSql)) {
                checkStmt.setString(1, addProducts_productID.getText());
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID already exists!");
                    alert.showAndWait();
                    return;
                }
            }

            // Insert new product 
            try (PreparedStatement insertStmt = connect.prepareStatement(insertProduct)) {
                insertStmt.setString(1, addProducts_productID.getText());
                insertStmt.setString(2, addProducts_brandName.getText());
                insertStmt.setString(3, addProducts_productName.getText());
                insertStmt.setString(4, (String) addProducts_status.getSelectionModel().getSelectedItem());

                // Handle numeric conversion
                try {
                    double price = Double.parseDouble(addProducts_price.getText());
                    insertStmt.setDouble(5, price);
                } catch (NumberFormatException e) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Invalid price format");
                    alert.showAndWait();
                    return;
                }

                insertStmt.executeUpdate();  // CORRECT execute method

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added!");
                alert.showAndWait();

                addProductsShowData();
                addProductsClear();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void addProductsUpdate() {
        String updateProduct = "UPDATE product SET brand = '"
                + addProducts_brandName.getText() + "', product_name = '"
                + addProducts_productName.getText() + "', status = '"
                + addProducts_status.getSelectionModel().getSelectedItem() + "', price = '"
                + addProducts_price.getText() + "' WHERE product_id = '"
                + addProducts_productID.getText() + "'";
        connect = database.connectDb();
        try {
            Alert alert;
            //checks if the fields are empty
            if (addProducts_productID.getText().isEmpty()
                    || addProducts_brandName.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_price.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                statement = connect.createStatement();
                statement.executeUpdate(updateProduct);

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Product" + addProducts_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                //if user clicks ok

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateProduct);
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Updated");
                    alert.showAndWait();
                    addProductsShowData();
                    addProductsClear();
                } else {
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProductsClear() {
        addProducts_productID.setText("");
        addProducts_brandName.setText("");
        addProducts_productName.setText("");
        addProducts_status.getSelectionModel().clearSelection();
        addProducts_price.setText("");
    }

     /*public void addProductsDelete() {
        String deleteProduct = "DELETE FROM product WHERE product_id = '"
                 + addProducts_productID.getText() + "'";
        connect = database.connectDb();
        try {
            Alert alert;
            //checks if the fields are empty
            if (addProducts_productID.getText().isEmpty()
                    || addProducts_brandName.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_price.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                statement = connect.createStatement();
                statement.executeUpdate(updateProduct);

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Product" + addProducts_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                //if user clicks ok

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(updateProduct);
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Updated");
                    alert.showAndWait();
                    addProductsShowData();
                    addProductsClear();
                } else {
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    private String[] statusList = {"Available", "Not Available"};

    public void addProductsStatusList() {
        List<String> listS = new ArrayList<>();
        for(String data : statusList) {
            listS.add(data);
        }
        ObservableList statusData = FXCollections.observableArrayList(listS);
        addProducts_status.setItems(statusData);
    }

    public ObservableList<productData> addProductsListData() {
        ObservableList<productData> prodList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product";
        connect = database.connectDb();
        try {
            productData prod;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                prod = new productData(result.getString("product_Id"),
                        result.getString("brand"),
                        result.getString("product_name"),
                        result.getString("status"),
                        result.getDouble("price"));
                prodList.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prodList;
    }

    private ObservableList<productData> addProductsList;

    public void addProductsShowData() { //to show the data on table view
        addProductsList = addProductsListData();
        addProducts_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addProducts_col_brandName.setCellValueFactory(new PropertyValueFactory<>("brand"));
        addProducts__col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addProducts__col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        addProducts_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProducts_tableView.setItems(addProductsList);
    }

    public void addProductsSelect() {
        productData prod = addProducts_tableView.getSelectionModel().getSelectedItem();
        int num = addProducts_tableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < - 1) {
            return;
        }
        addProducts_productID.setText(prod.getProductId());
        addProducts_brandName.setText(prod.getBrand());
        addProducts_productName.setText(prod.getProductName());
        addProducts_price.setText(String.valueOf(prod.getPrice()));
    }

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

    public void displayUsername() {
        username.setText(getData.username);
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

            addProductsShowData();
            addProductsStatusList();
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

        displayUsername();
        addProductsShowData();
        addProductsStatusList();
    }

}
