package Services;

import Repositories.TicketRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public class TicketService {

    private final TicketRepository tr = new TicketRepository();

    public TicketService() throws SQLException {
    }

    public void createTable() throws SQLException{
        tr.createTable();
    }
    public void insertTicket(String movieTitle, Date screen_time, int quantity, int cinema_id, int price, Time start_at) throws SQLException {
        tr.insertTicket(movieTitle,screen_time,quantity,cinema_id,price,start_at);
    }
    public void viewTickets() throws SQLException {
        tr.viewTickets();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public String searchById(int ticketId) throws SQLException{
        return tr.searchById(ticketId);
    }
    public String searchByMovie(String movieName) throws SQLException {
        return tr.searchByMovie(movieName);
    }
    public String searchByDate(int month,int day) throws SQLException {
        return tr.searchByDate(month,day);
    }
    public String fullSearch(int month,int day,String movieName) throws SQLException {
        return tr.fullSearch(month,day,movieName);
    }
    public boolean ifExists(int ticketId) throws SQLException {
        return tr.ifExists(ticketId);
    }
    public int getQuantity(int ticketId) throws SQLException {
        return tr.getQuantity(ticketId);
    }
    public void setQuantity(int ticketId,int quantity) throws SQLException {
        tr.setQuantity(ticketId,quantity);
    }
    public int getPrice(int ticketId) throws SQLException{
        return tr.getPrice(ticketId);
    }
}
