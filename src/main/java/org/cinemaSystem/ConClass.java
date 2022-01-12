package org.cinemaSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConClass {

    private static ConClass conClass;
    private Connection connection;

    private ConClass() throws SQLException{
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.
                    getConnection("jdbc:postgresql://localhost:5432/cinemahw",
                            "intellij", "intellij");
        }
        catch (SQLException e){
            System.out.println("SQL Exception");
        }
        catch (ClassNotFoundException e){
            System.out.println("Class not found Exception");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static ConClass getInstance() {
        try {
            if (conClass == null) {
                conClass = new ConClass();
            } else if (conClass.getConnection().isClosed()) {
                conClass = new ConClass();
            }
            return conClass;
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return conClass;
    }

}
