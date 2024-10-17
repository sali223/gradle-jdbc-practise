package org.example;

import java.sql.*;

public class App {
    public String getGreeting() {
        return "Hello World, this ";
    }

    static final String QUERY = "SELECT * FROM cars";
    static final String INSERT_SQL = "INSERT INTO cars (make, model, year) VALUES (?, ?, ?)";
    static final String DELETE_SQL = "DELETE FROM cars WHERE id = ?";

    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "admin";

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.println("Connected to the PostgreSQL server successfully.");

            // Inserting car records
            insertCar(connection, INSERT_SQL, "BMW", "X5", 2022);
            insertCar(connection, INSERT_SQL, "Honda", "Civic", 2021);
            insertCar(connection, INSERT_SQL, "Lexus", "RX", 2023);
            insertCar(connection, INSERT_SQL, "Toyota", "Camry", 2022);
            insertCar(connection, INSERT_SQL, "Nissan", "Rogue", 2021);
            insertCar(connection, INSERT_SQL, "Volkswagen", "Golf", 2022);
            insertCar(connection, INSERT_SQL, "Peugeot", "206", 2021);
            insertCar(connection, INSERT_SQL, "Honda", "HRV", 2024);

            // Querying all cars
            Statement statement = connection.createStatement();
            var selectAllResult = statement.executeQuery(QUERY);

            while (selectAllResult.next()) {
                System.out.println("make= " + selectAllResult.getString("make"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    //  insert a car into the database
    public static void insertCar(Connection conn, String sql, String make, String model, int year) {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, make);
            pstmt.setString(2, model);
            pstmt.setInt(3, year);

            int affectedRows = pstmt.executeUpdate();
            System.out.println("New record inserted successfully. Rows affected: " + affectedRows);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
