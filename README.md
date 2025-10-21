TABLE OF CONTENTS
1. Abstract	
2. Introduction	
3. Problem Statement	
4. Objective	
5. System Design	
4.1 Table Descriptions	
4.2 Schema Characteristics
5. Methodology	
5.1 Development Phases
5.2 System Flow Diagram	
5.3 Tools and Technologies Used	
6. Results and Discussion	
6.1 Login Form	
6.2 Employee Role Execution	
6.3 Admin Role Execution	
6.4 System Accuracy and Robustness	
6.5 Summary of Observations	
7. Conclusion	

 


TABLE OF FIGURES

Figure 1: Supermarket Schema Diagram	
Figure 2: Login Window	
Figure 3: Employee Role Popup window	
Figure 4: Employee Search window	
Figure 5: Admin Login Window	
Figure 6: Admin Panel Window	
Figure 7: Employee Login Window	
Figure 8: Employee Panel Window	



1. Abstract
The objective of this project is to design and implement a GUI-based Supermarket Management System (SMS) using Java and MySQL to automate inventory tracking, sales processing, and employee management. The system provides role-based dashboards (Admin, Cashier, Stock Manager) with secure login authentication. Admins can manage products, employees, and suppliers; cashiers process sales and generate bills; and stock managers monitor inventory levels and reorder goods. Built with Java fx for the frontend and JDBC for MySQL database connectivity, the system ensures real-time data synchronization, secure transactions, and Employee-friendly interactions. The application demonstrates the integration of Java frontend development with robust backend database operations for efficient supermarket management. 
2. Introduction
The essence of a supermarket management system is for an effective automation of the management of a supermarket. The Supermarket Management System is project that deals with supermarket automation and it includes both purchasing and selling of items. Supermarket management system is the system where all the aspects related to the proper management of supermarket is done. These aspects involve managing information about the various products, staff, managers, customers, billing etc. This system provides an efficient way of managing the supermarket information. Also allows the customer to purchase and pay for the items purchased. This study is based on the sales transaction and billing of items in a supermarket. This study is to produce software which manages the sales activity done in a supermarket, maintaining the stock details, maintaining the records of the sales done for a particular month/year. The Employees will consume less time in calculation and the sales activity will be completed within a fraction of seconds whereas manual system will make the Employee to write it down which is a long procedure and so paper work will be reduced and the Employee can spend more time monitoring the supermarket. The program will be Employee friendly and easy to use. The system will allow the Employee when to reorder fora particular goods. The system will display all the items whose name start with the letter selected by the Employee. He can select out of those displayed. Finally, a separate bill will be generated for each customer. This will be saved in  the database. Any periodic records can be viewed at any time. If the stock is not available, the supermarket orders and buys from a prescribed vendor. The amount will be paid by deducting the total amount acquired in the sales activity. Admin provides a unique Employeename and password for each employee through which they can login to proceed on their daily activities.


3. Problem Statement
In most organizations, structured data such as employee records, departmental details, and project assignments are maintained in relational databases like MySQL. While databases offer powerful querying capabilities, direct interaction typically requires knowledge of SQL, which can be a barrier for Employees who lack technical expertise. Additionally, without a proper Employee interface and role-based access control, these systems are vulnerable to unauthorized modifications, data inconsistency, and poor usability.
The absence of a centralized, intuitive, and secure system for managing company data presents the following challenges:
●	Lack of usability: Employees must execute raw SQL queries to interact with the database, making the system difficult for non-programmers to use.
●	No access control: Without Employee roles, all Employees have unrestricted access to modify data, risking data integrity.
●	Inconsistent data operations: Manual errors in SQL queries can result in corrupted or incomplete records.
●	No real-time feedback: Traditional systems lack immediate visual feedback, making data validation and error handling less transparent.


4. Objective
This project is a JavaFX-based Supermarket Management System integrated with a MySQL backend, featuring:
●	Secure login for admins and employees

●	Role-based dashboards for controlled access

●	Real-time interaction with database tables

●	Full product and employee CRUD operations for admins

●	Searchable product listings for employees

The system bridges backend data handling with a Employee-friendly interface to ensure efficient supermarket operations and data integrity.

5. System Design
The backend of the application is built on a relational MySQL database schema tailored to represent a supermarket's operational structure. The schema includes entities such as:
●	Employees – capturing login credentials, personal details, and roles.

