package P3;

import P2.Reiziger;
import P2.ReizigerDAO;

import java.sql.*;
import java.util.ArrayList;

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
            pst.setInt(6,adres.getReiziger().getId());
            pst.execute();
            pst.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public ArrayList<Adres> findAllAdressen() {
        try{
            String q = "SELECT * FROM adres";
            PreparedStatement ps = connection.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            ArrayList<Adres> adressen = new ArrayList<>();

            while (rs.next()){
                int adres_id = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                int reiziger_id = rs.getInt("reiziger_id");

                String rq = "SELECT * FROM reiziger WHERE reiziger_id = ?";
                PreparedStatement rps = connection.prepareStatement(rq);
                rps.setInt(1, reiziger_id);
                ResultSet rrs = rps.executeQuery();
                while (rrs.next()){
                    String naam = rrs.getString("voorletters");
                    String achternaam = rrs.getString("achternaam");
                    String tussenvoegsel = rrs.getString("tussenvoegsel");
                    Date geboortedatum = rrs.getDate("geboortedatum");
                    int reizigerid = rrs.getInt("reiziger_id");
                    Reiziger reiziger = new Reiziger(reizigerid, naam, achternaam, tussenvoegsel, geboortedatum);
                    adressen.add(new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger));
                }
                rps.close();
                rrs.close();
            }
            ps.close();
            rs.close();

            return adressen;
        }
        catch (Exception exc){
            System.out.println(exc);
        }
        return null;
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
            pst.setInt(6,adres.getReiziger().getId());
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
            pst.setInt(1,adres.getReiziger().getId());
            pst.execute();
            pst.close();


            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

//    @Override
//    public Adres findById(Adres adres) {
//        try {
//            String q = "SELECT * FROM adres JOIN reiziger ON reiziger.reiziger_id = adres.reiziger_id WHERE reiziger.reiziger_id = ?";
//            PreparedStatement pst = connection.prepareStatement(q);
//            pst.setInt(1,adres.getReiziger().getId());
//        }
//        catch (SQLException throwables) {
//            throwables.printStackTrace();
//            return null;
//        }
//
//        return null;
//    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String q = "SELECT * FROM adres WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,reiziger.getId());
            ResultSet rs = pst.executeQuery();

            rs.next();
            int adres_id = rs.getInt("adres_id");
            String postcode = rs.getString("postcode");
            String huisnummer = rs.getString("huisnummer");
            String straat = rs.getString("straat");
            String woonplaats = rs.getString("woonplaats");
            Adres adres = new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger);
            pst.close();
            return adres;

        }
        catch (SQLException ignored) {
            return null;
        }
    }
}
