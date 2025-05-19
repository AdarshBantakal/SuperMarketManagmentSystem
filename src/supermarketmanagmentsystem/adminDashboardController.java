/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarketmanagmentsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private TableColumn<employeeData, String> employees_col_employeeID;

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
    private TableColumn<employeeData, String> employees_col_gender;

    @FXML
    private TableColumn<employeeData, String> employees_col_lastName;

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
    private TableColumn<employeeData, Date> employees_col_date;

    @FXML
    private TableColumn<employeeData, String> employees_col_firstName;

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
    private TableColumn<employeeData, String> employees_col_password;

    @FXML
    private AnchorPane employees_form;

    @FXML
    private Button employees_saveBtn;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private TableView<employeeData> employees_tableView;

    @FXML
    private Label username;

    @FXML
    private TextField addProducts_gstRate;

    @FXML
    private TableColumn<productData, Double> addProducts_col_gstRate;

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
        String insertProduct = "INSERT INTO product (product_id, brand, product_name, status, price, gst_rate) VALUES (?, ?, ?, ?, ?, ?)";
        connect = database.connectDb();

        try {
            Alert alert;

            // Validate fields
            if (addProducts_productID.getText().isEmpty()
                    || addProducts_brandName.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_price.getText().isEmpty()
                    || addProducts_gstRate.getText().isEmpty()) {

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

                // Handle numeric conversion for price
                try {
                    double price = Double.parseDouble(addProducts_price.getText());
                    insertStmt.setDouble(5, price);
                } catch (NumberFormatException e) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Invalid price format");
                    alert.showAndWait();
                    return;
                }

                // Handle numeric conversion for GST rate
                try {
                    double gstRate = Double.parseDouble(addProducts_gstRate.getText());
                    if (gstRate < 0 || gstRate > 100) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setContentText("GST rate must be between 0 and 100");
                        alert.showAndWait();
                        return;
                    }
                    insertStmt.setDouble(6, gstRate);
                } catch (NumberFormatException e) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setContentText("Invalid GST rate format");
                    alert.showAndWait();
                    return;
                }

                insertStmt.executeUpdate();

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

    /* public void addProductsUpdate() {
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
                     //to update the data on table view
                    addProductsShowData();
                    //clear the fields once you update the data
                    addProductsClear();
                } else {
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public void addProductsUpdate() {
        String updateProduct = "UPDATE product SET brand = ?, product_name = ?, status = ?, price = ?, gst_rate = ? WHERE product_id = ?";
        connect = database.connectDb();

        try {
            Alert alert;

            // Validate fields
            if (addProducts_productID.getText().isEmpty()
                    || addProducts_brandName.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_price.getText().isEmpty()
                    || addProducts_gstRate.getText().isEmpty()) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
                return;
            }

            // Confirmation dialog
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE Product " + addProducts_productID.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                try (PreparedStatement pstmt = connect.prepareStatement(updateProduct)) {
                    pstmt.setString(1, addProducts_brandName.getText());
                    pstmt.setString(2, addProducts_productName.getText());
                    pstmt.setString(3, (String) addProducts_status.getSelectionModel().getSelectedItem());

                    // Handle numeric conversion for price
                    try {
                        double price = Double.parseDouble(addProducts_price.getText());
                        pstmt.setDouble(4, price);
                    } catch (NumberFormatException e) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setContentText("Invalid price format");
                        alert.showAndWait();
                        return;
                    }

                    // Handle numeric conversion for GST rate
                    try {
                        double gstRate = Double.parseDouble(addProducts_gstRate.getText());
                        if (gstRate < 0 || gstRate > 100) {
                            alert = new Alert(AlertType.ERROR);
                            alert.setContentText("GST rate must be between 0 and 100");
                            alert.showAndWait();
                            return;
                        }
                        pstmt.setDouble(5, gstRate);
                    } catch (NumberFormatException e) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setContentText("Invalid GST rate format");
                        alert.showAndWait();
                        return;
                    }

                    pstmt.setString(6, addProducts_productID.getText());
                    pstmt.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    addProductsShowData();
                    addProductsClear();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }

// Helper method to append fields to the SQL query
    private void appendField(StringBuilder query, boolean isFirstField, String column, String value) {
        if (!isFirstField) {
            query.append(", ");
        }
        query.append(column).append(" = '").append(value).append("'");
    }

    public void addProductsClear() {
        addProducts_productID.setText("");
        addProducts_brandName.setText("");
        addProducts_productName.setText("");
        addProducts_status.getSelectionModel().clearSelection();
        addProducts_price.setText("");
        addProducts_gstRate.setText("");
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
                statement.executeUpdate(deleteProduct);

                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Product" + addProducts_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                //if user clicks ok

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(deleteProduct);
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Deleted ! ");
                    alert.showAndWait();
                     //to delete the data on table view
                    addProductsShowData();
                     //to clear the fields once you delelte the data
                    addProductsClear();
                } else {
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    public void addProductsDelete() {
        // Always use PreparedStatement to prevent SQL injection
        String deleteProduct = "DELETE FROM product WHERE product_id = ?";
        connect = database.connectDb();

        try {
            Alert alert;

            // Check if product ID is empty (only mandatory field for deletion)
            if (addProducts_productID.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a product to delete");
                alert.showAndWait();
                return;
            }

            // Confirmation dialog FIRST
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Product "
                    + addProducts_productID.getText() + "?");

            Optional<ButtonType> option = alert.showAndWait();
            if (!option.isPresent() || option.get() != ButtonType.OK) {
                return;
            }

            // Execute deletion
            try (PreparedStatement pstmt = connect.prepareStatement(deleteProduct)) {
                pstmt.setString(1, addProducts_productID.getText());
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Succesfully Deleted ! ");
                    alert.showAndWait();
                    addProductsShowData();
                    addProductsClear();
                } else {
                    alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning Message");
                    alert.setHeaderText(null);
                    alert.setContentText("No product was deleted ! ");
                    alert.showAndWait();

                }
            }
        } catch (SQLException e) {
            Alert alert;
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Database Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Delete failed: ! " + e.getMessage());
            alert.showAndWait();

            e.printStackTrace();
        }
    }

// Helper method for alerts
    private String[] statusList = {"Available", "Not Available"};

    public void addProductsStatusList() {
        List<String> listS = new ArrayList<>();
        for (String data : statusList) {
            listS.add(data);
        }
        ObservableList statusData = FXCollections.observableArrayList(listS);
        addProducts_status.setItems(statusData);
    }

    public void addProductsSearch() {
        // Wrap the ObservableList in a FilteredList (initially display all data)
        FilteredList<productData> filteredData = new FilteredList<>(addProductsList, p -> true);

        // Add a listener to the search field to dynamically filter the data
        addProducts_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                // If the search field is empty, display all products
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }

                // Convert the search text to lowercase for case-insensitive comparison
                String lowerCaseFilter = newValue.toLowerCase();

                // Check if any product attribute contains the search text
                if (product.getProductId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (product.getBrand().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (product.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (product.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(product.getPrice()).contains(lowerCaseFilter)) {
                    return true;
                }

                // No match found
                return false;
            });
        });

        // Wrap the FilteredList in a SortedList
        SortedList<productData> sortedData = new SortedList<>(filteredData);

        // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(addProducts_tableView.comparatorProperty());

        // Set the sorted and filtered data to the TableView
        addProducts_tableView.setItems(sortedData);
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
                        result.getDouble("price"),
                        result.getDouble("gst_rate"));
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
        addProducts_col_gstRate.setCellValueFactory(new PropertyValueFactory<>("gstRate"));
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
        addProducts_gstRate.setText(String.valueOf(prod.getGstRate()));
    }

    /*public void employeesSave() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String insertEmployee = "INSERT INTO employee "
                + " (employee_id, password, firstName, lastName, gender, date) "
                + "VALUES (?, ?, ?, ?, ?, ?) ";
        connect = database.connectDb();
        try {
            Alert alert;
            if (employees_employeeID.getText().isEmpty()
                    || employees_password.getText().isEmpty()
                    || employees_firstName.getText().isEmpty()
                    || employees_lastName.getText().isEmpty()
                    || employees_gender.getSelectionModel().getSelectedItem()
                    == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                String checkExist = "SELECT employee_id FROM employee WHERE employee_id
                +employees_employeeID.getText() +
                """;
                statement = connect.createStatement();
                result = statement.executeQuery(checkExist);
                //IF THE EMPLOYEE IS ALREADY working
                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID" + employees_employeeID.getText() + "was already eists!");
                    alert.showAndWait();
                }else{
                    prepare = connect.prepareStatement(insertEmployee);
                    prepare.setString(1, employees_employeeID.getText());
                    prepare.setString(2, employees_password.getText());
                    prepare.setString(3, employees_firstName.getText());
                    prepare.setString(4, employees_lastName.getText());
                    prepare.setString(5, (String) employees_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(6, String.valueOf(sqlDate));
                    prepare.executeUpdate(); 
                }
            }
            }catch (Exception e) 
        {e.printStackTrace();
                    }
        }*/
    public void employeesSave() {

        // Use LocalDate for current date
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());

        String insertEmployee = "INSERT INTO employee (employee_id, password, firstName, lastName, gender, date) VALUES (?, ?, ?, ?, ?, ?)";

        connect = database.connectDb();

        try {
            Alert alert;

            // Validate input fields
            if (employees_employeeID.getText().isEmpty()
                    || employees_password.getText().isEmpty()
                    || employees_firstName.getText().isEmpty()
                    || employees_lastName.getText().isEmpty()
                    || employees_gender.getSelectionModel().getSelectedItem() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                // Check if employee ID already exists
                String checkExist = "SELECT employee_id FROM employee WHERE employee_id = ?";
                prepare = connect.prepareStatement(checkExist);
                prepare.setString(1, employees_employeeID.getText());
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID " + employees_employeeID.getText() + " already exists!");
                    alert.showAndWait();
                } else {
                    // Insert new employee record
                    prepare = connect.prepareStatement(insertEmployee);
                    prepare.setString(1, employees_employeeID.getText());
                    prepare.setString(2, employees_password.getText());
                    prepare.setString(3, employees_firstName.getText());
                    prepare.setString(4, employees_lastName.getText());
                    prepare.setString(5, employees_gender.getSelectionModel().getSelectedItem().toString());
                    prepare.setDate(6, sqlDate);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee added successfully!");
                    alert.showAndWait();

                    //  refresh the employee list
                    employeesShowListData();
                    //to clear the fields
                    employeesReset();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String[] genderList = {"Male", "Female"};

    public void employeesGender() {
        List<String> genderL = new ArrayList<>();
        for (String data : genderList) {
            genderL.add(data);
        }
        ObservableList listG = FXCollections.observableArrayList(genderL);
        employees_gender.setItems(listG);
    }

    public void employeesUpdate() {
        // Get values from input fields
        String employeeID = employees_employeeID.getText();
        String password = employees_password.getText();
        String firstName = employees_firstName.getText();
        String lastName = employees_lastName.getText();
        String gender = (String) employees_gender.getSelectionModel().getSelectedItem();

        connect = database.connectDb();

        try {
            Alert alert;
            // Check if the employee ID is empty
            if (employeeID.isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Employee ID cannot be empty");
                alert.showAndWait();
                return;
            }

            // Start building the update query dynamically
            StringBuilder updateQuery = new StringBuilder("UPDATE employee SET ");
            boolean isFirstField = true;

            // Append each field to the query only if it is not empty
            if (!password.isEmpty()) {
                appendField(updateQuery, isFirstField, "password", password);
                isFirstField = false;
            }
            if (!firstName.isEmpty()) {
                appendField(updateQuery, isFirstField, "firstName", firstName);
                isFirstField = false;
            }
            if (!lastName.isEmpty()) {
                appendField(updateQuery, isFirstField, "lastName", lastName);
                isFirstField = false;
            }
            if (gender != null && !gender.isEmpty()) {
                appendField(updateQuery, isFirstField, "gender", gender);
            }

            // Add the WHERE clause to update the correct employee by ID
            updateQuery.append(" WHERE employee_id = '").append(employeeID).append("'");

            // Ask for user confirmation before executing the update
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE Employee " + employeeID + "?");
            Optional<ButtonType> option = alert.showAndWait();

            // If the user confirms, execute the update
            if (option.get().equals(ButtonType.OK)) {
                statement = connect.createStatement();
                statement.executeUpdate(updateQuery.toString());

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated");
                alert.showAndWait();

                // Update the table view
                employeesShowListData();
                // Clear the input fields after the update
                employeesReset();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void employeesDelete() {
        String deleteEmployee = "DELETE FROM employee WHERE employee_id = ?";
        connect = database.connectDb();

        try {
            Alert alert;

            // Validate: Check if employee ID is entered
            if (employees_employeeID.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select an employee to delete");
                alert.showAndWait();
                return;
            }

            // Confirm deletion
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Employee ID: "
                    + employees_employeeID.getText() + "?");

            Optional<ButtonType> option = alert.showAndWait();
            if (!option.isPresent() || option.get() != ButtonType.OK) {
                return;
            }

            // Proceed with deletion
            try (PreparedStatement pstmt = connect.prepareStatement(deleteEmployee)) {
                pstmt.setString(1, employees_employeeID.getText());
                int affectedRows = pstmt.executeUpdate();

                if (affectedRows > 0) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    employeesShowListData();
                    employeesReset();
                } else {
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning Message");
                    alert.setHeaderText(null);
                    alert.setContentText("No employee was deleted!");
                    alert.showAndWait();
                }
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("Deletion failed: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void employeesReset() {
        employees_employeeID.setText("");
        employees_password.setText("");
        employees_firstName.setText("");
        employees_lastName.setText("");
        employees_gender.getSelectionModel().clearSelection();
    }

    public ObservableList<employeeData> employeesListData() {
        ObservableList<employeeData> emData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee";
        connect = database.connectDb();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                employeeData employee = new employeeData(
                        result.getString("employee_id"),
                        result.getString("password"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getDate("date")
                );
                emData.add(employee);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emData;
    }

    private ObservableList<employeeData> employeesList;

    public void employeesShowListData() {
        employeesList = employeesListData();

        employees_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employees_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employees_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employees_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        employees_col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        employees_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        employees_tableView.setItems(employeesList);
    }

    public void employeesSelect() {
        employeeData employeeD = employees_tableView.getSelectionModel().getSelectedItem();
        int num = employees_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return; // No selection or invalid index
        }

        employees_employeeID.setText(employeeD.getEmployeeId());
        employees_password.setText(employeeD.getPassword());
        employees_firstName.setText(employeeD.getFirstName());
        employees_lastName.setText(employeeD.getLastName());
        //employees_gender.setValue(employeeD.getGender());
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
            dashboard_btn.setStyle("-fx-background-color: #008ca6 ; "
                    + "-fx-border-color: #013220; "
                    + "-fx-border-width: 0.01px;");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            employees_btn.setStyle("-fx-background-color:transparent");
        } else if (event.getSource() == addProducts_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(true);
            employees_form.setVisible(false);
            addProducts_btn.setStyle("-fx-background-color: #005d76; "
                    + "-fx-border-color: #013220; "
                    + "-fx-border-width: 0.01px;");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            employees_btn.setStyle("-fx-background-color:transparent");
            addProductsSearch();
            addProductsShowData();
            addProductsStatusList();
        } else if (event.getSource() == employees_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(false);
            employees_form.setVisible(true);
            employees_btn.setStyle("-fx-background-color:  #003d5c; "
                    + "-fx-border-color: #013220; "
                    + "-fx-border-width: 0.01px;");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            employeesListData();
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
        employeesShowListData();
        employeesGender();
        //to set dashboard as default visible form
        dashboard_form.setVisible(true);
        addProducts_form.setVisible(false);
        employees_form.setVisible(false);
        // Style dashboard button as active by default
        dashboard_btn.setStyle("-fx-background-color: #008ca6 ; "
                + "-fx-border-color: #013220; "
                + "-fx-border-width: 0.01px;");

        // Ensure other buttons are styled as inactive
        addProducts_btn.setStyle("-fx-background-color: transparent;");
        employees_btn.setStyle("-fx-background-color: transparent;");
    }

}