●	Products – including product ID, brand, name, price, and availability status.

●	Admins – authorized personnel with elevated access privileges.

●	(Optional: Sales or Inventory Logs) – to record transactions and stock movements.

The relationships between entities are maintained through foreign key constraints, ensuring referential integrity and consistent data management throughout the system.
 

Figure 1: SuperMarket Schema Diagram
Fig. 1 illustrates the core components of the company database:
4.1 Table Descriptions
1. employee
●	Primary Key: id
●	Stores employee login and profile info:
○	employee_id, firstname, lastname, gender, date, password
Connected to employee_logs via triggers for audit trail

2. employee_logs
●	Tracks every insert/update/delete on employee
●	Fields include employee data and an action_type (INSERT, UPDATE, DELETE)
3. product
●	Primary Key: id
●	Core product catalog:
○	product_id, brand, product_name, status, price, stock, gst_rate
●	Linked to product_logs and referenced by:
○	sales
○	invoice_items

4. product_logs
●	Audit table for product changes (similar to employee_logs)
●	Tracks INSERT, UPDATE, DELETE operations
 5. sales
●	Primary Key: sale_id
●	Tracks quick sales:
○	product_id, quantity, total_price, date
●	Foreign key:
○	product_id → product(product_id)

6. customers
●	Primary Key: id
●	Stores customer info: name, phone, address
●	Linked to invoices

7. invoices
●	Primary Key: invoice_no
●	Full invoice data:
○	invoice_date, total_amount, tax_amount, customer_id
●	Foreign key:
○	customer_id → customers(id)
8. invoice_items
●	Primary Key: i
●	Line items in invoices:
○	invoice_no, product_id, quantity, unit_price, tax_amount
●	Foreign keys:
○	invoice_no → invoices
○	product_id → product
9. admin
●	Primary Key: id
●	Stores login credentials for administrators:
○	Employeename, password

🧾 10. tax_rates
●	Dynamic GST/tax rules:
○	rate, applicable_from



4.2 Schema Characteristics:
Normalization
The database schema is designed in accordance with the principles of Third Normal Form (3NF) to eliminate redundancy and ensure data integrity. Each table represents a single entity, and attributes are dependent solely on the primary key.
 Integrity
Referential integrity is maintained through the use of foreign key constraints:
●	employee.department_id references DEPARTMENT
●	PRODUCT.department_id references DEPARTMENT
●	SALES.employee_id and product_id reference EMPLOYEE and PRODUCT respectively
●	DEPENDENT.employee_id ensures each dependent is linked to a valid employee

These constraints prevent orphan records and maintain consistent relationships across the system.
 Scalability
The schema follows a modular design, making it easy to extend. New modules such as:
●	Attendance tracking
●	Payroll management
●	Inventory logs
 can be integrated with minimal disruption to existing functionality by referencing core entities like EMPLOYEE and PRODUCT.


Security
The LOGIN page enforces role-based access control:
●	Admins can perform full CRUD operations.
●	Employees have restricted permissions limited to billing and searching data.
Sensitive data, such as login credentials, is protected and excluded from direct manipulation through the GUI.

5. Methodology
The project follows a modular design to separate the frontend (JavaFX GUI) from backend (MySQL database operations). The GUI is built using JavaFX with FXML and CSS for clean layout and styling. Backend logic uses JDBC for secure interaction with the MySQL database.
The system is structured into three main layers:
●	Employee Interface Layer: Built with JavaFX components such as buttons, forms, tables, and charts.

●	Authentication & Role Management: Login credentials and roles (admin or employee) are validated from the LOGIN table to control access.

●	Database Transaction Layer: Uses JDBC to execute SQL queries with foreign key constraints for integrity.





5.1 Development Phases


Phase 1: Employee Interface Design

