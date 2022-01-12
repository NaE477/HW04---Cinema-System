package Repositories;

import org.cinemaSystem.ConClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminRepository {

    ConClass conClass = ConClass.getInstance();
    Connection connection = conClass.getConnection();

    public AdminRepository() throws SQLException {
    }


    public void createTable() throws SQLException {
        String sqlStatement = "CREATE TABLE IF NOT EXISTS admins(" +
                "id             SERIAL PRIMARY KEY," +
                "username       VARCHAR(20) UNIQUE," +
                "password       VARCHAR(40)" +
                ");";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.execute();
        System.out.println("AdminRep createTable executed.");
    }

    public boolean authentication(String username, String password) throws SQLException{
        String sqlStatement = "SELECT username,password FROM admins WHERE username = ? AND password = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    public String getUsernames() throws SQLException {
        StringBuilder output = new StringBuilder();
        String sqlStatement = "SELECT username FROM admins;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            output.append(" ").append(resultSet.getString("username"));
            }
        return output.toString();
        }

    public void signUpToRep(String username, String password) throws SQLException {
        String sqlStatement = "INSERT INTO admins (username,password) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.execute();
        preparedStatement.close();
    }

}
