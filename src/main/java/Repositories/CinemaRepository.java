package Repositories;

import org.cinemaSystem.ConClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CinemaRepository {

    ConClass conClass = ConClass.getInstance();
    Connection connection = conClass.getConnection();
    String username, password, cinema_name;
    int id;
    boolean confirmation;

    public CinemaRepository() throws SQLException {
    }

    public CinemaRepository(String username) throws SQLException {
        String sqlStatement = "SELECT id,cinema_name,confirmation " +
                "FROM cinemas " +
                "WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        this.username = username;
        this.id = resultSet.getInt(1);
        this.cinema_name = resultSet.getString(2);
        this.confirmation = resultSet.getBoolean(3);
        preparedStatement.close();
    }

    public void createTable() throws SQLException {
        String sqlStatement = "CREATE TABLE IF NOT EXISTS cinemas(" +
                "id             SERIAL primary key," +
                "cinema_name    VARCHAR(20)," +
                "username       VARCHAR(20) unique," +
                "password       VARCHAR(40)," +
                "confirmation   BOOLEAN" +
                ");";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.execute();
        System.out.println("CinemaRep createTable Executed.");
    }

    public boolean authentication(String username, String password) throws SQLException {
        String sqlStatement = "SELECT username,password FROM cinemas WHERE username = ? AND password = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public String getUsernames() throws SQLException {
        StringBuilder output = new StringBuilder();
        String cinemaUsernames = "SELECT username FROM cinemas;";
        PreparedStatement preparedStatement = connection.prepareStatement(cinemaUsernames);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            output.append(" ").append(resultSet.getString("username"));
        }
        return output.toString();
    }

    public void signUpToRep(String username, String password, String cinemaName) throws SQLException {
        String sqlStatement = "INSERT INTO cinemas (username,password,cinema_name,confirmation) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, cinemaName);
        preparedStatement.setBoolean(4, false);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public void viewCinemas() throws SQLException {
        String sqlStatement = "SELECT id,cinema_name,confirmation FROM cinemas;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                System.out.print("ID: " + resultSet.getString(1));
                System.out.print(" , Cinema Name: " + resultSet.getString(2));
                System.out.print(" , Confirmation Status: " + confirmationString(resultSet.getBoolean(3)));
                System.out.println();
            }
        } else {
            System.out.println("No Cinema has registered yet.");
            preparedStatement.close();
        }
    }

    public void viewCinemasForAdmin() throws SQLException{
        String sqlStatement = "SELECT id,cinema_name,confirmation FROM cinemas;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.isBeforeFirst()) {
            while (resultSet.next()) {
                if (!resultSet.getBoolean(3)) {
                    System.out.print("ID: " + resultSet.getString(1));
                    System.out.print(" , Cinema Name: " + resultSet.getString(2));
                    System.out.print(" , Confirmation Status: " + confirmationString(resultSet.getBoolean(3)));
                    System.out.println();
                }
            }
        } else {
            System.out.println("No Cinema has registered yet.");
            preparedStatement.close();
        }
    }

    public void confirm(int id) throws SQLException {
        String confirmStatement = "UPDATE cinemas " +
                "SET confirmation = true " +
                "WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(confirmStatement);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public boolean idExists(int id) throws SQLException {
        String sqlStatement = "SELECT cinema_name FROM cinemas WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            preparedStatement.close();
            return true;
        } else {
            preparedStatement.close();
            return false;
        }
    }

    public boolean isConfirmed(int id) throws SQLException {
        String sqlStatement = "SELECT confirmation FROM cinemas WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getBoolean(1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCinema_name() {
        return cinema_name;
    }

    public void setCinema_name(String cinema_name) {
        this.cinema_name = cinema_name;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    private String confirmationString(boolean confirmation){
        if (confirmation) {
            return "Confirmed";
        }
        else return "Not Confirmed";
    }
}
