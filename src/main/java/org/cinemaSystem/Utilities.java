package org.cinemaSystem;

import Services.AdminService;
import Services.CinemaService;
import Services.CustomerService;
import Users.Admin;
import Users.Cinema;
import Users.Customer;
import Users.User;

import java.sql.*;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Utilities {

    ConClass conClass = ConClass.getInstance();
    Connection connection = conClass.getConnection();
    Scanner scanner = new Scanner(System.in);
    AdminService adminService = new AdminService();
    CinemaService cinemaService = new CinemaService();
    CustomerService customerService = new CustomerService();

    public Utilities() throws SQLException {
    }


    public User login() throws SQLException {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (adminService.authentication(username, password)) {
            return new Admin();
        } else if (cinemaService.authentication(username, password)) {
            return new Cinema(username);
        } else if (customerService.authentication(username, password)) {
            return new Customer(username);
        } else return null;
    }

    public void signUp() throws SQLException, InterruptedException {
        String username = usernameReceiver();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        String role = roleReceiver().toUpperCase(Locale.ROOT);
        switch (role) {
            case "ADMIN" -> {
                adminService.signUpService(username, password);
                System.out.println("You've been singned up successfully!");
                Thread.sleep(1000);
            }
            case "CINEMA" -> {
                String cinemaName = regexAdder(cinemaNameRegex, "Cinema Name",4);
                cinemaService.signUpService(username, password, cinemaName);
                System.out.println("You've been singned up successfully!");
                Thread.sleep(1000);
            }
            case "CUSTOMER" -> {
                System.out.print("First Name: ");
                String firstname = scanner.nextLine();
                System.out.print("Last Name: ");
                String lastname = scanner.nextLine();
                customerService.signUpService(username, password,firstname,lastname);
                System.out.println("You've been singned up successfully!");
                Thread.sleep(1000);
            }
        }
    }

    public String usernameReceiver() throws SQLException {
        while (true) {
            System.out.print("Username: ");
            String username = scanner.nextLine();
            if (checkExistenceByRep(username)) {
                System.out.println("This username Already Exists! Try another one: ");
            } else {
                return username;
            }
        }
    }

    private boolean checkExistenceByRep(String username) throws SQLException {
        boolean flag;
        String adminUsernames = adminService.getUsernames();
        String customerUsernames = customerService.getUsernames();
        String cinemaUsernames = cinemaService.getUsernames();

        if (checkExistenceByString(adminUsernames, username)) {
            flag = true;
        } else if (checkExistenceByString(customerUsernames, username)) {
            flag = true;
        } else flag = checkExistenceByString(cinemaUsernames, username);

        return flag;
    }

    public String roleReceiver() {
        System.out.print("Role(Admin,Cinema,Customer): ");
        String input = scanner.nextLine();
        if (input.toUpperCase(Locale.ROOT).equals("CINEMA") || input.toUpperCase(Locale.ROOT).equals("ADMIN") || input.toUpperCase(Locale.ROOT).equals("CUSTOMER")) {
            return input;
        } else {
            System.out.println("Wrong Role!");
            roleReceiver();
        }
        return null;
    }

    private boolean checkExistenceByString(String usernames, String username) {
        boolean flag = true;
        for (int i = 0; i < usernames.split(" ").length; i++) {
            if(usernames.split(" ")[i].equals(username)){
                flag = true;
                break;
            }
            else flag = false;
        }
        return flag;
    }


    public String regexAdder(String regex, String tag, int leastChars) {
        while (true) {
        System.out.print(tag + "(least length:" + leastChars + "): ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
            if (checkRegex(input, regex)) {
                return input;
            } else {
                System.out.println("Wrong " + tag + " Format. Enter a Correct " + tag + " Format:");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean checkRegex(String input, String regexPattern) {
        return Pattern.compile(regexPattern).matcher(input).matches();
    }

    public int intReceiver() {
        while(true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("You should enter a number here: ");
            }
        }
    }

    public Double doubleReceiver() {
        while(true) {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("You should enter a number here:");
            }
        }
    }

    public int monthReceiver(){
            while (true) {
                System.out.print("Month: ");
                int monthNum = intReceiver();
                if (monthNum < 1 || monthNum > 12) {
                    System.out.println("Enter a number between 1 and 12.");
                } else {
                    return monthNum;
                }
            }
    }
    public int dayReceiver(int month){
        while (true) {
            System.out.print("Day: ");
            int day = intReceiver();
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                if (day > 31 || day < 0) {
                    System.out.println("Enter a number between 1 and 31 for this month.");
                } else return day;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                if (day > 30 || day < 0) {
                    System.out.println("Enter a number between 1 and 30 for this month.");
                } else return day;
            } else if (month == 2) {
                if (day > 28 || day < 0) {
                    System.out.println("Enter a number between 1 and 28 for this month.");
                } else return day;
            }
        }
    }
    public int hourReceiver(){
        while (true) {
            System.out.print("Hour: ");
            int hour = intReceiver();
            if(hour > 23 || hour < 0){
                System.out.println("Enter a valid hour ");
            }
            else return hour;
        }
    }
    public int minuteReceiver(){
        while (true){
            System.out.print("Minute: ");
            int minute = intReceiver();
            if(minute > 59 || minute < 0){
                System.out.println("Enter a valid minute ");
            }
            else return minute;
        }
    }
    public double percentageReceiver(){
        while(true){
            System.out.println("Percentage: ");
            double percent = doubleReceiver();
            if(percent > 100 || percent < 0){
                System.out.print("Enter a percent between 0 to 100.");
            }
            else return percent;
        }
    }

    public boolean isNumeric(String input){
        if (input == null) {
            return false;
        }
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void printRed(String input) throws InterruptedException {
        System.out.print("------------------------------\n" + ANSI_RED + input + ANSI_RESET + "\n------------------------------\n");
        Thread.sleep(1000);
    }

    public void printGreen(String input) throws InterruptedException {
        System.out.print("------------------------------\n" + ANSI_GREEN + input + ANSI_RESET + "\n------------------------------\n");
        Thread.sleep(1000);
    }

    public void printYellow(String input) throws InterruptedException {
        System.out.print("------------------------------\n" + ANSI_YELLOW + input + ANSI_RESET + "\n------------------------------\n");
        Thread.sleep(1000);
    }

    public void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_YELLOW = "\u001B[33m";
    private final String cinemaNameRegex = "^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$";
}
