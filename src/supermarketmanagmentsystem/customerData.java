/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package supermarketmanagmentsystem;

/**
 *
 * @author DELL
 */


import java.sql.*;

public class customerData {
    private String name;
    private String phone;
    private String address;

    public customerData(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    // Getters
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    // Find customer by name or phone
    public static customerData findCustomer(String name, String phone) throws SQLException {
        String sql = "SELECT * FROM customers WHERE name = ? OR phone = ?";
        try (Connection conn = database.connectDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new customerData(
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getString("address")
                );
            }
            return null;
        }
    }

    // Create new customer
    public static boolean createCustomer(customerData customer) throws SQLException {
        String sql = "INSERT INTO customers (name, phone, address) VALUES (?, ?, ?)";
        try (Connection conn = database.connectDb();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getPhone());
            pstmt.setString(3, customer.getAddress());
            
            return pstmt.executeUpdate() > 0;
        }
    }
}
