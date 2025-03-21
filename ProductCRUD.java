package defalut;
import java.sql.*;
import java.util.Scanner;

public class ProductCRUD {
	static final String URL = "jdbc:mysql://localhost:3306/store_db?useSSL=false&allowPublicKeyRetrieval=true";
    static final String USER = "root";
    static final String PASSWORD = "root"; // Change this to your MySQL password


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Product Management ===");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addProduct();
                case 2 -> viewProducts();
                case 3 -> updateProduct();
                case 4 -> deleteProduct();
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 5);

        sc.close();
    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void addProduct() {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)")) {

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Product Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();

            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, qty);
            stmt.executeUpdate();

            System.out.println("Product added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewProducts() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Product")) {

            System.out.println("\nProductID | ProductName | Price | Quantity");
            System.out.println("-----------------------------------------");

            while (rs.next()) {
                System.out.printf("%-9d | %-12s | %.2f | %d%n",
                        rs.getInt("ProductID"), rs.getString("ProductName"),
                        rs.getDouble("Price"), rs.getInt("Quantity"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProduct() {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Product SET Price = ?, Quantity = ? WHERE ProductID = ?")) {

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Product ID to update: ");
            int id = sc.nextInt();
            System.out.print("Enter new Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter new Quantity: ");
            int qty = sc.nextInt();

            stmt.setDouble(1, price);
            stmt.setInt(2, qty);
            stmt.setInt(3, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product updated successfully!");
            } else {
                System.out.println("No product found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct() {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Product WHERE ProductID = ?")) {

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Product ID to delete: ");
            int id = sc.nextInt();

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Product deleted successfully!");
            } else {
                System.out.println("❌ No product found with the given ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}