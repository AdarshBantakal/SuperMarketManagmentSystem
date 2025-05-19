package supermarketmanagmentsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.HBox;

public class employeeDashboardController implements Initializable {

    // FXML Injections
    @FXML private Text lCusName;
    @FXML private AnchorPane availability_form;
    @FXML private TableColumn<BillItem, Integer> colQuantity;
    @FXML private Label profile_employeeID;
    @FXML private Text lTotalPlusTaxNum;
    @FXML private TableColumn<BillItem, String> colBrandName;
    @FXML private TextField txtQuantity;
    @FXML private JFXButton billing_add_btn;
    @FXML private AnchorPane main_form;
    @FXML private Text cGSTAmt;
    @FXML private TextField billing_brandName;
    @FXML private TextField txtCustomerName;
    @FXML private TextField txtCustomerPhone;
    @FXML private TextField txtCustomerAddress;
    @FXML private ComboBox<String> billing_productName;
    @FXML private Label profile_position;
    @FXML private Label profile_name;
    @FXML private Button logout;
    @FXML private Button availability_btn;
    @FXML private AnchorPane billing_form;
    @FXML private Button availability_search_btn;
    @FXML private TableView<Product> availability_table;
    @FXML private Button profile_btn;
    @FXML private JFXComboBox<String> comboBoxCustomer;
    @FXML private Button billing_btn;
    @FXML private StackPane content_area;
    @FXML private TextField availability_search;
    @FXML private Button minimize;
    @FXML private JFXButton billing_delete_btn;
    @FXML private TableColumn<BillItem, Double> colTotal;
    @FXML private Button profile_edit_btn;
    @FXML private JFXCheckBox checkBoxGST;
    @FXML private Text lCusStreet;
    @FXML private AnchorPane profile_form;
    @FXML private TableView<BillItem> tblBilling;
    @FXML private TableColumn<BillItem, Double> colPrice;
    @FXML private Text lCusPhone;
    @FXML private Text lTotalAmountNum;
    @FXML private Text sGSTAmt;
    @FXML private TableColumn<BillItem, String> colProductName;
    @FXML private Label username;
    @FXML private Button billing_checkout_btn;
    @FXML private Button biiling_calculate_btn;

    private final ObservableList<BillItem> billItems = FXCollections.observableArrayList();
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeComponents();
        setupDatabaseConnection();
        loadInitialData();
        setupTableColumns();
        
