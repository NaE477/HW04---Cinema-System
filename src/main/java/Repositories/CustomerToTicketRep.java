package Repositories;

import org.cinemaSystem.ConClass;

import java.sql.*;
import java.util.ArrayList;

public class CustomerToTicketRep {
    ConClass conClass = ConClass.getInstance();
    Connection connection = conClass.getConnection();

    public CustomerToTicketRep() throws SQLException {
    }

    public void createTable() throws SQLException {
        String createStatement = "CREATE TABLE IF NOT EXISTS customerToTicket(" +
                "customerId     INTEGER," +
                "ticketId   INTEGER," +
                "quantity   INTEGER," +
                "price      INTEGER," +
                "FOREIGN KEY (customerId) REFERENCES customers(id)," +
                "FOREIGN KEY (ticketId) REFERENCES tickets(id)" +
                ");";
        PreparedStatement preparedStatement = connection.prepareStatement(createStatement);
        preparedStatement.execute();
        preparedStatement.close();
        System.out.println("CustomerRep createTable Executed.");
    }
    public void insert(int customerId,int ticketId,int quantity,int price) throws SQLException {
        String insertStatement = "INSERT INTO customertoticket (customerid, ticketid, quantity, price) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setInt(1,customerId);
        preparedStatement.setInt(2,ticketId);
        preparedStatement.setInt(3,quantity);
        preparedStatement.setInt(4,price);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public String viewReservedTickets(int customerId) throws SQLException {
        StringBuilder output = new StringBuilder();
        String queryStatement = "SELECT cinemas.cinema_name,tickets.id,tickets.movie_title,tickets.screen_time,customertoticket.quantity,customertoticket.price FROM customertoticket" +
                " INNER JOIN tickets ON tickets.id = customertoticket.ticketid " +
                " INNER JOIN cinemas ON tickets.cinema_id = cinemas.id " +
                " WHERE customerid = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement);
        preparedStatement.setInt(1,customerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            output.append("Ticket ID: ").append(resultSet.getInt("id")).append(", Movie Title: ").append(resultSet.getString("movie_title")).append(", Screen Time: ").append(resultSet.getDate("screen_time")).append(", Quantity: ").append(resultSet.getInt("quantity")).append(", Price per Ticket: ").append(resultSet.getInt("price")).append(", Total Price of this Ticket: ").append(resultSet.getInt("price") * resultSet.getInt("quantity")).append(", Cinema Name: ").append(resultSet.getString("cinema_name")).append("\n");
        }
        return output.toString();
    }
    public int totalPrice(int customerId) throws SQLException{
        int totalPrice = 0;
        String totalPriceStatement = "SELECT price,quantity FROM customertoticket" +
                " WHERE customerid = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(totalPriceStatement);
        preparedStatement.setInt(1,customerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            totalPrice += resultSet.getInt("price") * resultSet.getInt("quantity");
        }
        return totalPrice;
    }
    public ArrayList<Integer> promosTicketsIds(int cinemaId) throws SQLException{
        ArrayList<Integer> output = new ArrayList<>();
        String queryStatement = "SELECT ticketid FROM customertoticket " +
                " INNER JOIN  tickets ON id = customertoticket.ticketid" +
                " INNER JOIN cinemas ON cinemas.id = tickets.cinema_id" +
                " WHERE cinemas.id = ? ;";
        PreparedStatement preparedStatement = connection.prepareStatement(queryStatement);
        preparedStatement.setInt(1,cinemaId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            output.add(resultSet.getInt("ticketid"));
        }
        return output;
    }
    public int getPrice(int ticketId) throws SQLException{
        String getPriceQuery = "SELECT price FROM customertoticket WHERE ticketid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(getPriceQuery);
        preparedStatement.setInt(1,ticketId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        else return 0;
    }
    public void applyPromo(int customerId, int newAmount,int oldAmount) throws SQLException{
        String setPriceQuery = "UPDATE customertoticket SET price = ?" +
                " WHERE customerid = ?" +
                " AND price = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(setPriceQuery);
        preparedStatement.setInt(1,newAmount);
        preparedStatement.setInt(2,customerId);
        preparedStatement.setInt(3,oldAmount);
        preparedStatement.execute();
        preparedStatement.close();
    }
}
