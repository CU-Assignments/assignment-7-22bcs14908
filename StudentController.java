package hard;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {
	static final String URL = "jdbc:mysql://localhost:3306/student_db?useSSL=false&allowPublicKeyRetrieval=true";;
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
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


    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Student")) {

            while (rs.next()) {
                students.add(new Student(rs.getInt("StudentID"), rs.getString("Name"), rs.getString("Department"), rs.getInt("Marks")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}