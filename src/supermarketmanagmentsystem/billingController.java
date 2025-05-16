package supermarketmanagmentsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXCheckBox;

public class billingController implements Initializable {

    // Database Connection
    private Connection connect;
    
    // UI Components
    @FXML private Text lCusName;
    @FXML private Text lCusStreet;
    @FXML private Text lGSTin;
    @FXML private Text lTotalAmountNum;
    @FXML private Text cGSTAmt;
    @FXML private Text sGSTAmt;
    @FXML private Text lTotalPlusTaxNum;
    @FXML private Text cGstper;
    @FXML private Text sGSTPer;
    
    @FXML private JFXComboBox<String> comboBoxCustomer;
    @FXML private JFXCheckBox checkBoxGST;
    
    @FXML private TableView<BillItem> tblBilling;
    @FXML private TableColumn<BillItem, String> colProductId;
    @FXML private TableColumn<BillItem, String> colProductName;
    @FXML private TableColumn<BillItem, Double> colPrice;
    @FXML private TableColumn<BillItem, Integer> colQuantity;
    @FXML private TableColumn<BillItem, Double> colTotal;
    
    @FXML private TextField txtProductId;
    @FXML private TextField txtQuantity;
    
    private final ObservableList<BillItem> billItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connect = database.connectDb();
        configureTableColumns();
        initializeTaxLabels();
        loadCustomers();
    }

    private void configureTableColumns() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblBilling.setItems(billItems);
    }

    private void initializeTaxLabels() {
        cGstper.setText("9%");
        sGSTPer.setText("9%");
    }

    // Customer Handling
    private void loadCustomers() {
        try {
            ObservableList<String> customers = FXCollections.observableArrayList();
            String sql = "SELECT name FROM customers WHERE active = true";
            try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                while(rs.next()) {
                    customers.add(rs.getString("name"));
                }
                comboBoxCustomer.setItems(customers);
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", "Failed to load customers: " + e.getMessage());
        }
    }

    @FXML
    private void handleCustomerSubmit() {
        String selectedCustomer = comboBoxCustomer.getValue();
        if(selectedCustomer == null || selectedCustomer.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please select a customer");
            return;
        }

        try {
            String sql = "SELECT name, address, gstin FROM customers WHERE name = ?";
            try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
                pstmt.setString(1, selectedCustomer);
                ResultSet rs = pstmt.executeQuery();

                if(rs.next()) {
                    lCusName.setText(rs.getString("name"));
                    lCusStreet.setText(rs.getString("address"));
                    lGSTin.setText(checkBoxGST.isSelected() ? rs.getString("gstin") : "N/A");
                    showAlert(AlertType.INFORMATION, "Success", "Customer details loaded");
                }
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    // Product Handling
    @FXML
    private void handleAdd() {
        String productId = txtProductId.getText().trim();
        String quantityStr = txtQuantity.getText().trim();

        if(productId.isEmpty() || quantityStr.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            if(quantity <= 0) throw new NumberFormatException();

            String sql = "SELECT product_id, product_name, price, stock FROM products WHERE product_id = ?";
            try (PreparedStatement pstmt = connect.prepareStatement(sql)) {
                pstmt.setString(1, productId);
                ResultSet rs = pstmt.executeQuery();

                if(rs.next()) {
                    if(rs.getInt("stock") < quantity) {
                        showAlert(AlertType.WARNING, "Stock Error", 
                            "Available stock: " + rs.getInt("stock"));
                        return;
                    }

                    billItems.add(new BillItem(
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getDouble("price"),
                        quantity
                    ));
                    updateTotals();
                    clearFields();
                } else {
                    showAlert(AlertType.ERROR, "Error", "Product not found");
                }
            }
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Error", "Invalid quantity");
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        BillItem selected = tblBilling.getSelectionModel().getSelectedItem();
        if(selected == null) {
            showAlert(AlertType.ERROR, "Error", "Select an item to delete");
            return;
        }

        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Delete");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Delete " + selected.getProductName() + "?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            billItems.remove(selected);
            updateTotals();
        }
    }

    // Calculations
    private void updateTotals() {
        double subtotal = billItems.stream()
                .mapToDouble(BillItem::getTotal)
                .sum();
        
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

    // Checkout
    @FXML
    private void handleBillSubmit() {
        if(billItems.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Add items before checkout");
            return;
        }

        Alert confirmation = new Alert(AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Checkout");
        confirmation.setContentText("Process final bill?");
        
        confirmation.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK) {
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
            String invoiceSQL = "INSERT INTO invoices (invoice_no, total_amount, customer_name) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connect.prepareStatement(invoiceSQL)) {
                pstmt.setString(1, invoiceNo);
                pstmt.setDouble(2, total);
                pstmt.setString(3, lCusName.getText());
                pstmt.executeUpdate();
            }
            
            // Insert items and update stock
            String itemSQL = "INSERT INTO invoice_items (invoice_no, product_id, quantity, price) VALUES (?, ?, ?, ?)";
            String stockSQL = "UPDATE products SET stock = stock - ? WHERE product_id = ?";
            
            for(BillItem item : billItems) {
                try (PreparedStatement itemStmt = connect.prepareStatement(itemSQL)) {
                    itemStmt.setString(1, invoiceNo);
                    itemStmt.setString(2, item.getProductId());
                    itemStmt.setInt(3, item.getQuantity());
                    itemStmt.setDouble(4, item.getUnitPrice());
                    itemStmt.executeUpdate();
                }
                
                try (PreparedStatement stockStmt = connect.prepareStatement(stockSQL)) {
                    stockStmt.setInt(1, item.getQuantity());
                    stockStmt.setString(2, item.getProductId());
                    stockStmt.executeUpdate();
                }
            }
            
            connect.commit();
            showAlert(AlertType.INFORMATION, "Success", 
                "Invoice " + invoiceNo + " created!\nTotal: ₹" + total);
            resetUI();
            
        } catch (SQLException | NumberFormatException e) {
            try {
                connect.rollback();
                showAlert(AlertType.ERROR, "Checkout Failed", e.getMessage());
            } catch (SQLException ex) {
                showAlert(AlertType.ERROR, "Critical Error", ex.getMessage());
            }
        } finally {
            try {
                connect.setAutoCommit(true);
            } catch (SQLException e) {
                showAlert(AlertType.ERROR, "Connection Error", e.getMessage());
            }
        }
    }

    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis();
    }

    private void resetUI() {
        billItems.clear();
        comboBoxCustomer.getSelectionModel().clearSelection();
        checkBoxGST.setSelected(false);
        lCusName.setText("");
        lCusStreet.setText("");
        lGSTin.setText("");
        updateTotals();
    }

    // Helpers
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtProductId.clear();
        txtQuantity.clear();
    }

    // BillItem Class
    public static class BillItem {
        private final SimpleStringProperty productId;
        private final SimpleStringProperty productName;
        private final SimpleDoubleProperty unitPrice;
        private final SimpleIntegerProperty quantity;

        public BillItem(String productId, String productName, double unitPrice, int quantity) {
            this.productId = new SimpleStringProperty(productId);
            this.productName = new SimpleStringProperty(productName);
            this.unitPrice = new SimpleDoubleProperty(unitPrice);
            this.quantity = new SimpleIntegerProperty(quantity);
        }

        // Getters
        public String getProductId() { return productId.get(); }
        public String getProductName() { return productName.get(); }
        public double getUnitPrice() { return unitPrice.get(); }
        public int getQuantity() { return quantity.get(); }
        public double getTotal() { return unitPrice.get() * quantity.get(); }
    }
}