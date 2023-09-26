package com.example.csia;

import java.sql.*;

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
        Date temp = null;

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {

            preparedStatement.setDate(1, dateToSearch);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve and output the results
                temp = resultSet.getDate("date");

                // Output or process the retrieved data as needed
                System.out.println("Column1: " + temp);

            } resultSet.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }

        if ( temp != null ) {
            return true;
        }else {
            return false;
        }
    }

    public String returndateex(Date targetdate , String exno) {

        String searchQuery = "SELECT " + exno + " FROM Display WHERE date = ?";
        String ex = null;


        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {

           //preparedStatement.setString(1, exno);
            preparedStatement.setDate(1, targetdate);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve and output the results
                ex = resultSet.getString(exno);

                // Output or process the retrieved data as needed
                System.out.println("Exercise: " + ex);

                return ex;

            } resultSet.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ex;
    }

    public String searchForSetAndRep(String exercise, int nodate) {
        String output = null;
        String searchQuery = null;
        if ( nodate == 1 || nodate == 4) {
            searchQuery = "Select * FROM runex WHERE ex = ?";
        }else if ( nodate == 2 || nodate == 5) {

            searchQuery = "Select * FROM swimex WHERE ex = ?";
        }else if ( nodate == 3 || nodate == 6) {

            searchQuery = "Select * FROM cycleex WHERE ex = ?";
        } else if ( nodate == 7) {
            searchQuery = "Select * FROM coreex WHERE ex = ?";
        }
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {

            preparedStatement.setString(1, exercise);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Retrieve and output the results

                String minset = resultSet.getString("minset");
                String minrep= resultSet.getString("minrep");

                // Output or process the retrieved data as needed
                System.out.println("set: " + minset + " rep: " + minrep );
                return exercise + " " + minset + " x " + minrep;

            } resultSet.close();



        } catch (SQLException e) {
            e.printStackTrace();
        }



        return output;
    }

    public String searchRunEx(int id) {
        String searchQuery = "SELECT * FROM runex WHERE id = ?";


        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            String runex = null;
            while (resultSet.next()) {
                // Retrieve and output the results
                runex = resultSet.getString("ex");

                // Output or process the retrieved data as needed
                System.out.println("Column1: " + runex);

            }
            resultSet.close();
            return runex;


        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }
    public String searchSwimEx(int id) {
        String searchQuery = "SELECT * FROM swimex WHERE id = ?";


        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            String swimex = null;
            while (resultSet.next()) {
                // Retrieve and output the results
                swimex = resultSet.getString("ex");

                // Output or process the retrieved data as needed
                System.out.println("Column1: " + swimex);

            }
            resultSet.close();
            return swimex;


        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }
    public String searchCycleEx(int id) {
        String searchQuery = "SELECT * FROM cycleex WHERE id = ?";


        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            String cycleex = null;
            while (resultSet.next()) {
                // Retrieve and output the results
                cycleex = resultSet.getString("ex");

                // Output or process the retrieved data as needed
                System.out.println("Column1: " + cycleex);

            }
            resultSet.close();
            return cycleex;


        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }
    public String searchCoreEx(int id) {
        String searchQuery = "SELECT * FROM runex WHERE id = ?";


        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(searchQuery)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            String coreex = null;
            while (resultSet.next()) {
                // Retrieve and output the results
                coreex = resultSet.getString("ex");

                // Output or process the retrieved data as needed
                System.out.println("Column1: " + coreex);

            }
            resultSet.close();
            return coreex;


        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }
}