●	Login and dashboard interfaces are built using JavaFX components such as TextField, PasswordField, Button, and Label.
●	Navigation between views (e.g., login, admin dashboard, employee dashboard) is handled using FXML scene switching for smooth transitions.
●	Role selection and redirection are managed based on login credentials, directing Employees to the appropriate interface.
Phase 2: Database Connection
●	The application establishes JDBC connection using DriverManager.getConnection() with MySQL Connector/J. PreparedStatements enable parameterized queries
●	The login table is used to authenticate Employees and determine their role (admin or Employee).
Phase 3: Role-Based Dashboard Logic
●	On successful login:
o	Admin Employees are redirected to a dashboard where they can select tables, view records, and perform insert, update, and delete operations.
o	General Employees are directed to a view-only dashboard that supports search operations.
Phase 4: Dynamic Table Loading and Tableview
●	Table data is retrieved using SQL queries (e.g., SELECT * FROM users) — excluding sensitive tables like LOGIN.
●	Results are displayed using JavaFX’s TableView component, which provides a table-like interface for billing data.
●	The TableView is dynamically populated by iterating over database results and binding them to table columns using PropertyValueFactory and observable lists.
Phase 5: Error Handling and Feedback
●	All Employee actions such as login failures, SQL exceptions, and validation errors are handled using JavaFX’s Alert dialog (AlertType.ERROR or AlertType.INFORMATION) to provide clear Employee feedback.
●	Input fields are validated to prevent blank or invalid entries, ensuring data integrity before database operations.


<img width="975" height="738" alt="image" src="https://github.com/user-attachments/assets/b79228f4-6ee2-42fd-975e-67c7d13155e9" />






5.2 System Flow Diagram
Error! Filename not specified.
Explanation of Flow:
Table 1: Action Table
Step	Action
1	Start the application: Launches the main window with login/register options.
2	Employee registers or logs in: Data is submitted to the login table for validation.
3	Role determination: The system fetches the Role column for the given Employee.
4a	If Role = admin: Admin dashboard opens, showing table selection dropdown, Tableview for data, and CRUD operation buttons.
4b	If Role = Employee: Employee dashboard opens with search functionality and a view-only interface.
5	Dynamic table load: SHOW TABLES command fetches available tables.
6	Execute SQL operations: Based on Employee input, SQL commands are executed and committed to the database.
7	Display result or error: Results are displayed in Tableview; errors shown in pop-ups.
8	End/Logout: Application closes or returns to login screen.

5.3 Tools and Technologies Used
Table 2: Development Tools and Technologies Used
Component	Technology
Programming Language	JAVA
GUI Library	JavaFX
Database	MySQL 8+
Connector	mysql.connector
Packaging (Optional)	fontawesomefx, jfoenix
Platform	Windows 11

6. Results and Discussion
The developed GUI-based application successfully connects a JavaFX frontend with a MySQL backend, enabling secure and role-specific interaction with the database.
Upon launching, users are presented with a login form. Based on their credentials and assigned role, the system dynamically loads the appropriate interface — granting full administrative control to admins or limited access to employees for billing and searching product data.
6.1 Login Form
   <img width="975" height="617" alt="image" src="https://github.com/user-attachments/assets/644bec78-a203-4815-bdfd-f2db4e3bd3da" />

 
Figure 2: Login Window
The application opens with a login interface where users must enter their registered Username and password. This form ensures that only authorized Employees and admin can proceed to their respective dashboards. Validation prevents empty input and provides appropriate error feedback for incorrect credentials.
6.2 Employee Role Execution
Employee Login and Welcome Message
 <img width="975" height="619" alt="image" src="https://github.com/user-attachments/assets/ffc427b9-e008-451b-86bb-9d1fe1c5fb07" />

Figure 3: Employee Role Popup window
Once a valid Employee logs in (e.g., Employeename: ABC123), a success popup confirms the role and access. The system immediately redirects the Employee to the Employee Dashboard.
Employee Dashboard – Billing View
 <img width="975" height="525" alt="image" src="https://github.com/user-attachments/assets/1f503711-79d5-4c9b-ab7c-819ef251029e" />
<img width="1009" height="544" alt="image" src="https://github.com/user-attachments/assets/a6a2f603-c5e1-4c78-b571-19d99640c4be" />
<img width="707" height="545" alt="image" src="https://github.com/user-attachments/assets/b85e0586-cb82-481c-b78a-d875faa4dcf2" />

  
Figure 4: Employee Search window
The Employee dashboard provides:
●	A dropdown list to select any available table (excluding the login table).
.
●	A Tableview widget that displays table data in a Employee-friendly tabular format.

Observation:
Employees cannot modify data, ensuring that sensitive records are protected from unintended changes.
6.3 Admin Role Execution
 <img width="975" height="629" alt="image" src="https://github.com/user-attachments/assets/c8c289e7-8141-47e1-bb69-58f8bf7672b1" />

