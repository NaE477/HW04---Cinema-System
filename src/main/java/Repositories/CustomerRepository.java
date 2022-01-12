package Repositories;

import org.cinemaSystem.ConClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository {

    int id;
    String username,first_name,last_name;

    public CustomerRepository() throws SQLException {
    }
    public CustomerRepository(String username) throws SQLException {
        String sqlStatement = "SELECT id,first_name,last_name,username FROM customers WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1,username);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        this.id = resultSet.getInt("id");
        this.first_name = resultSet.getString("first_name");
        this.last_name = resultSet.getString("last_name");
        this.username = resultSet.getString("username");
        preparedStatement.close();
    }

    public void createTable() throws SQLException {
        String sqlStatement = "CREATE TABLE IF NOT EXISTS customers(" +
                "id         SERIAL PRIMARY KEY," +
                "username   VARCHAR(20)," +
                "password   VARCHAR(50)," +
                "first_name VARCHAR(50)," +
                "last_name  VARCHAR(50)" +
                ");";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.execute();
        preparedStatement.close();
        System.out.println("CustomerRep createTable Executed.");
    }
    public boolean authentication(String username, String password) throws SQLException{
        String sqlStatement = "SELECT username,password FROM customers WHERE username = ? AND password = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
    public String getUsernames() throws SQLException {
        StringBuilder output = new StringBuilder();
        String sqlStatement = "SELECT username FROM customers;";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            output.append(" ").append(resultSet.getString("username"));
        }
        return output.toString();
    }
    public void signUpToRep(String username, String password,String firstName,String lastName) throws SQLException {
        String sqlStatement = "INSERT INTO customers (username,password,first_name,last_name) VALUES(?,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        preparedStatement.setString(3,firstName);
        preparedStatement.setString(4,lastName);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public String viewProfile(int customerId) throws SQLException {
        String viewProfStatement = "SELECT * FROM customers WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(viewProfStatement);
        preparedStatement.setInt(1,customerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return "ID: " + resultSet.getInt("id") +
                    ", First Name: " + resultSet.getString("first_name") +
                    ", Last Name: " + resultSet.getString("last_name") +
                    ", Username: " + resultSet.getString("username");
        }
        else return "";
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    ConClass conClass = ConClass.getInstance();
    Connection connection = conClass.getConnection();
}
