package Users;

import org.cinemaSystem.Utilities;

import java.sql.SQLException;
import java.util.Scanner;

public abstract class User {
    String username,password;
    int id;

    Scanner scanner = new Scanner(System.in);
    Utilities utils = new Utilities();

    protected User() throws SQLException {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
