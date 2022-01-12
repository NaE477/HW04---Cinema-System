package Services;

import Repositories.AdminRepository;

import java.sql.SQLException;

public class AdminService {
    private final AdminRepository ar = new AdminRepository();

    public AdminService() throws SQLException {
    }

    public void createTable() throws SQLException {
        ar.createTable();
    }
    public boolean authentication(String username,String password) throws SQLException {
        return ar.authentication(username,password);
    }
    public String getUsernames() throws SQLException {
        return ar.getUsernames();
    }
    public void signUpService(String username,String password) throws SQLException {
        ar.signUpToRep(username,password);
    }
}
