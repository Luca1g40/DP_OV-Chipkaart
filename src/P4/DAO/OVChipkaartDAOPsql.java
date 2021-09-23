package P4.DAO;

import P4.Domain.Adres;
import P4.Domain.OVChipkaart;
import P4.Domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{

    private Connection connection;
    private ReizigerDAO rdao;

    public OVChipkaartDAOPsql(Connection connection){
        this.connection = connection;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }



    @Override
    public ArrayList<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            String q = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,reiziger.getId());
            ResultSet rs = pst.executeQuery();
            ArrayList<OVChipkaart> ovchips = new ArrayList<>();

            while (rs.next()) {
                int kaartnummer = rs.getInt("kaart_nummer");
                Date geldigTot = rs.getDate("geldig_tot");
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");
                ovchips.add(new OVChipkaart(kaartnummer, geldigTot, klasse, saldo, reiziger));
            }
            rs.close();
            pst.close();
            return ovchips;

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteOVChipkaart(OVChipkaart ov) {
        try {

            String q = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1, ov.getKaart_nummer());
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
    public boolean saveOVChipkaart(OVChipkaart ov) {
        try {
            String q = ("INSERT INTO ov_chipkaart" +
                    " (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id)" +
                    "VALUES (?, ?, ? , ? , ?)");

            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,ov.getKaart_nummer());
            pst.setDate(2, (Date) ov.getGeldigTot());
            pst.setInt(3, ov.getKlasse());
            pst.setDouble(4, ov.getSaldo());
            pst.setInt(5, ov.getReiziger().getId());

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
    public boolean updateOVChipkaart(OVChipkaart ov) {
        try {
            String q = "UPDATE ov_chipkaart SET " +
                    "kaart_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ?" +
                    "WHERE kaart_nummer = ?";
            PreparedStatement pst = connection.prepareStatement(q);
            pst.setInt(1,ov.getKaart_nummer());
            pst.setDate(2, (Date) ov.getGeldigTot());
            pst.setInt(3, ov.getKlasse());
            pst.setDouble(4, ov.getSaldo());
            pst.setInt(5, ov.getReiziger().getId());
            pst.setInt(6,ov.getKaart_nummer());
            pst.execute();
            pst.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
