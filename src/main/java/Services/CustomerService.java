package Services;

import Repositories.CustomerRepository;

import java.sql.SQLException;

public class CustomerService {

    private final CustomerRepository cr = new CustomerRepository();

    public CustomerService() throws SQLException {
    }

    public void createTable() throws SQLException {
        cr.createTable();
    }
    public boolean authentication(String username,String password) throws SQLException {
        return cr.authentication(username,password);
    }
    public String getUsernames() throws SQLException {
        return cr.getUsernames();
    }
    public void signUpService(String username,String password,String firstName,String lastName) throws SQLException {
        cr.signUpToRep(username,password,firstName,lastName);
    }
    public String viewProfile(int customerId) throws SQLException {
        return cr.viewProfile(customerId);
    }
    public String getUsername(){
        return cr.getUsername();
    }
    public String getFirst_name(){
        return cr.getFirst_name();
    }
    public String getLast_name(){
        return cr.getLast_name();
    }
    public int getId(){
        return cr.getId();
    }
}
