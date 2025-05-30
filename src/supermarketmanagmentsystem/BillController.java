 package supermarketmanagmentsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class BillController implements Initializable {

    @FXML private Label lblInvoiceNo;
    @FXML private Label lblDate;
    @FXML private Label lblCustomerName;
    @FXML private Label lblCustomerPhone;
    @FXML private Label lblCustomerAddress;
    @FXML private TableView<BillItem> tblItems;
    @FXML private TableColumn<BillItem, String> colBrand;
    @FXML private TableColumn<BillItem, String> colProduct;
    @FXML private TableColumn<BillItem, Double> colPrice;
    @FXML private TableColumn<BillItem, Integer> colQuantity;
    @FXML private TableColumn<BillItem, Double> colTotal;
    @FXML private Label lblSubtotal;
    @FXML private Label lblCGST;
    @FXML private Label lblSGST;
    @FXML private Label lblGrandTotal;
    @FXML private Button btnPrint;

    private ObservableList<BillItem> billItems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        setCurrentDate();
    }

    private void setupTableColumns() {
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblItems.setItems(billItems);
    }

    private void setCurrentDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        lblDate.setText(now.format(formatter));
    }

    public void setInvoiceNumber(String invoiceNo) {
        lblInvoiceNo.setText(invoiceNo);
    }

    public void setCustomerDetails(String name, String phone, String address) {
        lblCustomerName.setText(name);
        lblCustomerPhone.setText(phone);
        lblCustomerAddress.setText(address);
    }

    public void setBillItems(ObservableList<employeeDashboardController.BillItem> items) {
        billItems.clear();
        for (employeeDashboardController.BillItem item : items) {
            billItems.add(new BillItem(
                item.getBrand(),
                item.getProductName(),
                item.getPrice(),
                item.getQuantity()
            ));
        }
        updateTotals();
    }

    public void setTotalAmount(String total) {
        lblGrandTotal.setText(total);
    }

    public void setGSTAmount(String cgst, String sgst) {
        lblCGST.setText(cgst);
        lblSGST.setText(sgst);
    }

    private void updateTotals() {
        double subtotal = billItems.stream().mapToDouble(BillItem::getTotal).sum();
        lblSubtotal.setText(String.format("₹%.2f", subtotal));
    }

    @FXML
    private void handlePrint() {
        // TODO: Implement print functionality
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Print");
        alert.setHeaderText(null);
        alert.setContentText("Print functionality will be implemented soon!");
        alert.showAndWait();
    }

    public static class BillItem {
        private final String brand;
        private final String productName;
        private final double price;
        private final int quantity;
        private final double total;

        public BillItem(String brand, String productName, double price, int quantity) {
            this.brand = brand;
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
            this.total = price * quantity;
        }

        public String getBrand() { return brand; }
        public String getProductName() { return productName; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public double getTotal() { return total; }
    }
}