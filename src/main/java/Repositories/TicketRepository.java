package Repositories;

import org.cinemaSystem.ConClass;

import java.sql.*;
import java.util.Locale;

public class TicketRepository {
    ConClass conClass = ConClass.getInstance();
    Connection connection = conClass.getConnection();

    public TicketRepository() throws SQLException {
    }

    public void createTable() throws SQLException {
        String sqlStatement = "CREATE TABLE IF NOT EXISTS tickets(" +
                "id             SERIAL primary key," +
                "screen_time    DATE," +
                "quantity       INTEGER," +
                "movie_title    VARCHAR(30)," +
                "cinema_id      INTEGER," +
                "price          INTEGER," +
                "start_at       TIME," +
                "FOREIGN KEY (cinema_id) REFERENCES cinemas(id)" +
                ");";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
        preparedStatement.execute();
        System.out.println("TicketRep createTable Executed.");
    }
    public void insertTicket(String movieTitle, Date screen_time, int quantity, int cinema_id,int price,Time start_at) throws SQLException {
        String insertStatement = "INSERT INTO tickets (movie_title,screen_time,quantity,cinema_id,price,start_at) VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertStatement);
        preparedStatement.setString(1, movieTitle);
        preparedStatement.setDate(2, screen_time);
        preparedStatement.setInt(3, quantity);
        preparedStatement.setInt(4, cinema_id);
        preparedStatement.setInt(5,price);
        preparedStatement.setTime(6,start_at);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public void viewTickets() throws SQLException {
        String viewQuery = "SELECT cinemas.cinema_name,tickets.id,tickets.movie_title,tickets.screen_time,tickets.quantity,tickets.price,tickets.start_at FROM tickets " +
                "INNER JOIN cinemas ON cinemas.id = tickets.cinema_id" +
                " ORDER BY tickets.id;";
        PreparedStatement preparedStatement = connection.prepareStatement(viewQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println("Ticket ID: " + resultSet.getInt("id") +
                    ", Cinema Name: " + resultSet.getString("cinema_name") +
                    ", Movie Title: " + resultSet.getString("movie_title") +
                    ", Screen Time: " + resultSet.getDate("screen_time") + "-" + resultSet.getTime("start_at") +
                    ", Ticket Price: " + resultSet.getInt("price") +
                    ", In Stock: " + resultSet.getInt("quantity"));
        }
        preparedStatement.close();
    }
    public String searchById(int ticketId) throws SQLException{
        StringBuilder output = new StringBuilder();
        String ticketSelect = "SELECT cinemas.cinema_name,tickets.id,tickets.movie_title,tickets.screen_time,tickets.quantity,tickets.start_at,tickets.price " +
                " FROM tickets " +
                " INNER JOIN cinemas ON cinemas.id = tickets.cinema_id " +
                " WHERE tickets.id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(ticketSelect);
        preparedStatement.setInt(1,ticketId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            output.append("Ticket ID: ").append(resultSet.getInt("id")).append(", Cinema Name: ").append(resultSet.getString("cinema_name")).append(", Movie Title: ").append(resultSet.getString("movie_title")).append(", Screen Time: ").append(resultSet.getDate("screen_time")).append("-").append(resultSet.getTime("start_at")).append(", Ticket Price: ").append(resultSet.getInt("price")).append(", In Stock: ").append(resultSet.getInt("quantity"));
        }
        return output.toString();
    }
    public String searchByMovie(String movieName) throws SQLException {
        StringBuilder output = new StringBuilder();
        String ticketSelect = "SELECT cinemas.cinema_name,tickets.id,tickets.movie_title,tickets.screen_time,tickets.quantity,tickets.start_at,tickets.price" +
                " FROM tickets " +
                " INNER JOIN cinemas ON cinemas.id = tickets.cinema_id " +
                " WHERE UPPER(movie_title) LIKE ? ;";
        PreparedStatement preparedStatement = connection.prepareStatement(ticketSelect);
        preparedStatement.setString(1, "%" + movieName.toUpperCase(Locale.ROOT) + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            output.append("Ticket ID: ").append(resultSet.getInt("id")).append(", Movie Title: ").append(resultSet.getString("movie_title")).append(", Screen Time: ").append(resultSet.getDate("screen_time")).append("-").append(resultSet.getTime("start_at")).append(", Cinema Name: ").append(resultSet.getString("cinema_name")).append(", Ticket Price: ").append(resultSet.getInt("price")).append(", In Stock: ").append(resultSet.getInt("quantity")).append("\n");
        }
        return output.toString();
    }
    public String searchByDate(int month, int day) throws SQLException {
        StringBuilder output = new StringBuilder();
        String ticketSelect = "SELECT cinemas.cinema_name,tickets.id,tickets.movie_title,tickets.screen_time,tickets.quantity,tickets.price,tickets.start_at" +
                " FROM tickets INNER JOIN cinemas ON cinemas.id = tickets.cinema_id" +
                " WHERE screen_time = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(ticketSelect);
        preparedStatement.setDate(1, new Date(2022 - 1900, month, day));
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            output.append("Ticket ID: ").append(resultSet.getInt("id")).append(", Cinema Name: ").append(resultSet.getString("cinema_name")).append(", Movie Title: ").append(resultSet.getString("movie_title")).append(", Screen Time: ").append(resultSet.getDate("screen_time")).append("-").append(resultSet.getTime("start_at")).append(", Ticket Price: ").append(resultSet.getInt("price")).append(", In Stock: ").append(resultSet.getInt("quantity")).append("\n");
        }
        return output.toString();
    }
    public String fullSearch(int month, int day, String movieName) throws SQLException {
        StringBuilder output = new StringBuilder();
        String ticketSelect = "SELECT cinemas.cinema_name,tickets.id,tickets.movie_title,tickets.screen_time,tickets.quantity,tickets.price,tickets.start_at" +
                " FROM tickets INNER JOIN cinemas ON cinemas.id = tickets.cinema_id" +
                " WHERE screen_time = ? AND UPPER(movie_title) LIKE ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(ticketSelect);
        preparedStatement.setDate(1, new Date(2022 - 1900,month,day));
        preparedStatement.setString(2, "%" + movieName.toUpperCase(Locale.ROOT) + "%");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
                 output.append("Ticket ID: ").append(resultSet.getInt("id")).append(", Cinema Name: ").append(resultSet.getString("cinema_name")).append(", Movie Title: ").append(resultSet.getString("movie_title")).append(", Screen Time: ").append(resultSet.getDate("screen_time")).append("-").append(resultSet.getTime("start_at")).append(", Ticket Price: ").append(resultSet.getInt("price")).append(", In Stock: ").append(resultSet.getInt("quantity")).append("\n");
            }
        return output.toString();
    }

    public boolean ifExists(int ticketId) throws SQLException {
        String sqlQuery = "SELECT * FROM tickets WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.setInt(1, ticketId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }
    public int getQuantity(int ticketId) throws SQLException {
        String qtyQuery = "SELECT quantity FROM tickets WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(qtyQuery);
        preparedStatement.setInt(1, ticketId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }
    public void setQuantity(int ticketId, int quantity) throws SQLException {
        String setQtyStatement = "UPDATE tickets" +
                " SET quantity = ? WHERE id =  ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(setQtyStatement);
        preparedStatement.setInt(1, quantity);
        preparedStatement.setInt(2, ticketId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }
    public int getPrice(int ticketId) throws SQLException{
        String priceQuery = "SELECT price FROM tickets WHERE id = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(priceQuery);
        preparedStatement.setInt(1,ticketId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }
}
