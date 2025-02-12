package week3;

import java.sql.Connection;
import java.sql.SQLException;

class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/FeeReportDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return java.sql.DriverManager.getConnection(URL, USER, PASSWORD);
    }
}


