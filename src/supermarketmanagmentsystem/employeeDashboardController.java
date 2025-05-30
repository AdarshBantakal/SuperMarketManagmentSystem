package supermarketmanagmentsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

public class employeeDashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Label username;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Button logout;

    @FXML
    private Button billing_btn;

    @FXML
    private AnchorPane billing_form;

    @FXML
    private BorderPane root;

    @FXML
    private Label lGSTin;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerPhone;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private Button loadCustomer_btn;

    @FXML
    private Label lCusName;

    @FXML
    private Label lCusPhone;

    @FXML
    private Label lCusStreet;

    @FXML
    private TextField billing_brandName;

    @FXML
    private ComboBox<String> billing_productName;

    @FXML
    private TextField txtQuantity;

    @FXML
    private Button billing_add_btn;

    @FXML
    private Button billing_delete_btn;

    @FXML
    private TableView<BillItem> tblBilling;

    @FXML
    private TableColumn<BillItem, String> colBrandName;

    @FXML
    private TableColumn<BillItem, String> colProductName;

    @FXML
    private TableColumn<BillItem, Double> colPrice;

    @FXML
    private TableColumn<BillItem, Integer> colQuantity;

    @FXML
    private TableColumn<BillItem, Double> colTotal;

    @FXML
    private Label lTotalAmountNum;

    @FXML
    private Label gstAmount;

    @FXML
    private Label lTotalPlusTaxNum;

    @FXML
    private Button biiling_calculate_btn;

    @FXML
    private Button billing_checkout_btn;

    private double x = 0;
    private double y = 0;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private final ObservableList<BillItem> billItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            displayUsername();
            connect = database.connectDb();
            setupTableColumns();
            loadAllProducts();
            
            // Add listener for brand name text field
            billing_brandName.textProperty().addListener((observable, oldValue, newValue) -> {
                searchProducts();
            });
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Failed to initialize: " + e.getMessage());
        }
    }

    private void setupTableColumns() {
        colBrandName.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblBilling.setItems(billItems);
    }

    // Billing Section
    public static class BillItem {
        private final SimpleStringProperty brand;
        private final SimpleStringProperty productName;
        private final SimpleDoubleProperty price;
        private final SimpleIntegerProperty quantity;
        private final SimpleDoubleProperty total;

        public BillItem(String brand, String productName, double price, int quantity) {
            this.brand = new SimpleStringProperty(brand);
            this.productName = new SimpleStringProperty(productName);
            this.price = new SimpleDoubleProperty(price);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.total = new SimpleDoubleProperty(price * quantity);
        }

        // Getters
        public String getBrand() { return brand.get(); }
        public String getProductName() { return productName.get(); }
        public double getPrice() { return price.get(); }
        public int getQuantity() { return quantity.get(); }
        public double getTotal() { return total.get(); }
    }

    @FXML
    private void handleCustomerSubmit() {
        String name = txtCustomerName.getText().trim();
        String phone = txtCustomerPhone.getText().trim();
        String address = txtCustomerAddress.getText().trim();
        
        if (name.isEmpty() || phone.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Name and Phone are required fields");
            return;
        }

        try {
            // First check if customer exists
            String checkSql = "SELECT * FROM customers WHERE name = ? AND phone = ?";
            PreparedStatement checkStmt = connect.prepareStatement(checkSql);
            checkStmt.setString(1, name);
            checkStmt.setString(2, phone);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Customer exists, load their details
                lCusName.setText(rs.getString("name"));
                lCusPhone.setText(rs.getString("phone"));
                lCusStreet.setText(rs.getString("address"));
                showAlert(AlertType.INFORMATION, "Success", "Customer loaded successfully");
            } else {
                // Customer doesn't exist, create new customer
                String insertSql = "INSERT INTO customers (name, phone, address) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = connect.prepareStatement(insertSql);
                insertStmt.setString(1, name);
                insertStmt.setString(2, phone);
                insertStmt.setString(3, address);
                insertStmt.executeUpdate();

                // Display the new customer's details
                lCusName.setText(name);
                lCusPhone.setText(phone);
                lCusStreet.setText(address);
                showAlert(AlertType.INFORMATION, "Success", "New customer created successfully");
            }
            
            // Clear the input fields
            txtCustomerName.clear();
            txtCustomerPhone.clear();
            txtCustomerAddress.clear();
            
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleAdd() {
        String brand = billing_brandName.getText().trim();
        String product = billing_productName.getValue();
        String quantityStr = txtQuantity.getText().trim();

        if (product == null || quantityStr.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please select a product and enter quantity");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                showAlert(AlertType.ERROR, "Error", "Quantity must be greater than 0");
                return;
            }

            String sql = "SELECT price, stock, brand FROM product WHERE product_name = ?";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, product);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                String exactBrand = rs.getString("brand");

                if (stock < quantity) {
                    showAlert(AlertType.WARNING, "Stock Error", "Available stock: " + stock);
                    return;
                }

                BillItem newItem = new BillItem(exactBrand, product, price, quantity);
                billItems.add(newItem);
                tblBilling.setItems(billItems);
                updateTotals();
                clearFields();
            } else {
                showAlert(AlertType.ERROR, "Error", "Product not found");
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Invalid quantity format");
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        BillItem selected = tblBilling.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(AlertType.ERROR, "Error", "Select an item to delete");
            return;
        }

        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Delete " + selected.getProductName() + "?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            billItems.remove(selected);
            updateTotals();
        }
    }

    private void updateTotals() {
        double subtotal = billItems.stream().mapToDouble(BillItem::getTotal).sum();
        double gst = subtotal * 0.18; // 18% GST
        double total = subtotal + gst;

        lTotalAmountNum.setText(String.format("₹%.2f", subtotal));
        gstAmount.setText(String.format("₹%.2f", gst));
        lTotalPlusTaxNum.setText(String.format("₹%.2f", total));
    }

    @FXML
    private void handleCalculate() {
        updateTotals();
    }

    @FXML
    private void handleBillSubmit() {
        if (billItems.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Add items before checkout");
            return;
        }

        if (lCusName.getText().isEmpty() || lCusPhone.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please load customer details first");
            return;
        }

        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Checkout");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Process final bill?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    processCheckout();
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert(AlertType.ERROR, "Database Error", "Failed to process checkout: " + e.getMessage());
                }
            }
        });
    }

    private void processCheckout() throws SQLException {
        try {
            connect.setAutoCommit(false);
            String invoiceNo = generateInvoiceNumber();
            double total = Double.parseDouble(lTotalPlusTaxNum.getText().replace("₹", ""));

            // Insert invoice
            String invoiceSQL = "INSERT INTO invoices (invoice_no, total_amount, customer_name, customer_phone, customer_address) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connect.prepareStatement(invoiceSQL)) {
                pstmt.setString(1, invoiceNo);
                pstmt.setDouble(2, total);
                pstmt.setString(3, lCusName.getText());
                pstmt.setString(4, lCusPhone.getText());
                pstmt.setString(5, lCusStreet.getText());
                pstmt.executeUpdate();
            }

            // Insert items and update stock
            String itemSQL = "INSERT INTO invoice_items (invoice_no, brand, product_name, quantity, price) VALUES (?, ?, ?, ?, ?)";
            String stockSQL = "UPDATE product SET stock = stock - ? WHERE brand = ? AND product_name = ?";
            
            for (BillItem item : billItems) {
                try (PreparedStatement itemStmt = connect.prepareStatement(itemSQL)) {
                    itemStmt.setString(1, invoiceNo);
                    itemStmt.setString(2, item.getBrand());
                    itemStmt.setString(3, item.getProductName());
                    itemStmt.setInt(4, item.getQuantity());
                    itemStmt.setDouble(5, item.getPrice());
                    itemStmt.executeUpdate();
                }

                try (PreparedStatement stockStmt = connect.prepareStatement(stockSQL)) {
                    stockStmt.setInt(1, item.getQuantity());
                    stockStmt.setString(2, item.getBrand());
                    stockStmt.setString(3, item.getProductName());
                    stockStmt.executeUpdate();
                }
            }

            connect.commit();
            
            // Show the bill after successful checkout
            showBill(invoiceNo);
            
            // Reset the form after successful checkout
            resetUI();
            
        } catch (SQLException e) {
            try { connect.rollback(); } catch (SQLException ex) {}
            showAlert(AlertType.ERROR, "Checkout Failed", e.getMessage());
            throw e;
        } finally {
            try { connect.setAutoCommit(true); } catch (SQLException e) {}
        }
    }

    private void showBill(String invoiceNo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Bill.fxml"));
            Parent root = loader.load();
            
            // Get the controller and set the data
            BillController billController = loader.getController();
            
            // Set customer details
            billController.setCustomerDetails(
                lCusName.getText(),
                lCusPhone.getText(),
                lCusStreet.getText()
            );
            
            // Set bill items
            billController.setBillItems(billItems);
            
            // Set totals
            billController.setTotalAmount(lTotalPlusTaxNum.getText());
            double gst = Double.parseDouble(gstAmount.getText().replace("₹", ""));
            String cgst = String.format("₹%.2f", gst/2);
            String sgst = String.format("₹%.2f", gst/2);
            billController.setGSTAmount(cgst, sgst);
            
            // Set invoice number
            billController.setInvoiceNumber(invoiceNo);
            
            // Create and show the bill window
            Stage billStage = new Stage();
            Scene scene = new Scene(root);
            billStage.setScene(scene);
            billStage.setTitle("Invoice #" + invoiceNo);
            billStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Failed to generate bill: " + e.getMessage());
        }
    }

    private void resetUI() {
        billItems.clear();
        lCusName.setText("");
        lCusStreet.setText("");
        lCusPhone.setText("");
        updateTotals();
    }

    private void clearFields() {
        billing_brandName.clear();
        billing_productName.setValue(null);
        txtQuantity.clear();
    }

    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis();
    }

    @FXML
    private void searchProducts() {
        String searchText = billing_brandName.getText().trim();
        
        try {
            String sql = "SELECT DISTINCT product_name FROM product WHERE product_name LIKE ? ORDER BY product_name";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();
            
            ObservableList<String> products = FXCollections.observableArrayList();
            
            while (rs.next()) {
                String product = rs.getString("product_name");
                products.add(product);
            }
            
            billing_productName.setItems(products);
                
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    private void loadAllProducts() {
        try {
            String sql = "SELECT DISTINCT product_name FROM product ORDER BY product_name";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            ObservableList<String> products = FXCollections.observableArrayList();
            
            while (rs.next()) {
                products.add(rs.getString("product_name"));
            }
            
            billing_productName.setItems(products);
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", e.getMessage());
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


    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void displayUsername() {
        username.setText(getData.username);
    }
} 