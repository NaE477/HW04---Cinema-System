package Repositories;

import org.cinemaSystem.ConClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PromoRepository {
    ConClass conClass = ConClass.getInstance();
    Connection connection = conClass.getConnection();

    public PromoRepository() throws SQLException {
    }
    public void createTable() throws SQLException{
        String createStatement = "CREATE TABLE IF NOT EXISTS promocodes(" +
                "code       VARCHAR(10)," +
                "cinema_id  INTEGER," +
                "operation  varchar," +
                "amount     DOUBLE PRECISION," +
                "FOREIGN KEY (cinema_id) REFERENCES cinemas(id)" +
                ");";
        PreparedStatement preparedStatement = connection.prepareStatement(createStatement);
        preparedStatement.execute();
        preparedStatement.close();
    }
    public void insert(String promoCode,int cinemaId,String operation,double amount) throws SQLException{
        String insertStatement = "INSERT INTO promocodes (code, cinema_id, operation, amount) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setString(1,promoCode);
        preparedStatement.setInt(2,cinemaId);
        preparedStatement.setString(3,operation);
        preparedStatement.setDouble(4,amount);
        preparedStatement.executeUpdate();
    }
    public int codesCinema(String promoCode) throws SQLException{
        String selectCode = "SELECT cinema_id FROM promocodes WHERE code = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectCode);
        preparedStatement.setString(1,promoCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt("cinema_id");
        }
        else{
            return -1;
        }
    }
    public String getOperation(String promoCode) throws SQLException {
        String getOperationQuery = "SELECT operation FROM promocodes WHERE code = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(getOperationQuery);
        preparedStatement.setString(1,promoCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString("operation");
    }
    public double getAmount(String promoCode) throws SQLException{
        String getOperationQuery = "SELECT amount FROM promocodes WHERE code = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(getOperationQuery);
        preparedStatement.setString(1,promoCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return resultSet.getDouble("amount");
        }else return 0;
    }
}
