package Users;

import Repositories.CinemaRepository;
import Services.CinemaService;

import java.sql.SQLException;

public class Admin extends User {

    public Admin() throws SQLException {
    }

    public void entry() throws SQLException, InterruptedException {
        while(true) {
            System.out.print("Enter 1 to enter confirmation section or 2 to Exit: ");
            String confOrExit = scanner.nextLine();
            if (confOrExit.equals("1")) {
                confirmation();
            } else if (confOrExit.equals("2")) {
                break;
            } else {
                System.out.println("Wrong entry.");
            }
        }
    }

    private void confirmation() throws SQLException, InterruptedException {
        CinemaService cs = new CinemaService();
        while (true) {
            utils.clear();
            cs.viewCinemasForAdmin();
            System.out.println("Enter the Cinema ID you want to confirm or -1 to exit: ");
            String cinemaIdString = scanner.nextLine();
            if (utils.isNumeric(cinemaIdString)) {
                int cinemaId = Integer.parseInt(cinemaIdString);
                if (cinemaId != -1) {
                    if (cs.idExists(cinemaId)) {
                        if (cs.isConfirmed(cinemaId)) {
                            utils.printYellow("Cinema is already confirmed.");
                            Thread.sleep(1000);
                        } else {
                            cs.confirm(cinemaId);
                            utils.printGreen("Cinema Confirmed");
                            Thread.sleep(1000);
                        }
                    } else {
                        utils.printRed("Cinema ID is invalid.");
                        Thread.sleep(1000);
                    }
                } else break;
            } else {
                System.out.println("Enter Cinema ID.");
                Thread.sleep(1000);
            }
        }
    }

}
