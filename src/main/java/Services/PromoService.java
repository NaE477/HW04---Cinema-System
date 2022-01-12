package Services;

import Repositories.PromoRepository;

import java.sql.SQLException;

public class PromoService {
    private final PromoRepository pr = new PromoRepository();

    public PromoService() throws SQLException {
    }
    public void insert(String promoCode,int cinemaId,String operation,double amount) throws SQLException{
        pr.insert(promoCode,cinemaId,operation,amount);
    }
    public int codesCinema(String promoCode) throws SQLException {
        return pr.codesCinema(promoCode);
    }
    public String getOperation(String promoCode) throws SQLException{
        return pr.getOperation(promoCode);
    }
    public double getAmount(String promoCode) throws SQLException{
        return pr.getAmount(promoCode);
    }

}
