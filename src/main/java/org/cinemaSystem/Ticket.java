package org.cinemaSystem;

import java.sql.Date;
import java.sql.Time;

public class Ticket {
    private final String movieName;
    private final Date date;
    private final int price;
    private final int quantity;
    private final Time start_at;

    public Ticket(String movieName,Date date,int price,Time start_at,int quantity){
     this.movieName = movieName;
     this.date = date;
     this.price = price;
     this.quantity = quantity;
     this.start_at = start_at;
    }
}
