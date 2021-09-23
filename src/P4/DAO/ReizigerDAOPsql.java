package P4.DAO;

import P4.Domain.Adres;
import P4.Domain.OVChipkaart;
import P4.Domain.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReizigerDAOPsql implements ReizigerDAO {

    private Connection connection;
    private AdresDAO adao;
    private OVChipkaartDAO ovdao;

    public ReizigerDAOPsql(Connection connection){
        this.connection = connection;
    }

    public void setAdao(AdresDAO adao) {
        this.adao = adao;
    }

    public void setOvdao(OVChipkaartDAO ovdao) {
        this.ovdao = ovdao;
    }

    @Override
    public boolean saveReiziger(Reiziger reiziger) throws SQLException{
        try {
            int id = reiziger.getId();
            String naam = reiziger.getVoorletters();
            String tussenvoegsel = reiziger.getTussenvoegsel();
            String achternaam = reiziger.getAchternaam();
            Date geboortedatum = reiziger.getGeboortedatum();
            String q = ("INSERT INTO reiziger" +
                    " (voorletters, tussenvoegsel, achternaam, geboortedatum, reiziger_id)" +
                    "VALUES (?, ?, ? , ? , ?)");

            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1,naam);
            pst.setString(2, tussenvoegsel);
            pst.setString(3, achternaam);
            pst.setDate(4, geboortedatum);
            pst.setInt(5, id);
            pst.execute();
            pst.close();
            Adres adres = reiziger.getAdres();
            ArrayList<OVChipkaart> allCards = reiziger.getOVC();
            if (adres != null){

                adao.saveAdres(adres);
            }
            if (allCards.size() > 0){
                for (OVChipkaart ov : allCards){
                    ovdao.saveOVChipkaart(ov);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateReiziger(Reiziger reiziger) throws SQLException{
        try {
            int id = reiziger.getId();
            String naam = reiziger.getVoorletters();
            String tussenvoegsel = reiziger.getTussenvoegsel();
            String achternaam = reiziger.getAchternaam();
            Date geboortedatum = reiziger.getGeboortedatum();


            String q = "UPDATE reiziger SET " +
                    "voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ?, reiziger_id = ? " +
                    "WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setString(1,naam);
            pst.setString(2, tussenvoegsel);
            pst.setString(3, achternaam);
            pst.setDate(4, geboortedatum);
            pst.setInt(5, id);
            pst.setInt(6, id);
            Adres adres = reiziger.getAdres();
            ArrayList<OVChipkaart> allCards = reiziger.getOVC();
            if(adres != null){
                adao.updateAdres(adres);
            }
            if (allCards.size() > 0){
                for (OVChipkaart ov : allCards){
                    ovdao.updateOVChipkaart(ov);
                }
            }
            pst.execute();
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteReiziger(Reiziger reiziger) throws SQLException{
        try {

            String q = "DELETE FROM reiziger adres WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, reiziger.getId());
            Adres adres = adao.findByReiziger(reiziger);
            ArrayList<OVChipkaart> allCards = ovdao.findByReiziger(reiziger);
            // Adres verwijderen
            if (adres != null){
                adao.deleteAdres(adres);
            }
            // Ov verwijderen
            if (allCards.size() > 0){
                for (OVChipkaart ov : allCards){
                    ovdao.deleteOVChipkaart(ov);
                }
            }
            // Query execute DELETE FROM reiziger adres WHERE reiziger_id = ?
            pst.execute();
            pst.close();


            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Reiziger> findAll() throws SQLException{
        try{
            Statement myStmt = connection.createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT * FROM reiziger");
            ArrayList<Reiziger> reizigers = new ArrayList<>();

            while (rs.next()){
                String naam = rs.getString("voorletters");
                String achternaam = rs.getString("achternaam");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                Date geboortedatum = rs.getDate("geboortedatum");
                int reizigerid = rs.getInt("reiziger_id");
                Reiziger reiziger = new Reiziger(reizigerid, naam, achternaam, tussenvoegsel, geboortedatum);
                // Reiziger.setAdres zet in de constructor van Adres een reiziger
                reiziger.setAdres(adao.findByReiziger(reiziger));
                // FindByReiziger zet in de constructor van de OVchipkaart de reiziger.

                ovdao.findByReiziger(reiziger);

                reizigers.add(reiziger);

            }
            rs.close();
            myStmt.close();
            return reizigers;
        } catch (SQLException ex){
            ex.printStackTrace();
        } catch (Exception exc){
            exc.printStackTrace();
        }
        return null;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        try {
            String q = "SELECT * FROM reiziger adres WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            Reiziger reiziger = null;

            while (rs.next()) {
                String naam = rs.getString("voorletters");
                String achternaam = rs.getString("achternaam");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                Date geboortedatum = rs.getDate("geboortedatum");
                int reizigerid = rs.getInt("reiziger_id");
                reiziger = new Reiziger(reizigerid, naam, achternaam, tussenvoegsel, geboortedatum);
                // Reiziger.setAdres zet in de constructor van Adres een reiziger
                reiziger.setAdres(adao.findByReiziger(reiziger));
                // FindByReiziger zet in de constructor van de OVchipkaart de reiziger.
                ovdao.findByReiziger(reiziger);

            }
            pst.close();
            return reiziger;

        }catch (SQLException ex){
            ex.printStackTrace();
        } catch (Exception exc){
            exc.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Reiziger> findByGbDatum(LocalDate datum) throws SQLException {
        try{
            String q = "SELECT *  FROM reiziger WHERE geboortedatum = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setDate(1, Date.valueOf(datum));
            ResultSet rs = pst.executeQuery();
            ArrayList<Reiziger> reizigers = new ArrayList<>();

            while (rs.next()) {
                String naam = rs.getString("voorletters");
                String achternaam = rs.getString("achternaam");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                Date geboortedatum = rs.getDate("geboortedatum");
                int reizigerid = rs.getInt("reiziger_id");
                Reiziger reiziger = new Reiziger(reizigerid, naam, tussenvoegsel, achternaam, geboortedatum);
                reiziger.setAdres(adao.findByReiziger(reiziger));
                // Reiziger.setAdres zet in de constructor van Adres een reiziger
                ovdao.findByReiziger(reiziger);
                // FindByReiziger zet in de constructor van de OVchipkaart de reiziger.
                reizigers.add(reiziger);

            }

        return reizigers;
        }

        catch (SQLException ex){
            ex.printStackTrace();
        } catch (Exception exc){
            exc.printStackTrace();
        }
        return null;
    }


}
