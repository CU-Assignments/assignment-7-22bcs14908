import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class FetchEmployeeData {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/company_db"; // Change "company_db" to your database name
        String user = "root"; // Change if you have a different username
        String password = "yourpassword"; // Replace with your MySQL password

        // JDBC Connection
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish Connection
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/company_db","root","root");

            // Create a Statement
            Statement stmt = conn.createStatement();

            // Execute Query
            String sql = "SELECT * FROM Employee";
            ResultSet rs = stmt.executeQuery(sql);

            // Process Result Set
            System.out.println("EmpID | Name     | Salary");
            System.out.println("---------------------------");

            while (rs.next()) {
                int empID = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");

                System.out.printf("%-5d | %-8s | %.2f%n", empID, name, salary);
            }

            // Close Resources
            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
