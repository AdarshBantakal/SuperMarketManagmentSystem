-- Create product_logs table to track changes
CREATE TABLE IF NOT EXISTS product_logs (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(100),
    brand VARCHAR(100),
    product_name VARCHAR(100),
    status VARCHAR(100),
    price DOUBLE,
    stock INT,
    gst_rate DECIMAL(5,2),
    action_type VARCHAR(10),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Trigger for adding new products
DELIMITER //
CREATE TRIGGER after_product_insert
AFTER INSERT ON product
FOR EACH ROW
BEGIN
    INSERT INTO product_logs (
        product_id, 
        brand, 
        product_name, 
        status, 
        price, 
        stock, 
        gst_rate, 
        action_type
    )
    VALUES (
        NEW.product_id,
        NEW.brand,
        NEW.product_name,
        NEW.status,
        NEW.price,
        NEW.stock,
        NEW.gst_rate,
        'INSERT'
    );
END //
DELIMITER ;

-- Trigger for updating products
DELIMITER //
CREATE TRIGGER after_product_update
AFTER UPDATE ON product
FOR EACH ROW
BEGIN
    INSERT INTO product_logs (
        product_id, 
        brand, 
        product_name, 
        status, 
        price, 
        stock, 
        gst_rate, 
        action_type
    )
    VALUES (
        NEW.product_id,
        NEW.brand,
        NEW.product_name,
        NEW.status,
        NEW.price,
        NEW.stock,
        NEW.gst_rate,
        'UPDATE'
    );
END //
DELIMITER ;

-- Trigger for deleting products
DELIMITER //
CREATE TRIGGER after_product_delete
AFTER DELETE ON product
FOR EACH ROW
BEGIN
    INSERT INTO product_logs (
        product_id, 
        brand, 
        product_name, 
        status, 
        price, 
        stock, 
        gst_rate, 
        action_type
    )
    VALUES (
        OLD.product_id,
        OLD.brand,
        OLD.product_name,
        OLD.status,
        OLD.price,
        OLD.stock,
        OLD.gst_rate,
        'DELETE'
    );
END //
DELIMITER ; 