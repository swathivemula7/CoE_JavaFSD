package week3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Student {
    public static boolean addStudent(String name, String email, String course, double fee, double paid, String address, String phone) {
        double due = fee - paid;
        String sql = "INSERT INTO student (name, email, course, fee, paid, due, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, course);
            stmt.setDouble(4, fee);
            stmt.setDouble(5, paid);
            stmt.setDouble(6, due);
            stmt.setString(7, address);
            stmt.setString(8, phone);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void displayDueFees() {
        String sql = "SELECT * FROM student WHERE due > 0";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Student: " + rs.getString("name") + ", Due Fee: " + rs.getDouble("due"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void viewAllStudents() {
        String sql = "SELECT * FROM student";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Student: " + rs.getString("name") + ", Course: " + rs.getString("course") + ", Due Fee: " + rs.getDouble("due"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
