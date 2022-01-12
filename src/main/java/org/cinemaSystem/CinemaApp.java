package org.cinemaSystem;


import Repositories.*;
import Users.Admin;
import Users.Cinema;
import Users.Customer;
import Users.User;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Scanner;

public class CinemaApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static Utilities utils;

    static {
        try {
            utils = new Utilities();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        utils.clear();

        /*AdminRepository ar = new AdminRepository();ar.createTable();
        CinemaRepository cr = new CinemaRepository();cr.createTable();
        CustomerRepository crr = new CustomerRepository();crr.createTable();
        TicketRepository tr = new TicketRepository();tr.createTable();
        PromoRepository pr = new PromoRepository();pr.createTable();
        CustomerToTicketRep cttr = new CustomerToTicketRep();cttr.createTable();*/

        while(true) {
            utils.printGreen("WELCOME TO THE CINEMA APP!");
            entry();
        }
    }
    public static void entry() throws SQLException, ClassNotFoundException, InterruptedException {
        while (true) {
            utils.clear();
            System.out.println("Enter S for Signup or L for Login:");
            String lOrS = scanner.nextLine();
            User user = null;
            if (lOrS != null) {
                utils.clear();
                if (lOrS.toUpperCase(Locale.ROOT).equals("L")) {
                    user = utils.login();
                } else if (lOrS.toUpperCase(Locale.ROOT).equals("S")) {
                    utils.signUp();
                    break;
                } else {
                    utils.clear();
                    System.out.println("Wrong entry.");
                    break;
                }
            } else {
                System.out.println("null value not accepted.");
                break;
            }

            if (user != null) {
                if (user instanceof Admin) {
                    utils.clear();
                    Admin admin = new Admin();
                    admin.entry();
                } else if (user instanceof Cinema) {
                    utils.clear();
                    Cinema cinema = new Cinema(user.getUsername());
                    cinema.entry();
                } else if (user instanceof Customer) {
                    utils.clear();
                    Customer customer = new Customer(user.getUsername());
                    customer.entry();
                }
            }
            else {
                utils.printRed("Wrong Username/Password!");

            }
        }
    }
}