Figure 5: Admin Login Window
Admin Login and Welcome Message
Admin Employees (e.g., Username: admin) also log in through the same interface. Once authenticated, the role is identified as "admin", and a success message is displayed.
Admin Dashboard – Edit Access
 <img width="975" height="533" alt="image" src="https://github.com/user-attachments/assets/0c5e9a15-3c7f-46e9-9c1e-c7a0ea50abb1" />
<img width="975" height="533" alt="image" src="https://github.com/user-attachments/assets/5720a41a-2ed4-473d-a196-1b5ea9f02e5e" />

Figure 6: Admin Panel Window

Supermarket Management | Admin Portal
 Welcome admin
________________________________________
 Navigation Panel (Left Side)
●	Add Product – Switch to Product Management View

●	 Employees – View and manage employee records

●	 SIGN OUT – Logout of admin portal

________________________________________ Product Management Panel
Product Entry Form (Left Center)
Field Name	Description
Product ID	Unique ID of the product
Brand Name	Company/Brand of the product
Product Name	Name of the product
GST Rate (%)	Applicable GST percentage
Status	Dropdown: Available / Not Available
Price (₹)	Price of the product in INR
Buttons:
●	Add – Insert a new product (opens pop-up form)
●	Update – Modify the selected product details
●	Clear – Reset all form fields
●	Delete – Remove the selected product (with confirmation)

________________________________________
Product Table View (Right Panel)
●	Search Bar: Filter products by name, brand, or ID.

●	Table Columns:

○	Product ID
○	Brand Name
○	Product Name
○	Status
○	Price (₹)
________________________________________
 Employee Management Interface
Employee Table View (Top Center)
●	Table Columns:

○	EmployeeID
○	Password
○	First Name
○	Last Name
○	Gender
○	Date of Joining

●	Displays current employee records from the database.
●	Admin can select any row to update or delete that record.

________________________________________
Employee Entry Form (Bottom Center)
Field Name	Description
Employee ID	Unique identifier for the employee
Password	Login password for the employee
First Name	First name of the employee
Last Name	Last name of the employee
Gender	Dropdown: Male / Female / Other
________________________________________
 Action Buttons:
●	 Clear – Reset all form fields
●	Delete – Remove the selected employee (after confirmation)
●	Update – Modify details of selected employee
●	 Save – Insert a new employee record

________________________________________
This UI allows the admin to manage the employee table dynamically using CRUD operations, with a clean table view and input form layout.


6.4 System Accuracy and Robustness
Feature Tested	Outcome
Login Authentication	Successful for valid Employees and admins
Role Identification	Accurate dashboard redirection
Table Loading	Instant, dynamic via dropdown
Record Insertion/Update	Correct via form, with constraint checks
Deletion	Successful with confirmation dialog
Error Handling	Informative popup messages
Search Operation (Employee)	Keyword-based, case-insensitive filtering
6.5 Summary of Observations
●	The Tableview widget effectively displays large tables in a readable format.
●	Use of StringVar, Combobox, and messagebox makes the interface Employee-friendly.
●	Database changes reflect in real-time without restarting the application.
●	All SQL commands are parameterized, preventing SQL injection.
●	The role-based system prevents unauthorized data manipulation.




7. Conclusion

This mini-project successfully demonstrates the design and implementation of a GUI-based company database management system using Java and MySQL. The application provides a secure, Employee-friendly interface for accessing and managing organizational data through role-based privileges using JavaFX to deliver dynamic and responsive visual interaction, while MySQL ensures robust backend data management.
The system effectively achieves its intended goals:
●	Role-based access control ensures that only authorized Employees can perform specific operations.
●	Admins have full control over the database with the ability to add, edit, or delete records.
●	General Employees are limited to billing and searching data, ensuring database integrity.
●	Dynamic table interaction using Tableview enables real-time updates without requiring raw SQL knowledge.
The integration of Tableview for tabular display, use of parameterized queries for database safety, and validation through pop-up messages enhance the system’s usability, security, and reliability. Furthermore, the modular structure of the application allows for future expansion, such as web deployment using Flask or Django, password encryption using hashing algorithms, and extended reporting features.
In conclusion, this project bridges the gap between database backend operations and intuitive frontend design, making it an ideal solution for educational institutes, companies, and administrative units needing basic internal database management.

