package Users;


import Repositories.CustomerRepository;
import Services.CustomerService;
import Services.CustomerToTicketService;
import Services.PromoService;
import Services.TicketService;

import java.sql.SQLException;
import java.util.ArrayList;

public class Customer extends User {

    private String first_name;
    private String last_name;

    public Customer(String username) throws SQLException {
        CustomerRepository cr = new CustomerRepository(username);
        super.id = cr.getId();
        super.username = cr.getUsername();
        this.first_name = cr.getFirst_name();
        this.last_name = cr.getLast_name();
    }

    public void entry() throws SQLException, InterruptedException {
        utils.clear();
        System.out.println("---------------------------------------");
        while (true) {
            utils.clear();
            System.out.print("""
                    Welcome to Customer Section.
                    Enter 1 to View all available tickets,
                    Enter 2 to Reserve a ticket,
                    Enter 3 to Search Through tickets,
                    Enter 4 to View reserved tickets,
                    Enter 5 to apply Promo Code,
                    Enter 6 to view profile,
                    Enter 0 to Exit.
                    ---------------------------------------
                    Option:      \040""");
            int option = utils.intReceiver();
            if(option != 0){
                TicketService ts = new TicketService();
                if(option == 1){
                    ts.viewTickets();
                }
                else if(option == 2){
                    reserveTicket(super.id);
                }
                else if(option == 3){
                    search();
                }
                else if(option == 4){
                    viewReservedTickets();
                }
                else if(option == 5){
                    applyPromo();
                }
                else if(option == 6){
                    viewProfile(super.id);
                }
            }
            else {
                System.out.println("Enter an option from 0 to 6");
                break;
            }
        }
        Thread.sleep(1000);
    }

    private void reserveTicket(int customerId) throws SQLException, InterruptedException {
        TicketService ts = new TicketService();
        ts.viewTickets();
        while(true) {
            utils.clear();
            System.out.print("Enter the ticket ID you want to Reserve or -1 to Exit: ");
            int ticketId = utils.intReceiver();
            if(ticketId != -1) {
                if (ts.ifExists(ticketId)) {
                    utils.printGreen(ts.searchById(ticketId));
                    Thread.sleep(1000);
                    while (true) {
                        int inStockTicket = ts.getQuantity(ticketId);
                        int ticketPrice = ts.getPrice(ticketId);
                        System.out.println("How many tickets do you want to reserve(In Stock: " + inStockTicket + "): ");
                        int reserveQty = utils.intReceiver();
                        if (reserveQty <= inStockTicket) {
                            CustomerToTicketService ctts = new CustomerToTicketService();
                            ctts.insert(customerId,ticketId,reserveQty,ticketPrice);
                            ts.setQuantity(ticketId, inStockTicket - reserveQty);
                            break;
                        }
                        else if(reserveQty == 0){
                            System.out.println("ok...");
                            break;
                        } else {
                            System.out.println("The number you entered is more than the ticket in stock!\n" +
                                    "Enter a less value: ");
                        }
                    }
                }
                else System.out.println("Ticket ID is Invalid.");
            }
            else break;
        }
    }

    private void search() throws SQLException, InterruptedException {
        TicketService ts = new TicketService();
        while (true){
            System.out.print("""
                    What do you want to search through tickets with:
                    1-Movie Title
                    2-Screen Time
                    3-Movie Title and Screen Time
                    0-Exit Searching
                    Option:      \040""");
            int opt = utils.intReceiver();
            utils.clear();
            if(opt >= 0 && opt <= 3){
                if(opt != 0){
                    if(opt == 1){
                        System.out.print("Movie Name: ");
                        String movieName = scanner.nextLine();
                        if(!ts.searchByMovie(movieName).equals("")){
                            System.out.print(ts.searchByMovie(movieName));
                        }
                        else System.out.println("No Ticket for this movie.");
                    }
                    else if(opt == 2){
                        int month = utils.monthReceiver() - 1;
                        int date = utils.dayReceiver(month + 1);
                        if(!ts.searchByDate(month,date).equals("")){
                            System.out.print(ts.searchByDate(month,date));
                        }
                        else System.out.println("No Ticket for this day.");
                    }
                    else {
                        int month = utils.monthReceiver() - 1;
                        int date = utils.dayReceiver(month + 1);
                        System.out.println("Movie Name: ");
                        String movieName = scanner.nextLine();
                        if(!ts.fullSearch(month,date,movieName).equals("")){
                            System.out.print(ts.fullSearch(month,date,movieName));
                        }
                        else System.out.println("No Ticket with these specifications.");
                    }
                }
                else break;
            }
            else System.out.println("Wrong Entry. Choose a number between 0 and 3");
            Thread.sleep(1000);
        }
    }

    private void viewReservedTickets() throws SQLException, InterruptedException {
        CustomerToTicketService ctts = new CustomerToTicketService();
        if(!ctts.reservedTickets(this.id).equals("")){
            utils.printGreen(ctts.reservedTickets(super.id));
            utils.printRed("Total Price: " + ctts.totalPrice(super.id));
        }
        else System.out.println("No tickets has been reserved yet.");
        Thread.sleep(2000);
    }

    private void applyPromo() throws SQLException,InterruptedException{
        PromoService ps = new PromoService();
        CustomerToTicketService ctts = new CustomerToTicketService();
        System.out.print("Please enter your Promo Code: ");
        String promoCode = scanner.nextLine();
        int promosCinemaId = ps.codesCinema(promoCode);
        double promoAmount = ps.getAmount(promoCode);
        ArrayList<Integer> promosTickets;
        promosTickets = ctts.promosTicketsIds(promosCinemaId);
        if(promosCinemaId != -1){
            if(promosTickets.size() > 0){
                for (int i = 0; i < promosTickets.size(); i++) {
                    int currentPrice = ctts.getPrice(promosTickets.get(i));
                    String promoOperation = ps.getOperation(promoCode);
                    int newPrice = calculatePromotion(currentPrice,promoAmount,promoOperation);
                    ctts.applyPromo(super.id,newPrice,currentPrice);
                    utils.printGreen("Promotion Done!");
                }
            }
            else{
                utils.printYellow("You haven't reserved ticket from the cinema that applied the promo.");
            }
        }
        else{
            utils.printRed("Promo Code does not exist.");
        }
    }

    private int calculatePromotion(int currentPrice,double amount,String operation){
        return switch (operation) {
            case "PERCENTAGE" -> (currentPrice * (100 - (int) amount)) / 100;
            case "SUBTRACT" -> currentPrice - (int) amount;
            case "CHARITYADD" -> currentPrice + (int) amount;
            default -> currentPrice;
        };
    }

    private void viewProfile(int id) throws SQLException, InterruptedException {
        CustomerService cs = new CustomerService();
        utils.printGreen(cs.viewProfile(id));
    }
}
