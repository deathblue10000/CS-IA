package com.example.csia;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseOperations {
    private final String jdbcUrl;
    private final String username;
    private final String password;

    public DatabaseOperations(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    public void retrieveDataFromDisplayTable() {
        String sqlQuery = "SELECT * FROM display";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                // Retrieve data from the result set
                Date date = resultSet.getDate("date");
                Integer type = resultSet.getInt("type");
                String ex1 = resultSet.getString("ex1");
                // Process the retrieved data as needed
                System.out.println("Employee ID: " + date + ", Name: " + type + " " + ex1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertDataIntoDisplayTable(Date date, Integer type, String ex1, String ex2, String ex3, String ex4) {
        String sqlQuery = "INSERT INTO display VALUES (?, ?, ?, ?, ?, ?)";
        String sqlAlter = "UPDATE display SET type = ?, ex1 = ?, ex2 = ?, ex3 = ?, ex4 = ? WHERE date = ?";


        if (searchDateInTable(date)) {
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(sqlAlter)) {


                preparedStatement.setInt(1, type);
                preparedStatement.setString(2, ex1);
                preparedStatement.setString(3, ex2);
                preparedStatement.setString(4, ex3);
                preparedStatement.setString(5, ex4);
                preparedStatement.setDate(6, date);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Data inserted successfully!");
                } else {
                    System.out.println("Data insertion failed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else {

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

                //preparedStatement.setDate(1, date);
                preparedStatement.setDate(1, date);
                preparedStatement.setInt(2, type);
                preparedStatement.setString(3, ex1);
                preparedStatement.setString(4, ex2);
                preparedStatement.setString(5, ex3);
                preparedStatement.setString(6, ex4);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Data inserted successfully!");
                } else {
                    System.out.println("Data insertion failed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }







    public boolean searchDateInTable(Date dateToSearch) {
        String searchQuery = "SELECT * FROM Display WHERE date = ?";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {

            preparedStatement.setDate(1, dateToSearch);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve and output the results
                Date column1 = resultSet.getDate("date");

                // Output or process the retrieved data as needed
                System.out.println("Column1: " + column1);

            } resultSet.close();
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
        }return false;
    }
}