        // Add listener for brand name changes
        billing_brandName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                loadProductsForBrand(newValue);
            }
        });
        
        // Add listener for product name selection
        billing_productName.setOnAction(e -> {
            String selectedBrand = billing_brandName.getText();
            String selectedProduct = billing_productName.getValue();
            if (selectedBrand != null && selectedProduct != null) {
                try {
                    String sql = "SELECT price FROM product WHERE brand = ? AND product_name = ?";
                    PreparedStatement pstmt = connect.prepareStatement(sql);
                    pstmt.setString(1, selectedBrand);
                    pstmt.setString(2, selectedProduct);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        // You can display the price somewhere if needed
                        double price = rs.getDouble("price");
                    }
                } catch (SQLException ex) {
                    showAlert(AlertType.ERROR, "Database Error", ex.getMessage());
                }
            }
        });
    }

    private void initializeComponents() {
        setupTableColumns();
        showBillingForm();
        setupButtonActions();
        displayUsername();
    }

    private void setupDatabaseConnection() {
        connect = database.connectDb();
    }

    private void loadInitialData() {
        loadCustomers();
        loadProfileData();
        loadAvailableProducts();
    }
    

    private void setupButtonActions() {
        billing_btn.setOnAction(e -> showBillingForm());
        availability_btn.setOnAction(e -> showAvailabilityForm());
        profile_btn.setOnAction(e -> showProfileForm());
        logout.setOnAction(e -> logout());
        billing_add_btn.setOnAction(e -> handleAdd());
        billing_checkout_btn.setOnAction(e -> handleBillSubmit());
        availability_search_btn.setOnAction(e -> searchProducts());

        minimize.setOnAction(e -> minimize());
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
        
        public SimpleStringProperty brandProperty() { return brand; }
        public SimpleStringProperty productNameProperty() { return productName; }
        public SimpleDoubleProperty priceProperty() { return price; }
        public SimpleIntegerProperty quantityProperty() { return quantity; }
        public SimpleDoubleProperty totalProperty() { return total; }
    }
   @FXML 
    private void handleCustomerSubmit(ActionEvent event) {
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
                showAlert(AlertType.INFORMATION, "Customer Found", "Existing customer loaded");
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
                showAlert(AlertType.INFORMATION, "Success", "New customer created");
            }
            
            // Clear the input fields
            txtCustomerName.clear();
            txtCustomerPhone.clear();
            txtCustomerAddress.clear();
            
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", e.getMessage());
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

    @FXML
    private void handleAdd() {
        String brand = billing_brandName.getText().trim();
        String product = billing_productName.getValue();
        String quantityStr = txtQuantity.getText().trim();

        if (brand.isEmpty() || product == null || quantityStr.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                showAlert(AlertType.ERROR, "Error", "Quantity must be greater than 0");
                return;
            }

            String sql = "SELECT price, stock, brand FROM product WHERE brand LIKE ? AND product_name = ?";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, "%" + brand + "%");
            pstmt.setString(2, product);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                String exactBrand = rs.getString("brand"); // Get the exact brand name from DB

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
        double cgst = subtotal * 0.09;
        double sgst = subtotal * 0.09;
        double total = subtotal + cgst + sgst;

        lTotalAmountNum.setText(String.format("₹%.2f", subtotal));
        cGSTAmt.setText(String.format("₹%.2f", cgst));
        sGSTAmt.setText(String.format("₹%.2f", sgst));
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

        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Checkout");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Process final bill?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                processCheckout();
            }
        });
    }
    
    

    private void processCheckout() {
        try {
            connect.setAutoCommit(false);
            String invoiceNo = generateInvoiceNumber();
            double total = Double.parseDouble(lTotalPlusTaxNum.getText().replace("₹", ""));

            // Insert invoice
            String invoiceSQL = "INSERT INTO invoices (invoice_no, total_amount, customer_name) VALUES (?, ?, ?,)";
            try (PreparedStatement pstmt = connect.prepareStatement(invoiceSQL)) {
                pstmt.setString(1, invoiceNo);
                pstmt.setDouble(2, total);
                pstmt.setString(3, lCusName.getText());
                //pstmt.setString(4, lCusPhone.getText());
                //pstmt.setString(5, lCusStreet.getText());
                pstmt.executeUpdate();
            }

            // Insert items and update stock
            String itemSQL = "INSERT INTO invoice_items (invoice_no, brand, product_name, quantity, price,) VALUES (?, ?, ?, ?, ?)";
            String stockSQL = "UPDATE products SET stock = stock - ? WHERE brand = ? AND product_name = ?";
            
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
            showAlert(AlertType.INFORMATION, "Success", "Invoice " + invoiceNo + " created!");
            resetUI();
        } catch (Exception e) {
            try { connect.rollback(); } catch (SQLException ex) {}
            showAlert(AlertType.ERROR, "Checkout Failed", e.getMessage());
        } finally {
            try { connect.setAutoCommit(true); } catch (SQLException e) {}
        }
    }

    // Utility Methods
    private void resetUI() {
        billItems.clear();
        comboBoxCustomer.getSelectionModel().clearSelection();
        checkBoxGST.setSelected(false);
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

    // Navigation Methods
    private void showBillingForm() {
        billing_form.setVisible(true);
        availability_form.setVisible(false);
        profile_form.setVisible(false);
        setActiveButton(billing_btn);
    }

    private void showAvailabilityForm() {
        billing_form.setVisible(false);
        availability_form.setVisible(true);
        profile_form.setVisible(false);
        setActiveButton(availability_btn);
        loadAvailableProducts();
    }

    private void showProfileForm() {
        billing_form.setVisible(false);
        availability_form.setVisible(false);
        profile_form.setVisible(true);
        setActiveButton(profile_btn);
    }

    private void setActiveButton(Button activeButton) {
        billing_btn.setStyle("-fx-background-color: transparent;");
        availability_btn.setStyle("-fx-background-color: transparent;");
        profile_btn.setStyle("-fx-background-color: transparent;");
        activeButton.setStyle("-fx-background-color: #32CD32;");
    }

    // Database Operations
    private void loadCustomers() {
        try {
            ObservableList<String> customers = FXCollections.observableArrayList();
            String sql = "SELECT name FROM customers ";
            try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    customers.add(rs.getString("name"));
                }
                comboBoxCustomer.setItems(customers);
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", "Failed to load customers: " + e.getMessage());
        }
    }

 public void loadAvailableProducts() {
    try {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String sql = "SELECT brand, product_name, stock, price FROM product";
        PreparedStatement pstmt = connect.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            productList.add(new Product(
                rs.getString("brand"),
                rs.getString("product_name"),
                rs.getInt("stock"),
                rs.getDouble("price")
            ));
        }
        availability_table.setItems(productList);
        
    } catch (SQLException e) {
        e.printStackTrace(); // Log the error
        showAlert(AlertType.ERROR, "Database Error", e.getMessage());
    }
}
    
     @FXML
    private void searchProducts() {
        String searchText = billing_brandName.getText().trim();
        
        try {
            String sql = "SELECT DISTINCT brand, product_name FROM product WHERE brand LIKE ? OR product_name LIKE ? ORDER BY brand, product_name";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, "%" + searchText + "%");
            pstmt.setString(2, "%" + searchText + "%");
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

    private void loadProductsForBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            billing_productName.setItems(FXCollections.observableArrayList());
            return;
        }
        
        try {
            String sql = "SELECT product_name FROM product WHERE brand LIKE ? ORDER BY product_name";
            PreparedStatement pstmt = connect.prepareStatement(sql);
            pstmt.setString(1, "%" + brand + "%");
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

    private void loadProfileData() {
        try {
            String sql = "SELECT * FROM employee WHERE employee_id = ?";
            try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
                pstmt.setString(1, getData.employeeId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    profile_employeeID.setText(rs.getString("employee_id"));
                    profile_name.setText(rs.getString("first_name") + " " + rs.getString("last_name"));
                   
                }
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    // Other Methods
    public void displayUsername() {
        username.setText(getData.username);
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    
    

    public void logout() {
        try {
            Alert confirmation = new Alert(AlertType.CONFIRMATION);
            confirmation.setTitle("Logout Confirmation");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Stage stage = (Stage) logout.getScene().getWindow();
                stage.close();

                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage loginStage = new Stage();
               loginStage.initStyle(StageStyle.UNDECORATED);
                loginStage.setScene(new Scene(root));
                loginStage.show();
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

    // Product Class
    public static class Product {
        private final String brand;
        private final String productName;
        private final int stock;
        private final double price;

        public Product(String brand, String productName, int stock, double price) {
            this.brand = brand;
            this.productName = productName;
            this.stock = stock;
            this.price = price;
        }

        public String getBrand() { return brand; }
        public String getProductName() { return productName; }
        public int getStock() { return stock; }
        public double getPrice() { return price; }
    }
}