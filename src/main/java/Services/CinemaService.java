package Services;

import Repositories.CinemaRepository;
import Users.Cinema;

import java.sql.SQLException;

public class CinemaService {

    private final CinemaRepository cr = new CinemaRepository();

    public CinemaService() throws SQLException {
    }

    public void createTable(String username) throws SQLException {
        CinemaRepository cr = new CinemaRepository(username);
        cr.createTable();
    }
    public boolean authentication(String username,String password) throws SQLException {
        return cr.authentication(username,password);
    }
    public String getUsernames() throws SQLException {
        return cr.getUsernames();
    }
    public void signUpService(String username,String password,String cinemaName) throws SQLException{
        cr.signUpToRep(username,password,cinemaName);
    }
    public void viewCinemas() throws SQLException{
        cr.viewCinemas();
    }
    public void viewCinemasForAdmin() throws SQLException{
        cr.viewCinemasForAdmin();
    }
    public void confirm(int id) throws SQLException{
        cr.confirm(id);
    }
    public boolean idExists(int id) throws SQLException {
        return cr.idExists(id);
    }
    public boolean isConfirmed(int id) throws SQLException {
        return cr.isConfirmed(id);
    }
    public int getId(){
        return cr.getId();
    }
    public String getUsername() throws SQLException{
        return cr.getUsernames();
    }
    public String getCinema_name(){
        return cr.getCinema_name();
    }
    public boolean isConfirmation(){
        return cr.isConfirmation();
    }
}
