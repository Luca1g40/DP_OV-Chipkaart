package P3.DAO;

import P3.Domain.Reiziger;
import P3.Domain.Adres;

import java.sql.*;
import java.util.ArrayList;

public class AdresDAOPsql implements AdresDAO{

    private Connection connection;
    private ReizigerDAO rdao;

    public AdresDAOPsql(Connection connection){
        this.connection = connection;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
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

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            String q = "SELECT * FROM adres WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,reiziger.getId());
            ResultSet rs = pst.executeQuery();
            Adres adres = null;

            while (rs.next()) {
                int adres_id = rs.getInt(1);
                String postcode = rs.getString(2);
                String huisnummer = rs.getString(3);
                String straat = rs.getString(4);
                String woonplaats = rs.getString(5);
                adres = new Adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger);
            }
            pst.close();
            return adres;

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Adres> findAll() throws SQLException {
        try{
            Statement myStmt = connection.createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT * FROM adres");
            ArrayList<Adres> adressen = new ArrayList<>();

            while (rs.next()){
                int adresId = rs.getInt("adres_id");
                String postcode = rs.getString("postcode");
                String huisnummer = rs.getString("huisnummer");
                String straat = rs.getString("straat");
                String woonplaats = rs.getString("woonplaats");
                int reizigerId = rs.getInt("reiziger_id");
                adressen.add(new Adres(adresId, postcode, huisnummer, straat, woonplaats, rdao.findById(reizigerId)));
            }
        return adressen;
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }


}
