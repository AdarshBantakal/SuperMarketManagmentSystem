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
import java.util.Comparator;
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

    private ObservableList<productData> addProductsList;

    private String[] statusList = {"Available", "Not Available"};

    public void addProductsAdd() {
        String insertProduct = "INSERT INTO product (product_id, brand, product_name, status, price, gst_rate) VALUES (?, ?, ?, ?, ?, ?)";
        connect = database.connectDb();
        try {
            Alert alert;
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
                String check = "SELECT product_id FROM product WHERE product_id = ?";
                prepare = connect.prepareStatement(check);
                prepare.setString(1, addProducts_productID.getText());
                result = prepare.executeQuery();

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID " + addProducts_productID.getText() + " already exists!");
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

                    try {
                        prepare.setDouble(6, Double.parseDouble(addProducts_gstRate.getText()));
                    } catch (NumberFormatException e) {
                        alert = new Alert(AlertType.ERROR);
                        alert.setContentText("Invalid GST rate format");
                        alert.showAndWait();
                        return;
                    }

                    prepare.executeUpdate();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    addProductsShowData();
                    addProductsClear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProductsUpdate() {
        String productID = addProducts_productID.getText();
        String brandName = addProducts_brandName.getText();
        String productName = addProducts_productName.getText();
        String status = (String) addProducts_status.getSelectionModel().getSelectedItem();
        String price = addProducts_price.getText();
        String gstRate = addProducts_gstRate.getText();

        connect = database.connectDb();

        try {
            Alert alert;

            if (productID.isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Product ID cannot be empty");
                alert.showAndWait();
                return;
            }

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE Product " + productID + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String updateProduct = "UPDATE product SET brand = ?, product_name = ?, status = ?, price = ?, gst_rate = ? WHERE product_id = ?";
                prepare = connect.prepareStatement(updateProduct);
                prepare.setString(1, brandName);
                prepare.setString(2, productName);
                prepare.setString(3, status);
                prepare.setDouble(4, Double.parseDouble(price));
                prepare.setDouble(5, Double.parseDouble(gstRate));
                prepare.setString(6, productID);
                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();
                addProductsShowData();
                addProductsClear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public void addProductsDelete() {
        String productID = addProducts_productID.getText();
        connect = database.connectDb();

        try {
            Alert alert;
            if (productID.isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a product to delete");
                alert.showAndWait();
                return;
            }

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Product " + productID + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteProduct = "DELETE FROM product WHERE product_id = ?";
                prepare = connect.prepareStatement(deleteProduct);
                prepare.setString(1, productID);
                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Deleted!");
                alert.showAndWait();
                addProductsShowData();
                addProductsClear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProductsStatusList() {
        List<String> listS = new ArrayList<>();
        for(String data : statusList) {
            listS.add(data);
        }
        ObservableList statusData = FXCollections.observableArrayList(listS);
        addProducts_status.setItems(statusData);
    }

    public void addProductsSearch() {
        FilteredList<productData> filteredData = new FilteredList<>(addProductsList, p -> true);

        addProducts_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(product -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

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

                return false;
            });
        });

        SortedList<productData> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(addProducts_tableView.comparatorProperty());

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

    public void addProductsShowData() {
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

    public void employeesSave() {
        java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());

        String insertEmployee = "INSERT INTO employee "
                + " (employee_id, password, firstname, lastname, gender, date) "
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
                String checkExist = "SELECT employee_id FROM employee WHERE employee_id = '"
                        + employees_employeeID.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(checkExist);
                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID" + employees_employeeID.getText() + "was already exists!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertEmployee);
                    prepare.setString(1, employees_employeeID.getText());
                    prepare.setString(2, employees_password.getText());
                    prepare.setString(3, employees_firstName.getText());
                    prepare.setString(4, employees_lastName.getText());
                    prepare.setString(5, (String) employees_gender.getSelectionModel().getSelectedItem());
                    prepare.setDate(6, sqlDate);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    employeesShowListData();
                    employeesReset();
                }
            }
        } catch (Exception e) {
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
        String employeeID = employees_employeeID.getText();
        String password = employees_password.getText();
        String firstName = employees_firstName.getText();
        String lastName = employees_lastName.getText();
        String gender = (String) employees_gender.getSelectionModel().getSelectedItem();

        connect = database.connectDb();

        try {
            Alert alert;
            if (employeeID.isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Employee ID cannot be empty");
                alert.showAndWait();
                return;
            }
            StringBuilder updateQuery = new StringBuilder("UPDATE employee SET ");
            boolean isFirstField = true;

            if (!password.isEmpty()) {
                appendField(updateQuery, isFirstField, "password", password);
                isFirstField = false;
            }
            if (!firstName.isEmpty()) {
                appendField(updateQuery, isFirstField, "firstname", firstName);
                isFirstField = false;
            }
            if (!lastName.isEmpty()) {
                appendField(updateQuery, isFirstField, "lastname", lastName);
                isFirstField = false;
            }
            if (gender != null && !gender.isEmpty()) {
                appendField(updateQuery, isFirstField, "gender", gender);
            }

            updateQuery.append(" WHERE employee_id = '").append(employeeID).append("'");

            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to UPDATE Employee " + employeeID + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                statement = connect.createStatement();
                statement.executeUpdate(updateQuery.toString());

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated");
                alert.showAndWait();

                employeesShowListData();
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

            if (employees_employeeID.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select an employee to delete");
                alert.showAndWait();
                return;
            }

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Employee ID: "
                    + employees_employeeID.getText() + "?");

            Optional<ButtonType> option = alert.showAndWait();
            if (!option.isPresent() || option.get() != ButtonType.OK) {
                return;
            }
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
            return; 
        }

        employees_employeeID.setText(employeeD.getEmployeeId());
        employees_password.setText(employeeD.getPassword());
        employees_firstName.setText(employeeD.getFirstName());
        employees_lastName.setText(employeeD.getLastName());
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
                Stage loginStage = new Stage();
                loginStage.initStyle(StageStyle.UNDECORATED);
                loginStage.setScene(scene);
                loginStage.show();
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
        if (event.getSource() == addProducts_btn) {
            addProducts_form.setVisible(true);
            employees_form.setVisible(false);
            addProducts_btn.setStyle("-fx-background-color: #005d76; "
                    + "-fx-border-color: #013220; "
                    + "-fx-border-width: 0.01px;");
            employees_btn.setStyle("-fx-background-color:transparent");
            addProductsSearch();
            addProductsShowData();
            addProductsStatusList();
        } else if (event.getSource() == employees_btn) {
            addProducts_form.setVisible(false);
            employees_form.setVisible(true);
            employees_btn.setStyle("-fx-background-color:  #003d5c; "
                    + "-fx-border-color: #013220; "
                    + "-fx-border-width: 0.01px;");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            employeesListData();
        }
    }

    public void close() {
        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to exit?");

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get().equals(ButtonType.OK)) {
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void minimize() {
        try {
            Stage stage = (Stage) main_form.getScene().getWindow();
            stage.setIconified(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connect = database.connectDb();
            displayUsername();

            // Initialize the products list
            addProductsList = addProductsListData();

            // Set up the search functionality
            addProductsSearch();

            // Show initial data
            addProductsShowData();
            addProductsStatusList();
            employeesShowListData();
            employeesGender();

            // Set default form visibility
            addProducts_form.setVisible(false);
            employees_form.setVisible(true);

            // Style buttons
            employees_btn.setStyle("-fx-background-color: #008ca6 ; "
                    + "-fx-border-color: #013220; "
                    + "-fx-border-width: 0.01px;");
            addProducts_btn.setStyle("-fx-background-color: transparent;");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Failed to initialize: " + e.getMessage());
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
