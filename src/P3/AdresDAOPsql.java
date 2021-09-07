package P3;

import P2.Reiziger;
import P2.ReizigerDAO;

import java.sql.*;

public class AdresDAOPsql implements AdresDAO{

    private Connection connection;
    private ReizigerDAO rdao;

    public AdresDAOPsql(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean saveAdres(Adres adres) {
        try {
            String q = ("INSERT INTO adres" +
                    " (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id)" +
                    "VALUES (?, ?, ? , ? , ?, ?)");

            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,adres.getAdres_id());
            pst.setString(2, adres.getPostcode());
            pst.setString(3, adres.getHuisnummer());
            pst.setString(4, adres.getStraat());
            pst.setString(5, adres.getWoonplaats());
            pst.setInt(6,adres.getReiziger_id());
            pst.execute();
            pst.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAdres(Adres adres) {
        try {
            String q = "UPDATE adres SET " +
                    "adres_id = ?, postcode = ?, huisnummer = ?, straat = ?, woonplaats = ? " +
                    "WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,adres.getAdres_id());
            pst.setString(2, adres.getPostcode());
            pst.setString(3, adres.getHuisnummer());
            pst.setString(4, adres.getStraat());
            pst.setString(5, adres.getWoonplaats());
            pst.setInt(6,adres.getReiziger_id());
            pst.execute();
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAdres(Adres adres) {
        try {

            String q = "DELETE FROM adres reiziger WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,adres.getReiziger_id());
            pst.execute();
            pst.close();


            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public Adres findById(Adres adres) {
        try {
            String q = "SELECT * FROM adres JOIN reiziger ON reiziger.reiziger_id = adres.reiziger_id WHERE reiziger.reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,adres.getReiziger_id());
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

        return null;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String q = "SELECT * FROM adres JOIN reiziger ON reiziger.reiziger_id = adres.reiziger_id WHERE reiziger.reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,reiziger.getId());
            ResultSet rs = pst.executeQuery();
            rs.next();
            int adres_id = rs.getInt(1);
            String postcode = rs.getString(2);
            String huisnummer = rs.getString(3);
            String straat = rs.getString(4);
            String woonplaats = rs.getString(5);
            int reiziger_id = rs.getInt(6);
            Adres adres = new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id);
            pst.close();
            return adres;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
