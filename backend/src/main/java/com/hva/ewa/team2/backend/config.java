public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/myDatabase";
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "password123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
}
