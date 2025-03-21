package hard;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Student> students = StudentController.getAllStudents();
        System.out.println("StudentID | Name | Department | Marks");
        for (Student s : students) {
            System.out.printf("%-9d | %-10s | %-10s | %d%n", s.getStudentID(), s.getName(), s.getDepartment(), s.getMarks());
        }
    }
}