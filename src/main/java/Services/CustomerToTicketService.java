package Services;

import Repositories.CustomerToTicketRep;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerToTicketService {
    private final CustomerToTicketRep cttr = new CustomerToTicketRep();

    public CustomerToTicketService() throws SQLException {
    }

    public void createTable() throws SQLException {
        cttr.createTable();
    }
    public void insert(int customerId,int ticketId,int quantity,int price) throws SQLException {
        cttr.insert(customerId,ticketId,quantity,price);
    }
    public String reservedTickets(int customerId) throws SQLException {
        return cttr.viewReservedTickets(customerId);
    }
    public int totalPrice(int customerId) throws SQLException{
        return cttr.totalPrice(customerId);
    }
    public ArrayList<Integer> promosTicketsIds(int cinemaId) throws SQLException {
        return cttr.promosTicketsIds(cinemaId);
    }
    public int getPrice(int ticketId) throws SQLException{
        return cttr.getPrice(ticketId);
    }
    public void applyPromo(int customerId,int newAmount,int oldAmount) throws SQLException {
        cttr.applyPromo(customerId,newAmount,oldAmount);
    }
}
